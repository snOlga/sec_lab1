package com.example.demo.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;

@Service
public class UserConverter {

    @Autowired
    private UserRepository userRepository;

    public UserEntity fromDTO(UserDTO dto) {
        if (dto == null)
            return null;

        return userRepository.findByLogin(dto.getLogin());
    }

    public UserDTO fromEntity(UserEntity entity) {
        if (entity == null)
            return null;

        return new UserDTO(entity.getLogin(), entity.getPassword());
    }

    public UserEntity fromId(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
