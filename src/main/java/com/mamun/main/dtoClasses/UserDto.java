package com.mamun.main.dtoClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
}
