package com.rongshuai.scoreboard.service;

import java.util.Map;

/**
 * Created by rongshuai on 2019/10/24 19:07
 */
public class Instruction {
    private String name;
    private String OP;
    private String SR1;
    private String SR2;
    private String DR;
    private String status;
    private int currentEXTime;
    private int totalEXTime;
    private Function function;
    private Boolean isRead;
    private Map<String,Integer> cycle;

    public Map<String, Integer> getCycle() {
        return cycle;
    }

    public void setCycle(Map<String, Integer> cycle) {
        this.cycle = cycle;
    }

    public Instruction(String name,String OP, String SR1, String SR2, String DR, String status,Map<String,Integer> cycle) {
        this.name = name;
        this.OP = OP;
        this.SR1 = SR1;
        this.SR2 = SR2;
        this.DR = DR;
        this.status = status;
        this.currentEXTime = 0;
        this.isRead = false;
        this.cycle=cycle;
        switch (OP){
            case "LD":
                this.totalEXTime = 1;
                break;
            case "SUBD":
                this.totalEXTime=2;
                break;
            case "ADDD":
                this.totalEXTime=2;
                break;
            case "MULTD":
                this.totalEXTime=10;
                break;
            case "DIVD":
                this.totalEXTime=40;
                break;
        }
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getOP() {
        return OP;
    }

    public void setOP(String OP) {
        this.OP = OP;
    }

    public String getSR1() {
        return SR1;
    }

    public void setSR1(String SR1) {
        this.SR1 = SR1;
    }

    public String getSR2() {
        return SR2;
    }

    public int getCurrentEXTime() {
        return currentEXTime;
    }

    public void setCurrentEXTime(int currentEXTime) {
        this.currentEXTime = currentEXTime;
    }

    public void setSR2(String SR2) {
        this.SR2 = SR2;
    }

    public String getDR() {
        return DR;
    }

    public void setDR(String DR) {
        this.DR = DR;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalEXTime() {
        return totalEXTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        if(SR2 == ""){
            return OP + "  " + DR + "," + SR1;
        }else{
            return OP + "  " + DR + "," + SR1 + "," + SR2;
        }

    }
}
