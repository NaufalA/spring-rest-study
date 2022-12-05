package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.UserData;
import com.enigmacamp.restapiintro.repositories.interfaces.UserRepository;
import com.enigmacamp.restapiintro.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserData> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserData> getById(Integer id) {
        return userRepository.findById(id);
    }
}
