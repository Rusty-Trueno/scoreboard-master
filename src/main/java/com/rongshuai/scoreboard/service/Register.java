package com.rongshuai.scoreboard.service;

/**
 * Created by rongshuai on 2019/10/24 19:33
 */
public class Register {
    private String name;
    private String OpName;

    public Register(String name) {
        this.name = name;
        OpName = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpName() {
        return OpName;
    }

    public void setOpName(String opName) {
        OpName = opName;
    }
}
