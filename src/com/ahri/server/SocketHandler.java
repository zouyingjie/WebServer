package com.ahri.server;

import com.ahri.server.constants.Constant;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;


/**
 * Created by zouyingjie on 2017/2/15.
 */
public class SocketHandler {

    private static Socket socket;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static JSONObject httpHead;
    private static BufferedReader reader;

    public static void handle(Socket socket) throws IOException {
        SocketHandler.socket = socket;
        inputStream = SocketHandler.socket.getInputStream();
        outputStream = SocketHandler.socket.getOutputStream();
        parseHttpHeader();
        resolveSocket();
        close();
    }

    /**
     * 提取HTTP请求头
     *
     * @throws IOException
     */
    private static void parseHttpHeader() throws IOException {

        reader = new BufferedReader(new InputStreamReader(inputStream));
        String httpHeadLine = reader.readLine();
        httpHead = HTTP.toJSONObject(httpHeadLine);
    }

    /**
     * 分发请求
     *
     * @throws IOException
     */
    private static void resolveSocket() throws IOException {
        System.out.println("Ready to solve");
        switch (getRequestMethod()) {
            case Constant.HTTP_REQUEST_GET:
                responseGetRequest();
                break;
            case Constant.HTTP_REQUEST_POST:
                responsePostRequest();
                break;
            default:
                responseOtherRequest();
                break;
        }
    }

    /**
     * 获取HTTP请求方法
     *
     * @return
     * @throws IOException
     */
    private static String getRequestMethod() throws IOException {
        return httpHead.optString("Method", "GET");
    }

    /**
     * 处理GET请求
     *
     * @throws IOException
     */
    private static void responseGetRequest() throws IOException {
        System.out.println("GET Request");

        String requestURI = httpHead.optString("Request-URI", "");
        String[] split = requestURI.split("\\?");

        if (split.length > 1) {
            JSONArray requestKeyArray = getRequestKey(split[1]);
            String s = RequestRoute.routeGetResponse(requestKeyArray);
            outputStream.write(s.getBytes());

        } else {
            responseOtherRequest();
        }

    }

    /**
     * 处理POST请求
     * <p>
     * TODO:解析POST中的实体数据,将其转换为Person类进行存储
     */
    private static void responsePostRequest() throws IOException {
        System.out.println("POST Request");
        String postData = loadPostData();
        RequestRoute.routePostResponse(postData);

    }

    /**
     * 加载post请求中的实体数据
     * @return
     * @throws IOException
     */
    private static String loadPostData() throws IOException {
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()){
                break;
            }
        }
        String data =  "";
        for (;;){
            data = reader.readLine();
            if (data.isEmpty()){
                break;
            }
            sb.append(data);
        }

       return sb.toString();
    }
    /**
     * 默认处理请求,除了GET和POST请求之外的所有请求一律用该方法返回
     * GET请求没有传入查询键值的时候也调用该方法
     *
     * @throws IOException
     */
    private static void responseOtherRequest() throws IOException {
        File file = new File(Constant.REUQEST_NOT_FOUND_PAGE_PATH);
        InputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, len);
        }
    }


    /**
     * 请求结束,关闭流
     *
     * @throws IOException
     */
    private static void close() throws IOException {
        inputStream.close();
        outputStream.close();
        System.out.println("Request Finished***************");
    }

    private static JSONArray getRequestKey(String requestParams) {
        JSONArray jsonArray = new JSONArray();
        String[] split = requestParams.split("&");

        for (String s : split) {
            String[] strings = s.split("=");
            String string = strings[1];
            jsonArray.put(string);
        }
        return jsonArray;
    }
}