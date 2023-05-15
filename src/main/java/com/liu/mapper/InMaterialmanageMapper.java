package com.liu.mapper;

import com.liu.entity.InMaterialmanage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-15
 */
public interface InMaterialmanageMapper extends BaseMapper<InMaterialmanage> {
    @Select("select material from purchase_agreements")
    List<String> getMaterials();

}
