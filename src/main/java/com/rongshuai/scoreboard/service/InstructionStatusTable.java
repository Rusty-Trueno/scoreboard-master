package com.rongshuai.scoreboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rongshuai on 2019/10/24 19:02
 */
public class InstructionStatusTable {
    private ArrayList<Instruction> list = new ArrayList<Instruction>();//指令状态列表


    public ArrayList<Instruction> getList() {
        return list;
    }

    public void eraseList(){
        list.clear();
    }

    public void addInstruction(String instruction){
        String[] str = instruction.split(" ");
        String op = str[0];
        String[] str1 = str[1].split(",");
        String dr,sr1,sr2;
        if(str1.length==2){
            dr = str1[0];
            sr1 = str1[1].split("\\(")[1].split("\\)")[0];
            sr2 = "";
        }else{
            dr = str1[0];
            sr1 = str1[1];
            sr2 = str1[2];
        }
        Map<String,Integer> cycle = new HashMap<String,Integer>();
        cycle.put("IS",0);
        cycle.put("RO",0);
        cycle.put("EX",0);
        cycle.put("WR",0);
        list.add(new Instruction(instruction,op,sr1,sr2,dr,"",cycle));
    }

    public void reset(){//指令状态表的重置
        list.forEach(instruction -> {
            instruction.setStatus("");
            instruction.setCurrentEXTime(0);
            instruction.setRead(false);
            Map<String,Integer> cycle = new HashMap<String,Integer>();
            cycle.put("IS",0);
            cycle.put("RO",0);
            cycle.put("EX",0);
            cycle.put("WR",0);
            instruction.setCycle(cycle);
        });
    }

    public int getRegisterNum(String name){
        return Integer.parseInt(name.substring(1));//返回寄存器名字对应的寄存器编号
    }

    public void fresh(Function[] currentFunctionTable,RegisterTable registerTable){
        //对当前指令的功能部件状态表中对应的行以及对应的目的寄存器的状态进行更新（执行完此步后，才进行指令的流水线操作）
        for(int i=0;i<currentFunctionTable.length;i++){
            Function currentFunction = currentFunctionTable[i];
            String Sr1 = currentFunction.getFj();
            String Sr2 = currentFunction.getFk();
            if(!currentFunction.getBusy()){
                currentFunction.reset();
            }
            if(!currentFunction.getQj().equals("")){
                int index = Integer.parseInt(Sr1.substring(1));
                Register currentRegister = registerTable.getTable()[index];
                if(currentRegister.getOpName().equals("")){
                    currentFunction.setRj("yes");
                    currentFunction.setQj("");
                }else{
                    currentFunction.setRj("no");
                }
            }else{
                if(!Sr1.equals("")){
                    currentFunction.setRj("yes");
                    currentFunction.setQj("");
                }
            }
            if(!currentFunction.getQk().equals("")){
                int index = Integer.parseInt(Sr2.substring(1));
                Register currentRegister = registerTable.getTable()[index];
                if(currentRegister.getOpName().equals("")){
                    currentFunction.setRk("yes");
                    currentFunction.setQk("");
                }else{
                    currentFunction.setRk("no");
                }
            }else{
                if(!Sr2.equals("")){
                    currentFunction.setRk("yes");
                    currentFunction.setQk("");
                }
            }
        }
    }

    public void eraseThisInstruction(Instruction currentInstruction,RegisterTable registerTable){
        String dr = currentInstruction.getDR();
        registerTable.getTable()[getRegisterNum(dr)].setOpName("");//将目的寄存器设置为空
        Function currentFunction = currentInstruction.getFunction();//获取当前指令对应的功能部件
        currentFunction.setRk("");
        currentFunction.setRj("");
        currentFunction.setQk("");
        currentFunction.setQk("");
        currentFunction.setQj("");
        currentFunction.setFk("");
        currentFunction.setFj("");
        currentFunction.setBusy(false);
        currentFunction.setOp("");
        currentInstruction.setCurrentEXTime(0);
        currentInstruction.setStatus("OVER");
    }

