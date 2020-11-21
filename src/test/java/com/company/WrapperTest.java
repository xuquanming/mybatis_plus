package com.company;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.company.mapper.UserMapper;
import com.company.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WrapperTest {

    @Autowired
    UserMapper userMapper;



    @Test
    public void test(){
        //查询name不为空的用户，且邮箱不为空的用户，年龄大于等于12的人
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println); //和map对比
    }

    @Test
    public void test2(){

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","Tom");
        System.out.println(userMapper.selectOne(wrapper));   //查询一个数据，出现多个结果使用list或者map
    }

    @Test
    public void test4(){
        //查询年龄在20~30之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //左和右  t%
        wrapper.notLike("name","e")
                .likeRight("email","t");
        userMapper.selectMaps(wrapper).forEach(System.out::println);    //查询结果数
    }

    @Test
    public void test5(){
        //
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id在子查询中查出来
        wrapper.inSql("id","select id from user where id<3")
                .notLike("name","e")
                .likeRight("email","t");
        userMapper.selectObjs(wrapper).forEach(System.out::println);    //查询结果数
    }

    @Test
    public void test6(){
        //
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //通过id进行排序
        wrapper.orderByDesc("id");   //desc降序，asc升序
        userMapper.selectList(wrapper).forEach(System.out::println);    //查询结果数
    }


    //模糊查询
    @Test
    public void test3(){
        //
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30);
        System.out.println(userMapper.selectCount(wrapper));   //查询结果数
    }
}
