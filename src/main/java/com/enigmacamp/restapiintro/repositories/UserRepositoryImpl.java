package com.enigmacamp.restapiintro.repositories;

import com.enigmacamp.restapiintro.models.UserData;
import com.enigmacamp.restapiintro.repositories.interfaces.UserRepository;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Value("${service.user.url}")
    String url;
    RestTemplate restTemplate;

    public UserRepositoryImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<UserData> findAll() {
        try {
            ResponseEntity<UserData[]> resp = restTemplate.getForEntity(url, UserData[].class);
            List<UserData> users = List.of(Objects.requireNonNull(resp.getBody()));
            return users;
        } catch (RestClientException e) {
            throw e;
        }
    }

    @Override
    public Optional<UserData> findById(Integer id) {
        try {
            ResponseEntity<UserData> resp = restTemplate.getForEntity(url + "/" + id, UserData.class);
            UserData user = resp.getBody();
            if (user != null) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new NotFoundException("User Not Found");
            }
            throw new RuntimeException(e);
        } catch (RestClientException e) {
            throw e;
        }
    }
}
