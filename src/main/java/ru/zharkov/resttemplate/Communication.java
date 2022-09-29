package ru.zharkov.resttemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.zharkov.resttemplate.model.User;

import java.util.List;

@Component
public class Communication {
    private final String URL = "http://94.198.50.185:7081/api/users";

    private RestTemplate restTemplate;

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null
                        , new ParameterizedTypeReference<List<User>>() {});
        List<User> allUsers = responseEntity.getBody();

        return allUsers;
    }

    /*
    Заголовок Set-cookie используется http сервером для отправки браузеру
    пользователя данных cookie, а заголовок Cookie используется браузером,
    чтобы позже вернуть полученные ранее данные cookie http серверу,
    чтобы сервер "узнал" клиента по данным cookie.
     */

    public User getUser(Long id) {
        User user = restTemplate.getForObject(URL + "/" + id, User.class);
        return user;
    }

    public void saveUser (User user) {
        Long id;
        if (user.getId() == null) {
            id = 0L;
        } else {
            id = user.getId();
        }

        if (id == 0) {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, user, String.class);
            System.out.println(responseEntity.getBody());
        } else {
            restTemplate.put(URL, user);
        }

    }

    public void deleteUser(Long id) {

    }
}
