package edu.ewubd.loginandsignup;

public class DataClass {
    public String location;
    public String type;
    public String phone;
    public String rent;
    public String size;
    public String description;
    public String imgURL1;
    public String imgURL2;
    public String imgURL3;
    public String imgURL4;
    public String username;

    public float latitude;
    public float longitude;
    public int boost;

    public DataClass() {
    }

    public DataClass(String location, String type, String phone, String rent, String size, String description, String imgURL1, String imgURL2, String imgURL3, String imgURL4, String username, float latitude, float longitude, int boost) {
        this.location = location;
        this.type = type;
        this.phone = phone;
        this.rent = rent;
        this.size = size;
        this.description = description;
        this.imgURL1 = imgURL1;
        this.imgURL2 = imgURL2;
        this.imgURL3 = imgURL3;
        this.imgURL4 = imgURL4;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.boost = boost;
    }
}
