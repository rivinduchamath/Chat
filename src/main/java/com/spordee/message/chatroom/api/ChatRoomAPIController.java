package com.spordee.message.chatroom.api;

import com.spordee.message.chatroom.domain.model.ChatRoom;
import com.spordee.message.chatroom.domain.model.ChatRoomUser;
import com.spordee.message.chatroom.domain.service.ChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomAPIController {
	private static final Logger logger = LoggerFactory.getLogger(ChatRoomAPIController.class);

	@Autowired
	private ChatRoomService chatRoomService;

	@GetMapping
	public ResponseEntity<List<ChatRoom>> getRooms(@RequestParam(required = false) String forUser) {
		try {
			List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
			if (StringUtils.isEmpty(forUser)) {
				chatRoomService.findAll().forEach(chatRooms::add);
			} else {
				chatRoomService.findRoomForUser(forUser).forEach(chatRooms::add);
			}

			if (chatRooms.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			Collections.sort(chatRooms, new Comparator<ChatRoom>() {
				public int compare(ChatRoom o1, ChatRoom o2) {
						if (o1.getUpdatedAt() == null || o2.getUpdatedAt() == null)
							return 0;
						return o2.getUpdatedAt().compareTo(o1.getUpdatedAt());
				}
			});

			return new ResponseEntity<>(chatRooms, HttpStatus.OK);
		} catch (Exception e) {
      logger.error("An exception occurred!", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ChatRoom> getRoomById(@PathVariable("id") String id) {
		ChatRoom chatRoomData = chatRoomService.findById(id);

		if (chatRoomData != null) {
			return new ResponseEntity<>(chatRoomData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ChatRoom> updateChatRoom(@PathVariable("id") String id, @RequestBody ChatRoom chatRoom) {
		ChatRoom chatRoomData = chatRoomService.findById(id);

		if (chatRoomData != null) {
			chatRoomData.setName(chatRoom.getName());
			chatRoomData.setDescription(chatRoom.getDescription());
			return new ResponseEntity<>(chatRoomService.save(chatRoomData), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// @Secured("ROLE_ADMIN")
	@PostMapping
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoom chatRoom) {
		try {
			ChatRoom _chatRoom = chatRoomService.save(chatRoom);
			return new ResponseEntity<>(_chatRoom, HttpStatus.CREATED);
		} catch (Exception e) {
      logger.error("An exception occurred!", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/participant/{chatRoomId}")
	@ResponseBody
	public ResponseEntity<ChatRoom> addUserToChatRoom(@PathVariable String chatRoomId,
			@RequestBody ChatRoomUser participant) {
		try {
			ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
			if (!chatRoom.getConnectedUsers().stream().filter(o -> o.getUsername().equals(participant.getUsername()))
					.findFirst().isPresent()) {
				chatRoom = chatRoomService.join(participant, chatRoom);
			}
			return new ResponseEntity<>(chatRoom, HttpStatus.OK);
		} catch (Exception e) {
      logger.error("An exception occurred!", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping(value = "/participant/{chatRoomId}")
	public ResponseEntity<ChatRoom> removeUserFromChatRoom(@PathVariable String chatRoomId,
			@RequestBody ChatRoomUser participant) {
		try {
			ChatRoom _chatRoom = chatRoomService.leave(participant, chatRoomService.findById(chatRoomId));
			return new ResponseEntity<>(_chatRoom, HttpStatus.OK);
		} catch (Exception e) {
      logger.error("An exception occurred!", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
