package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
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
            close(socket,dataOutputStream,dataInputStream);
        }
    }

    @Override
    public void run() {
        String clientMsg;

        while (socket.isConnected()){
            try {
                clientMsg=dataInputStream.readUTF();
                message(clientMsg);
            } catch (IOException e) {
                e.printStackTrace();
                close(socket,dataOutputStream,dataInputStream);
            }
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

}
