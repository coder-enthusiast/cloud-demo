package com.lsx.consumer.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;

    private String name;

    private String password;

    private String email;

    private String tel;

    private String head;

    private String realname;

    private String address;

    private Date createDate;

}
