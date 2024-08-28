package com.example.mywebapp;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.Socket;

@RestController
public class HelloController {
    public static final String NETWORK_SECURITY_NWIEB_SECURE_PASSWORD_ATTR = "nwiebSecureUserPassword";

    String UPPERCASE = "bad password";

    String ip = "192.168.12.42"; // Noncompliant
    Socket socket = new Socket(ip, 6667);

    public static final String OTHER_PWD = "also hard coded";

    public HelloController() throws IOException {
        // Do nothing because of X and Y.
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam String name) {
        try {
            return "Hello " + name;
        } catch (Exception t) {
            t.printStackTrace();
        }
        return "something else";
    }
}