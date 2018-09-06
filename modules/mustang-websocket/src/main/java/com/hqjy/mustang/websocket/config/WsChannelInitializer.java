package com.hqjy.mustang.websocket.config;

import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.websocket.constant.WsConstant;
import com.hqjy.mustang.websocket.service.WsService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * ws 具体实现类
 *
 * @author : heshuangshuang
 * @date : 2017/12/8 10:29
 */
public class WsChannelInitializer extends ChannelInitializer<Channel> {
    private RedisUtils redisUtils;
    private WsService wsService;

    public WsChannelInitializer(RedisUtils redisUtils, WsService wsService) {
        this.redisUtils = redisUtils;
        this.wsService = wsService;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new WebSocketServerCompressionHandler());
        //处理心跳
        pipeline.addLast(new IdleStateHandler(0, 0, 1800, TimeUnit.SECONDS));
        //将请求和应答消息编码或解码为http消息
        pipeline.addLast(new HttpServerCodec());
        //向客户端发送html5文件，主要用于支持浏览器和服务端进行websocket通信,支持异步发送大的码流，但不占用过多的内存
        pipeline.addLast(new ChunkedWriteHandler());
        //将多个消息转换为单一FullHttpRequest 或者FullHttpResponse,因为Http解码器在每个http消息中会生成多个消息对象
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        //http请求处理,指定需要处理的url
        pipeline.addLast(new WsHttpHandler(redisUtils));
        //设置webosocket连接路径
        pipeline.addLast(new WebSocketServerProtocolHandler(WsConstant.WEBSOCKET_PATH));
        //websocket请求处理,
        pipeline.addLast(new WsHandler(wsService));
    }
}
