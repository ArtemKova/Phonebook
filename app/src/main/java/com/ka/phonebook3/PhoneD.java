package com.ka.phonebook3;

public class PhoneD {
    private int img;
    private String name_contact;
    private String number_contact;

    public PhoneD(int img, String name_contact, String number_contact){
        this.img = img;
        this.name_contact = name_contact;
        this.number_contact = number_contact;
    }

    public int getImg(){
        return this.img;
    }

    public String getName_contact(){
        return this.name_contact;
    }

    public String getNumber_contact(){
        return this.number_contact;
    }

}
