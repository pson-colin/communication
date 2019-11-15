package life.senlin.communication.service;

import life.senlin.communication.mapper.UserMapper;
import life.senlin.communication.model.User;
import life.senlin.communication.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: colin
 * @Date: 16:52 2019/11/12
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> dbUsers = userMapper.selectByExample(userExample);
        if(dbUsers.size() == 0){
            //插入新用户
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreate());
            userMapper.insertSelective(user);
        }else{
            //更新用户
            User dbuser = dbUsers.get(0);
            User updateUser = new User();
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setGmtModify(System.currentTimeMillis());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }

}
