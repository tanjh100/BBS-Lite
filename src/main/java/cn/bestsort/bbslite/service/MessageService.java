package cn.bestsort.bbslite.service;

import cn.bestsort.bbslite.enums.CustomizeErrorCodeEnum;
import cn.bestsort.bbslite.enums.FunctionItem;
import cn.bestsort.bbslite.enums.MessageEnum;
import cn.bestsort.bbslite.exception.CustomizeException;
import cn.bestsort.bbslite.mapper.*;
import cn.bestsort.bbslite.pojo.model.*;
import cn.bestsort.bbslite.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bestsort
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<MessageVo>getListByUser(long userId, MessageEnum type){
        MessageExample example = new MessageExample();
        if (type.equals(MessageEnum.ALL)){
            example.createCriteria().andSendToEqualTo(userId);
        }else if (MessageEnum.getCode(type) != null){
            example.createCriteria().andSendToEqualTo(userId)
                    .andTypeEqualTo(MessageEnum.getCode(type));
        }else {
            throw new CustomizeException(CustomizeErrorCodeEnum.NO_WAY);
        }


        List<MessageVo> result = new ArrayList<>();
        List<Message> list = messageMapper.selectByExample(example);
        Set<Long> userIdSet = new HashSet<>();
        Set<Long> questionSet = new HashSet<>();
        for (Message item:list){
            userIdSet.add(item.getSendBy());
            questionSet.add(item.getSendTo());
            MessageVo messageVo = MessageVo.builder()
                    .gmtCreate(item.getGmtCreate())
                    .isRead(item.getIsRead().equals((byte)1))
                    .item(FunctionItem.getByCode(item.getType()))
                    .build();
            result.add(messageVo);
        }

        /*获得去重后的用户信息*/
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(new ArrayList<>(userIdSet));
        Map<Long,User> users = userMapper.selectByExample(userExample)
                .stream().collect(Collectors.toMap(User::getId, user->user));

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdIn(new ArrayList<>(questionSet));
        Map<Long,Question>questions = questionMapper.selectByExample(questionExample)
                .stream().collect(Collectors.toMap(Question::getId,question->question));
        for (MessageVo item : result){
            MessageVo.builder()
                    .title(questions.get(item.getSendToId()).getTitle())
                    .gmtCreate(item.getGmtCreate())
                    .isRead(item.isRead())
                    .sendBy(item.getSendBy())
                    .item(item.getItem());
        }
        return result;
    }
    public int sendMessage(Message message){
        return messageMapper.insertSelective(message);
    }
    private String buildMessage(){
        return null;
    }
}
