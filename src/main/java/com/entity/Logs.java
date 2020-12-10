package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/9
 * @Version
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Logs {
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String createDate ;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String sendDate ;
    private long longCode ;
    private long mobile ;
    private String smsContext;
    private int status ;
    private String ipAddr;
}
