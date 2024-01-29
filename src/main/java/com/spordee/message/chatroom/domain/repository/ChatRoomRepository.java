package com.spordee.message.chatroom.domain.repository;

import com.spordee.message.chatroom.domain.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {
  List<ChatRoom> findByConnectedUsersUsername(String username);
}
