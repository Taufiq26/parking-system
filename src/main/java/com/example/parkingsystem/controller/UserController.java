package com.example.parkingsystem.controller;

import com.example.parkingsystem.model.User;
import com.example.parkingsystem.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/login")
	public String login() {
        return "login";
	}

    @PostMapping("login/process") // Map ONLY POST Requests
    public RedirectView loginProcess (
        @RequestParam String username,
        @RequestParam String password
    ) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User u = userRepository.doLogin(username, password);

        if (u == null)
            return new RedirectView("/login");
        else
            return new RedirectView("/dashboard");
    }
}
