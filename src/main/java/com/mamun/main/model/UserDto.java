package com.mamun.main.model;


import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String userId;
    private String email;
    private String password;

}