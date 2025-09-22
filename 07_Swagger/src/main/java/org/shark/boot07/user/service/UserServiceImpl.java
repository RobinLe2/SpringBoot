package org.shark.boot07.user.service;

import java.util.List;

import org.shark.boot07.common.dto.PageDTO;
import org.shark.boot07.common.util.PageUtil;
import org.shark.boot07.user.dto.UserDTO;
import org.shark.boot07.user.exception.UserNotFoundException;
import org.shark.boot07.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserMapper userMapper;
  private final PageUtil pageUtil;
  
  @Transactional(readOnly = true)
  @Override
  public List<UserDTO> getUserList(PageDTO dto, String sort) {
    long totalItem = userMapper.selectUserCount();
    dto.setTotalItem(totalItem);
    pageUtil.calculatePaging(dto);  // <<< PageDTO의 모든 필드 값이 채워지는 순간입니다.
    return userMapper.selectUserList(dto.getOffset(), dto.getSize(), sort);
  }

  @Transactional(readOnly = true)
  @Override
  public UserDTO getUserById(Long uid) {
    UserDTO foundUser = userMapper.selectUserById(uid);
    if (foundUser == null) {
      throw new UserNotFoundException(1, "회원 ID (" + uid + ") 조회 실패");
    } else {
      return foundUser;
    }
  }

  @Override
  public UserDTO createUser(UserDTO user) {
    userMapper.insertUser(user);
    user.setCreatedAt(userMapper.selectUserById(user.getUid()).getCreatedAt());
    log.info("created user : {}", user);
    return user;
  }

  @Override
  public UserDTO updateUser(UserDTO user, Long uid) {
    user.setUid(uid);
    int updatedCount = userMapper.updateUser(user);
    if (updatedCount == 0) {
      throw new UserNotFoundException(2, "회원 ID (" + uid + ") 조회 실패");
    }
    return userMapper.selectUserById(uid);
  }

  @Override
  public void deleteUser(Long uid) {
    int deletedCount = userMapper.deleteUser(uid);
    if (deletedCount == 0) {
      throw new UserNotFoundException(3, "회원 ID (" + uid + ") 조회 실패");
    }
  }

}
