package info.yangguo.demo.fsm;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User:杨果
 * Date:15/1/6
 * Time:下午3:30
 * <p/>
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        CallInfo callInfo=new CallInfo();
        callInfo.setRpid("rpid");
        callInfo.setIp("192.168.1.1");
        callInfo.setIp("192.168.1.2");
        callInfo.setIp("192.168.1.3");
        callInfo.setIp("192.168.1.4");
        HashMap map=new HashMap();
        map.put("STATE1","STATE1");
        map.put("STATE2","STATE2");
        map.put("STATE3","STATE3");
        map.put("STATE4","STATE4");
        callInfo.setOthers(map);
        Context context=new ContextImpl();
        context.setCallInfo(callInfo);
        States.execute(context);
    }
}
