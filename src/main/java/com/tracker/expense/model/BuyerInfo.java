package com.tracker.expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerInfo {

    private String userName;
    private Integer bookId;
    private double cost;
   

}
