package com.huce.project.service;

import jakarta.servlet.http.HttpSession;

public class SessionService {
    public boolean CheckSession(HttpSession session, String name) {
        String username = (String) session.getAttribute("name");
        String token = (String) session.getAttribute("token");
        return username != null && username.equals(name);
    }
}
