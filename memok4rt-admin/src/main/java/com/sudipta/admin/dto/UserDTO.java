package com.sudipta.admin.dto;


import java.time.LocalDateTime;

import lombok.Data;


@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String role;
    private String mobile;
    private LocalDateTime createdAt;
}
