package bm.app.springsecurityjwtdemo.controller;

import bm.app.springsecurityjwtdemo.config.LoginCredentials;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The login endpoint is available in Spring Security, but this is my custom one.
 */
@RestController
public class LoginController {

    /**
     * The method does not need to do much. The authentication will be carried out by
     * the filter and the below is here to be for Swagger.
     */
    @PostMapping("/login")
    public void  login(@RequestBody LoginCredentials loginCredentials) {

    }
    
}
