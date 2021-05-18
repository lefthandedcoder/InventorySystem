/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<Part> currParts = FXCollections.observableArrayList();
    
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
        Part selectedPart = availPartsTableView.getSelectionModel().getSelectedItem();
        currParts.add(selectedPart);
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
    void onActionModifyProduct(ActionEvent event) {
    }

    @FXML
    void onActionRemovePart(ActionEvent event) {
    }

    @FXML
    void onActionSearchPart(ActionEvent event) {
    }
    
    private boolean inventoryCheck(int min, int max, int stock) {
        boolean isValid = true;

        if (stock < min || stock > max) {
            isValid = false;
        }
        return isValid;
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
        
        currPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        currPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        currPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        currPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        currPartsTableView.setItems(currParts);
    }
    
}
