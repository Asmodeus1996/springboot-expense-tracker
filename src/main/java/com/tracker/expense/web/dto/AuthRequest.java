package com.tracker.expense.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    private String userName;
    private String password;
    private String email;
    private String profession;
    private String name;
    private Double balance;

}
