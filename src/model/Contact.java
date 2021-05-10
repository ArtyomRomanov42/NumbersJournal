package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class Contact {
    private String name;
    private String phone;

    //используем проперти
    private StringProperty phoneNumberProp;
    private StringProperty nameProp;

    public Contact(String name, String phone) {
        this.phone = phone;
        this.name = name;
    }

    public Contact() {
        this.nameProp = new SimpleStringProperty();
        this.phoneNumberProp = new SimpleStringProperty();
    }

    public ObservableValue<String> getPhoneValue() {
        return phoneNumberProp;
    }

    public void setPhoneNumber(String phoneNumberProperty) {
        this.phoneNumberProp.set(phoneNumberProperty);
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public void setNameProp(String nameProp) {
        this.nameProp.set(nameProp);
    }

    public ObservableValue<String> getNameValue() {
        return nameProp;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
