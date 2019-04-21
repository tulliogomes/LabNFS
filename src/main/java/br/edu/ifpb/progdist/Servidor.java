package br.edu.ifpb.progdist;

import ch.qos.logback.core.CoreConstants;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Servidor {
    public static void main (String[] args){
        ServerSocket servidor = null;
        final String RAIZ = "D:\\";
        try {
            servidor = new ServerSocket(5000);
            System.out.println("Servidor Online!");

            Socket socket = servidor.accept();
            System.out.println("Cliente Conectado!");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true){
                String resp = dis.readUTF();
                System.out.println("Cliente: " + resp);
                //if (resp.equals("xau")) break;
                switch(resp){
                    case "sair":
                        socket.close();
                        break;

                    case "readdir":
                        System.out.println("Comando Recebido");
                        File arquivo = new File(RAIZ);
                        String arquivos = "";
                        for(File arq: arquivo.listFiles()){
                            arquivos += arq.getName() + "\n";
                        }
                        dos.writeUTF(arquivos);
                        break;

                    case "rename":
                        System.out.println("Comando Recebido!");
                        String renamemsg = "Qual arquivo deseja renomear?";
                        dos.writeUTF(renamemsg);
                        String resp1 = dis.readUTF();
                        //Path oldnamep = Paths.get(RAIZ + resp1);
                        File oldname = new File(RAIZ + resp1);
                        String renamemsg2 = "Qual o novo nome do arquivo?";
                        dos.writeUTF(renamemsg2);
                        String resp2 = dis.readUTF();
                        //Path newnamep = Paths.get(RAIZ + resp2);
                        File newnamep = new File(RAIZ + resp2);
                        Boolean resp3 = oldname.renameTo(newnamep);
                        dos.writeUTF( "O arquivo " + resp1 + " foi alterado para " + resp2);
                        break;

                    case "create":
                        System.out.println("Comando Recebido!");
                        String createmsg = "Qual o nome do arquivo?";
                        dos.writeUTF(createmsg);
                        resp = dis.readUTF();
                        Path createp = Paths.get(RAIZ + resp);
                        Files.createFile(createp);
                        dos.writeUTF( "O arquivo " + resp + " foi criado!");

                        break;

                    case "remove":
                        System.out.println("Comando Recebido!");
                        String deletemsg = "Qual o nome do arquivo a ser removido?";
                        dos.writeUTF(deletemsg);
                        resp = dis.readUTF();
                        Path deletep = Paths.get(RAIZ + resp);
                        Files.delete(deletep);
                        dos.writeUTF( "O arquivo " + resp + " foi removido!");
                        break;

                }

                //Scanner sc = new Scanner(System.in);
                //System.out.println("Servidor: ");
                //String mensagem = sc.nextLine();
                //dos.writeUTF(mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
