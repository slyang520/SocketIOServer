package com.slyang.im.socketio.message.codec;

import io.netty.buffer.ByteBuf;

public interface MsgDecode<Packet> {

    Packet decodePacket(ByteBuf packet);

}
