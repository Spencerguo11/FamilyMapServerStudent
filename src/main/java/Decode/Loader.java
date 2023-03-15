package Decode;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Loader {

    public static NameData decodeNames(String file){
        Gson gson = new Gson();
        try{
            NameData temp = gson.fromJson(new FileReader(file), NameData.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static LocationData decodeLocations(String file){
        Gson gson = new Gson();
        try{
            LocationData temp = gson.fromJson(new FileReader(file), LocationData.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
