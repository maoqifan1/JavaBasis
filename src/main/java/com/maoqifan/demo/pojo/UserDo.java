package com.maoqifan.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDo {

    /**
     * User pojo
     */
    @UserAnnotation(name = "用户名")
    private String userName;
    @UserAnnotation(name = "密码")
    private String password;
    @UserAnnotation(name = "性别", dictValue = "0=女,1=男")
    private int sex;

}
