package com.lulinjun.hdfs.service;

import com.lulinjun.hdfs.mapper.UserMapper;
import com.lulinjun.hdfs.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper um;
    public List<MyUser> selectById(String id){
        List<MyUser> users = um.selectById(id);
        return users;
    }
    public List<MyUser> login(String id,String password){
        List<MyUser> users = um.login(id,password);
        return users;
    }
    public boolean modify(MyUser user){
        try {
            um.modify(user);
            return true;
        }catch (Exception e){
           return false;
        }
    }


    public boolean addUser(MyUser user) {
        return um.addUser(user);
    }
}
