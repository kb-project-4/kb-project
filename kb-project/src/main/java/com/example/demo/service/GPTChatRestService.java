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
 
    messages.add(new ChatMessage("system", "너는 문자열을 분석해서 json타입으로 변환하는 데이터 분석 프로그램이야. json의 key 값은 'action', 'name', 'amount' 가 있어. 'action'의 value는 ['송금', '조회', '예', '아니오'] 중 한 가지야. 오타가 존재할 수 있으니 유사한 단어로 이해해 줘. 'action'이 '조회' 또는 '예', '아니오'일 때 'name'은 공백으로 주고, 'amount' 는 0으로 하면 돼. 예를 들어 '김건에게 만원 송금해줘' 라는 입력을 받으면 { \"action\" : \"송금\", \"name\" : \"김건\", \"amount\" : 10000}  이렇게 응답하면 돼."
    		+ "만약 사용자가 예라고 하면 너는 {\"action\" : \"예\", \"name\" : \" \", \"amount\" : 0} 이렇게 답변하면 돼 "
    		+"action에 속한 value와 비슷한 맥락은 해당 action의 value로 값을 설정해줘."
    		+ "만약 'action'에 해당하지 않는 값을 입력한다면, 'action' 의 value를  'etc', name은 \" \", 'amount'는 0으로 해줘"));
     messages.add(new ChatMessage("user", content));
    GPTCompletionChatRequest gptCompletionChatRequest = new GPTCompletionChatRequest("gpt-3.5-turbo", messages, 100);
    ChatCompletionResult chatCompletion = openAiService.createChatCompletion(
            GPTCompletionChatRequest.of(gptCompletionChatRequest));

    CompletionChatResponse response = CompletionChatResponse.of(chatCompletion);
    String json = response.getMessages().get(0).getMessage();
    System.out.println(json);
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
