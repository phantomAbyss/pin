package com.pinche.infrastructure.repository;

import com.pinche.domain.relation.UserOrderRelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
@Mapper
public interface UserOrderRelRepository {
    /**
     * 新增关系
     *
     * @param userOrderRelDO
     * @return
     */
    Integer insert(@Param("userOrderRelDO") UserOrderRelDO userOrderRelDO);

    /**
     * 查询队友
     *
     * @param orderId
     * @return
     */
    List<Integer> queryPartner(@Param("orderId") Integer orderId);

    /**
     * 是否存在关系
     *
     * @param orderId
     * @param userId
     * @return
     */
    Integer exit(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    /**
     * 查询自己行程
     *
     * @param userId
     * @return
     */
    List<Integer> queryOwnOrderList(@Param("userId") Integer userId);

    /**
     * 逻辑删除
     *
     * @param orderId
     * @param userId
     * @return
     */
    Integer logicDelete(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    /**
     * 是否是leader
     *
     * @param orderId
     * @param userId
     * @return
     */
    Integer isLeader(@Param("orderId") Integer orderId, @Param("userId") Integer userId);

    /**
     * 重新选举
     *
     * @param orderId
     * @param userId
     * @return
     */
    Integer updateLeader(@Param("orderId") Integer orderId, @Param("userId") Integer userId);
}
