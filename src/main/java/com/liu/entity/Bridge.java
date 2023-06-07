package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-29
 */
@Getter
@Setter
  @TableName("weight_bridge")

public class Bridge implements Serializable {

    private static final long serialVersionUID = 1L;
      @TableId(value = "id" , type = IdType.AUTO)
      private Integer id;

      private String orderId;

      private String state;

      private Integer truckId;

      private String driver;

      private String materialId;

      private Integer supplyNum;

      private Long grossWeight;

      private Long tareWeight;

      private Long netWeight;

      private String person;

      private String remark;
}
