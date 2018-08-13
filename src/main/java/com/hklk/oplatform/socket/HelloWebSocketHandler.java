package com.hklk.oplatform.socket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * Author: Al-assad 余林颖
 * E-mail: yulinying_1994@outlook.com
 * Date: 2018/1/10 0:47
 * Description:
 */
public class HelloWebSocketHandler extends AbstractWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            //处理文本消息
            System.out.println("get message: " + message.getPayload());
            //模拟延时
            Thread.sleep(2000);
            //发送信息
            session.sendMessage(new TextMessage("from server: 你好!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("websocket connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("websocket connection close");
    }
}
