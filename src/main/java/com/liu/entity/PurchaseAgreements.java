package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-05-08
 */
@Getter
@Setter
  @TableName("purchase_agreements")

public class PurchaseAgreements implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String agreementCode;

    private String status;

    private String date;

    private String material;

    private String supplier;

    private String buyTime;

    private String contactPerson;

    private String contactPhone;
}
