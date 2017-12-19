package com.parser.test;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

import java.io.StringReader;
import java.util.List;

public class SelectParser {
    public static void main(String[] args) throws JSQLParserException {
        String statement = "SELECT " +
                "LOCATION_D.REGION_NAME, LOCATION_D.AREA_NAME, COUNT(DISTINCT INCIDENT_FACT.TICKET_ID) " +
                "FROM LOCATION_D, INCIDENT_FACT " +
                "WHERE ( LOCATION_D.LOCATION_SK=INCIDENT_FACT.LOCATION_SK ) " +
                "GROUP BY LOCATION_D.REGION_NAME, LOCATION_D.AREA_NAME";
        CCJSqlParserManager parserManager = new CCJSqlParserManager();
        Select select = (Select) parserManager.parse(new StringReader(statement));

        PlainSelect plain = (PlainSelect) select.getSelectBody();
        List selectitems = plain.getSelectItems();
        System.out.println(selectitems.size());
        for (int i = 0; i < selectitems.size(); i++) {
            Expression expression = ((SelectExpressionItem) selectitems.get(i)).getExpression();
            System.out.println("Expression:-" + expression);
            Column col=(Column)expression;
            System.out.println(col.getTable()+","+col.getColumnName());
        }
    }
}
