//package com.liu.mapper;
//
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.web.bind.annotation.PathVariable;
//
//import java.util.List;
//
///**
// * <p>
// *  Mapper 接口
// * </p>
// *
// * @author 刘小白
// * @since 2023-05-01
// */
//public interface SpplierMapper11 extends BaseMapper<Spplier> {
//
//    @Select("select contactPerson, contactPhone from bgm_spplier where supplierName = #{name}")
//    Spplier selectInfoByName(@PathVariable("name") String name);
//
//    @Select("select supplierName from bgm_spplier")
//    List<String> getNames();
//}
