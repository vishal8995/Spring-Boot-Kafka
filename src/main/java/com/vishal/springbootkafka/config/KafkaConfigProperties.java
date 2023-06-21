package com.vishal.springbootkafka.config;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "dev.kafka")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaConfigProperties {
    private String topic;
}