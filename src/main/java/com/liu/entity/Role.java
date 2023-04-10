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
 * @since 2023-04-06
 */
@Getter
@Setter
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      private String name;

      private String description;

      private String flag;
}
