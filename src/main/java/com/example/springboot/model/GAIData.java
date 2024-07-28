package com.example.springboot.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.*; // for precaution
//import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class GAIData {
    // GAI's Data
    private final Map<String, Set<String>> wordCategories;
    private static List<String> chatLogs;
    private final Random random;

    //constructor
    public GAIData() {
        wordCategories = new HashMap<>();
        chatLogs = new ArrayList<>();
        random= new Random();
        addWordsToCategory("Webscrape",
                "webscrape", "webscrapping", "web scrape", "scrape web");
        addWordsToCategory("Question",
                "what","when","where","why", "how", "was","can","?");
        addWordsToCategory("Greetings",
                "hello", "hi", "hey","haro","hola","gutentag");
        // Add more categories and words as needed
        addWordsToCategory("Farewell",
                "bye", "see ya", "adios", "sayanara", "have a good day","goodnight");
    }

    //Functions
    private void addWordsToCategory(String category, String... words) {
        Set<String> wordSet = new HashSet<>();
        for (String word : words) {
            wordSet.add(word);
        }
        wordCategories.put(category, wordSet);
    }

    public String filter(String question) {
        chatLogs.add(question);
        for (Map.Entry<String, Set<String>> entry : wordCategories.entrySet()) {
            String category = entry.getKey();
            Set<String> words = entry.getValue();

            for (String word : words) {
                if (question.contains(word)) {
                    return executeCodeForCategory(category);
                }
            }
        }
        return "Error processing message.";
    }

    public static void clearChatLogs() {
        chatLogs.clear();
    }

    public String getWebScrapeResults() {
        try {
            // Specify the URL to scrape
            String url = "https://www.fresnocitycollege.edu/";

            // Fetch and parse the HTML document from the URL
            Document document = Jsoup.connect(url).get();

            // Extract all the links and their titles from the document
            Elements links = document.select("a[href]");
            StringBuilder result = new StringBuilder();
            for (Element link : links) {
                // Extract the link and the title
                String linkHref = link.attr("abs:href");
                String linkText = link.text();
                result.append("Link: ").append(linkHref).append(", Text: ").append(linkText).append("\n");
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during web scraping.";
        }
    }

    //Core Chat Function for GAI
    private String executeCodeForCategory(String category) {
        if ("Greetings".equals(category)) {
            // Get a random word from the "Greetings" category
            Set<String> farewellWords = wordCategories.get(category);
            if (farewellWords != null && !farewellWords.isEmpty()) {
                List<String> greetingsList = new ArrayList<>(farewellWords);
                int randomIndex = random.nextInt(greetingsList.size());
                return "\uD83D\uDDFFGAI: " + greetingsList.get(randomIndex) + "!";
            }
        }
        if("Farewell".equals(category)){
            Set<String> greetingsWords = wordCategories.get(category);
            if (greetingsWords != null && !greetingsWords.isEmpty()) {
                List<String> greetingsList = new ArrayList<>(greetingsWords);
                int randomIndex = random.nextInt(greetingsList.size());
                return "\uD83D\uDDFFGAI: " + greetingsList.get(randomIndex) + "!";
            }
        }
        if("Question".equals(category)){
            return "\uD83D\uDDFFGai: im not very smart, sorry. " +
                    "I wish knew the answer to your question, but my creator gave me only a specific set of instructions to follow.";
        }
        if("Webscrape".equals(category)){
            return getWebScrapeResults();
        }
        return "Error processing message.";
    }

    /*For Data of OpenAI Implementation
    @Getter
    @Setter
    private int token;
    @Getter
    @Setter
    private int query;
    */
}

