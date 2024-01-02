package com.ms.user.services.impl;

import com.ms.user.dtos.UserDTO;
import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;
import com.ms.user.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public final class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserServiceImpl(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Override
    public UserModel createUser(UserDTO userDTO) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);

//        if (userRepository.existsByEmail(userDTO.email())) {
//            throw new RuntimeException("Email já cadastrado.");
//        }

        userModel = userRepository.save(userModel);
        userProducer.publishMessageEmail(userModel);
        return userModel;
    }
}
