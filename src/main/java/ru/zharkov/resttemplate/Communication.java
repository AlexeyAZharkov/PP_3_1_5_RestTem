package ru.zharkov.resttemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.zharkov.resttemplate.model.User;

import java.util.List;

@Component
public class Communication {
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String set_cookie;

    private RestTemplate restTemplate;

    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null
                        , new ParameterizedTypeReference<List<User>>() {});
        List<User> allUsers = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
        set_cookie = headers.getFirst(headers.SET_COOKIE).split(";")[0];
// set_cookie отсюда видимо не доживали до ПУТ метода... взял эти куки после ПОСТ метода
        return allUsers;
    }

    public String getCookie() {
        return set_cookie;
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

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity requestEntity = new HttpEntity(user, requestHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);

        // set_cookie отсюда беру для ПУТ и Делите метода
        HttpHeaders headers = responseEntity.getHeaders();
        set_cookie = headers.getFirst(headers.SET_COOKIE).split(";")[0];

        System.out.println("post = " + responseEntity.getBody());
    }

        public void editUser(User user) {

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Cookie", set_cookie);

            System.out.println(requestHeaders);

            HttpEntity requestEntity = new HttpEntity(user, requestHeaders);
            ResponseEntity responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
            System.out.println("PUT = " + responseEntity.getBody());
            }

    public void deleteUser(Long id) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", set_cookie);
        System.out.println(requestHeaders);
        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

        ResponseEntity responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, requestEntity, String.class);
        System.out.println("DELETE = " + responseEntity.getBody());

    }
}
