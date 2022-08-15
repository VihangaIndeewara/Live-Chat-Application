package controller;

import com.sun.security.ntlm.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientDashBoardController extends Thread {
    public TextArea txtArea;
    public TextField txtField;
    public FileChooser chooser;
    public File path;
    public Label lblName;

    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;


    public void initialize(){
        String userName = ClientLoginFormController.userName;
        lblName.setText(userName);

        try {
            socket=new Socket("localhost",3088);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter=new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        String message=txtField.getText();
        printWriter.println(lblName.getText()+" : " + message);
        printWriter.flush();
        txtField.clear();
        if (message.equalsIgnoreCase("exit")) {
            Stage stage = (Stage) txtField.getScene().getWindow();
            stage.close();
        }

    }

    public void run(){
        try {
            while (true){
                String massage = bufferedReader.readLine();
                String[] tokens = massage.split(" ");
                String command = tokens[0];

                StringBuilder clientMassage = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    clientMassage.append(tokens[i] + " ");
                }

                String[] massageAr = massage.split(" ");
                String string = "";
                for (int i = 0; i < massageAr.length - 1; i++) {
                    string += massageAr[i + 1] + " ";
                }

                Text text = new Text(string);
                String fChar = "";

                if (string.length() > 3) {
                    fChar = string.substring(0, 3);
                }

                if (fChar.equalsIgnoreCase("img")) {
                    string = string.substring(3, string.length() - 1);

                    File file = new File(string);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitWidth(150);
                    imageView.setFitHeight(200);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!command.equalsIgnoreCase(lblName.getText())) {
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text("  " + command + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);
                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": Me ");
                        hBox.getChildren().add(text1);
                    }



                } else {
                    TextFlow tempTextFlow = new TextFlow();

                    if (!command.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text name = new Text(command + " ");
                        name.getStyleClass().add("name");
                        tempTextFlow.getChildren().add(name);
                    }

                    tempTextFlow.getChildren().add(text);
                    tempTextFlow.setMaxWidth(200);

                    TextFlow textFlow = new TextFlow(tempTextFlow);
                    HBox hBox = new HBox(12);

                    if (!command.equalsIgnoreCase(lblName.getText() + ":")) {

                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(textFlow);
                    } else {
                        Text text1 = new Text(clientMassage + ": Me");
                        TextFlow textFlow1 = new TextFlow(text1);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(textFlow1);
                    }

                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void photoOnMouseClickedOnAction(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        chooser = new FileChooser();
        chooser.setTitle("Open Image");
        this.path = chooser.showOpenDialog(stage);
        printWriter.println(lblName.getText() + " " + "img" + path.getPath());
        printWriter.flush();
    }
}
