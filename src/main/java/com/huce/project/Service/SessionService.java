package com.huce.project.service;

import jakarta.servlet.http.HttpSession;

public class SessionService {
    public boolean CheckSession(HttpSession session, String name) {
        String username = (String) session.getAttribute("name");
        return username != null && username.equals(name);
    }
}
