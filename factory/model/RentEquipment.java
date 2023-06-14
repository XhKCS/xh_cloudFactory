package com.factory.model;

import java.io.Serializable;

public class RentEquipment extends Equipment  implements Serializable{
    boolean isRented;

    public RentEquipment(String name, EquipmentCategory category, String size, String description) {
        super(name, category, size, description);
        isRented = false;
        this.setRentState("未被租用");
    }

    public void beRented() {
        isRented = true;
        this.setRentState("已被租用");
    }
    public void beReturned() {
        isRented = false;
        this.setRentState("未被租用");
        this.setBelongFactoryID(-1);
    }

//    public String getRentState() {
//        if (isRented==true) {
//            this.setRentState("已被租用");
//        }
//        else {
//            this.setRentState("未被租用");
//        }
//        return super.getRentState();
//    }

}
