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

    /**
     * in-house radio button
     */
    @FXML
    private RadioButton inHouseRBtn;

    /**
     * outsourced radio button
     */
    @FXML
    private RadioButton outsourcedRBtn;
    /**
     * part name text field
     */
    @FXML
    private TextField partNameTxt;
    
    /**
     * part price text field
     */
    @FXML
    private TextField partPriceTxt;
    /**
     * max inventory text field
     */
    @FXML
    private TextField maxInvTxt;

    /**
     * min inventory text field
     */
    @FXML
    private TextField minInvTxt;
    
    /**
     * part ID text field
     */
    @FXML
    private TextField partIDTxt;
    
    /**
     * error label
     */
    @FXML
    private Label errorLabel;

    /**
     * error message label
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
        alert.setTitle("Cancel Add Part");
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
     * In-house radio button selected
     * @param event for clicking the in-house radio button
     */
    @FXML
    void onActionInHouseRBtn(ActionEvent event) {
        partIDLabel.setText("Machine ID");
    }

    /**
     * Outsourced radio button selected
     * @param event for clicking the outsourced radio button
     */
    @FXML
    void onActionOutsourcedRBtn(ActionEvent event) {
        partIDLabel.setText("Company Name");
    }

    /**
     * Part save event
     * @param event
     * @throws java.io.IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        
        try {
            int id = 0;
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
                                newInHousePart.setId(Inventory.getNewPartId());
                                Inventory.addPart(newInHousePart);
                                partAdded = true;
                            } catch (Exception e) {
                            //Invalid machine ID error
                            errorLabel.setVisible(true);
                            errorTxtLabel.setText("Invalid machine ID.");
                            errorTxtLabel.setVisible(true);
                            }
                        } 
                        if (outsourcedRBtn.isSelected()) {
                            companyName = partIDTxt.getText();
                            OutsourcedPart newOutsourcedPart = new OutsourcedPart(id, name, price, stock, min, max, companyName);
                            newOutsourcedPart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newOutsourcedPart);
                            partAdded = true;
                        }
                        if (partAdded){
                            errorLabel.setVisible(false);                            
                            errorTxtLabel.setVisible(false);
                            //Confirm save
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Save Part");
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
     * Checks for min inventory logical error
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
     * Checks for inventory logical error
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
        inHouseRBtn.setSelected(true);
        partIDLabel.setText("Machine ID");
        errorLabel.setVisible(false);
        errorTxtLabel.setVisible(false);
    }    
    
}
