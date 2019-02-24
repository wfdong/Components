package components.controllers.loginControllers;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import components.beans.daobeans.InitialOperation;
import components.beans.loginbeans.*;
import components.controllers.AbstractController;
import components.Response;
import components.RestService.daoService.MysqlCRUDService;
import components.RestService.loginService.SimpleLoginService;

@RestController
public class SimpleLoginController extends AbstractController{
	private static final String template = "login status: %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    private MysqlCRUDService mysqlCRUDService;
    
	public SimpleLoginController() throws JAXBException, FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		if(this.initialOperations!=null&&!this.initialOperations.isEmpty()) {
			String initSqls="";
			for(InitialOperation initOper:this.initialOperations) {
				initSqls=initSqls+initOper.getContent();
			}
			if(initSqls.length()>1) {
				try {
				    mysqlCRUDService.excuteSql(initSqls);
				}catch(DataAccessException e) {
					System.out.println(e.getStackTrace());
				}
			}
		}
	}
    
	@RequestMapping({"/login","/traceLogin"})
    public Response login(@RequestParam(value="username", defaultValue="") String username, 
    		@RequestParam(value="passwd", defaultValue="") String passwd) {
    	String ID_db = mysqlCRUDService.isValidDBUser(mysqls.get("2").getContent(), username, passwd);
    	//return user status
    	if(ID_db!=null&&!ID_db.isEmpty()) {
    		return new SimpleLogin(ID_db, 0, String.format(template, "sucess!ID is:"+ID_db));
    	}else {
    		return new SimpleLogin(ID_db, 0, String.format(template, "failed!"));
    	}
    }
    
}
