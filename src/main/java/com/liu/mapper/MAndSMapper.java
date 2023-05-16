package com.liu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.entity.mAndS;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface MAndSMapper extends BaseMapper<mAndS> {
    @Select("select supplier from purchase_agreements where material = #{material}")
    List<String> getAgreementSupplier(@PathVariable String material);

    @Select("select agreement_code from purchase_agreements where supplier = #{supplier} and material = #{material}")
    List<String> getAgreementCode(@PathVariable String supplier, @PathVariable String material);
}
