package com.fileUploading.fileUploading;

import com.fileUploading.fileUploading.types.StorageProperties;
import com.fileUploading.fileUploading.storage.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class FileUploadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploadingApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
