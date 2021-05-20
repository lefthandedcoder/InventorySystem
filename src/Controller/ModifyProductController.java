package Controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
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
    
    Stage stage;
    Parent scene;
    Product currProduct = MainController.getProductModify();
    ObservableList<Part> currParts = currProduct.getAllAssociatedParts();
    
    @FXML
    private TextField partSearchBox;
    
    @FXML
    private TextField productIDTxt;
    @FXML
    private TextField productInvTxt;
    @FXML
    private TextField productPriceTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField maxInvTxt;
    @FXML
    private TextField minInvTxt;
    
    @FXML
    private TableView<Part> availPartsTableView;
    @FXML
    private TableColumn<Part, Integer> availPartIDCol;
    @FXML
    private TableColumn<Part, String> availPartNameCol;
    @FXML
    private TableColumn<Part, Integer> availPartInvCol;
    @FXML
    private TableColumn<Part, Integer> availPartPriceCol;
    
    @FXML
    private TableView<Part> currPartsTableView;
    @FXML
    private TableColumn<Part, Integer> currPartIDCol;
    @FXML
    private TableColumn<Part, String> currPartNameCol;
    @FXML
    private TableColumn<Part, Integer> currPartInvCol;
    @FXML
    private TableColumn<Part, Double> currPartPriceCol;

    @FXML
    void onActionAddPart(ActionEvent event) {
        Part currPart = availPartsTableView.getSelectionModel().getSelectedItem();
        currParts.add(currPart);
        currPartsTableView.setItems(currParts);
    }

    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        int id = 0;
        String name = productNameTxt.getText();
        Double price = Double.parseDouble(productPriceTxt.getText());
        int stock = Integer.parseInt(productInvTxt.getText());
        int min = Integer.parseInt(minInvTxt.getText());
        int max = Integer.parseInt(maxInvTxt.getText());
        
        Product newProduct = new Product(id, name, price, stock, min, max);
        
        for (Part part : currParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    onActionDisplayMainMenu(event);
    }

    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part currPart = currPartsTableView.getSelectionModel().getSelectedItem();

        currParts.remove(currPart);
        currPartsTableView.setItems(currParts);

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        availPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availPartsTableView.setItems(Inventory.getAllParts());
        
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
                    return true;
                }
            
                //Compare all part names
                String search = newValue.toLowerCase();
                
                if (part.getName().toLowerCase().contains(search) || Integer.valueOf(part.getId()).toString().equals(search)) {
                    return true; // Filter matches part name or id.
                } else
                    return false; // Does not match.
                
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
