package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-04-17
 */
@Getter
@Setter
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String name;

      private Integer score;

      private String times;

      private Boolean state;

      private Integer teacherId;

      @TableField(exist = false)
      private String teacher;
}
