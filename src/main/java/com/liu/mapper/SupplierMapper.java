package com.liu.mapper;

import com.liu.entity.Supplier;
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
 * @since 2023-05-08
 */
public interface SupplierMapper extends BaseMapper<Supplier> {
    @Select("select contact_person, contact_phone from bgm_supplier where supplier_name = #{name}")
    Supplier selectInfoByName(@PathVariable("name") String name);

    @Select("select supplier_name from bgm_supplier")
    List<String> getNames();
}
