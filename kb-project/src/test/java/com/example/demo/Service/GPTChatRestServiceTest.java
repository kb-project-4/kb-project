package com.example.demo.Service;

import com.example.demo.dto.GPTResponseDto;
import com.example.demo.service.GPTChatRestService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class GPTChatRestServiceTest {
    @Autowired
    GPTChatRestService gptChatRestService;
    @Test
    void 문자열을_객체로_변환() {
        GPTResponseDto gptResponseDto = gptChatRestService.completionChat("김건에게 만원 송금");

        GPTResponseDto result = new GPTResponseDto("송금", "김건", new BigDecimal("10000"));

        assertEquals(gptResponseDto.getName(), result.getName());
        assertEquals(gptResponseDto.getAction(), result.getAction());
        assertEquals(gptResponseDto.getAmount(), result.getAmount());
    }
}