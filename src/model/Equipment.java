package model;

public class Equipment {
    private int id;
    private String name;
    private String category;
    private int totalQuantity;
    private int availableQuantity;

    public Equipment(int id, String name, String category, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.totalQuantity = quantity;
        this.availableQuantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getTotalQuantity() { return totalQuantity; }
    public int getAvailableQuantity() { return availableQuantity; }

    public void decreaseAvailable() { availableQuantity--; }
    public void increaseAvailable() { availableQuantity++; }
    public boolean isAvailable() { return availableQuantity > 0; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + category +
               " | Available: " + availableQuantity + "/" + totalQuantity;
    }
}