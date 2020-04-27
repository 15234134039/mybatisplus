package com.it.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.mybatisplus.mapper.UserMapper;
import com.it.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusApplicationTest {

    /**
     * 继承了BaseMapper，所有的方法都来自己父类
     * 我们也可以编写自己的扩展方法！
     */
    @Autowired
    UserMapper userMapper;

    /**
     * 参数是一个 Wrapper ，条件构造器，这里我们先不用 null
     * 查询全部用户
     */
    @Test
    public void contextLoads(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 测试插入
     */
    @Test
    public void insert(){
        User user = new User();
        user.setName("张三");
        user.setAge(10);
        user.setEmail("zhangsan@qq.com");

        int result = userMapper.insert(user); // 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 发现，id会自动回填
    }

    /**
     * 测试更新
     */
    @Test
    public void update(){
        User user = new User();
        user.setId(1254309107268382722L);
        //通过条件自动拼接动态sql
        user.setName("张三三");
        user.setAge(5);

        //注意：updateById 参数是一个对象！
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

    /**
     * 测试乐观锁成功！
     */
    @Test
    public void testOptimisticLocker(){
        User user = userMapper.selectById(1L);
        user.setName("lisi");
        user.setAge(14);
        userMapper.updateById(user);
    }

    /**
     * 测试乐观锁失败！多线程下
     */
    @Test
    public void testOptimisticLocker2(){
        //线程 1
        User user = userMapper.selectById(1L);
        user.setName("lisi2");
        user.setAge(15);

        //模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("lisi3");
        user2.setAge(16);
        userMapper.updateById(user2);

        //如果没有乐观锁就会覆盖插队线程的值！
        userMapper.updateById(user);
    }

    /**
     * 测试查询
     */
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    /**
     * 批量查询
     */
    @Test
    public void testSelectByBatchId(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    /**
     * 按条件查询之一使用map操作
     */
    @Test
    public void testSelectByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三三");
        map.put("age", 5);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    /**
     * 测试分页
     */
    @Test
    public void testPage(){
        //参数一：当前页  参数二：页面大小，使用了分页插件之后，所有的分页操作也变得简单的！
        Page<User> page = new Page<User>(1,3);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    /**
     * 根据id删除
     */
    @Test
    public void testDeleteById(){
        userMapper.deleteById(1254256502882295811L);
    }

    /**
     * 批量删除
     */
    @Test
    public void testDeleteBatchIds(){
        userMapper.deleteBatchIds(Arrays.asList(1254255916212371458L, 1254256502882295809L, 1254256502882295810L));
    }

    /**
     * 根据map删除
     */
    @Test
    public void testDeleteByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三三");
        userMapper.deleteByMap(map);
    }
}
