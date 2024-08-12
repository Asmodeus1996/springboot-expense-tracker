package com.tracker.expense.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    private Long expenseId;
    private String title;
    private Instant createdDate;
    private Instant updatedDate;
    private double amount;
    private String description;
    private PersonDto person;
}
