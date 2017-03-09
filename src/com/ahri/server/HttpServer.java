package com.ahri.server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zouyingjie on 2017/2/15.
 */
public class HttpServer {

    public static void main(String[] args) {
        try {
            waitRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitRequest() throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        //只绑定端口, 既可以通过127.0.0.1访问, 也可以用本机的IP从外界访问
        serverSocket.bind(new InetSocketAddress(4321));

        System.out.println("Server is watting for connect...\n");
        System.out.println(serverSocket.toString());

        while (true) {
            Socket acceptSocket = serverSocket.accept();
            System.out.println("Already Connect");
            SocketHandler.handle(acceptSocket);
        }
    }

}
