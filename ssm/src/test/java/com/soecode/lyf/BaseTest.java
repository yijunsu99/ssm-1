package com.soecode.lyf;

import com.soecode.lyf.dto.AppointExecution;
import com.soecode.lyf.entity.Book;
import com.soecode.lyf.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({ "classpath:spring/spring-dao.xml"})
public class BaseTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testList() {
        List<Book> list = bookService.getList();
        list.forEach(System.out::println);
    }

    @Test
    public void testGetById() {
        Book book = bookService.getById(1001);
        System.out.println(book);
    }

    @Test
    public void testAppoint() {
        AppointExecution appoint = bookService.appoint(1000, 2046);
        System.out.println(appoint);
    }

}
