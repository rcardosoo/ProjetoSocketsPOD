/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.edu.pod;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Rafael
 */
public class Client {
    
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 6800);
            ClientThread ct = new ClientThread(s);
            ct.start();
            while (true) {
                DataInputStream in = new DataInputStream(s.getInputStream());
                String m = in.readUTF();
                if(m.equals("bye")) {
                    break;
                } else {
                    System.out.println(m);
                }
            }
            s.close();
            System.out.println("Conexão encerrada!");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    
}
