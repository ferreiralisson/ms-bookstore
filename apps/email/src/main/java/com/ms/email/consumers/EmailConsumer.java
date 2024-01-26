package com.ms.email.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.email.dtos.EmailDTO;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "${broker.queue.email.name}", groupId = "group_id")
    public void listenerEmailQueue(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            EmailDTO email = objectMapper.readValue(message, EmailDTO.class);
            var emailModel = new EmailModel();
            BeanUtils.copyProperties(email, emailModel);
            emailService.sendEmail(emailModel);
        } catch (JsonProcessingException e) {
            logger.info("Erro ao processar mensagem: " + e.getMessage());
        }
    }

}
