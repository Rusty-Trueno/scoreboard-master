package com.rongshuai.scoreboard.service;

/**
 * Created by rongshuai on 2019/10/24 19:17
 */
public class Function {
    private String name;
    private Boolean Busy;
    private String Op;
    private String Fi;
    private String Fj;
    private String Fk;
    private String Qj;
    private String Qk;
    private String Rj;
    private String Rk;

    public Function(String name) {
        this.name = name;
        Busy = false;
        Op = "";
        Fi = "";
        Fj = "";
        Fk = "";
        Qj = "";
        Qk = "";
        Rj = "";
        Rk = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBusy() {
        return Busy;
    }

    public void setBusy(Boolean busy) {
        Busy = busy;
    }

    public String getOp() {
        return Op;
    }

    public void setOp(String op) {
        Op = op;
    }

    public String getFi() {
        return Fi;
    }

    public void setFi(String fi) {
        Fi = fi;
    }

    public String getFj() {
        return Fj;
    }

    public void setFj(String fj) {
        Fj = fj;
    }

    public String getFk() {
        return Fk;
    }

    public void setFk(String fk) {
        Fk = fk;
    }

    public String getQj() {
        return Qj;
    }

    public void setQj(String qj) {
        Qj = qj;
    }

    public String getQk() {
        return Qk;
    }

    public void setQk(String qk) {
        Qk = qk;
    }

    public String getRj() {
        return Rj;
    }

    public void setRj(String rj) {
        Rj = rj;
    }

    public String getRk() {
        return Rk;
    }

    public void setRk(String rk) {
        Rk = rk;
    }

    public void reset(){
        Busy = false;
        Op = "";
        Fi = "";
        Fj = "";
        Fk = "";
        Qj = "";
        Qk = "";
        Rj = "";
        Rk = "";
    }
}
