package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.UserData;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserData> getAll();
    Optional<UserData> getById(Integer id);
}
