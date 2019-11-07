package com.rongshuai.scoreboard.service;

import java.util.Scanner;

/**
 * Created by rongshuai on 2019/10/24 19:56
 */
public class Test {
    public static void main(String[] args) {
        InstructionStatusTable instructionStatusTable = new InstructionStatusTable();
        FunctionUnitTable functionUnitTable = new FunctionUnitTable();
        RegisterTable registerTable = new RegisterTable();
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        while(!str.equals("-1")){//接受指令
            instructionStatusTable.addInstruction(str);
            str = scan.nextLine();
        }
        System.out.println("请输入想要跳到的步骤：（输入-1结束）");
        int step = scan.nextInt();
        while(step != -1){
            instructionStatusTable.reset();
            functionUnitTable.reset();
            registerTable.reset();
            instructionStatusTable.goto_step(step,functionUnitTable,registerTable);
            instructionStatusTable.result();
            functionUnitTable.result();
            registerTable.result();
            step = scan.nextInt();
        }
    }
}
