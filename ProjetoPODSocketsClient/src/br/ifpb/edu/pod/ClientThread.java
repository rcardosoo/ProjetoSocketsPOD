/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.edu.pod;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class ClientThread extends Thread {
    private Socket s;
    Scanner teclado = new Scanner(System.in);

    public ClientThread(Socket s) {
        this.s = s;
    }
    
    public void run() {
        try {
            while (true) {
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                //String m = JOptionPane.showInputDialog("Digite a sua mensagem:");
                String m = teclado.nextLine();
                out.writeUTF(m);
                if (m.equals("bye")) {
                    System.out.println("Fechando conexão...");
                    break;
                }
            }
        } catch (IOException ex) {
            //
        }
    }
}
