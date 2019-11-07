package com.rongshuai.scoreboard.service;

/**
 * Created by rongshuai on 2019/10/24 19:03
 */
public class RegisterTable {
    private Register[] table = new Register[31];
    public RegisterTable(){
        for(int i=0;i<31;i++){
            table[i] = new Register("F" + i);
        }
    }

    public Register[] getTable() {
        return table;
    }

    public void reset(){//寄存器表的重置
        for(int i=0;i<31;i++){
            table[i].setOpName("");
        }
    }

    public void result(){
        System.out.println("结果寄存器状态表");
        for(int i=0;i<31;i++){
            System.out.println("寄存器：" + table[i].getName() + ", 部件名称：" + table[i].getOpName());
        }
    }
}
