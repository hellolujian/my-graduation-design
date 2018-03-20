package cn.edu.ujs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.edu.ujs.mapper")
public class ResourceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagementApplication.class, args);
	}
}
