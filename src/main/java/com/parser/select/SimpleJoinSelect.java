package com.parser.select;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;

/**
 * Join连接
 * 目的SQL：
 * SELECT T_A.month, T_A.SUM_TOTAL  FROM KB_ZX_CUST_PROD_SUM T_A JOIN KB_ZX_CUST_PROD_SUM T_B ON T_A.ID = T_B.ID WHERE T_A.MONTH = '201512' AND  ( T_A.month = '12')  AND  ( T_A.VIP_TYPE = '高值商客')  AND  ( T_A.ZX_CUST_CODE = '12')
 */
public class SimpleJoinSelect {
    public static void main(String[] args){
        SimpleJoinSelect demo = new SimpleJoinSelect();
        String table= "KB_ZX_CUST_PROD_SUM";
        String[][] condition ={{"MONTH","12"},{"VIP_TYPE","高值商客"},{"ZX_CUST_CODE","12"}};
        String[][] outColumn = {{"MONTH"},{"SUM_TOTAL"}};
        String limit = "122";
        demo.selectObjectDemo(table, null,condition, outColumn,null,limit);
        demo.jsqlDemo(table, null,condition, outColumn,null,limit);
    }


    //SelectObject使用
    SelectObject selectObjectDemo(String table,String aliasName,String[][] condition,String[][] outColumn,String[][] groubyColumn,String limit){
        SimpleSelectDemo demo = new SimpleSelectDemo();
        SelectObject selectObject = demo.selectObjectDemo(table, aliasName, condition, outColumn, groubyColumn, limit);
        Join join = selectObject.addJoin(table, "T_B", false);
        try {
            SelectObject.addJoinOnExpression(join, "T_A.ID = T_B.ID");
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        System.out.println("Join :"+selectObject);
        return selectObject;
    }

    //JSqlParser直接调用
    PlainSelect jsqlDemo(String ta,String aliasName,String[][] condition,String[][] outColumn,String[][] groubyColumn,String rowCount){
        SimpleSelectDemo demo = new SimpleSelectDemo();
        PlainSelect plainSelect = demo.jsqlDemo(ta, aliasName, condition, outColumn, groubyColumn, rowCount);
        List<Join> joins = new ArrayList<Join>();
        Join join = new Join();
        Table table = new Table(ta);
        table.setAlias(new Alias("T_B",false));
        join.setRightItem(table);
        //join.setSimple(true);
        BinaryExpression onExpression = new EqualsTo();
        onExpression.setLeftExpression(new Column(new Table("T_A"), "ID"));
        onExpression.setRightExpression(new Column("T_B.ID"));
        join.setOnExpression(onExpression);
        joins.add(join);
        plainSelect.setJoins(joins);
        System.out.println("Join :"+plainSelect);
        return plainSelect;
    }
}
