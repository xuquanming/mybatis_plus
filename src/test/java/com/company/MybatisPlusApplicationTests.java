package com.company;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.mapper.UserMapper;
import com.company.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class MybatisPlusApplicationTests {

    //继承了BaseMapper，所有的方法都来自父类，我们也可以编写自己的扩展方法
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

        //参数是一个wrapper，条件构造器，这里我们先不用，传入null
        //查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    //测试插入
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("小明的学习");
        user.setAge(3);
        user.setEmail("99@qq.com");
        int result = userMapper.insert(user); //帮我们自动生成id
        System.out.println(result);
        System.out.println(user);
    }

    //测试更新
    @Test
    public void testUpdate(){
        User user = new User();
        user.setId(1L);
        user.setName("小明");
        user.setAge(22);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    //测试乐观锁成功
    @Test
    public void testOptimisticLocker(){
        //查询用户信息
        User user = userMapper.selectById(1L);
        //修改用户信息
        user.setName("mie");
        user.setEmail("dd@qq.com");
        //执行更新操作
        userMapper.updateById(user);
    }


    //测试乐观锁失败,多线程下
    @Test
    public void testOptimisticLocker2(){
        //线程1
        User user = userMapper.selectById(1L);
        //修改用户信息
        user.setName("11mie");
        user.setEmail("dd@qq.com");
        //模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        //修改用户信息
        user2.setName("22mie");
        user2.setEmail("dd@qq.com");
        userMapper.updateById(user2);
        //可以使用自旋锁来多次尝试提交
        userMapper.updateById(user);//如果没有乐观锁就会覆盖插队线程的值
    }


    //测试查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(2L);
        System.out.println(user);
    }

    //测试批量查询
    @Test
    public void testSelectByBatchId(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //条件查询之一  使用map操作
    @Test
    public void testSelectByBatchIds(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","Tom");
        map.put("age","3");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页查询
    @Test
    public void testPage(){


        //参数一：当前页
        //参数二：页面大小
        //使用了分页插件之后，所有的分页操作变的简单
        Page<User> page = new Page<>(2,5);
        userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    //测试删除
    @Test
    public void deleteById(){
        userMapper.deleteById(2L);
    }

    //通过id批量删除
    @Test
    public void deleteBatchId(){
        userMapper.deleteBatchIds(Arrays.asList(1330002742293676034L,1330002742293676035L));
    }

    //通过Map删除
    @Test
    public void deleteMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","22mie");
        userMapper.deleteByMap(map);
    }

}
