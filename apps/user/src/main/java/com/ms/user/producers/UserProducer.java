package com.ms.user.producers;

import com.ms.user.dtos.EmailDTO;
import com.ms.user.models.UserModel;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final AmqpTemplate amqpTemplatea;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public UserProducer(AmqpTemplate amqpTemplatea) {
        this.amqpTemplatea = amqpTemplatea;
    }

    public void publishMessageEmail(UserModel userModel){
        var emailDTO = new EmailDTO(
                userModel.getUserId(),
                userModel.getEmail(),
                "Cadastro realizado com sucesso",
                userModel.getName().concat(", seja bem vindo")
        );

        amqpTemplatea.convertAndSend("", routingKey, emailDTO);
    }
}
