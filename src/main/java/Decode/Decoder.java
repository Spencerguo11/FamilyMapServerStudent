package Decode;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Decoder {

    public static StringArray decodeNames(String file){
        Gson gson = new Gson();
        try{
            StringArray temp = gson.fromJson(new FileReader(file), StringArray.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public static LocationArray decodeLocations(String file){
        Gson gson = new Gson();
        try{
            LocationArray temp = gson.fromJson(new FileReader(file), LocationArray.class);
            return temp;

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
}
