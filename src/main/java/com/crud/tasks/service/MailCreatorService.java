package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private TaskRepository taskRepository;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_goal", companyConfig.getCompanyGoal());
        context.setVariable("company_email", companyConfig.getCompanyMail());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("goodbye_message", "Thank you for created a new card");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        return templateEngine.process("mail/created-trello-card-mail.html", context );
    }

    public String buildAmountOfTasksDailyMail(String message) {

        long numberOfTasks = taskRepository.count();

        List<String> zeroTaskFunctionality = new ArrayList<>();
        zeroTaskFunctionality.add("There is no tasks in your list");

        List<String> moreThenZeroTaskFunctionality = new ArrayList<>();
        moreThenZeroTaskFunctionality.add("You can create tasks");
        moreThenZeroTaskFunctionality.add("You can edit and delete tasks");
        moreThenZeroTaskFunctionality.add("You can create card and assign it to a specific board and list");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyConfig.getCompanyName());
        context.setVariable("company_goal", companyConfig.getCompanyGoal());
        context.setVariable("company_email", companyConfig.getCompanyMail());
        context.setVariable("company_phone", companyConfig.getCompanyPhone());
        context.setVariable("goodbye_message", "The mail with information about amount of your tasks will be sand tomorrow");
        context.setVariable("preview_message", "Mail contains information about amount of tasks");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("tasks_quantity", numberOfTasks);
        context.setVariable("zero_task_functions", zeroTaskFunctionality);
        context.setVariable("more_then_zero_task_functions", moreThenZeroTaskFunctionality);
        if (numberOfTasks == 0) {
            context.setVariable("is_zero_tasks", true);
        } else {
            context.setVariable("is_zero_tasks", false);
        }
        return templateEngine.process("amount-of-task-mail", context);
    }

}
