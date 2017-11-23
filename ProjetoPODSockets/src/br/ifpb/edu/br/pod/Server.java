/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.edu.br.pod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 *
 * @author Rafael
 */
public class Server {
    private static List<Connection> connections = Collections.synchronizedList(new ArrayList<Connection>());

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(6800, 100);
            System.out.println("Server started!");
            while (true) {
                System.out.println("...");
                Socket s = server.accept();
                System.out.println(s.getInetAddress()+" entrou.");
                Connection conn = new Connection(s);
                connections.add(conn);
                ThreadConn thread = new ThreadConn();
                thread.setConnection(conn);      
                thread.start();
            }

        } catch (IOException ex) {
            //
        }
    }

    public static List<Connection> getConnections() {
        return connections;
    }
   

}
