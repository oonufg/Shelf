package ru.pablo.Spring.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pablo.Domain.Entities.User;
import ru.pablo.Spring.Security.CUserDetailsService;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private CUserDetailsService userService;

    @PostMapping("/signup")
    public void registration(@RequestBody User user){
        userService.save(user);
    }



}