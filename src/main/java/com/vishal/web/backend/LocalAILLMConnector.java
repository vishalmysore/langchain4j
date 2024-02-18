package com.vishal.web.backend;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LocalAILLMConnector implements LLM{

    private String baseUrl;
    private String modelName;
    private ChatLanguageModel model;
    public LocalAILLMConnector(@Value("${llm-base-url}") String baseUrl,
                               @Value("${llm-model-name}") String modelName) {
        this.baseUrl = baseUrl;
        this.modelName = modelName;
         model = LocalAiChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .temperature(0.9)
                .build();


    }

    public ChatLanguageModel getModel() {
        return model;
    }

    public String inference(String query, String context){
        String answer = model.generate(query);
        return answer;
    }

    public String prompt(String dish) {
        PromptTemplate promptTemplate = PromptTemplate.from("give me recipe for {{it}}.");

        Prompt prompt = promptTemplate.apply(dish);
        return prompt.text();
    }
    public String promptMultiple(String dish) {
        PromptTemplate promptTemplate = PromptTemplate.from("give '{{item}}' in {{type}}.");

        Map<String, Object> variables = new HashMap<>();
        variables.put("item", "paneer");
        variables.put("type", "breakfast");

        Prompt prompt = promptTemplate.apply(variables);
        return prompt.text();
    }
   }

