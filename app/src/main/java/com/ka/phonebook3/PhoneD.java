package com.ka.phonebook3;

import android.graphics.Bitmap;

public class PhoneD {
    private Bitmap img;
    private String name_contact;
    private String number_contact;

    public PhoneD(Bitmap img, String name_contact, String number_contact){
        this.img = img;
        this.name_contact = name_contact;
        this.number_contact = number_contact;
    }

    public Bitmap getImg(){
        return this.img;
    }

    public String getName_contact(){
        return this.name_contact;
    }

    public String getNumber_contact(){
        return this.number_contact;
    }

}
