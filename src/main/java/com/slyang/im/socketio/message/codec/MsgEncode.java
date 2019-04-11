package com.slyang.im.socketio.message.codec;

import io.netty.buffer.ByteBuf;

public interface MsgEncode<Packet> {

    ByteBuf encodePacket(Packet packet);

}
