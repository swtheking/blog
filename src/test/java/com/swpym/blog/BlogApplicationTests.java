package com.swpym.blog;

import com.swpym.blog.common.util.QRCodeUtil;
import com.swpym.blog.common.vo.QRCodeParams;
import com.swpym.blog.common.vo.QRParamsException;
import com.swpym.blog.pojo.User;
import com.swpym.blog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@EnableCaching
class BlogApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
		User sw = userService.findAccountInfoByUsername("sw");
		System.out.println(sw);
	}

	@Test
	void restHttptest(){
	}

	public static void main(String[] args) {
		QRCodeParams vo = new QRCodeParams();
		try {
			QRCodeUtil.generateQRImage(vo);
		} catch (QRParamsException e) {
			e.printStackTrace();
		}
	}


}
