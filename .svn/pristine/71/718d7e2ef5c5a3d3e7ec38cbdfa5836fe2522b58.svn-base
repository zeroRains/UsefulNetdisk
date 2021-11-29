package com.lulinjun.hdfs.mapper;

import com.lulinjun.hdfs.model.StarFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StarFileMapper {

    @Insert("INSERT INTO star VALUES (#{uid},#{path})")
    public boolean startFile(StarFile f);

    @Select("SELECT * FROM path WHERE id = #{user}")
    public List<StarFile> searchStartFile(String user);
}
