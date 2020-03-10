package com.swpym.blog;

import com.swpym.blog.pojo.User;
import com.swpym.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootTest
@EnableCaching
class BlogApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		User sw = userService.findAccountInfoByUsername("sw");
		System.out.println(sw);
	}

}
