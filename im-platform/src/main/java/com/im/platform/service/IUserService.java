package com.im.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.im.platform.entity.User;
import com.im.platform.dto.LoginDTO;
import com.im.platform.dto.RegisterDTO;
import com.im.platform.vo.LoginVO;
import com.im.platform.vo.UserVO;

import java.util.List;


public interface IUserService extends IService<User> {

    LoginVO login(LoginDTO dto);

    LoginVO refreshToken(String refreshToken);

    void register(RegisterDTO dto);

    User findUserByName(String username);

    void update(UserVO vo);

    List<UserVO> findUserByNickName(String nickname);

    List<Long> checkOnline(String userIds);

}
