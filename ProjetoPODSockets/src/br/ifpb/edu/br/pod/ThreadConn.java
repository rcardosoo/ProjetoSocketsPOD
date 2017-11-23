/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.edu.br.pod;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class ThreadConn extends Thread {
    private Connection connection;
    private Controller controller;
    private String command;
    private boolean stop=false;

    public void run() {
        this.controller = new Controller(this.connection);
        
        while(!stop) {   
            try {
                DataInputStream in = new DataInputStream(this.connection.getSocket().getInputStream()); 
                this.command = in.readUTF();
              
                if (command.startsWith("send -all ") || command.startsWith("send -user ")) {
                    this.controller.send(command);
                } else if (command.equals("bye")) {
                    this.controller.bye();
                    stop = true;
                } else if (command.equals("list")) {
                    this.controller.list();
                } else if (command.startsWith("rename ")) {
                    String name = command.substring(7);
                    this.controller.rename(name);
                } else {
                    this.controller.invalido();
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadConn.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    public void setConnection(Connection conn) {
        this.connection = conn;
    }
    
    
}
