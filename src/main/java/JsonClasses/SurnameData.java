package JsonClasses;

public class SurnameData {
    private String[] Surnames;

    public SurnameData(String[] SurnamesInput){
        Surnames = SurnamesInput;
    }

    public String[] getSurnames() {
        return Surnames;
    }

    public void setFirstNames(String[] Surnames) {
        this.Surnames = Surnames;
    }

}
