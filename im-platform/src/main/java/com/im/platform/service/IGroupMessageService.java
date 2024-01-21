package com.im.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.im.common.model.GroupMessageInfo;
import com.im.platform.entity.GroupMessage;
import com.im.platform.vo.GroupMessageVO;

import java.util.List;


public interface IGroupMessageService extends IService<GroupMessage> {


    Long sendMessage(GroupMessageVO vo);

    void recallMessage(Long id);

    void pullUnreadMessage();

    List<GroupMessageInfo> findHistoryMessage(Long groupId, Long page, Long size);
}
