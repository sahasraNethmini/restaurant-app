package entity;

public class Item implements SuperEntity {

    private String code;
    private String description;
    private Double unitPrice;
    private int qtyOnHand;

    public Item() {
    }

    public Item(String code, String description, Double unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public Item(String code, int qtyOnHand) {
        this.code = code;
        this.qtyOnHand = qtyOnHand;
    }

    public Item(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Item [code=" + code + ", description=" + description + ", unitPrice=" + unitPrice + ", qtyOnHand="
                + qtyOnHand + "]";
    }

}
