package inventorysystemchristiandye;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHousePart;
import model.Inventory;
import model.OutsourcedPart;
import model.Product;

/**
 *
 * @author Christian Dye
 */
public class CDInventorySystem extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Add sample parts
        int partId = Inventory.getNewPartId();
        InHousePart bikeBrakes = new InHousePart(partId,"Brakes", 15.00, 10, 1, 15, 101);
        partId = Inventory.getNewPartId();
        Inventory.addPart(bikeBrakes);
        InHousePart bikeWheel = new InHousePart(partId,"Wheel", 11.00, 16, 1, 20, 102);
        Inventory.addPart(bikeWheel);
        partId = Inventory.getNewPartId();
        OutsourcedPart bikeSeat = new OutsourcedPart(partId,"Seat", 15.00, 10, 1, 15, "Schwinn");
        Inventory.addPart(bikeSeat);
        
        // Add sample products
        int productId = Inventory.getNewProductId();
        Product bike1 = new Product(productId, "Giant Bike", 299.99, 5, 1, 10);
        bike1.addAssociatedPart(bikeBrakes);
        bike1.addAssociatedPart(bikeWheel);
        bike1.addAssociatedPart(bikeSeat);
        Inventory.addProduct(bike1);
        
        productId = Inventory.getNewProductId();
        Product bike2 = new Product(productId, "Tricycle", 99.99, 3, 1, 10);
        bike2.addAssociatedPart(bikeBrakes);
        bike2.addAssociatedPart(bikeWheel);
        bike2.addAssociatedPart(bikeSeat);
        Inventory.addProduct(bike2);
        
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
    Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
    
    Scene scene = new Scene(root);
    
    stage.setScene(scene);
    stage.show();

    }
    
}
