package JsonClasses;

public class Location {
    private String country;
    private String city;
    private float latitude;
    private float longitude;

    public Location(String countryInput, String cityInput, float latitudeInput, float longitudeinput){
        country = countryInput;
        city = cityInput;
        latitude = latitudeInput;
        longitude = longitudeinput;
    };

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
