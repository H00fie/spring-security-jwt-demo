package bm.app.springsecurityjwtdemo.controller;

import bm.app.springsecurityjwtdemo.config.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Annotations allow Spring to find the classes which are to become beans. It's using
 * a component scan mechanism for it.
 */
@RestController
public class HelloController {

    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/") //This path means root context of the application. So the start of it.
    public String hello() {
        return helloService.sayHello();
    }

}
