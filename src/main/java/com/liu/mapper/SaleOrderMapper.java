package com.liu.mapper;

import com.liu.entity.SaleOrder;
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
 * @since 2023-05-23
 */
public interface SaleOrderMapper extends BaseMapper<SaleOrder> {
    @Select("select agreement_code from sale_agreements")
    List<String> getagreementCodes();

    @Select("select clientname from sale_agreements where agreement_code = #{agreementsCode}")
    String getClientname(@PathVariable String agreementsCode);
}
