package com.pinche.infrastructure.repository;

import com.pinche.domain.chat.UserInfoForChat;
import com.pinche.domain.user.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
@Mapper
public interface UserRepository {
    /**
     * 新增用户
     *
     * @param userDO
     * @return
     */
    int insert(@Param("userDO") UserDO userDO);

    /**
     * 查找用户
     *
     * @param openId
     * @return
     */
    UserDO find(@Param("openId") String openId);

    /**
     * 查找用户openId
     *
     * @param userId
     * @return
     */
    String findOpenId(@Param("userId") Integer userId);

    /**
     * 查找用户信息
     *
     * @param userId
     * @return
     */
    UserInfoForChat findChatInfo(@Param("userId") Integer userId);

    /**
     * 更新用户
     *
     * @param userDO
     * @return
     */
    int update(@Param("userDO") UserDO userDO);
}
