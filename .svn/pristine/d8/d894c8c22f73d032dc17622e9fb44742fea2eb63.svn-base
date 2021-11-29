package com.lulinjun.hdfs.service;

import com.lulinjun.hdfs.mapper.StarFileMapper;
import com.lulinjun.hdfs.model.StarFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StarFileService {
    @Autowired
    StarFileMapper sm;

    public boolean star(StarFile starFile){
        return sm.startFile(starFile);
    }

    public List<StarFile> getStar(String uid){
        return sm.searchStartFile(uid);
    }
}
