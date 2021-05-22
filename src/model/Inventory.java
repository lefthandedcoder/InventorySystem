package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author chris
 */
public class Inventory {
    
    // Declare fields

    /**
     * The product ID
     */
    private static int productId = 0;

    /**
     * The part ID
     */
    private static int partId = 0;

    /**
     * The observable list of all parts in inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * The observable list of all products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Observable list of all products in inventory lookup
     */
    public static ObservableList<Product> productsLookup = null;

    /**
     * Observable list of all parts in inventory lookup
     */
    public static ObservableList<Part> partsLookup = null;
    
    /**
     *
     * @param newPart is the newly added part
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
     *
     * @param newProduct is the newly added product
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    /**
     *
     * @return gets new part ID
     */
    public static int getNewPartId() {
        return ++partId;
    }
    
    /**
     *
     * @return gets new product ID
     */
    public static int getNewProductId() {
        return ++productId;
    }
    
    /**
     *
     * @param partID retrieves part ID from inventory search
     * @return list of parts found by ID
     */
    public static Part lookupPart(int partID){
        Part partLookup = null;
        
        for (Part part : allParts) {
            if (part.getId() == partID) {
                partLookup = part;
            }
        }
        
        return partLookup;
    }
    
    /**
     *
     * @param productID
     * @return list of products found by ID
     */
    public static Product lookupProduct(int productID){
        Product productLookup = null;
        
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                productLookup = product;
            }
        }
        
        return productLookup;
    }
    
    /**
     *
     * @param partName
     * @return list of parts found by name
     */
    public static ObservableList<Part> lookupPart(String partName){
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                partsLookup.add(part);
            }
        }
        
        return partsLookup;
    }
    
    /**
     *
     * @param productName
     * @return list of products found by name
     */
    public static ObservableList<Product> lookupProduct(String productName){
        productsLookup = null;
        
        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                productsLookup.add(product);
            }
        }
        
        return productsLookup;
    }
    
    /**
     *
     * @param index allParts inventory index
     * @param selectedPart part to update
     */
    public static void updatePart(int index, Part selectedPart){ 
        allParts.set(index, selectedPart);
    }
    
    /**
     *
     * @param index allProducts inventory index
     * @param selectedProduct product to update
     */
    public static void updateProduct(int index, Product selectedProduct){ 
        allProducts.set(index, selectedProduct);
    }
    
    /**
     *
     * @param selectedPart part to delete
     * @return deletes part if found
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     *
     * @param selectedProduct product to delete
     * @return deletes product if found
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * gets all parts
     * @return all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    /**
     * gets all products
     * @return all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
