package com.tracker.expense.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailBuilder {

    private  final TemplateEngine templateEngine;

    public String build(String msg){

        Context context = new Context();
        context.setVariable("message", msg);
        return templateEngine.process("mail" , context);

    }

}
