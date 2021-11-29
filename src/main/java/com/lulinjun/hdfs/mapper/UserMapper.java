package com.lulinjun.hdfs.mapper;

import com.lulinjun.hdfs.model.MyUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id=#{id}")
    public List<MyUser> selectById(String id);

    @Select("SELECT * FROM users WHERE id=#{id} AND password=#{password}")
    public List<MyUser> login(String id, String password);

    @Update("UPDATE users SET password=#{password},username=#{username},phone=#{phone},email=#{email},info=#{info} WHERE id = #{id}")
    void modify(MyUser user);

    @Insert("INSERT INTO users VALUES (#{id},#{password},#{username},#{phone},#{email},#{info})")
    boolean addUser(MyUser user);
}
