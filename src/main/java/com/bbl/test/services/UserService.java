package com.bbl.test.services;

import com.bbl.test.dtos.UserDTO;
import com.bbl.test.models.User;
import com.bbl.test.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public UserDTO getUserById(Long id) {
        User foundUser = userRepository.findUserById(id);

        // can use mapping
        UserDTO foundUserDTO = new UserDTO();
        foundUserDTO.setName(foundUser.getName());
        foundUserDTO.setUsername(foundUser.getUsername());
        foundUserDTO.setPhone(foundUser.getPhone());
        foundUserDTO.setEmail(foundUser.getEmail());
        foundUserDTO.setWebsite(foundUser.getWebsite());

        return foundUserDTO;
    }

    private Boolean validateUserDTO(UserDTO userDTO) {
        return !(userDTO.getName().isBlank() || userDTO.getUsername().isBlank() || userDTO.getEmail().isBlank());
    }

    public void createUser(UserDTO userDTO) {
        if(!validateUserDTO(userDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some of the following fields are not found: name, username or email.");
        }
        userRepository.createUser(userDTO.getName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getWebsite());
    }

    public void updateUser(Long id, UserDTO userDTO) {
        if(!validateUserDTO(userDTO)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some of the following fields are not found: name, username or email.");
        }
        userRepository.updateUser(id, userDTO.getName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getWebsite());

    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
