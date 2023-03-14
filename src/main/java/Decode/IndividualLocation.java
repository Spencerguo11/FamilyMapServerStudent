package Decode;

public class IndividualLocation {

    private String country;
    private String city;
    private float latitude;
    private float longitude;

    public IndividualLocation(){
        country = new String();
        city = new String();
    }
    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
