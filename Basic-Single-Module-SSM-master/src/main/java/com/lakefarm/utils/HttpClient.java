package com.lakefarm.utils;

import java.io.*;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.*;

public class HttpClient {
      
    public static final String METHOD_GET  = "GET";  
    public static final String METHOD_POST = "POST";  
      
    private final CookieManager cs;  
    private final String urlString;  
      
    public HttpClient(String urlString, CookieManager cm){  
        if (cm == null)  
            throw new RuntimeException("CookieManager for HttpClient() can NOT be NULL");  
          
        this.urlString =urlString;  
        this.cs = cm;  
    }  
      
    public BufferedReader doRequestGET(){
        return doRequest(METHOD_GET, null);  
    }  
      
    public BufferedReader doRequestPOST(String paramsToBePost){  

        return doRequest(METHOD_POST, paramsToBePost);
    }  
      
      
      
      
    private BufferedReader doRequest(String requestMethod, String paramsToBePost){  
          
        HttpURLConnection conn = null;
        DataOutputStream output = null;
        BufferedReader in = null;  
          
        try {  
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();  
  
            //属性"User-Agent" 和 "Host"必须有，否则报错： "您的请求来路不正确或验证字串不符，无法提交。"  
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9");  
            conn.setRequestProperty("Host",url.getHost());  
              
            conn.setDefaultUseCaches(false);  
            conn.setConnectTimeout(5000);  
            conn.setRequestProperty("Keep-Alive", "300");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
              
            cs.setCookies(conn);  
              
            if (requestMethod.toUpperCase().equals(METHOD_POST)) {  
                conn.setDoOutput(true);  
                conn.setDoInput(true);  
                conn.setRequestMethod(requestMethod);  
                  
  
                //setInstanceFollowRedirects can then be used to set if   
                //redirects should be followed or not and this should be used before the  
                //connection is established (via getInputStream, getResponseCode, and other  
                //methods that result in the connection being established).  
  
                //inorder to disable the redirects  
                conn.setInstanceFollowRedirects(false);  
                  
//              application/x-www-form-urlencoded  
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
             
                output=new DataOutputStream(conn.getOutputStream());  
                output.writeBytes(paramsToBePost);  
                output.flush();  
                output.close();  
                  
            } else {  
//              User-Agent  Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.9) Gecko/20100315 Firefox/3.5.9  
//              Accept  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8  
//              Accept-Language zh-cn,zh;q=0.5  
//              Accept-Encoding gzip,deflate  
//              Accept-Charset  GB2312,utf-8;q=0.7,*;q=0.7  
                  
//                conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
//                conn.setRequestProperty("Accept-Language","zh-cn,zh;q=0.5");  
//                conn.setRequestProperty("Accept-Encoding","gzip,deflate");  
//                conn.setRequestProperty("Accept-Charset","GB2312,utf-8;q=0.7,*;q=0.7");  
  
            }  
              
            conn.connect();  
            cs.storeCookies(conn);  
              
            int code = conn.getResponseCode();  
  
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
  
//          String line;  
//          while ((line = in.readLine()) != null) {  
//              System.out.println(line);  
//          }            
  
        }catch(Exception e){  
            in = null;  
            e.printStackTrace();  
        }finally{  
            try {  
                if(output != null) output.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
          
        return in;  
    }

    public static void main(String[] args) {
//        HttpClient h=new HttpClient("http://localhost:8080/lakeFarm/GetUserInfo.from",“)
    }
}  