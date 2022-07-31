package com.hoanghiep.perfume;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hoanghiep.perfume.controllers.AdminController;

@SpringBootTest
class HustperfumeServerApplicationTests {

	@Autowired
	AdminController adminController;
	//sanity check
	@Test
	void contextLoads() throws Exception{
		assertThat(adminController).isNotNull();
	}

}
