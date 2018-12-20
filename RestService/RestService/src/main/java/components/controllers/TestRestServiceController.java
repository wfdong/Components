package components.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import components.Response;
import components.beans.Test;

@RestController
public class TestRestServiceController {

	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/test")
    public Response test(@RequestParam(value="name", defaultValue="World") String name) {
        return new Test(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
}
