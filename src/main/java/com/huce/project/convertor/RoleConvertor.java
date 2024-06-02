package com.huce.project.convertor;

import com.huce.project.entity.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConvertor implements AttributeConverter<Role, String> {

    @Override
public String convertToDatabaseColumn(Role role) {
    if (role == null) {
        return null;
    }
    
    // Ví dụ: Chuyển đổi giá trị role thành chuỗi và thực hiện bất kỳ biến đổi nào khác nếu cần
    return role.getRoleName().toUpperCase(); // Ví dụ: Lưu trữ tên vai trò dưới dạng chữ hoa
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
