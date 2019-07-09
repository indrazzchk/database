package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import model.Employee;

@RestController
@RequestMapping("/db")
public class DbController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping(value="/createtable",method=RequestMethod.GET)
	public String createTable() {
		String ret="Table could not be created !";
		try {
			jdbcTemplate.execute("CREATE TABLE employee (ID INT, NAME VARCHAR, AGE INT)");
			ret="Table employee created successfully !";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			return ret;
	}
	
	@RequestMapping(value="/droptable",method=RequestMethod.GET)
	public String dropTable() {
		String ret="Table employee already dropped !";
		try {
			jdbcTemplate.execute("DROP TABLE employee");
			ret="Table employee dropped successfully !";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			return ret;
	}
	
	@RequestMapping(value="/putdata",method=RequestMethod.GET)
	public String putData() {
		String ret="Table already has the record !";
        try {
			jdbcTemplate.update("INSERT INTO employee(ID, NAME, AGE) VALUES(1,'INDRASHIS','27'),(2,'SUMAN',29),(3,'GOVINDA',24)");
			ret="Table employee populated successfully !";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
            return ret;
	}
	
	@RequestMapping(value="/getall",method=RequestMethod.GET)
	public List<Employee> showAll() {
		return jdbcTemplate.query("select * from employee", new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
				Employee emp=new Employee();
				emp.setId(rs.getInt("ID"));
				emp.setAge(rs.getInt("AGE"));
				emp.setName(rs.getString("NAME"));
				
				return emp;
			}
		});
	}
	@RequestMapping("/get/{id}")
	public List<Employee> insertRecord(@PathVariable("id") String id) {
		return jdbcTemplate.query(("select * from employee where NAME='"+id.toUpperCase()+"'"), new RowMapper<Employee>() {
			@Override
			public Employee mapRow(ResultSet rs, int rownumber) throws SQLException {
				Employee emp=new Employee();
				emp.setId(rs.getInt("ID"));
				emp.setAge(rs.getInt("AGE"));
				emp.setName(rs.getString("NAME"));
				
				return emp;
			}
		});
	}
}
