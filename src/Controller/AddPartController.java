/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField partIDTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField maxInvTxt;
    @FXML
    private TextField minInvTxt;

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionInHouseRBtn(ActionEvent event) {

    }

    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {

    }

    @FXML
    void onActionSavePart(ActionEvent event) {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
