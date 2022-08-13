package controller;

import com.sun.security.ntlm.Client;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FirstClientDashBoardController {
    public TextArea txtArea;
    public TextField txtField;

    final int PORT=3088;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message= "";

    public void initialize(){
      new Thread(() -> {
        try {
            socket=new Socket("localhost",PORT);
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());

            while (!message.equals("bye")){
                message=dataInputStream.readUTF();
                txtArea.appendText("\n"+message);
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

    public void photoOnMouseClickedOnAction(MouseEvent mouseEvent) {
    }
}
