package com.zyxm.module.entity.pojo;

import com.zyxm.config.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author Robert
 * @Create 2020/9/17
 * @Desc TODO
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "报表测试实体类")
public class ReportTest {
    @Excel("学号")
    @ApiModelProperty(value = "学号")
    private Integer stuNo;
    @Excel("姓名")
    @ApiModelProperty(value = "姓名")
    private String name;
    @Excel("性别")
    @ApiModelProperty(value = "性别")
    private String sex;
    @Excel("年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @Excel("出生日期")
    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;
}
