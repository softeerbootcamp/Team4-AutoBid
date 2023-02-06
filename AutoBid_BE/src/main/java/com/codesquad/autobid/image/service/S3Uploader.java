package com.codesquad.autobid.image.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component
public class S3Uploader {

	private final AmazonS3 amazonS3;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public S3Uploader(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

	// MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
	public String upload(MultipartFile multipartFile) throws IOException, IOException {
		String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentLength(multipartFile.getInputStream().available());

		amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

		return amazonS3.getUrl(bucket, s3FileName).toString();
	}

}