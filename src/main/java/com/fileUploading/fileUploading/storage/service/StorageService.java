package com.fileUploading.fileUploading.storage.service;

import com.fileUploading.fileUploading.types.ResponseType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	boolean store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);


	Resource loadAsResource(String filename);

	void deleteAll();

}
