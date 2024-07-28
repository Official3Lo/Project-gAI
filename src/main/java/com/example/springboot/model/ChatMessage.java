package com.example.springboot.model;

//import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@DataAmount
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String role;
    private String content;
}
