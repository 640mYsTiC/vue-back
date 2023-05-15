package com.liu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.entity.mAndS;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MAndSMapper extends BaseMapper<mAndS> {
    @Select("select * from purchase_agreements where material = #{material}")
    List<mAndS> getAgreementDetail(@PathVariable String material);
}
