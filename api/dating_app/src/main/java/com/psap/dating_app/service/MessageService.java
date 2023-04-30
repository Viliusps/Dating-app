package com.psap.dating_app.service;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.psap.dating_app.model.Message;
import com.psap.dating_app.repository.MessageRepository;

@AllArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAllByOrderByIdAsc();
    }

    public List<Message> getMessagesByChatId(long id) {
        return messageRepository.findByChatId(id);
    }

    public Message getMessageById(long id) {
        return messageRepository.findById(id).get();
    }

    public Message createMessage(Message message) {
        Message newMessage = new Message();
        newMessage.setId(message.getId());
        newMessage.setContent(message.getContent());
        newMessage.setDate(message.getDate());
        newMessage.setSender(message.getSender());
        newMessage.setChat(message.getChat());
        return messageRepository.save(newMessage);
    }

    public boolean existsMessage(long id) {
        return messageRepository.existsById(id);
    }

    public Message updateMessage(Long id, Message message) {
        Message messageFromDb = messageRepository.findById(id).get();
        messageFromDb.setContent(message.getContent());
        messageFromDb.setDate(message.getDate());
        messageFromDb.setChat(message.getChat());
        messageFromDb.setSender(message.getSender());
        return messageRepository.save(messageFromDb);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

}
