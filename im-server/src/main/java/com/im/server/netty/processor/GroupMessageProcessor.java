package com.im.server.netty.processor;

import com.im.common.contant.RedisKey;
import com.im.common.enums.IMCmdType;
import com.im.common.enums.IMSendCode;
import com.im.common.model.GroupMessageInfo;
import com.im.common.model.IMRecvInfo;
import com.im.common.model.IMSendInfo;
import com.im.common.model.SendResult;
import com.im.server.netty.UserChannelCtxMap;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GroupMessageProcessor extends  MessageProcessor<IMRecvInfo<GroupMessageInfo>> {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Async
    @Override
    public void process(IMRecvInfo<GroupMessageInfo> recvInfo) {
        GroupMessageInfo messageInfo = recvInfo.getData();
        List<Long> recvIds = recvInfo.getRecvIds();
        log.info("接收到群消息，发送者:{},群id:{},接收id:{}，内容:{}",messageInfo.getSendId(),messageInfo.getGroupId(),recvIds,messageInfo.getContent());
        for(Long recvId:recvIds){
            try {
                ChannelHandlerContext channelCtx = UserChannelCtxMap.getChannelCtx(recvId);
                if(channelCtx != null){
                    // 推送消息到用户
                    IMSendInfo sendInfo = new IMSendInfo();
                    sendInfo.setCmd(IMCmdType.GROUP_MESSAGE.code());
                    sendInfo.setData(messageInfo);
                    channelCtx.channel().writeAndFlush(sendInfo);
                    // 消息发送成功确认
                    String key = RedisKey.IM_RESULT_GROUP_QUEUE;
                    SendResult sendResult = new SendResult();
                    sendResult.setRecvId(recvId);
                    sendResult.setCode(IMSendCode.SUCCESS);
                    sendResult.setMessageInfo(messageInfo);
                    redisTemplate.opsForList().rightPush(key,sendResult);

                }else {
                    // 消息发送失败确认
                    String key = RedisKey.IM_RESULT_GROUP_QUEUE;
                    SendResult sendResult = new SendResult();
                    sendResult.setRecvId(recvId);
                    sendResult.setCode(IMSendCode.NOT_FIND_CHANNEL);
                    sendResult.setMessageInfo(messageInfo);
                    redisTemplate.opsForList().rightPush(key,sendResult);
                    log.error("未找到WS连接,发送者:{},群id:{},接收id:{}，内容:{}",messageInfo.getSendId(),messageInfo.getGroupId(),recvIds,messageInfo.getContent());
                }
            }catch (Exception e){
                // 消息发送失败确认
                String key = RedisKey.IM_RESULT_GROUP_QUEUE;
                SendResult sendResult = new SendResult();
                sendResult.setRecvId(recvId);
                sendResult.setCode(IMSendCode.UNKONW_ERROR);
                sendResult.setMessageInfo(messageInfo);
                redisTemplate.opsForList().rightPush(key,sendResult);
                log.error("发送消息异常,发送者:{},群id:{},接收id:{}，内容:{}",messageInfo.getSendId(),messageInfo.getGroupId(),recvIds,messageInfo.getContent());
            }
        }
    }

}
