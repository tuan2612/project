package com.huce.project.convertor;

import com.huce.project.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConvertor implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getRoleName();
    }

    @Override
    public Role convertToEntityAttribute(String roleName) {
        if (roleName == null) {
            return null;
        }

        for (Role role : Role.values()) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }

        throw new IllegalArgumentException("Unknown role: " + roleName);
    }
}