    public void goto_step(int step,FunctionUnitTable functionUnitTable,RegisterTable registerTable){
        for(int i=0;i<step;i++){
            List<Instruction> WRs = new ArrayList<Instruction>();//当前已经执行到WR完成的指令列表
            for(int j=0;j<list.size();j++){
                String op = list.get(j).getOP();
                String sr1 = list.get(j).getSR1();
                String sr2 = list.get(j).getSR2();
                String dr = list.get(j).getDR();
                List<Function> currentFunctionList = functionUnitTable.getFunctionUnitMap().get(op);//获取当前指令对应的功能部件列表
                if(list.get(j).getStatus().equals("")){//如果指令准备流出
                    int indexOfRegister = Integer.parseInt(dr.substring(1));
                    Register currentRegister = registerTable.getTable()[indexOfRegister];//获取当前的目的寄存器的状态
                    for(int k=0;k<currentFunctionList.size();k++){//遍历当前指令对应的功能部件表，查找可用的功能部件，并使用
                        if(!currentFunctionList.get(k).getBusy()){
                            //如果对应的功能部件空闲
                            if(currentRegister.getOpName().equals("")){//如果没有其他指令占用当前的目的寄存器
                                list.get(j).setStatus("IS");//将指令状态设置为流出成功
                                String DrOpName = currentFunctionList.get(k).getName();//获取当前指令对应的目的寄存器对应的当前部件名称
                                String RjOpName = registerTable.getTable()[getRegisterNum(sr1)].getOpName();
                                String RkOpName = "";
                                if(!sr2.equals("")){
                                    RkOpName = registerTable.getTable()[getRegisterNum(sr2)].getOpName();
                                }
                                currentRegister.setOpName(DrOpName);//将当前指令的部件名称写到当前的目的寄存器中
                                currentFunctionList.get(k).setBusy(true);//将当前功能部件设置为忙状态
                                currentFunctionList.get(k).setOp(op);//当前功能部件对应的方法
                                currentFunctionList.get(k).setFi(dr);//目的寄存器
                                currentFunctionList.get(k).setFj(sr1);//源寄存器1
                                currentFunctionList.get(k).setFk(sr2);//源寄存器2
                                currentFunctionList.get(k).setQj(RjOpName);//向源寄存器1中写结果的功能部件
                                currentFunctionList.get(k).setQk(RkOpName);//向源寄存器2中写结果的功能部件
                                list.get(j).setFunction(currentFunctionList.get(k));//将功能部件表添加到当前指令对象中
                                Function currentFunctionUnit = list.get(j).getFunction();//获取当前指令对应的功能部件
                                if((!sr1.equals(""))&&(!currentFunctionUnit.getRj().equals("no"))){
                                    currentFunctionUnit.setRj("yes");
                                }
                                if((!sr2.equals(""))&&(!currentFunctionUnit.getRk().equals("no"))){
                                    currentFunctionUnit.setRk("yes");
                                }
                                list.get(j).getCycle().put("IS",i+1);
                            }
                        }
                        break;//遇到一个空闲的功能部件，就不用继续遍历剩下的功能部件了
                    }
                    break;
                }else if(list.get(j).getStatus().equals("IS")){//如果指令已经成功流出
                    Function currentFunctionUnit = list.get(j).getFunction();//获取当前指令对应的功能部件
                    if((!currentFunctionUnit.getRk().equals("no"))&&(!currentFunctionUnit.getRj().equals("no"))){
                        //如果当前指令的源寄存器均没有被占用，则可以对该指令进行读操作数
                        list.get(j).setStatus("RO");//将指令状态修改读操作数成功
                        list.get(j).getCycle().put("RO",i+1);
                    }
                }else if(list.get(j).getStatus().equals("RO")){//如果指令已经读完了操作数
                    list.get(j).setCurrentEXTime(list.get(j).getCurrentEXTime() + 1);//将当前指令执行时间+1
                    int currentEXTime = list.get(j).getCurrentEXTime();
                    if(currentEXTime >= list.get(j).getTotalEXTime()){
                        //如果指令执行完成
                        list.get(j).setStatus("EX");//将指令状态设置为执行成功
                        list.get(j).getCycle().put("EX",i+1);
                    }
                }else if(list.get(j).getStatus().equals("EX")){//如果指令已经执行成功了
                    int mark =0;//用来标记是否目前有指令的读寄存器
                    for(int k=0;k<j;k++){
                        if(!list.get(k).getRead()&&!list.get(k).getSR1().equals("")&&list.get(k).getSR1().substring(1).equals(dr.substring(1))){
                            mark=1;
                            break;
                        }
                        if(!list.get(k).getRead()&&!list.get(k).getSR2().equals("")&&list.get(k).getSR2().substring(1).equals(dr.substring(1))){
                            mark=1;
                            break;
                        }
                    }
                    if(mark == 0){
                        //如果不存在WAR相关，则可以写回
                        list.get(j).setStatus("WR");//将当前指令的状态修改为写回成功
                        WRs.add(list.get(j));//将当前指令添加到已经执行完的指令列表
                        list.get(j).getCycle().put("WR",i+1);
                    }

                }
//                else if(list.get(j).getStatus().equals("WR")){
//                    eraseThisInstruction(list.get(j),registerTable);
//                }
            }
            for(int k = 0;k<WRs.size();k++){
                eraseThisInstruction(WRs.get(k),registerTable);
            }
            fresh(functionUnitTable.getTable(),registerTable);
            for(int k = 0;k<list.size();k++){
                if(list.get(k).getStatus().equals("RO")&&!list.get(k).getRead()){
                    list.get(k).setRead(true);
                }
            }
        }
    }
    public void result(){
        list.forEach(instruction ->{
            System.out.println("指令名称：" + instruction.getName()
                    + "运行状态：" + instruction.getStatus()
                    + "所需执行时间：" + (instruction.getTotalEXTime()-instruction.getCurrentEXTime())
                    + "各状态对应的时钟周期数----IS:" + (instruction.getCycle().get("IS") == 0 ? " ":instruction.getCycle().get("IS"))
                    + " RO:" + (instruction.getCycle().get("RO") == 0 ? " ":instruction.getCycle().get("RO"))
                    + " EX:" + (instruction.getCycle().get("EX") == 0 ? " ":instruction.getCycle().get("EX"))
                    + " WR:" + (instruction.getCycle().get("WR") == 0 ? " ":instruction.getCycle().get("WR"))
            );
        });
    }
}
