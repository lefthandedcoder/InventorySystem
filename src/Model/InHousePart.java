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
public class InHousePart extends Part {
    //Declare field

    /**
     * the machineId
     */
    private int machineId;
    
    //Declare constructor

    /**
     *
     * @param machineId
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public InHousePart(int machineId, int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
