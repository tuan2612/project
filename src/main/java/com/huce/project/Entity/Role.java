package com.huce.project.entity;

public enum Role {
    ADMIN("A"),
    USER("U");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
