package JsonClasses;

public class LocationData {
    private Location[] locations;

    public LocationData(Location[] locationsInput){
        locations = locationsInput;
    }

    public Location[] getLocations() {
        return locations;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }
}
