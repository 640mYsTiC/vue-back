package com.liu.mapper;

import com.liu.common.Result;
import com.liu.entity.SaleAgreements;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-18
 */
public interface SaleAgreementsMapper extends BaseMapper<SaleAgreements> {

    @Select("select clientname from client_manage")
    List<String> getClients();



}
