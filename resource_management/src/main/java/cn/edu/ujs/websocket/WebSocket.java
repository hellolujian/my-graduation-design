package cn.edu.ujs.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 配置websocket站点
 * Created by DELL on 2018/4/29.
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocket {

    private static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    /**
     * 在线人数
     */
    public static int onlineNumber = 0;

    /**
     * 所有的对象
     */
    public static List<WebSocket> webSocketList = new CopyOnWriteArrayList<>();

    /**
     * 会话
     */
    private Session session;

    /**
     * 建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {

        onlineNumber++;
        webSocketList.add(this);
        this.session = session;
        logger.info("有新连接加入！当前在线人数：" + onlineNumber);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void opCloce() {

        onlineNumber--;
        webSocketList.remove(this);
        logger.info("有连接关闭！当前在线人数：" + onlineNumber);
    }

    @OnMessage
    public void onMessage(String message, Session session) {

        logger.info("收到客户端发来的消息：{}", message);
        sendMessage("欢迎连接！");
    }

    public void sendMessage(String message) {

        for (WebSocket webSocket : webSocketList) {
            logger.info("websocket广播消息：{}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}













