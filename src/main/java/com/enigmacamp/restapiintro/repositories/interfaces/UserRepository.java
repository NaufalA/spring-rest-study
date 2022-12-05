package com.enigmacamp.restapiintro.repositories.interfaces;

import com.enigmacamp.restapiintro.models.UserData;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserData> findAll();
    Optional<UserData> findById(Integer id);
}
