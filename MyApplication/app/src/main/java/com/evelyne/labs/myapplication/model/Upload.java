package com.evelyne.labs.myapplication.model;

public class Upload {

    private String mcompany,mcapacity,mplate,mprice;
    private String mimageUri;

    public Upload(String trim, String s){}

    public Upload(String company,String capacity,String plate, String price, String imageUri){
        if(company.trim().equals("")){
            company = "no name";
        }
        if(capacity.trim().equals("")){
            capacity = "no name";
        }
        if(plate.trim().equals("")){
            plate = "no name";
        }
        if(price.trim().equals("")){
            price = "no name";
        }
        mcompany = company;
        mcapacity = capacity;
        mplate = plate;
        mprice = price;
        mimageUri = imageUri;

    }

    public String getMcompany() {
        return mcompany;
    }

    public void setMcompany(String mcompany) {
        this.mcompany = mcompany;
    }

    public String getMcapacity() {
        return mcapacity;
    }

    public void setMcapacity(String mcapacity) {
        this.mcapacity = mcapacity;
    }

    public String getMplate() {
        return mplate;
    }

    public void setMplate(String mplate) {
        this.mplate = mplate;
    }

    public String getMprice() {
        return mprice;
    }

    public void setMprice(String mprice) {
        this.mprice = mprice;
    }

    public String getMimageUri() {
        return mimageUri;
    }

    public void setMimageUri(String mimageUri) {
        this.mimageUri = mimageUri;
    }
}
