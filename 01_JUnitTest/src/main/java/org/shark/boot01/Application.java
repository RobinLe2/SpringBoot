package org.shark.boot01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication
 * 
 * 1. Spring Boot 애플리케이션의 메인 클래스에 추가하는 핵심 어노테이션입니다.
 * 2. 3개의 어노테이션을 합쳐 놓은 메타 어노테이션입니다.
 *    1) @Configuration
 *    2) @EnableAutoConfiguration : Spring Boot의 자동 설정 기능 활성화
 *    3) @ComponentScan
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
