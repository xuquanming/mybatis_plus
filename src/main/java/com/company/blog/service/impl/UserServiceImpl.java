package com.company.blog.service.impl;

import com.company.blog.entity.User;
import com.company.blog.mapper.UserMapper;
import com.company.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ming
 * @since 2020-11-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
