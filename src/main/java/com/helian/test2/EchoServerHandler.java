package com.helian.test2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class EchoServerHandler extends SimpleChannelInboundHandler<Object>{//ChannelInboundHandlerAdapter{//ChannelHandlerAppender {
	public void channelRead(
			ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println((msg instanceof ByteBuf));
        ctx.write(msg);
        //解析ByteBuf
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("to 服务器："+body);
        
    }
	public void channelReadComplete(ChannelHandlerContext ctx) { 
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) //flush掉所有写回的数据
		.addListener(ChannelFutureListener.CLOSE); //当flush完成后关闭channel
	} 
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) { 
		cause.printStackTrace();//捕捉异常信息
		ctx.close();//出现异常时关闭channel 
	}
	@Override
	protected void messageReceived(
			ChannelHandlerContext paramChannelHandlerContext, Object paramI)
			throws Exception {
		// TODO Auto-generated method stub
		
	} 	
}