package com.arvin.shiro.service;

import com.arvin.shiro.mapper.TheacherMapper;
import com.arvin.shiro.model.Theacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: TeacherService
 * @Description: TODO
 * @Author: Arvin @hand-china.com
 * @Date: 2020/11/16
 * @Since version-1.0
 **/
@Service
public class TeacherService {

    @Autowired
    private TheacherMapper theacherMapper;

    public Map<String, Object> selectByTeacherName(String userName){
        Map<String, Object> resultMap =new HashMap<>();
        Theacher teacher = theacherMapper.selectTeacherName(userName);
        if(null == teacher){
            resultMap.put("code","400");
            resultMap.put("msg","用户不存在");
        }else {
            resultMap.put("code","200");
            resultMap.put("data",teacher);
        }
        return resultMap;
    }
}
