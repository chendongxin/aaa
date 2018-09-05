package com.hqjy.transfer.websocket.config;


import com.hqjy.transfer.common.base.utils.JsonUtil;
import com.hqjy.transfer.common.base.utils.R;
import com.hqjy.transfer.websocket.constant.WsConstant;
import com.hqjy.transfer.websocket.service.WsService;
import com.hqjy.transfer.websocket.utils.WsCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 处理webSocket链接
 *
 * @author : heshuangshuang
 * @date : 2017/12/8 10:27
 */
@Slf4j
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private WsService wsService;

    public WsHandler(WsService wsService) {
        this.wsService = wsService;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //握手成功完成，通道被升级到webSockets
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            log.info("握手成功完成，通道被升级到webSockets");
            //删除之前的http通道
            ctx.pipeline().remove(WsHttpHandler.class);
            //获取当前用户userId
            Channel channel = ctx.channel();
            String userId = channel.attr(WsConstant.CHANNEL_USERID_KEY).get();
            //token验证失败
            if (StringUtils.isEmpty(userId)) {
                //发送断开消息并，断开
                channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.toJson(R.ok(WsCode.WS_UNAUTHORIZED))));
                channel.close();
                log.debug("发送断开消息，并断开连接");
                return;
            }
            //之前绑定的会话
            Channel oldChannel = WebSocketServer.channelPool.get(userId);
            //如果之前已经绑定了会话，获得之前会话的id,对比当前会话id，如果不一致，则需要断开之前的会话
            if (oldChannel != null && !channel.id().equals(oldChannel.id())) {
                log.debug("用户{} 之前有了会话，进行拆除", userId);
                wsService.disconnect(userId);
            }
            WebSocketServer.channelPool.put(userId, channel);
            //此通道添加到组
            WebSocketServer.CHANNEL_GROUP.add(channel);
            // 发送连接成功消息
            wsService.sendMessage(userId, JsonUtil.toJson(R.ok(WsCode.WS_CONNECTION)));
            log.debug("websocket当前连接数量：" + WebSocketServer.channelPool.size());
            // TODO 连接成功用户写入Redis
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        Channel channel = ctx.channel();
        String userId = channel.attr(WsConstant.CHANNEL_USERID_KEY).get();
        String message = msg.text();
        if (userId != null && !StringUtils.isEmpty(message)) {
            if ("@heart".equals(message)) {
                //发送心跳回复
                channel.writeAndFlush(new TextWebSocketFrame(userId));
            } else {
                //wsService.processMessage(userId, msg.text());
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String userId = channel.attr(WsConstant.CHANNEL_USERID_KEY).get();
        if (userId != null) {
            //如果之前已经绑定了会话，获得之前会话的id,对比当前会话id，如果不一致，则需要断开之前的会话
            Channel old = WebSocketServer.channelPool.get(userId);
            if (old != null && !channel.id().equals(old.id())) {
                log.debug("用户：{} 断开旧连接", userId);
            } else {
                WebSocketServer.channelPool.remove(userId);
                WebSocketServer.CHANNEL_GROUP.remove(channel);
                log.debug("用户：{} 离线", userId);
                // TODO 断开用户从Redis中删除
            }
        }
        ctx.fireChannelInactive();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //offLines(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("webSocket连接发生异常:{}", cause.getMessage());
        //offLines(ctx);
    }


}
