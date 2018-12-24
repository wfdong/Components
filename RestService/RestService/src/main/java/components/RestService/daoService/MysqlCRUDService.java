package components.RestService.daoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import components.beans.daobeans.Operation;

@EnableJpaRepositories("components.RestService.daoService")
@EntityScan("components")
@Repository
public class MysqlCRUDService implements CommandLineRunner{
	@Autowired
    DataSource dataSource;
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void excuteSql(String sql) throws DataAccessException{
		    jdbcTemplate.execute(sql);
	}
	
	public String isValidDBUser(String Sql, String username, String passwd) {
		System.out.println("***********************"+Sql+", username="+username+", passwd="+passwd);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(Sql, new Object[] {username, passwd});
		String ID = (String) results.get(0).get("ID");
		//System.out.println(results);
		return ID;
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Our DataSource is = " + dataSource);
		System.out.println("Our jdbcTemplate is = " + jdbcTemplate);
	}


}
