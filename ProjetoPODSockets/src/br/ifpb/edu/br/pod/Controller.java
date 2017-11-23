/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifpb.edu.br.pod;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rafael
 */
public class Controller {

    private Connection conn;

    public Controller(Connection conn) {
        this.conn = conn;
    }

    public String createMessage(String textoDaMensagem) {
        Date dataHoraAtual = new Date();
        String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
        String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

        String texto = this.conn.getSocket().getInetAddress() + ":" + this.conn.getSocket().getLocalPort() + "/~"
                + this.conn.getName() + " : " + textoDaMensagem + " " + hora + "-" + data;

        return texto;
    }

    public void send(String param) {
        if (param.startsWith("send -all")) {
            
            String mensagem = param.substring(9);
            //para cada conexão vao ser enviada a mensagem
            if(mensagem.equals("")){
                try {
                    DataOutputStream out = new DataOutputStream(conn.getSocket().getOutputStream());
                    out.writeUTF("Mensagem inválida!");
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                for (Connection conn : Server.getConnections()) {
                    try {
                        DataOutputStream out = new DataOutputStream(conn.getSocket().getOutputStream());
                        //vai remover o "send -all " da mensagem
                        mensagem = param.substring(9);
                        //se a mensagem estiver o OK, manda ela para o metódo createMessage criar ela e após envia para o user
                        String text = createMessage(mensagem);
                        out.writeUTF(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Boolean flag = false;
            //remove o "send -user " da mensagem
            String m = param.substring(11);
            for (Connection conn : Server.getConnections()) {
                //vai verificar se o nome do usuario que ta sendo enviado a mensagem é o mesmo do user da vez
                if (m.startsWith(conn.getName())) {
                    //caso o user exista flag´= true
                    flag = true;
                    try {
                        DataOutputStream out = new DataOutputStream(conn.getSocket().getOutputStream());
                        //pega o tamanho do nome do usuário para remover da string
                        int nomeSize = conn.getName().length();
                        //aqui remove o nome do usuário e +1 é do espaço
                        String mensagem = m.substring(nomeSize + 1);
                        //vai verificar se a mensagem está vazia, se estiver mostra erro
                        if(mensagem.equals("")){
                            //out.writeUTF("Mensamge inválida!");
                        }else{
                            //se a mensagem estiver ok envia manda para o createMessage e depois manda
                            String text = createMessage(mensagem);
                            out.writeUTF(text);
                        }                    
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }//se cair aqui, não achou o user da mensagem
            if (!flag) {
                DataOutputStream out;
                try {
                    out = new DataOutputStream(conn.getSocket().getOutputStream());
                    out.writeUTF("Usuário inexistente!");
                } catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public void rename(String name) {
        DataOutputStream out;
        try {
            out = new DataOutputStream(conn.getSocket().getOutputStream());
            if (userExists(name)) {
                out.writeUTF("Nome ja está em uso!");
            } else {
                if ((name == null) || (name == "") || ( name.startsWith(" "))) {
                    out.writeUTF("Nome inválido!");
                } else { 
                    this.conn.setName(name);
                    out.writeUTF("Renomeado com sucesso!");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean userExists(String name) {
        for (Connection conn : Server.getConnections()) {
            if (conn.getName() != null) {
                if (conn.getName().equals(name))
                    return true;
            }
        }
        return false;
    }

    public void bye() {
        try {
            System.out.println("bye " + conn.getSocket().getInetAddress());
            DataOutputStream out;

            out = new DataOutputStream(conn.getSocket().getOutputStream());
            out.writeUTF("bye");

            Server.getConnections().remove(this.conn);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void list() {
        String texto = "";
        for (Connection conn : Server.getConnections()) {
            texto += conn.toString();
        }
        DataOutputStream out;

        try {
            out = new DataOutputStream(conn.getSocket().getOutputStream());
            out.writeUTF(texto);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public void invalido() {
        DataOutputStream out;

        try {
            out = new DataOutputStream(conn.getSocket().getOutputStream());
            out.writeUTF("Comando inválido..");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
