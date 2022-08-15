package controller;

import com.sun.security.ntlm.Client;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientDashBoardController extends Thread {
    public TextArea txtArea;
    public TextField txtField;

    final int PORT=3088;
    public Label lblName;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message= "";

    public void initialize(){
        String userName = ClientLoginFormController.userName;
        lblName.setText(userName);

        try {
            socket=new Socket("localhost",PORT);
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        message=txtField.getText();
        dataOutputStream.writeUTF(lblName.getText()+" : " + txtField.getText().trim());
        dataOutputStream.flush();
        txtField.clear();
    }

    public void photoOnMouseClickedOnAction(MouseEvent mouseEvent) {
    }
}
