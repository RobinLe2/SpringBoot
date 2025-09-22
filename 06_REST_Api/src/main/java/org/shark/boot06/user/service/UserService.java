package org.shark.boot06.user.service;

import java.util.List;

import org.shark.boot06.common.dto.PageDTO;
import org.shark.boot06.user.dto.UserDTO;

public interface UserService {
  List<UserDTO> getUserList(PageDTO dto, String sort);
  UserDTO getUserById(Long uid);
  UserDTO createUser(UserDTO user);
  UserDTO updateUser(UserDTO user, Long uid);
  void deleteUser(Long uid);
}
