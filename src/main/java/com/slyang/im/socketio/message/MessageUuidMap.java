package com.slyang.im.socketio.message;

public class MessageUuidMap {

	private String socketUuuid;
	private SimpleMessage message;

	public MessageUuidMap() {
	}

	public MessageUuidMap(String socketUuuid, SimpleMessage message) {
		this.socketUuuid = socketUuuid;
		this.message = message;
	}

	public String getSocketUuuid() {
		return socketUuuid;
	}

	public void setSocketUuuid(String socketUuuid) {
		this.socketUuuid = socketUuuid;
	}

	public SimpleMessage getMessage() {
		return message;
	}

	public void setMessage(SimpleMessage message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return "MessageUuidMap{" +
				"socketUuuid='" + socketUuuid + '\'' +
				", message=" + message +
				'}';
	}
}
