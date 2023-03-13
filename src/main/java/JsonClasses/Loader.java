package JsonClasses;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Loader {

    public static NameData decodeNames(String path)  {
        Gson gson = new Gson();
        try {

            NameData nameData = gson.fromJson(new FileReader(path), NameData.class);
            return nameData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LocationData decodeLocations(String path) {
        Gson gson = new Gson();
        try {
            LocationData locationData = gson.fromJson(new FileReader(path), LocationData.class);
            return locationData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
