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

    /** Java docs can be accessed from "InventorySystemChristianDye\dist\javadoc"
     * This is the main method for this inventory management system.
     * FUTURE ENHANCEMENT for this controller is to 
     * add a select all feature to the parts tables 
     * in the add/modify product screens. This would
     * allow for batch removal or batch adds of parts.

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
        partId = Inventory.getNewPartId();
        InHousePart bikeHorn = new InHousePart(partId,"Horn", 5.00, 10, 1, 15, 103);
        Inventory.addPart(bikeHorn);
        
        // Add sample products with associated parts
        int productId = Inventory.getNewProductId();
        Product bike1 = new Product(productId, "Giant Bike", 299.99, 5, 1, 10);
        bike1.addAssociatedPart(bikeBrakes);
        bike1.addAssociatedPart(bikeWheel);
        bike1.addAssociatedPart(bikeSeat);
        Inventory.addProduct(bike1);
        
        productId = Inventory.getNewProductId();
        Product bike2 = new Product(productId, "Tricycle", 99.99, 3, 1, 10);
        bike2.addAssociatedPart(bikeBrakes);
        bike2.addAssociatedPart(bikeSeat);
        bike2.addAssociatedPart(bikeHorn);
        Inventory.addProduct(bike2);
        
        launch(args);
    }

    /**
     * Starts the application
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        
    Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
    
    Scene scene = new Scene(root);
    
    stage.setScene(scene);
    stage.show();

    }
    
}
