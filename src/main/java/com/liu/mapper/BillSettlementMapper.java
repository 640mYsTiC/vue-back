package com.liu.mapper;

import com.liu.entity.BillSettlement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.entity.PurchaseAgreements;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-09
 */
public interface BillSettlementMapper extends BaseMapper<BillSettlement> {

    @Select("select agreement_code from purchase_agreements")
    List<String> getCodes();

    @Select("select supplier, material from purchase_agreements where agreement_code = #{agreementCode}")
    PurchaseAgreements getDetail(@PathVariable("agreementCode") String agreementCode);
}
