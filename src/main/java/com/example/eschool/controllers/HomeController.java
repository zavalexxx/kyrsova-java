package com.example.eschool.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {
        @GetMapping("/")
        public void home(HttpServletResponse response, Model model, @AuthenticationPrincipal OidcUser principal) throws IOException {
            if (principal != null) {
                model.addAttribute("profile", principal.getClaims());
            }
            response.sendRedirect("/swagger-ui/index.html");
        }
}
