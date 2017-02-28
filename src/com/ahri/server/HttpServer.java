package com.ahri.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by zouyingjie on 2017/2/15.
 */
public class HttpServer {

    public static final String IP = "127.0.0.1";
    public static final int PORT = 4321;

    public static void main(String[] args) {
        try {
            waitRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitRequest() throws Exception {
        ServerSocket serverSocket =
                new ServerSocket(PORT, 1, InetAddress.getByName(IP));
        System.out.println("Server is watting for connect...");

        while (true) {
            Socket acceptSocket = serverSocket.accept();
            SocketHandler.handle(acceptSocket);
        }
    }

}
