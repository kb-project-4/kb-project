package com.example.demo.service;

import com.example.demo.dto.CompletionChatResponse;
import com.example.demo.dto.GPTCompletionChatRequest;
import com.example.demo.dto.GPTResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GPTChatRestService {

  private final OpenAiService openAiService;

  public GPTResponseDto completionChat(String content) {
    List<ChatMessage> messages = new ArrayList<>();
    messages.add(new ChatMessage("system", "너는 문자열을 분석해서 json타입으로 변환하는 데이터 분석 프로그램이야. json의 key 값은 'action', 'name', 'amount' 가 있고 'action'의 value는 ['송금', '조회'] 중 한 가지야. 오타가 존재할 수 있으니 유사한 단어로 이해해 줘. 'action'이 '조회' 일 때 'name', 'amount' 는 null을 값으로 하면 돼. 예를 들어 '김건에게 만원 송금해줘' 라는 입력을 받으면 { \"action\" : \"송금\", \"name\" : \"김건\", \"amount\" : 10000} 이렇게 응답하면 돼"));
    messages.add(new ChatMessage("system", "만약 예 또는 아니오가 나온다면 { \"action\" : \"예\", \"name\" : \"\", \"amount\" : } 이런식으로 대답하면 돼"));
    messages.add(new ChatMessage("user", content));
    GPTCompletionChatRequest gptCompletionChatRequest = new GPTCompletionChatRequest("gpt-3.5-turbo", messages, 100);
    ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
            GPTCompletionChatRequest.of(gptCompletionChatRequest));

    CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);
    String json = response.getMessages().get(0).getMessage();
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      GPTResponseDto gptResponseDto = objectMapper.readValue(json, GPTResponseDto.class);
      return gptResponseDto;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
