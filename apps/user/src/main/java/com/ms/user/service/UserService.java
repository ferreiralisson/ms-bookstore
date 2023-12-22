package com.ms.user.service;

import com.ms.user.dto.UserDTO;
import com.ms.user.model.UserModel;

public interface UserService {
    UserModel createUser(UserDTO userDTO);
}
