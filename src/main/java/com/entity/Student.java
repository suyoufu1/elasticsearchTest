package com.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName
 * @Author syf
 * @Date 2020/12/9
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @JsonIgnore
    private String id;

    private String name ;
    private Long age ;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;


}
