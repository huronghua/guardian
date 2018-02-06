package com.banmatrip.guardian.core.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtil {
    public static String addSingleQuote(String str){
        if (StringUtils.isNotBlank(str)) {
            String[] values = str.split(",");
            String valuesa="";
            String valuesb="";
            String pattern="\'([a-zA-Z0-9-\\--_]*)\'";//如果本身就有单引号则不必再加一层单引号
            for (int i = 0; i < values.length; i++) {
                if(Pattern.matches(pattern,values[i])){
                    valuesa+=values[i]+",";
                    continue;
                }
                valuesa+="'"+values[i]+"',";
            }
            valuesb=valuesa.substring(0,valuesa.length()-1);
            return valuesb;
        } else {
            return "";
        }
    }

    /**
     * 分割日期字符串为两个日期字符串
     * param example：20160101to20170214
     * return HashMap<String,String> example: begin:20160101  endDate:20170214
     * */
    public static Map<String,String> splitDateRange(String str){
        Map<String,String> date = new HashMap();
        try {
            String pattern = "\'([0-9-\\-]{10})to([0-9-\\-]{10})\'";//匹配：'2020-02-02to2020-02-03'
            if(Pattern.matches(pattern,str)){
                str = str.substring(1,str.length()-1);
            }
            String[] dateRange = str.split("to");
            if(dateRange.length==2) {
                date.put("beginDate", dateRange[0]);
                date.put("endDate", dateRange[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 字符添加单引号
     * @param string
     * @return
     */
    public static String stringAppendSingleQuote(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("'");
        stringBuffer.append(string);
        stringBuffer.append("'");
        return stringBuffer.toString();
    }

    /*
    * 拼接行转列
    * @param Map
    * @return String
    * 销售利润表专用
    * */
    public static String lineToRowAppend(List<Map<String,Object>> result){
        StringBuffer stringBuffer = new StringBuffer();
        if (CollectionUtils.isNotEmpty(result)) {
            for (Map<String,Object> map : result) {
                if (!MapUtils.isEmpty(map)) {
                    stringBuffer.append("sum(IF(id ='" + map.get("id") +"', 1, 0)) AS " + "'"+map.get("name") + "',");
                }
            }
        }
        return stringBuffer.toString();
    }
    /**List转String
     * @Param List:需要转换的List
     * @Param separator:分割list元素的符号
     * **/
    public static String listToString(List list, char separator) {
        StringBuffer sb = new StringBuffer();
        if(CollectionUtils.isNotEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i)).append(separator);
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public static List addSingleQuoteToList(String str){
        List<String> list = new ArrayList();
        if (StringUtils.isNotBlank(str)) {
            String[] values = str.split(",");
            for (int i = 0; i < values.length; i++) {
                list.add(values[i]);
            }
            return list;
        } else {
            return null;
        }
    }

    //字符分割list<Integer>
    public static List<Integer> addSingleQuoteToListInteger(String str){
        List<Integer> list = new ArrayList();
        if (StringUtils.isNotBlank(str)) {
            String[] values = str.split(",");
            for (int i = 0; i < values.length; i++) {
                String tmp = values[i] == null ? "0" : values[i];
                list.add(Integer.valueOf(tmp));
            }
            return list;
        } else {
            return null;
        }
    }
}
