package com.example.demo.handler;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.Service.BankAccountService;
import com.example.demo.Service.GPTChatRestService;
import com.example.demo.Service.LogService;
import com.example.demo.Service.UserService;
import com.example.demo.dto.GPTResponseDto;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.User;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	UserService useService;

	@Autowired
	BankAccountService bankAccountService;
	
	@Autowired
	LogService logService;
	
	@Autowired
    GPTChatRestService gptChatRestService; // chat gpt rest api

	
	private enum Command {
		송금, 조회,
	}

	private enum UserState {
		INITIAL, WAITING_CONFIRMATION // 초기 상태 및 확인(예/아니오) 기다리는 상태
	}

	private UserState userState = UserState.INITIAL; // 현재는 초기상
	private User user = new User(); // HttpSession에서 가져온 user정보를 담을 객체

	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("연결 시도중");
	}

	
	@Override
	// 실제로 서버와 통신하는
	// handleTextMessage
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload(); // message(Client textMessage), 사용자의 메세지
		user = (User) session.getAttributes().get("user"); // Session으로부터 유저 정보 가져옴
		
		GPTResponseDto gptResponseDto = gptChatRestService.completionChat(payload);
		
		String action = gptResponseDto.getAction();
		int amount = gptResponseDto.getAmount().intValue();
		String name = gptResponseDto.getName();
		

		if (userState == UserState.INITIAL) {
			if (action.equals("송금")) {
				session.sendMessage(new TextMessage("송금하시겠습니까?")); // Client에게 값 전송
				userState = UserState.WAITING_CONFIRMATION;
			}

			else if (action.equals("조회")) {
				List<BankAccount> bankAccountByuserId = bankAccountService.getBankAccountByuserId(user);
				Long balance = bankAccountByuserId.get(0).getAmount();
				String username = user.getUsername();
				String msg = username + "의 잔액은 " + balance.toString() +"원 입니다.";
				session.sendMessage(new TextMessage(msg));
			}

			else
				session.sendMessage(new TextMessage("다시 말씀해주세요."));
		}

		else if (userState == UserState.WAITING_CONFIRMATION) {
			if (action.equals("예")) {
				session.sendMessage(new TextMessage("송금이 완료되었습니다."));
				logService.saveLog(null, user, null);
				userState = UserState.INITIAL;
			} else if (action.equals("아니오")) {
				session.sendMessage(new TextMessage("다시 말씀해주세요."));
				userState = UserState.INITIAL;
			} else {
				session.sendMessage(new TextMessage("잘못된 입력입니다. 다시 말씀해주세요."));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 클라이언트와의 연결이 종료되었을 때 실행되는 메소드
		System.out.println("WebSocket connection closed.");
	}

}