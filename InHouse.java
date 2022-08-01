package Inventory;

/**
 * @author Alec Holtzapfel
 */
public class InHouse extends Part{
    private int machineId;

    /**
     * constructs InHouse part
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * sets machine id
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
