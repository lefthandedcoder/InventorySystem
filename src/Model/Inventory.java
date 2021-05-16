/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author chris
 */
public class Inventory {
    
    // Declare fields
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partID){
        Part partRetrieved = null;
        
        for (Part part : allParts) {
            if (part.getId() == partID) {
                partRetrieved = part;
            }
        }
        
        return partRetrieved;
    }
    
    public static Product lookupProduct(int productID){
        Product productRetrieved = null;
        
        for (Product product : allProducts) {
            if (product.getId() == productID) {
                productRetrieved = product;
            }
        }
        
        return productRetrieved;
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsRetrieved = null;
        
        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                partsRetrieved.add(part);
            }
        }
        
        return partsRetrieved;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsRetrieved = null;
        
        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsRetrieved.add(product);
            }
        }
        
        return productsRetrieved;
    }
    
    public static void updatePart(int index, Part selectedPart){ 
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct){ 
        allProducts.set(index, selectedProduct);
    }
    
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }
    
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
