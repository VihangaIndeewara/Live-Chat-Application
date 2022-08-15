package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerDashBoardController {
    public TextArea txtArea;
    public TextField txtField;

    final int PORT=3088;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    String message= "";

    public void initialize() throws IOException {
        serverSocket = new ServerSocket(PORT);
        txtArea.appendText("Server Started!!!");

        while (true) {
            socket = serverSocket.accept();
            txtArea.appendText("\n New Client Connected!!!");

            ClientHandler clientHandler = new ClientHandler(socket, clientHandlers);
            clientHandlers.add(clientHandler);
            clientHandler.start();
        }
    }
}
