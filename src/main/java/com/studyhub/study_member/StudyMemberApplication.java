package com.studyhub.study_member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class StudyMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyMemberApplication.class, args);
	}

}
