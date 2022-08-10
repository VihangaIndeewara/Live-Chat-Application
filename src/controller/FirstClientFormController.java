package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class FirstClientFormController {
    public JFXTextField txtUserName;
    public AnchorPane LoginContext;
    static String name="";

    public static String getName(){
        return name;
    }

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().length()>0){
            name=txtUserName.getText();
            setUi("../view/FirstClientDashBoard");
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Enter User Name").show();
        }

    }

    public void setUi(String location) throws IOException {
        Stage stage = (Stage) LoginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(location+".fxml"))));
        stage.show();
    }
}
