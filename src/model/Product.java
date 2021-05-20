package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Christian Dye
 */
public class Product {
    
    //Declare fields

    /**
     *
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * the id
     */
    private int id;

    /**
     * the name
     */
    private String name;

    /**
     * the price
     */
    private double price;

    /**
     * the stock
     */
    private int stock;

    /**
     * the min
     */
    private int min;

    /**
     * the max
     */
    private int max;

    /**
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /**
     *
     * @param part associated with product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }    
    
    /**
     *
     * @param selectedAssociatedPart deletes selected part association
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     *
     * @return list of all parts associated with product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
