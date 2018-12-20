package components.controllers.loginControllers;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import components.beans.loginbeans.*;
import components.Response;
import components.RestService.loginService.SimpleLoginService;

@RestController
public class SimpleLoginController {

	private static final String template = "login status: %s!";
    private final AtomicLong counter = new AtomicLong();
    private SimpleLoginService simpleLoginService;
    
    @RequestMapping({"/login","/index","/"})
    public Response login(@RequestParam(value="username", defaultValue="") String username, 
    		@RequestParam(value="passwd", defaultValue="") String passwd) {
    	//verify user
    	simpleLoginService = new SimpleLoginService();
    	String ID_db = simpleLoginService.isValidDBUser(username, passwd);
    	//return user status
    	if(ID_db!=null&&!ID_db.isEmpty()) {
    		return new SimpleLogin(ID_db, 0, String.format(template, "sucess!"));
    	}else {
    		return new SimpleLogin(ID_db, 0, String.format(template, "failed!"));
    	}
    }
    
    
}
