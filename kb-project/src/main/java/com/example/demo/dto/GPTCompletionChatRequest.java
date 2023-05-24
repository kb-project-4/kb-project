package com.example.demo.dto;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GPTCompletionChatRequest {

  private String model;

  private List<ChatMessage> messages;

  private Integer maxTokens;


  public static ChatCompletionRequest of(GPTCompletionChatRequest request) {
    return ChatCompletionRequest.builder()
        .model(request.getModel())
        .messages(convertChatMessage(request))
        .maxTokens(request.getMaxTokens())
        .build();
  }

  private static List<ChatMessage> convertChatMessage(GPTCompletionChatRequest request) {
    return request.getMessages();
  }

}
