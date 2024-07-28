package com.example.springboot.server;

import com.example.springboot.model.GAIData;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GAIService {
    private final GAIData dictionary;
    //private static clearChatty;

    public GAIService() {
        this.dictionary = new GAIData();
        //clearChatty = new GaiData();
    }

    //methods requested by controller
    public String processMessage(@RequestParam String question) {
        return dictionary.filter(question);
    }

    /* [GAIData temp]
    public String void clearChat(@RequestBody String clear){
        clearChatty.clearChatLogs();
    }
*/
}





/* Default [1st completion build]
package com.example.springboot.server;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GAIService {
    // Placeholder method to parse token usage from the response
    public String getTokenUsage() {
        // Replace with actual logic to fetch or calculate token usage
        int tokensUsed = fetchTokenUsage();// Your logic here to get or calculate tokens used
        return "Tokens used: " + tokensUsed;
    }
    public String getQueryUsage() {
        // Replace with actual logic to fetch or calculate query usage
        int queriesMade = fetchQueryUsage();// Your logic here to get or calculate queries made
        return "Queries made: " + queriesMade;
    }
    private int parseTokenUsageFromResponse(String response) {
        // Replace with actual logic to parse token usage from the response
        // For example, if the response is in JSON format, you might use a JSON parser
        return 0;
    }
    private int parseQueryUsageFromResponse(String response) {
        // Replace with actual logic to parse query usage from the response
        // For example, if the response is in JSON format, you might use a JSON parser
        return 0;
    }
    // Placeholder method to fetch or calculate actual token usage
    private int fetchTokenUsage() {
        // Replace this with actual logic to fetch or calculate token usage
        // For example, if your chatbot service provides this information, use that
        return 100; // Replace with actual value
    }

    // Placeholder method to fetch or calculate actual query usage
    private int fetchQueryUsage() {
        // Replace this with actual logic to fetch or calculate query usage
        // For example, if your chatbot service provides this information, use that
        return 50; // Replace with actual value
    }
    public static String processMessage(@RequestParam("question") String question) {
        if (question.contains("hello")) {
            return "\uD83D\uDDFFGAI: Haro! ";
        }
        if (question.contains("bye")) {
            return "\uD83D\uDDFFGAI: See ya! ";
        }
        return "\uD83D\uDDFFGAI: Sorry, I did not understand that.";
    }
}
 */
