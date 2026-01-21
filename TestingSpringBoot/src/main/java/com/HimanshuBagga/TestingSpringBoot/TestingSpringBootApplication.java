package com.HimanshuBagga.TestingSpringBoot;

import com.HimanshuBagga.TestingSpringBoot.Service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TestingSpringBootApplication implements CommandLineRunner {
// commandLineRunner -> It is used to run some code automatically when the application starts
    private final DataService dataService;

	public static void main(String[] args) {
		SpringApplication.run(TestingSpringBootApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception{
        System.out.println("The Data is : " + dataService.getData());
    }

}

/*

METHOD-1 -> via Maven
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev/prod/*
METHOD-2 -> via Enviroment variables
SPRING_PROFILES_ACTIVE ./mvnw spring-boot:run
METHOD-3 -> via JAR files
java -Dspring.profiles.active=prod -jar target/<jar file>.jar

 */
