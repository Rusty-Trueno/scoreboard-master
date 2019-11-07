package com.rongshuai.scoreboard.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rongshuai on 2019/10/24 19:03
 */
public class FunctionUnitTable {
    private Function[] table = new Function[5];//功能状态部件表

    private Map<String, List<Function>> functionUnitMap = new HashMap<String,List<Function>>();//名称和功能部件列表的映射

    public Function[] getTable() {
        return table;
    }

    public Map<String, List<Function>> getFunctionUnitMap() {
        return functionUnitMap;
    }

    public FunctionUnitTable() {//功能部件表的初始化
        for(int i=0;i<5;i++){
            switch(i){
                case 0:
                    table[i] = new Function("整数");
                    break;
                case 1:
                    table[i] = new Function("乘法1");
                    break;
                case 2:
                    table[i] = new Function("乘法2");
                    break;
                case 3:
                    table[i] = new Function("加法");
                    break;
                case 4:
                    table[i] = new Function("除法");
                    break;
            }
        }
        functionUnitMap.put("LD", Arrays.asList(table[0]));//整数部件
        functionUnitMap.put("MULTD",Arrays.asList(table[1],table[2]));//乘法部件
        functionUnitMap.put("SUBD",Arrays.asList(table[3]));//加法部件
        functionUnitMap.put("ADDD",Arrays.asList(table[3]));//加法部件
        functionUnitMap.put("DIVD",Arrays.asList(table[4]));//除法部件
    }

    public void reset(){//功能单元表的重置
        for(int i=0;i<5;i++){
            table[i].reset();
        }
    }

    public void result(){
        System.out.println("功能部件状态表：");
        for(int i=0;i<5;i++){
            System.out.println("功能部件：" + table[i].getName());
            System.out.println("Busy:" + table[i].getBusy()
                 + ", Op:" + table[i].getOp() + ",Fi:" + table[i].getFi()
                    + ", Fj:" + table[i].getFj() + ", Fk:" + table[i].getFk()
                    + ", Qj:" + table[i].getQj() + ", Qk:" + table[i].getQk()
                    + ", Rj:" + table[i].getRj() + ", Rk:" + table[i].getRk()
            );
        }
    }
}
