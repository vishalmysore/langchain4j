package com.vishal.web.backend;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

@Component
public class MasterChefConnector {
    @Autowired
    private ChromaDbConnector dbConector;
    @Autowired
    private LocalAILLMConnector localAILLMConnector;
    private ChatMemory chatMemory;
    public MasterChefConnector() {
        chatMemory = MessageWindowChatMemory.withMaxMessages(10);
    }

    public ChefHelper getChefHelper() {
        return AiServices.builder(ChefHelper.class)
                .chatLanguageModel(localAILLMConnector.getModel())
                .contentRetriever(dbConector.getContentRetriever())
                .chatMemory(chatMemory)
                .build();
    }
}
