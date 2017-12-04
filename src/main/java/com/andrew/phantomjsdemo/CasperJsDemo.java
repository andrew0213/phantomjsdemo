package com.andrew.phantomjsdemo;

import java.io.*;

/**
 *
 * @author xinhuiyang
 * @date 2017/12/4
 */
public class CasperJsDemo {
    public static void main(String[] args) {
        //根目录
        String casperJsPathRoot = "/Users/xinhuiyang/Documents/iCourtWork/phantomjsdemo/src/main/resources/casperjs/";
        //casperjs文件路径
        String casperJsPath = casperJsPathRoot + "node_modules/.bin/casperjs";
        //脚本名称
        String jsFileName = "casperDown.js";

        String resultStr = "";
        BufferedReader br = null;
        Process p=null;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(casperJsPath, jsFileName);
            processBuilder.command().add("--output-encoding=utf-8");
            processBuilder.command().add("--web-security=no");
            processBuilder.command().add("--ignore-ssl-errors=true");
            processBuilder.command().add("--ssl-protocol=any");

            processBuilder.directory(new File(casperJsPathRoot));
            /*
            //若已配置过phantomjs环境变量,此处可省略
            processBuilder.environment().put("PHANTOMJS_EXECUTABLE", "/usr/local/bin/phantomjs");*/

            p = processBuilder.start();
            InputStream is = p.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sbf = new StringBuilder();
            String tmp = "";
            while ((tmp = br.readLine()) != null) {
                sbf.append(tmp).append("\r\n");
            }
            resultStr = sbf.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(p!=null){
                p.destroy();
            }
        }
        System.out.println(resultStr);
    }
}
