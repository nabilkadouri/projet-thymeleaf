package com.hb.cda.thymeleafproject.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hb.cda.thymeleafproject.dto.UserRegisterDTO;
import com.hb.cda.thymeleafproject.entity.User;
import com.hb.cda.thymeleafproject.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private UserRepository repo;
    private PasswordEncoder encoder;

    public AuthController(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("dto", new UserRegisterDTO(null, null, null));
        return "register-form";
    }

    @PostMapping("/register")
    public String processRegister(@Valid UserRegisterDTO dto, BindingResult bindingResult, Model model) {
        if(repo.findByUsername(dto.getUsername()).isPresent()) {
            bindingResult.addError(new FieldError("dto", "username", "already taken"));
        }
        if(!dto.getPassword().equals(dto.getRepeatPassword())) {
            bindingResult.addError(new FieldError("dto", "repeatPassword", "does not match with password"));

        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("errors",bindingResult.getFieldErrors());
            model.addAttribute("dto", new UserRegisterDTO(null, null, null));
            return "register-form";
        }
        User user = new User(
            dto.getUsername(),
            encoder.encode(dto.getPassword()), 
            "ROLE_USER");
        repo.save(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login-form.html";
    }
}
