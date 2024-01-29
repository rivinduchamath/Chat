package com.spordee.message.chatroom.api;

import com.spordee.message.chatroom.domain.model.ChatRoomUser;
import com.spordee.message.chatroom.domain.service.ChatRoomService;
import com.spordee.message.chatroom.domain.service.InstantMessageService;
import com.spordee.message.entity.InstantMessage;
import com.spordee.message.entity.InstantMessageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatRoomController {

	@Autowired
	private ChatRoomService chatRoomService;

	@Autowired
	private InstantMessageService instantMessageService;

	@SubscribeMapping("/connected.users")
	public List<ChatRoomUser> listChatRoomConnectedUsersOnSubscribe(SimpMessageHeaderAccessor headerAccessor) {
		String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
		return chatRoomService.findById(chatRoomId).getConnectedUsers();
	}

	@SubscribeMapping("/old.messages")
	public List<InstantMessage> listOldMessagesFromUserOnSubscribe(Principal principal,
																   SimpMessageHeaderAccessor headerAccessor) {
		String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
		return instantMessageService.findAllInstantMessagesFor(principal.getName(), chatRoomId);
	}

	@MessageMapping("/send.message")
	public void sendMessage(@Payload InstantMessage instantMessage, Principal principal,
			SimpMessageHeaderAccessor headerAccessor) {
		String chatRoomId = headerAccessor.getSessionAttributes().get("chatRoomId").toString();
		instantMessage.setFromUser(principal.getName());
		InstantMessageKey instantMessageKey = new InstantMessageKey();
		instantMessageKey.setChatRoomId(chatRoomId);
		instantMessage.setInstantMessageKey(instantMessageKey);

		if (instantMessage.isPublic()) {
			chatRoomService.sendPublicMessage(instantMessage);
		} else {
			chatRoomService.sendPrivateMessage(instantMessage);
		}
	}
}
