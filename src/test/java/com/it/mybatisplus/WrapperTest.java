package com.it.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.mybatisplus.mapper.UserMapper;
import com.it.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;


@SpringBootTest
public class WrapperTest {
    
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
     */
    @Test
    void contextLoads(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age", 12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    /**
     * 查询名字Sandy
     */
    @Test
    void test2(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "Sandy");
        //selectOne查询一条记录
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    /**
     * 查询年龄在 20 ~ 30 岁之间的用户
     */
    @Test
    void test3(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 25);
        Integer count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

    /**
     * 模糊查询
     * 查询名字中不含i且email以t开头
     * likeRight t%
     */
    @Test
    void test4(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notLike("name", "i")
                .likeRight("email", "t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 子查询
     *     SELECT id,name,age,email,create_time,update_time,deleted,version
     *     FROM
     *         user
     *     WHERE
     *         deleted=0
     *         AND id IN (
     *             select
     *                 id
     *             from
     *                 user
     *             where
     *                 id <3
     *         )
     */
    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id 在子查询中查出来，这里写死了查询的语句，可以使用方法来替换这里的语句
        wrapper.inSql("id", "select id from user where id <3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    /**
     * 通过id进行排序
     */
    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
}
