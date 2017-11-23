/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.edu.br.pod;

import java.net.Socket;

/**
 *
 * @author Rafael
 */
public class Connection {
    private String name;
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public String toString() {
        if (name == null) {
            name = "Sem nome";
        }
        return "User: " + name + " IP: " + socket.getInetAddress() +" \n";
    }
    
    
}
