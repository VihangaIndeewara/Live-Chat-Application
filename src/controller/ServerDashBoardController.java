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
    Socket localSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message= "";

    public void initialize(){
        new Thread(() -> {
            try {
                serverSocket=new ServerSocket(PORT);
                txtArea.appendText("Server Started!!!");
                localSocket=serverSocket.accept();
                txtArea.appendText("Client Connected!!!");

                dataInputStream=new DataInputStream(localSocket.getInputStream());
                dataOutputStream=new DataOutputStream(localSocket.getOutputStream());

                while (!message.equals("bye")){
                    message=dataInputStream.readUTF();
                    txtArea.appendText(message);
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
}
