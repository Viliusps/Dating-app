package com.psap.dating_app.controller;
import java.util.List;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psap.dating_app.model.Message;
import com.psap.dating_app.service.MessageService;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") long id) {
        if (messageService.existsMessage(id)) {
            return new ResponseEntity<>(messageService.getMessageById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/byChat/{id}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable("id") long id) {
        return new ResponseEntity<>(messageService.getMessagesByChatId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@Valid @RequestBody Message message) {
        return new ResponseEntity<>(messageService.createMessage(message), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") long id, @Valid @RequestBody Message message) {
        if (messageService.existsMessage(id)) {
            return new ResponseEntity<>(messageService.updateMessage(id, message), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<Message> deleteMessage(@PathVariable("id") Long id) {
        if (messageService.existsMessage(id)) {
            messageService.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}