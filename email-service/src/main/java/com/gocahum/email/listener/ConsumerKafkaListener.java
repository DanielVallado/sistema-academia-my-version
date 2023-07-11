package com.gocahum.email.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocahum.email.dto.EmailDto;
import com.gocahum.email.service.IEmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Log4j2
public class ConsumerKafkaListener {

    @Autowired
    private IEmailService emailService;

    @KafkaListener(topics = {"uady-topic"}, groupId = "default")
    private void listener(String message) {
        log.info(message);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);
            log.info("Mensaje recibido:");
            emailService.sendEmail(emailDto.getToUser(), emailDto.getSubject(), emailDto.getMessage());
        } catch (JsonProcessingException e) {
            log.error("Error al procesar el JSON recibido: " + message, e);
        }
    }

}
