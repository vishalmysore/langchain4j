package org.example;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.localai.LocalAiChatModel;

public class CookGPTLocalAI {
    public static void main(String[] args) {
        ChatLanguageModel model = LocalAiChatModel.builder()
                .baseUrl("https://localai-6mxzvcwm2a-vp.a.run.app")
                .modelName("phi-2")
                .temperature(0.9)
                .build();

        String answer = model.generate("what can i eat today?");
        System.out.println(answer);
    }
}
