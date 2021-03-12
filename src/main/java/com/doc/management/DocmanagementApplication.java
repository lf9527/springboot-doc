package com.doc.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.doc.management.dao")
@SpringBootApplication
public class DocmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocmanagementApplication.class, args);
		System.out.println("Spring Boot start Success");
	}

}
