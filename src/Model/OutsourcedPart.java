/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Christian Dye
 */
public class OutsourcedPart extends Part {
    
    //Declare field

    /**
     * the companyName
     */
    private String companyName;
    
    //Declare constructor

    /**
     *
     * @param companyName
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public OutsourcedPart(String companyName, int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
}
