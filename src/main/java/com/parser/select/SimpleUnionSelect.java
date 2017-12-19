package com.parser.select;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperation;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.UnionOp;

public class SimpleUnionSelect {
    public static void main(String[] args){
        String table= "KB_ZX_CUST_PROD_SUM";
        String[][] condition ={{"MONTH","12"},{"VIP_TYPE","高值商客"},{"ZX_CUST_CODE","12"}};
        String[][] outColumn = {{"MONTH"},{"SUM_TOTAL"}};
        String limit = "122";
        SimpleUnionSelect demo = new SimpleUnionSelect();
        demo.selectDemo(table, null, condition, outColumn, null, limit);
        demo.jsqlDemo(table, null, condition, outColumn, null, limit);
    }
    //SelectObject使用
    SelectObject selectDemo(String table,String aliasName,String[][] condition,String[][] outColumn,String[][] groubyColumn,String limit){
        SimpleSelectDemo demo = new SimpleSelectDemo();
        SelectObject selectObject = demo.selectObjectDemo(table, aliasName, condition, outColumn, groubyColumn, limit);
        selectObject.addUnionSelect(demo.selectObjectDemo(table, aliasName, condition, outColumn, groubyColumn, limit), false, true);
        System.out.println("unionSelect:"+selectObject);
        return selectObject;
    }

    //JSqlParser直接调用
    SetOperationList jsqlDemo(String table,String aliasName,String[][] condition,String[][] outColumn,String[][] groubyColumn,String limit){
        SimpleSelectDemo demo = new SimpleSelectDemo();
        PlainSelect plainSelectA = demo.jsqlDemo(table, aliasName, condition, outColumn, groubyColumn, limit);
        PlainSelect plainSelectB = demo.jsqlDemo(table, aliasName, condition, outColumn, groubyColumn, limit);
        SetOperationList unionSelect = new SetOperationList();
        List<SelectBody> select = new ArrayList<SelectBody>();
        List<Boolean> brackets = new ArrayList<Boolean>();
        List<SetOperation> ops = new ArrayList<SetOperation>();
        //添加第一个Union语句
        select.add(plainSelectA);
        brackets.add(false);
        //添加其他的Union语句
        select.add(plainSelectB);
        brackets.add(false);
        UnionOp op = new UnionOp();
        op.setAll(true);
        ops.add(op);
        unionSelect.setBracketsOpsAndSelects(brackets, select, ops);
        System.out.println("unionSelect:"+unionSelect);
        return unionSelect;
    }
}