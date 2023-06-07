package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-06-05
 */
@Getter
@Setter
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;


      @TableId(value = "vehicle_id" , type = IdType.AUTO)
        private Integer vehicleId;

      private Integer plateNumber;

      private String brand;

      private String model;

      private Integer capacity;

      private LocalDateTime purchaseDate;

      private String status;

      private String createdAt;

      private LocalDateTime updatedAt;

      private String state;

      private String driver;
}
