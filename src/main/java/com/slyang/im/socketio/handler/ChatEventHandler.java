package com.slyang.im.socketio.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import com.slyang.im.RedisClient;
import com.slyang.im.socketio.message.codec.MsgDecodeJSON;
import com.slyang.im.socketio.message.msgcontent.AbsMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;

@Component
public class ChatEventHandler {

	private Logger logger = LoggerFactory.getLogger(ChatEventHandler.class);

	@Resource(name = "chat1namespace")
	private SocketIONamespace chat1namespace;
	@Autowired
	private PubSubStore pubSubStore;

	@Autowired
	RedissonClient redissonClient;

	private final static String TOPIC_SINGLE_CHAT = "single_chat";


	@PostConstruct
	public void init() {

		RTopic topic = redissonClient.getTopic(TOPIC_SINGLE_CHAT);

		topic.addListener(MessageUuidMap.class, new MessageListener<MessageUuidMap>() {
			@Override
			public void onMessage(CharSequence channel, MessageUuidMap msg) {
				SocketIOClient ioClient = chat1namespace.getClient(UUID.fromString(msg.getSocketUuuid()));
				if (ioClient != null) {
					ioClient.sendEvent("message", msg.getMessage());
					logger.debug("[	    TOPIC_SINGLE_CHAT success]]");
				}else{
					logger.debug("[	    TOPIC_SINGLE_CHAT not find client]]");
				}
			}
		});

	}

	@OnConnect
	@SuppressWarnings(value = {"unchecked"})
	public void onConnect(SocketIOClient client) {

		String user_id = client.getHandshakeData().getSingleUrlParam("user_id");
		logger.debug("[onConnect ={}   {}]]", user_id, client.getSessionId().toString());

		// redis 存储  userid > uuid  map
		RBucket<String> bucket = redissonClient.getBucket(String.format(RedisClient.REDISKEY.IM_USERID, user_id));
		bucket.set(client.getSessionId().toString());

		logger.debug("[bucket   {}]]", bucket.get());

	}

	@OnDisconnect
	@SuppressWarnings(value = {"unchecked"})
	public void onDisconnect(SocketIOClient client) {

		String user_id = client.getHandshakeData().getSingleUrlParam("user_id");
		logger.debug("[onDisconnect ={}  {}]]", user_id, client.getSessionId().toString());

		client.del(String.format(RedisClient.REDISKEY.IM_USERID, user_id));

	}

	@OnEvent(value = "message")
	@SuppressWarnings(value = {"unchecked"})
	public void onEvent(SocketIOClient client, AckRequest request, byte[] data) {

		ByteBuf byteBuf = Unpooled.copiedBuffer(data);

		MsgDecodeJSON msgDecodeJSON=new MsgDecodeJSON();
		AbsMessage message = msgDecodeJSON.decodePacket(byteBuf);

		if(message==null) return;

		logger.debug(" bucket to    {} ", message.getClass());
		logger.debug(" bucket to    {} ", message.toString());

		client.sendEvent("message", data);

		// msg > from
		String user_id = client.getHandshakeData().getSingleUrlParam("user_id");
		String from_key = String.format(RedisClient.REDISKEY.IM_USERID, user_id);
		RBucket<String> bucket_from = redissonClient.getBucket(from_key);
		logger.debug("[bucket from  {}    {}]]", from_key, bucket_from.get());

		// msg > to
		String to_key = String.format(RedisClient.REDISKEY.IM_USERID, message.getUserid_to());
		RBucket<String> bucket = redissonClient.getBucket(to_key);
		logger.debug("[bucket to    {}  {}]]", to_key, bucket.get());


		String other_uuid = bucket.get();
		boolean flag=false;
		if (!StringUtils.isEmpty(other_uuid)) {
			SocketIOClient ioClient = chat1namespace.getClient(UUID.fromString(other_uuid));
			if (ioClient != null) {
				ioClient.sendEvent("message", data);
				flag = true;
			}
			// 用户在其他机器
			if(!flag){
				RTopic topic = redissonClient.getTopic(TOPIC_SINGLE_CHAT);
				topic.publish(new MessageUuidMap(other_uuid,data));
			}
		}

		// 广播消息
		//chat1namespace.getBroadcastOperations().sendEvent("message", data);
	}

	public static void main(String[] args) {
		 String h ="hello,world";
		byte[] bytes = h.getBytes(Charset.forName("UTF-8"));
		System.out.println(Arrays.toString(bytes));

	}


}
