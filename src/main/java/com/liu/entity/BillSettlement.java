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
 * @since 2023-05-09
 */
@Getter
@Setter
  @TableName("bill_settlement")
public class BillSettlement implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String settlementCode;

    private String date;

    private String agreementCode;

    private String status;

    private String supplier;

    private String material;

    private String billTime;

    private Double weight;

    private Double transFee;

    private Double supplyFee;

    private Double settlementFee;

    private String operator;
}
