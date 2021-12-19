package com.pinche.service;

import com.google.common.base.Preconditions;
import com.pinche.domain.chat.MessageBody;
import com.pinche.domain.chat.MessageDO;
import com.pinche.domain.chat.UserInfoForChat;
import com.pinche.infrastructure.repository.MessageRepository;
import com.pinche.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public Boolean isExist(Integer orderId) {
        Integer row = messageRepository.findExist(orderId);
        return row != 0;
    }

    public void insert(MessageBody body, Integer userId, Integer orderId) {
        int row = messageRepository.inert(assemble(body, userId, orderId));
        Preconditions.checkState(row == 1, "insert message error");
    }

    public List<MessageBody> queryAll(Integer orderId, Integer userId) {
        List<MessageDO> messageDOS = messageRepository.queryAll(orderId);
        return messageDOS.parallelStream().map(x -> assemble(x, userId)).collect(Collectors.toList());
    }

    private MessageBody assemble(MessageDO messageDO, Integer userId) {
        UserInfoForChat userDO = userRepository.findChatInfo(messageDO.getUserId());
        MessageBody messageBody = new MessageBody(messageDO.getTime(), userDO, messageDO.getMessage());
        messageBody.setOwnMsg(messageDO.getUserId().equals(userId));
        return messageBody;

    }

    private MessageDO assemble(MessageBody body, Integer userId, Integer orderId) {
        MessageDO messageDO = new MessageDO();
        messageDO.setUserId(userId);
        messageDO.setTime(body.getTime());
        messageDO.setMessage(body.getMessage());
        messageDO.setOrderId(orderId);
        return messageDO;

    }

}
