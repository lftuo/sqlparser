package com.parser.select;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SubSelect;

public class SimpleSubselectDemo {
    public static void main(String[] args){
        String table= "KB_ZX_CUST_PROD_SUM";
        String[][] condition ={{"MONTH","12"},{"VIP_TYPE","高值商客"},{"ZX_CUST_CODE","12"}};
        String[][] outColumn = {{"MONTH","COUNT"},{"SUM_TOTAL","SUM","CAST","double"},{"ZX_CUST_NAME"},{"CUST_SUBST_NAME"}};
        String[][] groubyColumn = {{"MONTH"},{"SUM_TOTAL"}};
        //子查询输出的字段
        String[][] suboutColumn = {{"MONTH"},{"SUM_TOTAL"},{"ZX_CUST_NAME"},{"CUST_SUBST_NAME"}};
        String limit = "122";
        SimpleSubselectDemo demo= new SimpleSubselectDemo();
        demo.suselectObjectDemo(table, condition, outColumn, groubyColumn, suboutColumn,limit);
        demo.jsqlDemo(table, condition, outColumn, groubyColumn, suboutColumn,limit);
    }

    //SelectObject使用
    SelectObject suselectObjectDemo(String table,String[][] condition,String[][] outColumn,String[][] groubyColumn,String[][] suboutColumn,String limit){
        SimpleSelectDemo demo = new SimpleSelectDemo();
        SelectObject selectObject = demo.selectObjectDemo("a", "a", null, outColumn, groubyColumn, limit);
        SelectObject subselectObject = demo.selectObjectDemo(table,null, condition, suboutColumn,null,null);
        selectObject.setSubselectObject(subselectObject, "a", false);;
        System.out.println("subselect:"+selectObject);
        return selectObject;
    }

    ////JSqlParser直接调用
    PlainSelect jsqlDemo(String ta,String[][] condition,String[][] outColumn,String[][] groubyColumn,String[][] suboutColumn,String rowCount){
        SimpleSelectDemo demo = new SimpleSelectDemo();
        PlainSelect plainSelect = demo.jsqlDemo("a", "a", null, outColumn, groubyColumn, rowCount);
        PlainSelect subplainSelect = demo.jsqlDemo(ta,null, condition, suboutColumn,null,null);
        SubSelect subselect = new SubSelect();
        subselect.setSelectBody(subplainSelect);
        subselect.setAlias(new Alias("a",false));
        plainSelect.setFromItem(subselect);
        System.out.println("subselect:"+plainSelect);
        return plainSelect;
    }
}