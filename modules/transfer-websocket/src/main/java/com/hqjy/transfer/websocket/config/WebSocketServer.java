package com.hqjy.transfer.websocket.config;

import com.hqjy.transfer.common.redis.utils.RedisUtils;
import com.hqjy.transfer.websocket.constant.WsConstant;
import com.hqjy.transfer.websocket.service.WsService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * netty 实现webSocket协议
 *
 * @author : heshuangshuang
 * @date : 2017/12/8 10:27
 */
@Component
public class WebSocketServer {
    private final static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * NioEventLoopGroup 和NioEventLoop 都可以.但是前者使用的是线程池.
     * 其实bossgroup如果服务端开启的是一个端口(大部分都是一个),单线程即可. 默认线程数是 cpu核心数的2倍. 但是也可以通过
     * -Dio.netty.eventLoopThreads 参数在服务端启动的时候指定 . boss用来监控tcp链接,执行
     * server.accept()操作,用于服务端接受客户端的连接
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * worker用来处理io事件,处理事件的读写到业务逻辑处理等后续操作,进行SocketChannel的网络读写
     */
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    /**
     * 通道组，将所有webSocket用户放进这个组，可以群推送消息
     */
    public static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    /**
     * 已经验证的过的用户放进map中，用于指定用户进行推送消息
     */
    public static ConcurrentHashMap<String, Channel> channelPool = new ConcurrentHashMap<>();
    /**
     * 当前连接的通道
     */
    private Channel channel;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private WsService wsService;
    /**
     * websocket是否开启，默认关闭
     */
    @Value("${netty.websocket.enabled: false}")
    private boolean enabled;
    /**
     * websocket监听端口
     */
    @Value("${netty.websocket.port: 8088}")
    private int port;

    /**
     * 开启服务
     */
    public void start() {
        if (enabled) {
            try {
                // netty 启动NIO服务端的辅助启动类，目的降低服务端的开发复杂度
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workGroup).handler(new LoggingHandler(LogLevel.INFO))
                        // 通道类型NIO webSocket
                        .channel(NioServerSocketChannel.class)
                        // 协议具体实现,IO事件的处理类，主要用于处理网络I/O时间，例如记录日志，对消息进行编解码等
                        .childHandler(new WsChannelInitializer(redisUtils, wsService))
                        // BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                        .option(ChannelOption.SO_BACKLOG, 1024);
                channel = bootstrap.bind(port).channel();
                logger.info("webSocket服务开启成功 ws://127.0.0.1:" + port + WsConstant.WEBSOCKET_PATH);
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                logger.error("InterruptedException in WebSocketServer start: " + e.getMessage(), e);
            } finally {
                // 优雅退出，释放线程池资源
                if (channel != null) {
                    channel.close();
                }
                CHANNEL_GROUP.close();
                workGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }
    }

}
