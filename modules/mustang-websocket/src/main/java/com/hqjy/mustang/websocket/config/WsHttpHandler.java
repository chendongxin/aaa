package com.hqjy.mustang.websocket.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hqjy.mustang.common.redis.utils.RedisKeys;
import com.hqjy.mustang.common.redis.utils.RedisUtils;
import com.hqjy.mustang.websocket.constant.WsConstant;
import com.hqjy.mustang.websocket.dto.UserDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 处理Http请求，用于webSocket连接前的认证
 *
 * @author : heshuangshuang
 * @date : 2017/12/8 10:27
 */
public class WsHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static Logger logger = LoggerFactory.getLogger(WsHttpHandler.class);
    private static final String WEBSOCKET = "websocket";
    private static final String UPGRADE = "Upgrade";
    private RedisUtils redisUtils;

    public WsHttpHandler(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess()) {
            logger.debug("消息译码失败");
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        if (!request.uri().contains(WsConstant.WEBSOCKET_PATH)) {
            logger.debug("返回404消息");
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND));
            return;
        }
        if (request.method() != GET || !WEBSOCKET.equals(request.headers().get(UPGRADE))) {
            logger.debug("不是webSocket请求");
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }
        //验证Token信息，从path中获取token参数
        String token = (request.uri().contains("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.") ? request.uri().substring(request.uri().indexOf("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.")) : request.uri()).replaceAll("/", "");
        logger.debug("得到token参数:{}", token);
        UserDTO user = checkToken(token);
        if (user == null) {
            logger.debug("token验证失败");
            /*sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, UNAUTHORIZED));
            return;*/
        } else {
            logger.info("token验证成功，用户id：{};准备开始建立连接", user.getUserId());
            //将获得到的uesrId放进channel中
            ctx.channel().attr(WsConstant.CHANNEL_USERID_KEY).getAndSet(String.valueOf(user.getUserId()));
        }
        //重新设置请求上下文，指定到webSocket路径上
        request.setUri(WsConstant.WEBSOCKET_PATH);
        ctx.fireChannelRead(request.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 检测token有效性，但这里暂时不检测用户状态
     */
    private UserDTO checkToken(String token) {
        try {
            DecodedJWT dJWT = JWT.decode(token);
            String userId = dJWT.getSubject();
            //读取redis中token信息，但是不改变原来的超时时间
            UserDTO redisUser = redisUtils.get(RedisKeys.User.token(Long.valueOf(userId)), UserDTO.class, -1);
            //验证jwt token的完整性和有效性
            JWT.require(Algorithm.HMAC256(redisUser.getPassword())).build().verify(token);
            //对比此token是否与上次登录的token一致，做到单用户登录,注释这儿可以多用户同时登录
            if (token.equals(redisUser.getToken())) {
                return redisUser;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        //如果响应获得状态代码，生成一个错误页面(200)
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }
        //如果需要，发送响应并关闭连接。
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
