package com.spordee.message.chatroom.domain.repository;

import com.spordee.message.entity.InstantMessage;
import com.spordee.message.entity.InstantMessageKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.util.List;
public interface InstantMessageRepository extends CassandraRepository<InstantMessage, InstantMessageKey> {

	List<InstantMessage> findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(String username, String chatRoomId);
	Slice<InstantMessage> findByInstantMessageKeyUsernameAndInstantMessageKeyChatRoomId(String username, String chatRoomId, Pageable pageable);
}
