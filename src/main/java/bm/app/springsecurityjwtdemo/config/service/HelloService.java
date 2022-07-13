package bm.app.springsecurityjwtdemo.config.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton") //That's the default setting anyway.
public class HelloService {

    public String sayHello() {
        return "Hello!";
    }

}
