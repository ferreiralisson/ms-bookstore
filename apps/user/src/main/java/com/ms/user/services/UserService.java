package com.ms.user.services;

import com.ms.user.dtos.UserDTO;
import com.ms.user.models.UserModel;

public interface UserService {
    UserModel createUser(UserDTO userDTO);
}
