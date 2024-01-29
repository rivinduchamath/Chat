package com.spordee.message.chatroom.domain.service;


import com.spordee.message.chatroom.domain.model.ChatRoom;
import com.spordee.message.chatroom.domain.model.ChatRoomUser;
import com.spordee.message.entity.InstantMessage;

import java.util.List;

public interface ChatRoomService {

	ChatRoom save(ChatRoom chatRoom);
	ChatRoom findById(String chatRoomId);
	ChatRoom join(ChatRoomUser joiningUser, ChatRoom chatRoom);
	ChatRoom leave(ChatRoomUser leavingUser, ChatRoom chatRoom);
	void sendPublicMessage(InstantMessage instantMessage);
	void sendPrivateMessage(InstantMessage instantMessage);
	List<ChatRoom> findAll();
	List<ChatRoom> findRoomForUser(String username);
}
