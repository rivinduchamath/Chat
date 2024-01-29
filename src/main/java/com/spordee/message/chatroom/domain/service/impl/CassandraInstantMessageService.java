package com.spordee.message.chatroom.domain.service.impl;

import com.spordee.message.chatroom.domain.model.ChatRoom;
import com.spordee.message.chatroom.domain.repository.InstantMessageRepository;
import com.spordee.message.chatroom.domain.service.ChatRoomService;
import com.spordee.message.chatroom.domain.service.InstantMessageService;
import com.spordee.message.entity.InstantMessage;
import com.spordee.message.entity.InstantMessageKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CassandraInstantMessageService implements InstantMessageService {

	@Autowired
	private InstantMessageRepository instantMessageRepository;

	@Autowired
	private ChatRoomService chatRoomService;

	@Override
	public void appendInstantMessageToConversations(InstantMessage instantMessage) {
		if (instantMessage.isFromAdmin() || instantMessage.isPublic()) {
			ChatRoom chatRoom = chatRoomService.findById(instantMessage.getInstantMessageKey().getChatRoomId());

			chatRoom.getConnectedUsers().forEach(connectedUser -> {
				InstantMessageKey instantMessageKey = instantMessage.getInstantMessageKey();
				instantMessageKey.setUsername(connectedUser.getUsername());
				instantMessage.setInstantMessageKey(instantMessageKey);
				instantMessageRepository.save(instantMessage);
			});
		} else {
			InstantMessageKey instantMessageKey = instantMessage.getInstantMessageKey();
			instantMessageKey.setUsername(instantMessage.getFromUser());
			instantMessage.setInstantMessageKey(instantMessageKey);
			instantMessageRepository.save(instantMessage);

			instantMessageKey.setUsername(instantMessage.getToUser());
			instantMessage.setInstantMessageKey(instantMessageKey);
			instantMessageRepository.save(instantMessage);
		}
	}

	@Override
	public List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId) {
		return instantMessageRepository.findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(username, chatRoomId);
	}

	@Override
	public Slice<InstantMessage> findInstantMessagesFor(String username, String chatRoomId, Pageable pageable) {
		return instantMessageRepository.findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(username, chatRoomId,
				pageable);
	}
}
