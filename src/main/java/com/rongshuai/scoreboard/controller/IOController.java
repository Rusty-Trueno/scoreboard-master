package com.rongshuai.scoreboard.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rongshuai.scoreboard.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rongshuai on 2019/10/29 18:03
 */
@CrossOrigin(origins = {"http://127.0.0.1:8081","null"})
@RestController
@RequestMapping("IO")
public class IOController {
    private InstructionStatusTable instructionStatusTable = new InstructionStatusTable();
    private FunctionUnitTable functionUnitTable = new FunctionUnitTable();
    private RegisterTable registerTable = new RegisterTable();
    @RequestMapping(value="/inputInstructions",method = RequestMethod.POST)
    @ResponseBody
    public String inputInstructions(@RequestBody String instructions){
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(instructions);
        JsonArray jsonArray = jsonObject.getAsJsonArray("instructions");//解析输入的指令集
        instructionStatusTable.eraseList();
        for(JsonElement jsonElement : jsonArray){
            String instruction = jsonElement.getAsString();//获取用户在前端输入的每条指令
            instructionStatusTable.addInstruction(instruction);//向指令状态表中添加指令
        }
        System.out.println(jsonObject.toString());
        return jsonObject.toString();//将指令原封不动的返回
    }

    @RequestMapping(value = "/inputStep/{step}",method = RequestMethod.GET)
    @ResponseBody
    public String inputStep(@PathVariable("step") String step){
        int Step = Integer.parseInt(step);//获取用户输入的步数
        instructionStatusTable.reset();
        functionUnitTable.reset();
        registerTable.reset();
        instructionStatusTable.goto_step(Step,functionUnitTable,registerTable);
        JsonArray jsonArray1 = new JsonArray();//用于存储指令状态表的信息
        JsonArray jsonArray2 = new JsonArray();//用于存储功能部件状态表的信息
        JsonArray jsonArray3 = new JsonArray();//用于存储结果寄存器表的信息
        JsonObject jsonAll = new JsonObject();//用于存储所有额的信息
        System.out.println(instructionStatusTable.getList().size());
        for(int i=0;i<instructionStatusTable.getList().size();i++){
            Instruction instruction = instructionStatusTable.getList().get(i);
            JsonObject jsonObject = new JsonObject();
            if(instruction.getCycle().get("IS")==0){
                jsonObject.addProperty("IS",' ');
            }else{
                jsonObject.addProperty("IS",instruction.getCycle().get("IS"));
            }
            if(instruction.getCycle().get("RO")==0){
                jsonObject.addProperty("RO",' ');
            }else{
                jsonObject.addProperty("RO",instruction.getCycle().get("RO"));
            }
            if(instruction.getCycle().get("EX")==0){
                jsonObject.addProperty("EX",' ');
            }else{
                jsonObject.addProperty("EX",instruction.getCycle().get("EX"));
            }
            if(instruction.getCycle().get("WR")==0){
                jsonObject.addProperty("WR",' ');
            }else{
                jsonObject.addProperty("WR",instruction.getCycle().get("WR"));
            }
            jsonObject.addProperty("name",instruction.getName());
            jsonArray1.add(jsonObject);
        }
        for(int i=0;i<functionUnitTable.getTable().length;i++){
            Function function = functionUnitTable.getTable()[i];
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name",function.getName());
            jsonObject.addProperty("Busy",function.getBusy());
            jsonObject.addProperty("Op",function.getOp());
            jsonObject.addProperty("Fi",function.getFi());
            jsonObject.addProperty("Fj",function.getFj());
            jsonObject.addProperty("Fk",function.getFk());
            jsonObject.addProperty("Qj",function.getQj());
            jsonObject.addProperty("Qk",function.getQk());
            jsonObject.addProperty("Rj",function.getRj());
            jsonObject.addProperty("Rk",function.getRk());
            jsonArray2.add(jsonObject);
        }
        for(int i=0;i<registerTable.getTable().length;i++){
            Register register = registerTable.getTable()[i];
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name",register.getName());
            jsonObject.addProperty("OpName",register.getOpName());
            jsonArray3.add(jsonObject);
        }
        jsonAll.add("instruction",jsonArray1);
        jsonAll.add("function",jsonArray2);
        jsonAll.add("register",jsonArray3);
        return jsonAll.toString();
    }
}
