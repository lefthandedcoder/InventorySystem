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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ModifyPartController implements Initializable {
    Stage stage;
    Parent scene;
    Part currPart;
    
    /**
     * The label for either the machine ID or the company name
     */
    @FXML
    private Label partIDLabel;    
    @FXML
    private TextField autoIDTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partPriceTxt;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField maxInvTxt;
    @FXML
    private TextField minInvTxt;
    @FXML
    private TextField partIDTxt;

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param event for clicking the in-house radio button
     */
    @FXML
    void onActionInHouseRBtn(ActionEvent event) {
        partIDLabel.setText("Machine ID");
    }

    /**
     *
     * @param event for clicking the outsourced radio button
     */
    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {
        partIDLabel.setText("Company Name");

    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        
        int id = currPart.getId();
        String name = partNameTxt.getText();
        int stock = Integer.parseInt(partInvTxt.getText());
        double price = Double.parseDouble(partPriceTxt.getText());
        int max = Integer.parseInt(maxInvTxt.getText());
        int min = Integer.parseInt(minInvTxt.getText());
        int machineId;
        String companyName;
        boolean partAdded = false;
        
        if(inHouseRBtn.isSelected()) {
            machineId = Integer.parseInt(partIDTxt.getText());
            InHousePart newInHousePart = new InHousePart(id, name, price, stock, min, max, machineId);
            Inventory.addPart(newInHousePart);
            partAdded = true;
        } 
        if (outsourcedRBtn.isSelected()) {
            companyName = partIDTxt.getText();
            OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
            Inventory.addPart(newOutsourcedPart);
            partAdded = true;
        }
        if (partAdded){
            Inventory.deletePart(currPart);
            onActionDisplayMainMenu(event);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currPart = MainController.getPartModify();
        if (currPart instanceof InHousePart) {
            inHouseRBtn.setSelected(true);
            partIDLabel.setText("Machine ID");
            partIDTxt.setText(String.valueOf(((InHousePart) currPart).getMachineId()));
        }

        if (currPart instanceof OutsourcedPart){
            outsourcedRBtn.setSelected(true);
            partIDLabel.setText("Company Name");
            partIDTxt.setText(((OutsourcedPart) currPart).getCompanyName());
        }

        autoIDTxt.setText(String.valueOf(currPart.getId()));
        partNameTxt.setText(currPart.getName());
        partInvTxt.setText(String.valueOf(currPart.getStock()));
        partPriceTxt.setText(String.valueOf(currPart.getPrice()));
        maxInvTxt.setText(String.valueOf(currPart.getMax()));
        minInvTxt.setText(String.valueOf(currPart.getMin()));
    }    
    
}
