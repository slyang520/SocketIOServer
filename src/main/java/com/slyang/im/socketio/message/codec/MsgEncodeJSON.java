package com.slyang.im.socketio.message.codec;

import com.slyang.im.socketio.message.msgcontent.AbsMessage;
import io.netty.buffer.ByteBuf;

public class MsgEncodeJSON implements MsgEncode<AbsMessage>{

    @Override
    public ByteBuf encodePacket(AbsMessage absMessage) {
        return null;
    }

}
