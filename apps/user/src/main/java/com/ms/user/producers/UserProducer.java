package com.ms.user.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.user.dtos.EmailDTO;
import com.ms.user.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final Logger logger = LoggerFactory.getLogger(UserProducer.class);

    @Value("${broker.queue.email.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMessageEmail(UserModel userModel) {
        try {
            var emailDTO = new EmailDTO(
                    userModel.getUserId(),
                    userModel.getEmail(),
                    "Cadastro realizado com sucesso",
                    userModel.getName().concat(", seja bem vindo")
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonEmail = objectMapper.writeValueAsString(emailDTO);
            logger.info("Payload enviado para o topico " + topicName + ": {}", emailDTO);
            kafkaTemplate.send(topicName, jsonEmail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
