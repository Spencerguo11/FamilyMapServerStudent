package Decode;

public class StringArray {

    private String[] data;

    public StringArray(){
        data = new String[150];
    }

    public String getValueAt(int i){
        return data[i];
    }

}