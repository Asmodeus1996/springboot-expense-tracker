package com.tracker.expense.web.controller;

import com.tracker.expense.service.IncomeService;
import com.tracker.expense.web.dto.IncomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public List<IncomeDto> getAllIncomes() {
        return incomeService.getAllIncomes();
    }

    @PostMapping
    public IncomeDto addIncome(@RequestBody IncomeDto incomeDto) {
        return incomeService.addIncome(incomeDto);
    }
    @PutMapping("/{id}")
public IncomeDto updateIncome(@PathVariable Long id, @RequestBody IncomeDto incomeDto) {
    return incomeService.updateIncome(id, incomeDto);
}

}
