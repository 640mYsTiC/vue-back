package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-15
 */
@Getter
@Setter
  @TableName("in_materialmanage")
public class InMaterialmanage implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String inCode;

    private String inMaterial;

    private String supplier;

    private String inTime;

    private Object inWeight;

    private String inStorage;

    private String agreementCode;

    private String operator;
}
