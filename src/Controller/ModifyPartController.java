package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    /**
     * Stage for window switch
     */
    Stage stage;

    /**
     * Scene for window switch
     */
    Parent scene;

    /**
     * selected part
     */
    Part currPart;
    
    /**
     * The label for either the machine ID or the company name
     */
    @FXML
    private Label partIDLabel;    

    /**
     * Text field for part ID
     */
    @FXML
    private TextField autoIDTxt;

    /**
     * Text field for part inventory
     */
    @FXML
    private TextField partInvTxt;

    /**
     * Text field for part price
     */
    @FXML
    private TextField partPriceTxt;

    /**
     * Toggle group for radio buttons
     */
    @FXML
    private ToggleGroup toggleGroup;

    /**
     * In-house radio button
     */
    @FXML
    private RadioButton inHouseRBtn;

    /**
     * Outsourced radio button
     */
    @FXML
    private RadioButton outsourcedRBtn;

    /**
     * Text field for part name
     */
    @FXML
    private TextField partNameTxt;

    /**
     * Text field for max inventory
     */
    @FXML
    private TextField maxInvTxt;

    /**
     * Text field for min inventory
     */
    @FXML
    private TextField minInvTxt;

    /**
     * Text field for machine ID or company name
     */
    @FXML
    private TextField partIDTxt;

    /**
     * Error label
     */
    @FXML
    private Label errorLabel;

    /**
     * Error message label
     */
    @FXML
    private Label errorTxtLabel;

    /**
     * Cancel event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modify Part");
        alert.setContentText("Cancel changes and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * In-house button toggle
     * @param event for clicking the in-house radio button
     */
    @FXML
    void onActionInHouseRBtn(ActionEvent event) {
        partIDLabel.setText("Machine ID");
    }

    /**
     * Outsourced button toggle
     * @param event for clicking the outsourced radio button
     */
    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {
        partIDLabel.setText("Company Name");

    }

    /**
     * Modify event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        
        try {
            int id = currPart.getId();
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(maxInvTxt.getText());
            int min = Integer.parseInt(minInvTxt.getText());
            int machineId;
            String companyName;
            boolean partAdded = false;
        
        if (name.isEmpty()) {
                //RUNTIME ERROR: Name empty exception
                errorLabel.setVisible(true);
                errorTxtLabel.setText("Name cannot be empty.");
                errorTxtLabel.setVisible(true);
                } else {
                    if (minVerify(min, max) && inventoryVerify(min, max, stock)) {
                        
                        if(inHouseRBtn.isSelected()) {
                            try {
                                machineId = Integer.parseInt(partIDTxt.getText());
                                InHousePart newInHousePart = new InHousePart(id, name, price, stock, min, max, machineId);
                                newInHousePart.setId(currPart.getId());
                                Inventory.addPart(newInHousePart);
                                partAdded = true;
                            } catch (Exception e) {
                            //LOGICAL ERROR: Invalid machine ID error
                            errorLabel.setVisible(true);
                            errorTxtLabel.setText("Invalid machine ID.");
                            errorTxtLabel.setVisible(true);
                            }
                        } 
                        if (outsourcedRBtn.isSelected()) {
                            companyName = partIDTxt.getText();
                            OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                            newOutsourcedPart.setId(currPart.getId());
                            Inventory.addPart(newOutsourcedPart);
                            partAdded = true;
                        }
                        if (partAdded){
                            errorLabel.setVisible(false);                            
                            errorTxtLabel.setVisible(false);
                            Inventory.deletePart(currPart);
                            //Confirm modify
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modify Part");
                            alert.setContentText("Save changes and return to main menu?");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                            }
                        }
                    }
                }
        } catch(Exception e) {
            //RUNTIME ERROR: Blank fields exception
            errorLabel.setVisible(true);
            errorTxtLabel.setText("Form contains blank fields or errors.");
            errorTxtLabel.setVisible(true);
        }

    }

    /**
     * Method for checking for min inventory logical error
     * @param min
     * @param max
     * @return
     */
    private boolean minVerify(int min, int max) {

        boolean minLess = true;

        if (min <= 0 || min >= max) {
            minLess = false;
            //LOGICAL ERROR: Min value error
            errorLabel.setVisible(true);
            errorTxtLabel.setText("Min must be less than Max.");
            errorTxtLabel.setVisible(true);
        }

        return minLess;
    }
    
    /**
     * Method for checking for inventory logical error
     * @param min
     * @param max
     * @param stock
     * @return
     */
    private boolean inventoryVerify(int min, int max, int stock) {

        boolean invBetween = true;

        if (stock < min || stock > max) {
            invBetween = false;
            //LOGICAL ERROR: Inventory value error
            errorLabel.setVisible(true);
            errorTxtLabel.setText("Inventory must be less than Max and greater than Min.");
            errorTxtLabel.setVisible(true);
        }

        return invBetween;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Populating current part data
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
        
        
        errorLabel.setVisible(false);
        errorTxtLabel.setVisible(false);
    }    
    
}
