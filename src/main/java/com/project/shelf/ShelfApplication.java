package com.project.shelf;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  // JPA Auditing 활성화
public class ShelfApplication {
    public static void main(String[] args) {
        // application 시작 지점에 .env 파일을 로드하도록 설정
        Dotenv dotenv = Dotenv.load();

        // 환경변수 set
        System.setProperty("IMP_KEY", dotenv.get("IMP_KEY"));
        System.setProperty("IMP_SECRET", dotenv.get("IMP_SECRET"));

        SpringApplication.run(ShelfApplication.class, args);
    }

}
