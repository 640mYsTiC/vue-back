package com.liu.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-06-02
 */
@Getter
@Setter
  public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "driver_id", type = IdType.AUTO)
    private Integer driverId;

    private String name;

    private String phone;

    private String driverLicense;

    private String drivingYears;

    private String updatedAt;
}
