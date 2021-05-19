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

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class AddPartController implements Initializable {
    
    /**
     * The label for either the machine ID or the company name
     */
    @FXML
    private Label partIDLabel;
    
    /**
     * The stage for running the application
     */
    Stage stage;

    /**
     * The scene for switching windows
     */
    Parent scene;
    
    /**
     * The auto-generated part ID text field
     */
    @FXML
    private TextField autoIDTxt;

    /**
     * The part inventory text field
     */
    @FXML
    private TextField partInvTxt;

    /**
     * The toggle group for the radio buttons.
     */
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton inHouseRBtn;
    @FXML
    private RadioButton outsourcedRBtn;
    /**
     *
     */
    @FXML
    private TextField partNameTxt;
    
    /**
     *
     */
    @FXML
    private TextField partPriceTxt;
    /**
     *
     */
    @FXML
    private TextField maxInvTxt;

    /**
     *
     */
    @FXML
    private TextField minInvTxt;
    
    /**
     *
     */
    @FXML
    private TextField partIDTxt;

    /**
     *
     * @param event
     * @throws IOException
     */
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

    /**
     *
     * @param event
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        
        int id = 0;
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
            newInHousePart.setId(Inventory.getNewPartId());
            Inventory.addPart(newInHousePart);
            partAdded = true;
        } 
        if (outsourcedRBtn.isSelected()) {
            companyName = partIDTxt.getText();
            OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
            newOutsourcedPart.setId(Inventory.getNewPartId());
            Inventory.addPart(newOutsourcedPart);
            partAdded = true;
        }
        if (partAdded){
            onActionDisplayMainMenu(event);
        }

    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inHouseRBtn.setSelected(true);
        partIDLabel.setText("Machine ID");
    }    
    
}
