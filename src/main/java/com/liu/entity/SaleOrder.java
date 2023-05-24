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
 * @since 2023-05-23
 */
@Getter
@Setter
  @TableName("sale_order")
public class SaleOrder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String orderCode;

    private String orderDate;

    private String status;

    private String agreementCode;

    private String pourTime;

    private String pourMethod;

    private String clientname;

    private Double estimatedVolume;

    private String operator;
}
