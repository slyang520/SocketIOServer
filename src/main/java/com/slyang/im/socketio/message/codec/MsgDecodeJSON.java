package com.slyang.im.socketio.message.codec;

import com.alibaba.fastjson.JSON;
import com.slyang.im.socketio.handler.ChatEventHandler;
import com.slyang.im.socketio.message.constant.MsgProtocol;
import com.slyang.im.socketio.message.msgcontent.AbsMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgDecodeJSON implements MsgDecode<AbsMessage>{

    private Logger logger = LoggerFactory.getLogger(ChatEventHandler.class);

    @Override
    public AbsMessage decodePacket(ByteBuf packet) {

        int msgMark = packet.readInt();
        logger.debug(" bucket to    {} ", msgMark);

        Class<?> classT= MsgProtocol.getClassByMsgMark(msgMark);
        if(classT==null) return null;

        byte[] msgContent= ByteBufUtil.getBytes(packet);

        return JSON.parseObject(msgContent,classT);
    }

}
