package JsonClasses;

public class LocationData {
    private Location[] data;

    public LocationData(){
        data = new Location[999];
    }

    public Location[] getLocations() {
        return data;
    }

    public void setLocations(Location[] locations) {
        this.data = locations;
    }
}
