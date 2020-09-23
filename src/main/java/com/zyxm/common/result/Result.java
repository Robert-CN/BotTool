package com.zyxm.common.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Robert
 * @Create 2020/9/21
 * @Desc TODO
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    @ApiModelProperty(value = "状态码", position = 1)
    private Integer code;
    @ApiModelProperty(value = "消息", position = 2)
    private String msg;
    @ApiModelProperty(value = "数据", position = 3)
    private T data;
}
