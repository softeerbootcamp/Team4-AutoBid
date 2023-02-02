package com.codesquad.autobid.image.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codesquad.autobid.image.service.S3Uploader;

@RestController
public class ImageController {
	private final S3Uploader s3Uploader;

	public ImageController(S3Uploader s3Uploader) {
		this.s3Uploader = s3Uploader;
	}

	@PostMapping("/image")
	public String uploadImage(@RequestParam("image") MultipartFile multipartFile) {
		try {
			return s3Uploader.upload(multipartFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
