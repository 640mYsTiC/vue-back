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
 * @since 2023-05-17
 */
@Getter
@Setter
  @TableName("out_materialmanage")
public class OutMaterialmanage implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String outCode;

    private String outMaterial;

    private String client;

    private String outTime;

    private Object outWeight;

    private String outStorage;

    private String operator;
}
