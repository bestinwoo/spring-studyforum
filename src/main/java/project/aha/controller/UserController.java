package project.aha.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/user")
    public String userInfo() {
        return "test";
    }

    @PostMapping("/user")
    public String join() {
        return "success";
    }

}
