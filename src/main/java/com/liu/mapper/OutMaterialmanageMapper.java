package com.liu.mapper;

import com.liu.entity.OutMaterialmanage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-17
 */
public interface OutMaterialmanageMapper extends BaseMapper<OutMaterialmanage> {

    @Select("select clientname from client_manage where clienttype = #{material}")
    List<String> getClient(@PathVariable String material);

    @Select("select clienttype from client_manage")
    List<String> getProduct();
}
