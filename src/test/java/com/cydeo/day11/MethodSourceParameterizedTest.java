package com.cydeo.day11;

import com.cydeo.utilities.ExcelUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MethodSourceParameterizedTest {

    @ParameterizedTest
    @MethodSource("getNames")
    public void testMethodSource(String name){
        System.out.println(name);
    }

    public static List<String> getNames(){
        //you can get value from anywhere almost anytype and return to your test
        //DB
        //Excel
        //other APIs

        List<String> names = Arrays.asList("Emre", "Latife", "Umit", "Zeynep");
        return names;

    }



    public static List<Map<String, String>> getExcelData(){

        ExcelUtil vytrackFile = new ExcelUtil("src/test/resources/Vytracktestdata.xlsx", "QA3-short");
        return vytrackFile.getDataList();
    }

    @ParameterizedTest
    @MethodSource("getExcelData")
    public void TestWithExcel(Map<String , String> userInfo){

        System.out.println(userInfo.get("firstname") + " " + userInfo.get("lastname"));
    }


}
