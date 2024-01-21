package com.im.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.im.common.model.PrivateMessageInfo;
import com.im.platform.vo.PrivateMessageVO;
import com.im.platform.entity.PrivateMessage;

import java.util.List;


public interface IPrivateMessageService extends IService<PrivateMessage> {

    Long sendMessage(PrivateMessageVO vo);

    void recallMessage(Long id);

    List<PrivateMessageInfo> findHistoryMessage(Long friendId, Long page,Long size);

    void pullUnreadMessage();

}
