package com.tracker.expense.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private String userName;
    private String email;
    private String profession;
    private String name;
    private Double balance;
}
