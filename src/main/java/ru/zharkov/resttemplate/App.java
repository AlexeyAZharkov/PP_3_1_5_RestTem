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
        System.out.println(communication.getCookie());

        User userToAdd = new User(3L,"James", "Brown", (byte) 34);
        communication.saveUser(userToAdd);

        System.out.println(communication.getCookie());

        User userToEdit = new User(3L,"Thomas", "Shelby", (byte) 34);
        communication.editUser(userToEdit);

        communication.deleteUser(3L);

    }
}
