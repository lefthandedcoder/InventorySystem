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
public class AddProductController implements Initializable {
    
    /**
     * Stage for switching windows
     */
    Stage stage;

    /**
     * Scene for switching windows
     */
    Parent scene;        

    /**
     * Observable list of currently associated parts
     */
    private ObservableList<Part> currParts = FXCollections.observableArrayList();
    
    /**
     * Auto-generated ID text field
     */
    @FXML
    private TextField productIDTxt;

    /**
     * product inventory text field
     */
    @FXML
    private TextField productInvTxt;

    /**
     * product price text field
     */
    @FXML
    private TextField productPriceTxt;

    /**
     * product name text field
     */
    @FXML
    private TextField productNameTxt;

    /**
     * product max inventory text field
     */
    @FXML
    private TextField maxInvTxt;

    /**
     * product min inventory text field
     */
    @FXML
    private TextField minInvTxt;

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
     * part search message
     */
    @FXML
    private Label partSearchLabel;

    /**
     * part search box
     */
    @FXML
    private TextField partSearchBox;

    /**
     * available parts table
     */
    @FXML
    private TableView<Part> availPartsTableView;

    /**
     * available parts ID column
     */
    @FXML
    private TableColumn<Part, Integer> availPartIDCol;

    /**
     * available parts name column
     */
    @FXML
    private TableColumn<Part, String> availPartNameCol;

    /**
     * available parts inventory column
     */
    @FXML
    private TableColumn<Part, Integer> availPartInvCol;

    /**
     * available parts price column
     */
    @FXML
    private TableColumn<Part, Double> availPartPriceCol;
    
    /**
     * current parts table
     */
    @FXML
    private TableView<Part> currPartsTableView;

    /**
     * current parts ID column
     */
    @FXML
    private TableColumn<Part, Integer> currPartIDCol;

    /**
     * current parts name column
     */
    @FXML
    private TableColumn<Part, String> currPartNameCol;

    /**
     * current parts inventory column
     */
    @FXML
    private TableColumn<Part, Integer> currPartInvCol;

    /**
     * current parts price column
     */
    @FXML
    private TableColumn<Part, Double> currPartPriceCol;

    /**
     * Add part association to product event
     * @param event
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part selectedPart = availPartsTableView.getSelectionModel().getSelectedItem();
        currParts.add(selectedPart);
        currPartsTableView.setItems(currParts);
    }

    /**
     * Cancel event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add Product");
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
     * Remove part association with product
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
            //Confirm remove
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
     * Save product event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        
        try {
            int id = 0;
            String name = productNameTxt.getText();
            Double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            int min = Integer.parseInt(minInvTxt.getText());
            int max = Integer.parseInt(maxInvTxt.getText());
            boolean productAdded = false;

            if (name.isEmpty()) {
                //RUNTIME ERROR: Name empty exception
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
                        productAdded = true;
                    }
                        if (productAdded){
                            errorLabel.setVisible(false);                            
                            errorTxtLabel.setVisible(false);
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            //Confirm save
                            alert.setTitle("Save Product");
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
            //Min value error
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
            //Inventory value error
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
        errorLabel.setVisible(false);
        errorTxtLabel.setVisible(false);
        availPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availPartsTableView.setItems(Inventory.getAllParts());
        
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
