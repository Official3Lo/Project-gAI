package com.example.springboot.model;

import com.example.springboot.model.ChatMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ChatCompletionRequest {

    private String model;
    private List<ChatMessage> messages;
    private int max_tokens=50;

    //constructor
    public ChatCompletionRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<ChatMessage>();
        this.messages.add(new ChatMessage("user", prompt));
    }
}
