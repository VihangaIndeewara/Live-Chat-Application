package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    public static ArrayList<ClientHandler> clientHandlerArrayList= new ArrayList<>();
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            dataInputStream=new DataInputStream(socket.getInputStream());
            this.username=dataInputStream.readUTF();
            clientHandlerArrayList.add(this);
            message("SERVER : "+ username + "added to the group");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
                String clientMsg;
                while ((clientMsg=dataInputStream.readUTF())!=null){
                    if(clientMsg.equalsIgnoreCase("bye")) {
                        break;
                        for (ClientHandler c:clientHandlerArrayList) {
                            System.out.println(c.dataInputStream.readUTF());
                        }
                    }



                clientMsg=dataInputStream.readUTF();
                message(clientMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                close(socket,dataOutputStream,dataInputStream);
            }

    }



    public void message(String msgToSend){
        for (ClientHandler clientHandler:clientHandlerArrayList) {
            try {
                if (!clientHandler.username.equals(username)){
                    clientHandler.dataOutputStream.writeUTF(msgToSend);
                    clientHandler.dataOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void close(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream){
        try {
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
