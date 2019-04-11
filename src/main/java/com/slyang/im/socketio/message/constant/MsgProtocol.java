package com.slyang.im.socketio.message.constant;

import com.slyang.im.socketio.message.msgcontent.ImageMessage;
import com.slyang.im.socketio.message.msgcontent.TextMessage;

import java.util.Arrays;
import java.util.Optional;

/**
 *  +-------------------+------------------+
 *  |     msgMark       |    message       |
 *  +-------------------+------------------+
 *  |     4字节          |                  |
 */
public enum MsgProtocol {

    TEXT_MSG(12, TextMessage.class),
    IMG_MSG(11, ImageMessage.class),
    ;

    private int msgMark;
    private Class<?> classT;

    public int getMsgMark() {
        return msgMark;
    }

    public Class<?> getClassT() {
        return classT;
    }

    MsgProtocol(int msgMark, Class<?> classT) {
        this.msgMark = msgMark;
        this.classT = classT;
    }

    public static Class<?> getClassByMsgMark(int msgMark){
        Optional<MsgProtocol> defOptional= Arrays.stream(MsgProtocol.values()).filter((o-> o.getMsgMark()==msgMark)).findFirst();
        return defOptional.<Class<?>>map(MsgProtocol::getClassT).orElse(null);
    }


}
