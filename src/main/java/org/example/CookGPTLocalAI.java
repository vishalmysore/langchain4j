package org.example;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;

public class CookGPTLocalAI {
    public static void main(String[] args) {
        ChatLanguageModel model = LocalAiChatModel.builder()
                .baseUrl("https://locaai-6mxzvcwm2a-uc.a.run.app")
                .modelName("phi-2")
                .temperature(0.9)
                .build();

        String answer = model.generate("A long time ago in a galaxy far, far away?");
        System.out.println(answer);
    }
}
