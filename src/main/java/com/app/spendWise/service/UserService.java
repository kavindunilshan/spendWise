package com.app.spendWise.service;

import com.app.spendWise.entity.User;
import com.app.spendWise.exception.NotFoundException;
import com.app.spendWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {

        if(userRepository.findById(user.getUserId()).isPresent()) {
            throw new NotFoundException("User already exist");
        }

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String  id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User updateUser(String  id, User newUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setCountry(newUser.getCountry());
        existingUser.setCurrency(newUser.getCurrency());

        return userRepository.save(existingUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
