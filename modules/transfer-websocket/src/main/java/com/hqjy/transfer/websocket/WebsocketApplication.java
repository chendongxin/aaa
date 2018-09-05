package com.hqjy.transfer.websocket;

import com.hqjy.transfer.websocket.config.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hqjy.transfer.websocket", "com.hqjy.transfer.common"})
public class WebsocketApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void run(String... args) throws Exception {
        webSocketServer.start();
    }
}
