package com.wrq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangqian on 2019/4/17.
 */
@Component
@ServerEndpoint("/webSocket/{shopKey}")
@Slf4j
public class WebSocket {

    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();
    private Session session;
    private String shopKey = "";

    /**
     * 连接建立成功调用的方法
     *
     */
    @OnOpen
    public void onOpen(@PathParam(value = "shopKey") String param, Session session) {
        shopKey = param;
        this.session = session;
        webSocketSet.put(param, this);
    }

    @OnClose
    public void onClose() {
        if ( !shopKey.equals("") ) {
            webSocketSet.remove(shopKey);
        }
    }

    @OnMessage
    public void toStore( String message ) {
        String orderNo =  message.substring(message.lastIndexOf(".")+1);
        String shopId = message.substring(0, message.lastIndexOf("."));
        try {
            if ( webSocketSet.get(shopId) != null ) {
                webSocketSet.get(shopId).sendMessage(orderNo);
            } else {
                log.info(" 当前店铺不在线！ ");
            }
        } catch (IOException e) {
            log.error(" webSocket推送发生错误 ，e ={}",  e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(" webSocket发生错误 ");
    }
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


}
