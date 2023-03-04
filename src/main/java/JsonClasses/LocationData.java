package JsonClasses;

public class LocationData {
    private Location[] data;

    public LocationData(Location[] locationsInput){
        data = locationsInput;
    }

    public Location[] getLocations() {
        return data;
    }

    public void setLocations(Location[] locations) {
        this.data = locations;
    }
}
