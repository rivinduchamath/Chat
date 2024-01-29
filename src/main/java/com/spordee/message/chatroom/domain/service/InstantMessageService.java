package com.spordee.message.chatroom.domain.service;


import com.spordee.message.entity.InstantMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
public interface InstantMessageService {

	void appendInstantMessageToConversations(InstantMessage instantMessage);
	List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId);
	Slice<InstantMessage> findInstantMessagesFor(String username, String chatRoomId, Pageable pageable);
}
