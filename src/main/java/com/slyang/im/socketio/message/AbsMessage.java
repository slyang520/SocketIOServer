package com.slyang.im.socketio.message;

public class AbsMessage<T> {

	private int chat_type = ChatType.CHAT_TYPE_SINGLE;

	private String userid_from;
	private UserInfoFrom user_info_from;
	private String userid_to;
	private String roomid_to;

	private String msg_key;
	private String msg_content;
	private int msg_type = MsgType.MSG_TYPE_TEXT;
	private T msg_content_extra;

	public String getUserid_from() {
		return userid_from;
	}

	public void setUserid_from(String userid_from) {
		this.userid_from = userid_from;
	}

	public String getUserid_to() {
		return userid_to;
	}

	public void setUserid_to(String userid_to) {
		this.userid_to = userid_to;
	}

	public String getMsg_key() {
		return msg_key;
	}

	public void setMsg_key(String msg_key) {
		this.msg_key = msg_key;
	}

	public String getMsg_content() {
		return msg_content;
	}

	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}

	public int getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(int msg_type) {
		this.msg_type = msg_type;
	}

	public T getMsg_content_extra() {
		return msg_content_extra;
	}

	public UserInfoFrom getUser_info_from() {
		return user_info_from;
	}

	public void setUser_info_from(UserInfoFrom user_info_from) {
		this.user_info_from = user_info_from;
	}

	public int getChat_type() {
		return chat_type;
	}

	public void setChat_type(int chat_type) {
		this.chat_type = chat_type;
	}

	public String getRoomid_to() {
		return roomid_to;
	}

	public void setRoomid_to(String roomid_to) {
		this.roomid_to = roomid_to;
	}

	public void setMsg_content_extra(T msg_content_extra) {
		this.msg_content_extra = msg_content_extra;
	}


	@Override
	public String toString() {
		return "AbsMessage{" +
				this.getClass() + "	" +
				"chat_type=" + chat_type +
				", userid_from='" + userid_from + '\'' +
				", user_info_from=" + user_info_from +
				", userid_to='" + userid_to + '\'' +
				", roomid_to='" + roomid_to + '\'' +
				", msg_key='" + msg_key + '\'' +
				", msg_content='" + msg_content + '\'' +
				", msg_type=" + msg_type +
				", msg_content_extra=" + msg_content_extra +
				'}';
	}

}
