package br.edu.ifpb.progdist;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {

        Socket socket = null;
        try {
            socket = new Socket("localhost", 5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true){
                Scanner sc = new Scanner(System.in);
                System.out.println("Cliente: ");
                String message = sc.nextLine();
                dos.writeUTF(message);

                String resp = dis.readUTF();
                //if (resp.equals("sair")) break;
                System.out.println("Servidor: " + resp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}