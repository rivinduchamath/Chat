package com.spordee.message.entity;

import com.google.common.base.Strings;

import com.spordee.message.utils.SystemUsers;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@Table("messages")
public class InstantMessage {

	@PrimaryKey
	private InstantMessageKey instantMessageKey;
	@Column("from_user")

	private String fromUser;
	@Column("to_user")

	private String toUser;
	private String text;
	@Column("is_notification")
	private boolean isNotification;

	public InstantMessage() {
		this.isNotification = false;
	}

	public InstantMessageKey getInstantMessageKey() {
		return this.instantMessageKey;
	}

	public void setInstantMessageKey(InstantMessageKey instantMessageKey) {
		this.instantMessageKey = instantMessageKey;
	}

	public boolean isPublic() {
		return Strings.isNullOrEmpty(this.toUser);
	}
	public boolean isFromAdmin() {
		return this.fromUser.equals(SystemUsers.ADMIN.getUsername());
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public boolean isIsNotification() {
		return this.isNotification;
	}

	public boolean getIsNotification() {
		return this.isNotification;
	}

	public void setIsNotification(boolean isNotification) {
		this.isNotification = isNotification;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}