package com.slyang.im.socketio;


import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.DefaultExceptionListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.pubsub.PubSubStore;
import com.slyang.im.socketio.handler.ChatEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class SocketIOStart implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(SocketIOStart.class);

	private SocketIOServer server;
	private PubSubStore pubSubStore;
	private SocketIONamespace chat1namespace;

	@Autowired
	RedissonStoreFactory redissonStoreFactory;

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setPort(9092);
		config.setStoreFactory(redissonStoreFactory);
		config.setExceptionListener(new DefaultExceptionListener());

		config.setAuthorizationListener(handshakeData -> {
			//身份验证处理
			String user_id = handshakeData.getSingleUrlParam("user_id");
            // todo
			logger.debug("[Authorization  user_id= {}     token = {}]]", user_id);
			return true;
		});

//		config.setOrigin("*");

		config.getSocketConfig().setReuseAddress(true);
		config.getSocketConfig().setSoLinger(0);
		config.getSocketConfig().setTcpNoDelay(true);
		config.getSocketConfig().setTcpKeepAlive(true);

		server = new SocketIOServer(config);
		chat1namespace = server.addNamespace("/chat1");
		pubSubStore = server.getConfiguration().getStoreFactory().pubSubStore();

		return server;
	}


	@Override
	public void run(String... strings) throws Exception {
		server.start();
	}

	@Bean(name = "chat1namespace")
	public SocketIONamespace getIMSocketIONameSpace(ChatEventHandler chatEventHandler) {
		chat1namespace.addListeners(chatEventHandler);
		return chat1namespace;
	}

	@Bean
	public PubSubStore pubSubStore() {
		return pubSubStore;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}

	@PreDestroy
	public void destory() {
		redissonStoreFactory.shutdown();
		server.stop();
	}

}
