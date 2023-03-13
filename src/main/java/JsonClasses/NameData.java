package JsonClasses;

public class NameData {
    private String[] data;
    public NameData(){
        data = new String[152];
    }

    public String get(int i) {
        return data[i];
    }

    public int getLength() {
        return data.length;
    }

}
