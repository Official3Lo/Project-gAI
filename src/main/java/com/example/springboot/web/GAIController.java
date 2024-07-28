package com.example.springboot.web;

import com.example.springboot.model.GAIData;
import com.example.springboot.model.ChatCompletionRequest;
import com.example.springboot.model.ChatCompletionResponse;
import com.example.springboot.server.GAIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

@RestController
public class GAIController {
    @Autowired
    private final GAIService gaiService;

    //openai rest temp
    @Autowired
    RestTemplate restTemplate;

    //constructor for gaiservice
    @Autowired
    public GAIController(GAIService gaiService) {
        this.gaiService = gaiService;
    }

    //Web Mappings
    @GetMapping("/")
    public ResponseEntity<String> index(Model model) throws IOException {
        // Load the content of the static/index.html file
        Resource resource = new ClassPathResource("static/index.html");
        if (resource.exists()) {
            byte[] contentBytes = Files.readAllBytes(resource.getFile().toPath());
            String content = new String(contentBytes, StandardCharsets.UTF_8);
            // Set the Content-Type header to indicate HTML content
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(content);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<String> askAQuestion(@RequestBody Map<String, String> requestBody, Model model) {
        String question = requestBody.get("question");
        // Use the GAIService to process the question
        String response = gaiService.processMessage(question);

        // + dynamic att to model for use in HTML file (OpenAI Imp Req)
        //model.addAttribute("tokenUsage", gaiService.getTokenUsage());
        //model.addAttribute("queryUsage", gaiService.getQueryUsage());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/hitopenai")
        public String getOpenAiResponse(@RequestBody String prompt)
        {
            ChatCompletionRequest chatCompletionRequest
                    = new ChatCompletionRequest("gpt-3.5-turbo",prompt);
            ChatCompletionResponse response
                    = restTemplate.postForObject("https://api.openai.com/v1/chat/completions", chatCompletionRequest,
                                                        ChatCompletionResponse.class);
            return response.getChoices().get(0).getMessage().getContent();
        }

    @DeleteMapping("/clearchat")
    public ResponseEntity<String> clearChatlogs(@RequestParam String clear) {
        if ("true".equalsIgnoreCase(clear)) {
            GAIData.clearChatLogs(); //refer to GAIData...
            return ResponseEntity.ok("Chat logs cleared");
        } else {
            return ResponseEntity.badRequest().body("Invalid parameter value");
        }
    }


}







/* Default [1st build]
package com.example.springboot.web;

import com.example.springboot.server.GAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@CrossOrigin(origins = "*")
@RestController
public class GAIController {

    private final GAIService gaiService;
    @Autowired
    public GAIController(GAIService gaiService) {
        this.gaiService = gaiService;
    }

    @GetMapping("/")
    public ResponseEntity<String> index(Model model) throws IOException {
        // Load the content of the static/index.html file
        Resource resource = new ClassPathResource("static/index.html");
        if (resource.exists()) {
            byte[] contentBytes = Files.readAllBytes(resource.getFile().toPath());
            String content = new String(contentBytes, StandardCharsets.UTF_8);
            model.addAttribute("tokenUsage", gaiService.getTokenUsage());
            model.addAttribute("queryUsage", gaiService.getQueryUsage());

            // Set the Content-Type header to indicate HTML content
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(content);
        } else {
            // ERROR 404
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/ask")
    public String askAQuestion(@RequestParam("question") String question, Model model) {
        // Use the GAIService to process the question
        String response = gaiService.processMessage(question);

        // Add dynamic attributes to the model for use in the HTML file
        model.addAttribute("tokenUsage", gaiService.getTokenUsage());
        model.addAttribute("queryUsage", gaiService.getQueryUsage());

        return response;
    }
}
 */