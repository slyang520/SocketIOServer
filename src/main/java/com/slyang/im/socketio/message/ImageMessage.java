package com.slyang.im.socketio.message;

import com.slyang.im.socketio.message.msgcontent.ImageContent;

public class ImageMessage extends AbsMessage<ImageContent> {

	@Override
	public int getMsg_type() {
		return MsgType.MSG_TYPE_IMG;
	}

}