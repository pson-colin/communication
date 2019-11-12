package life.senlin.communication.service;

import life.senlin.communication.mapper.UserMapper;
import life.senlin.communication.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: colin
 * @Date: 16:52 2019/11/12
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser == null){
            //插入新用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //更新用户
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModify(System.currentTimeMillis());
            userMapper.update(dbUser);
        }
    }

}
