package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ModifyProductController implements Initializable {
    
    /**
     * stage variable
     */
    Stage stage;

    /**
     * scene variable
     */
    Parent scene;

    /**
     * selected product
     */
    Product currProduct;

    /**
     * currently associated parts
     */
    private ObservableList<Part> currParts = FXCollections.observableArrayList();
    
    /**
     * text field for product ID
     */
    @FXML
    private TextField productIDTxt;

    /**
     * text field for product inventory
     */
    @FXML
    private TextField productInvTxt;

    /**
     * text field for product price
     */
    @FXML
    private TextField productPriceTxt;

    /**
     * text field for product name
     */
    @FXML
    private TextField productNameTxt;

    /**
     * text field for max inventory
     */
    @FXML
    private TextField maxInvTxt;

    /**
     * text field for min inventory
     */
    @FXML
    private TextField minInvTxt;
    
    /**
     * part search label
     */
    @FXML
    private Label partSearchLabel;

    /**
     * part search text box
     */
    @FXML
    private TextField partSearchBox;

    /**
     * table of available parts
     */
    @FXML
    private TableView<Part> availPartsTableView;

    /**
     * ID column for available parts
     */
    @FXML
    private TableColumn<Part, Integer> availPartIDCol;

    /**
     * name column for available parts
     */
    @FXML
    private TableColumn<Part, String> availPartNameCol;

    /**
     * inventory column for available parts
     */
    @FXML
    private TableColumn<Part, Integer> availPartInvCol;

    /**
     * price column for available parts
     */
    @FXML
    private TableColumn<Part, Integer> availPartPriceCol;
    
    /**
     * tale for currently associated parts
     */
    @FXML
    private TableView<Part> currPartsTableView;

    /**
     * ID column for associated parts
     */
    @FXML
    private TableColumn<Part, Integer> currPartIDCol;

    /**
     * Name column for associated parts
     */
    @FXML
    private TableColumn<Part, String> currPartNameCol;

    /**
     *  Inventory column for associated parts
     */
    @FXML
    private TableColumn<Part, Integer> currPartInvCol;

    /**
     * Price column for associated parts
     */
    @FXML
    private TableColumn<Part, Double> currPartPriceCol;

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
     * Add part event
     * @param event
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part currPart = availPartsTableView.getSelectionModel().getSelectedItem();
        currParts.add(currPart);
        currPartsTableView.setItems(currParts);
    }

    /**
     * Cancel event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        //Confirm cancel changes
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Modify Product");
        alert.setContentText("Cancel changes and return to main menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** RUNTIME ERROR: When fields are blank or values do not match data types,
     * the system cannot handle the action event. The try/catch code catches these errors
     * Modify event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {        
        try {
            int id = 0;
            String name = productNameTxt.getText();
            Double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            int min = Integer.parseInt(minInvTxt.getText());
            int max = Integer.parseInt(maxInvTxt.getText());
            boolean productAdded = false;

            if (name.isEmpty()) {
                /**RUNTIME ERROR: Name empty exception*/
                errorLabel.setVisible(true);
                errorTxtLabel.setText("Name cannot be empty.");
                errorTxtLabel.setVisible(true);
                } else {
                    if (minVerify(min, max) && inventoryVerify(min, max, stock)) {
                        
                        Product newProduct = new Product(id, name, price, stock, min, max);
        
                        for (Part part : currParts) {
                                        newProduct.addAssociatedPart(part);
                        }

                        newProduct.setId(Inventory.getNewProductId());
                        Inventory.addProduct(newProduct);
                        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                        productAdded = true;
                    }
                        if (productAdded){
                            errorLabel.setVisible(false);                            
                            errorTxtLabel.setVisible(false);
                            //Confirm modify
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Modify Product");
                            alert.setContentText("Save changes and return to main menu?");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                            Inventory.deleteProduct(currProduct);
                            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                            scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
                            stage.setScene(new Scene(scene));
                            stage.show();
                            }
                        }
                    }
        } catch(Exception e) {
            /**RUNTIME ERROR: Blank fields exception*/
            errorLabel.setVisible(true);
            errorTxtLabel.setText("Form contains blank fields or errors.");
            errorTxtLabel.setVisible(true);
        }
    }
    
    /**
     * Method for checking for min logical error
     * @param min
     * @param max
     * @return checks for min inventory logical error
     */
    private boolean minVerify(int min, int max) {

        boolean minLess = true;

        if (min <= 0 || min >= max) {
            minLess = false;
            /**LOGICAL ERROR: Min value error*/
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
     * @return checks for inventory logical error
     */
    private boolean inventoryVerify(int min, int max, int stock) {

        boolean invBetween = true;

        if (stock < min || stock > max) {
            invBetween = false;
            /**LOGICAL ERROR: Inventory value error*/
            errorLabel.setVisible(true);
            errorTxtLabel.setText("Inventory must be less than Max and greater than Min.");
            errorTxtLabel.setVisible(true);
        }

        return invBetween;
    }

    /**
     * Remove associated part event
     * @param event
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part currPart = currPartsTableView.getSelectionModel().getSelectedItem();
        if (currPart == null) {
            //RUNTIME ERROR: No part selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Remove Part");
            alert.setContentText("Part not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            // Confirm remove
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Part");
            alert.setContentText("Remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                currParts.remove(currPart);
                currPartsTableView.setItems(currParts); 
            }
        }

    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorLabel.setVisible(false);
        errorTxtLabel.setVisible(false);
        
        // Populating current product and associated part data
        currProduct = MainController.getProductModify();
        currParts = currProduct.getAllAssociatedParts();
        
        
        productIDTxt.setText(String.valueOf(currProduct.getId()));
        productNameTxt.setText(currProduct.getName());
        productInvTxt.setText(String.valueOf(currProduct.getStock()));
        productPriceTxt.setText(String.valueOf(currProduct.getPrice()));
        maxInvTxt.setText(String.valueOf(currProduct.getMax()));
        minInvTxt.setText(String.valueOf(currProduct.getMin()));
        
        //Populating available parts table
        availPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availPartsTableView.setItems(Inventory.getAllParts());
        
        //Populating currently associated parts table
        currPartsTableView.setItems(currParts);
        currPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        currPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        currPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        currPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // Wrapping observable lists (allParts and allProducts) in a filtered list
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);
        
        // Setting the filter predicate whenever the filter changes
        partSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredParts.setPredicate(part -> {
                // If filter text is empty, display all parts
            
                if (newValue == null || newValue.isEmpty()) {
                    partSearchLabel.setVisible(false);
                    return true;
                }
            
                //Compare all part names
                String search = newValue.toLowerCase();
                
                if (part.getName().toLowerCase().contains(search) || Integer.valueOf(part.getId()).toString().equals(search)) {
                    return true; // Filter matches part name or id.
                } else {
                    partSearchLabel.setText("Part not found!");
                    partSearchLabel.setVisible(true);
                    return false; // Does not match.
                }
            });
        });
        
        // Wrapping filtered list in a sorted list.
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);
        
        // Binding the sorted list comparator to the TableView comparator.
        sortedParts.comparatorProperty().bind(availPartsTableView.comparatorProperty());
        
        // Adding sorted (and filtered) parts to table.
        availPartsTableView.setItems(sortedParts);
    }
}
