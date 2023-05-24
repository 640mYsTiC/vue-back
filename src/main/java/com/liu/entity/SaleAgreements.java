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
 * @since 2023-05-18
 */
@Getter
@Setter
  @TableName("sale_agreements")
public class SaleAgreements implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String agreementCode;

    private String status;

    private String signTime;

    private String clientname;

    private String deliveryAddress;

    private Object distance;

    private String deliveryTime;

    private String operator;
}
