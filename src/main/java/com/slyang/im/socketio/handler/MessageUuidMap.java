package com.slyang.im.socketio.handler;

public class MessageUuidMap {

	private String socketUuuid;
	private byte[] message;

	public MessageUuidMap() {
	}

	public MessageUuidMap(String socketUuuid, byte[] message) {
		this.socketUuuid = socketUuuid;
		this.message = message;
	}

	public String getSocketUuuid() {
		return socketUuuid;
	}

	public void setSocketUuuid(String socketUuuid) {
		this.socketUuuid = socketUuuid;
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
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
