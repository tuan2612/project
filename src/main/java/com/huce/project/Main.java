package com.huce.project;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.huce.project.Service.FilesStorageService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class Main implements CommandLineRunner{
@Resource
	FilesStorageService storageService;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
  	public void run(String... args) {
    try {
      //storageService.deleteAll();
      storageService.init();
    } catch (Exception e) {
      // Handle initialization errors gracefully
      System.err.println("Error occurred during initialization: " + e.getMessage());
    }
  }
}
