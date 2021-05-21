/*FUTURE ENHANCEMENT would be to add a delete all feature 
for the two tables and for a select all/delete all 
feature for the parts tables in the add/modify product windows */

package Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
public class MainController implements Initializable {

    /**
     * Stage for switching windows
     */
    Stage stage;

    /**
     * Scene for switching windows
     */
    Parent scene;

    /**
     * part to modify
     */
    private static Part partModify;

    /**
     * product to modify
     */
    private static Product productModify;

    /**
     * retrieves part to modify
     * @return
     */
    public static Part getPartModify() {
        return partModify;
    }

    /**
     * retrieves product to modify
     * @return
     */
    public static Product getProductModify() {
        return productModify;
    }
    
    /**
     * part search box
     */
    @FXML
    private TextField partSearchBox;

    /**
     * part table
     */
    @FXML
    private TableView<Part> partTableView;    

    /**
     * part ID column
     */
    @FXML
    private TableColumn<Part, Integer> partIDCol;

    /**
     * part name column
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * part inventory column
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /**
     * part price column
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    
    /**
     * part search message label
     */
    @FXML
    private Label partSearchLabel;
    
    /**
     * product search box
     */
    @FXML
    private TextField productSearchBox;

    /**
     * product table
     */
    @FXML
    private TableView<Product> productTableView;

    /**
     * product ID column
     */
    @FXML
    private TableColumn<Product, Integer> productIDCol;

    /**
     * product name column
     */
    @FXML
    private TableColumn<Product, String> productNameCol;

    /**
     * product inventory column
     */
    @FXML
    private TableColumn<Product, Integer> productInvCol;

    /**
     * product price column
     */
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    /**
     * product search message
     */
    @FXML
    private Label productSearchLabel;

    /**
     * Delete part event
     * @param event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part partDelete = partTableView.getSelectionModel().getSelectedItem();
        if (partDelete == null) {
            //RUNTIME ERROR: No part selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Part");
            alert.setContentText("Part not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            //Delete part ocnfirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setContentText("Delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partDelete);
            }
        }
    }

    /**
     * Delete product event
     * @param event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product productDelete = productTableView.getSelectionModel().getSelectedItem();
        if (productDelete == null) {
            //RUNTIME ERROR: No product selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Product");
            alert.setContentText("Product not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            // Delete product confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setContentText("Delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = productDelete.getAllAssociatedParts();
                
                // Associated parts exception
                if (assocParts.size() >= 1) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Parts Associated");
                    alert.setContentText("Remove all parts from product before deletion.");
                    alert.showAndWait();
                } else {
                    Inventory.deleteProduct(productDelete);
                }
            }
        }
    }

    /**
     * Add part event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayAddPart(ActionEvent event) throws IOException {        
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Add product event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayAddProduct(ActionEvent event) throws IOException {        
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Modify part event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayModifyPart(ActionEvent event) throws IOException {
        partModify = partTableView.getSelectionModel().getSelectedItem();
        if (partModify == null) {
            //RUNTIME ERROR:
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Part");
            alert.setContentText("Part not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();  
        }        
    }

    /**
     * Modify product event
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDisplayModifyProduct(ActionEvent event) throws IOException {
        productModify = productTableView.getSelectionModel().getSelectedItem();
        
        if (productModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify Product");
            alert.setContentText("Product not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();  
        }   
    }

    /**
     * Exit program event
     * @param event
     */
    @FXML
    void onActionExitSystem(ActionEvent event) {
        // Exit confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setContentText("Exit program?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // Wrapping observable lists (allParts and allProducts) in a filtered list
        FilteredList<Part> filteredParts = new FilteredList<>(Inventory.getAllParts(), p -> true);        
        FilteredList<Product> filteredProducts = new FilteredList<>(Inventory.getAllProducts(), p -> true);
        
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
                    partSearchLabel.setText("Parts found!");
                    partSearchLabel.setVisible(true);
                    return true; // Filter matches part name or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if(filteredParts.isEmpty()){
                partSearchLabel.setText("Parts not found!");
                partSearchLabel.setVisible(true);
            }
        });
        // Setting the filter predicate whenever the filter changes
        productSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredProducts.setPredicate(product -> {
                // If filter text is empty, display all parts
            
                if (newValue == null || newValue.isEmpty()) {
                    productSearchLabel.setVisible(false);
                    return true;
                }
            
                //Compare all part names
                String search = newValue.toLowerCase();
                
                if (product.getName().toLowerCase().contains(search) || Integer.valueOf(product.getId()).toString().equals(search)) {
                    productSearchLabel.setText("Products found!");
                    productSearchLabel.setVisible(true);
                    return true; // Filter matches product name or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if(filteredProducts.isEmpty()){
                productSearchLabel.setText("Products not found!");
                productSearchLabel.setVisible(true);
        }
        });
        
        // Wrapping filtered list in a sorted list.
        SortedList<Part> sortedParts = new SortedList<>(filteredParts);        
        SortedList<Product> sortedProducts = new SortedList<>(filteredProducts);
        
        // Binding the sorted list comparator to the TableView comparator.
        sortedParts.comparatorProperty().bind(partTableView.comparatorProperty());
        sortedProducts.comparatorProperty().bind(productTableView.comparatorProperty());
        
        // Adding sorted (and filtered) parts to table.
        partTableView.setItems(sortedParts);
        productTableView.setItems(sortedProducts);
    }
}
