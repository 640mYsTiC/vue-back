package com.liu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author 刘小白
 * @since 2023-06-04
 */
@Getter
@Setter
  @TableName("product_task")

public class ProductTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;

    private String taskId;

  @Override
  public String toString() {
    return "ProductTask{" +
            "id=" + id +
            ", taskId='" + taskId + '\'' +
            ", state='" + state + '\'' +
            ", taskDate=" + taskDate +
            ", schedule='" + schedule + '\'' +
            ", clientName='" + clientName + '\'' +
            ", projectName='" + projectName + '\'' +
            ", constructionSite='" + constructionSite + '\'' +
            ", concrete='" + concrete + '\'' +
            ", way='" + way + '\'' +
            ", planVolume=" + planVolume +
            ", car='" + car + '\'' +
            ", distance=" + distance +
            ", driver='" + driver + '\'' +
            ", line=" + line +
            ", remark='" + remark + '\'' +
            '}';
  }

      private String state;

      private LocalDate taskDate;

      private String schedule;

      private String clientName;

      private String projectName;

      private String constructionSite;

      private String concrete;

      private String way;

      private Long planVolume;

      private String car;

      private Long distance;

      private String driver;

      private Integer line;

      private String remark;
}
