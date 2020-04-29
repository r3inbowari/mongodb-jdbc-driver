package com.dbschema.debug;


//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

import org.bson.conversions.Bson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class FTQQDebugConnector {

    private static String host = "https://sc.ftqq.com/";
    private static String key = "SCU95761Te034c249fe732b1b5cb523f8effaf9d05ea6ece34e3d9";

//    public static void okPost(String title, String content) throws IOException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        Request request = new Request.Builder()
//                .url(host + key + ".send?text=" + title + "&desp=" + content)
//                .method("GET", null)
//                .build();
//        Response response = client.newCall(request).execute();
//    }

    public static void main(String[] args) throws IOException {
        // DebugMsg("主人服务器又挂掉啦~", "hhhhhh");
        // okPost("gotoschool", "hhhhhh");
    }

    static int newpoint = 0;

    public static String push() {
        newpoint++;
        return String.valueOf(newpoint);
    }

    public static void DebugMsg(String title, String content) {
        doGet(host + key + ".send?text=" + title + push() + "&desp=" + content);
    }

    public static void DebugMsg(String content) {
        doGet(host + key + ".send?text=DEBUG" + push() + "&desp=" + content);
    }

    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return result;
    }
}
