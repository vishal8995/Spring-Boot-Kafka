package com.vishal.springbootkafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishal.springbootkafka.config.KafkaConfigProperties;
import com.vishal.springbootkafka.domain.CustomerVisitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootKafkaApplication {

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(final KafkaTemplate<String, String> kafkaTemplate, final KafkaConfigProperties kafkaConfigProperties) throws JsonProcessingException {
		final CustomerVisitEvent event = CustomerVisitEvent.builder()
				.custID(UUID.randomUUID().toString())
				.visitTime(LocalDateTime.now())
				.build();

		final String payload = objectMapper.writeValueAsString(event);

		return args -> {
			kafkaTemplate.send(kafkaConfigProperties.getTopic(), payload);
		};
	}

	@KafkaListener(topics = "customer-visit")
	public String listens (final String in) {
		System.out.println(in);
		return in;
	}
}
