package com.tracker.expense.service;

import com.tracker.expense.web.dto.IncomeDto;
import com.tracker.expense.model.Income;
import com.tracker.expense.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public List<IncomeDto> getAllIncomes() {
        return incomeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public IncomeDto addIncome(IncomeDto incomeDto) {
        Income income = new Income();
        income.setSource(incomeDto.getSource());
        income.setAmount(incomeDto.getAmount());
        income.setDate(incomeDto.getDate());

        Income savedIncome = incomeRepository.save(income);
        return convertToDto(savedIncome);
    }
    public IncomeDto updateIncome(Long id, IncomeDto incomeDto) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Income not found"));
        income.setSource(incomeDto.getSource());
        income.setAmount(incomeDto.getAmount());
        income.setDate(incomeDto.getDate());
    
        Income updatedIncome = incomeRepository.save(income);
        return convertToDto(updatedIncome);
    }
    

    private IncomeDto convertToDto(Income income) {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setId(income.getId());
        incomeDto.setSource(income.getSource());
        incomeDto.setAmount(income.getAmount());
        incomeDto.setDate(income.getDate());
        return incomeDto;
    }
}
