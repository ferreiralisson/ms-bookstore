package com.ms.user.service.impl;

import com.ms.user.dto.UserDTO;
import com.ms.user.model.UserModel;
import com.ms.user.repository.UserRepository;
import com.ms.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserModel createUser(UserDTO userDTO) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);

        if (userRepository.existsByEmail(userDTO.email())) {
            throw new RuntimeException("Email já cadastrado.");
        }

        return userRepository.save(userModel);
    }
}
