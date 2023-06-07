package com.liu.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args){
        generator();
    }

    public static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/kybagr?serverTimezone=GMT%2b8",
                        "root",
                        "z10bzz5199")
                .globalConfig(builder -> {
                    builder.author("刘小白") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\webSource\\apache-maven-3.8.5\\maven-repo\\sb+vue\\bgm\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.liu") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\webSource\\apache-maven-3.8.5\\maven-repo\\sb+vue\\bgm\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.addInclude("driver") // 设置需要生成的表名
                            .addTablePrefix("bgm_", "sys_", "material_", "weigh_"); // 设置过滤表前缀
                })
                .execute();
    }
}
