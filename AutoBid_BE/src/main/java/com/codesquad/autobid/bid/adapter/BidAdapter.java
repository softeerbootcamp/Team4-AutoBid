package com.codesquad.autobid.bid.adapter;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import org.springframework.core.serializer.Serializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.codesquad.autobid.bid.request.BidRegisterRequest;

@Service
public class BidAdapter {
	private final KafkaTemplate<String, Object> kafkaTemplate;

	public BidAdapter(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@KafkaListener(topics = "topic2", groupId = "topic-group")
	public void listen(BidRegisterRequest message) {
		System.out.println("asd----------");
		System.out.println(message);
		System.out.println("asd----------");
	}

	@KafkaListener(topics = "topic2", groupId = "topic-group2")
	public void listen2(BidRegisterRequest message) {
		System.out.println("asd----------");
		System.out.println(message);
		System.out.println("asd----------");
	}

	public void produce(BidRegisterRequest bidRegisterRequest) {
		this.kafkaTemplate.send("topic2", bidRegisterRequest);
	}
}
