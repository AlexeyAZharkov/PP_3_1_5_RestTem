package ru.zharkov.resttemplate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.zharkov.resttemplate.configuration.RestTemplateConfig;
import ru.zharkov.resttemplate.model.User;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(RestTemplateConfig.class);

        Communication communication = context.getBean("communication", Communication.class);
        List<User> allUsers =  communication.getAllUsers();
        System.out.println(allUsers);

    }
}
