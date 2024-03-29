package com.spordee.message.chatroom.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;

public class ChatRoomUser implements Comparable<ChatRoomUser> {
	@Indexed
	private String username;
	private Date joinedAt = new Date();
	
	public ChatRoomUser() {

	}
	
	public ChatRoomUser(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getJoinedAt() {
		return joinedAt;
	}
	
	@Override
	public String toString() {
		return this.username;
	}

	@Override
	public int compareTo(ChatRoomUser chatRoomUser) {
		return this.username.compareTo(chatRoomUser.getUsername());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "joinedAt");
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "joinedAt");
	}
}
