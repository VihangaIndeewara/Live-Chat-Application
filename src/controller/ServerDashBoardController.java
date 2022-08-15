package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerDashBoardController {
    public TextArea txtArea;
    public TextField txtField;

    final int PORT=3088;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message= "";

    public void initialize(){
        new Thread(() -> {
            try {
                serverSocket=new ServerSocket(PORT);
                txtArea.appendText("Server Started!!!");

                socket=serverSocket.accept();
                txtArea.appendText("\n New Client Connected!!!");

                ClientHandler clientHandler=new ClientHandler(socket);


                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());

                while (!message.equals("bye")){
                    message=dataInputStream.readUTF();
                    txtArea.appendText("\n\n"+message);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtField.getText().trim());
        dataOutputStream.flush();
        txtField.clear();
    }

    public  void  clear(){

            try {
                if (serverSocket!=null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
