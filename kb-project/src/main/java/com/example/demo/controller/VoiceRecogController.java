package com.example.demo.controller;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class VoiceRecogController {
    public VoiceRecogController() {
    }

    @GetMapping("/voiceRecognition")
    public String voiceRecognition(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        return "voiceRecog";

    }
}
