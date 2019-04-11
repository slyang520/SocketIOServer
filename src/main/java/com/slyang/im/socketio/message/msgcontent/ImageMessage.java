package com.slyang.im.socketio.message.msgcontent;

import com.slyang.im.socketio.message.constant.MsgType;

public class ImageMessage extends AbsMessage<ImageMessage.ImgContentExtra> {

	@Override
	public int getMsg_type() {
		return MsgType.MSG_TYPE_IMG;
	}


	class ImgContentExtra{

		private String imgUrl;


		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

	}

}