package JsonClasses;

public class MaleNameData {
    private String[] maleNames;

    public MaleNameData(String[] maleNamesInput){
        maleNames = maleNamesInput;
    }

    public String[] getMaleNames() {
        return maleNames;
    }

    public void setMaleNames(String[] maleNames) {
        this.maleNames = maleNames;
    }
}
