package JsonClasses;

public class FemaleNameData {
    private String[] femaleNames;

    public FemaleNameData(String[] femaleNamesinput){
        femaleNames = femaleNamesinput;
    }

    public String[] getFemaleNames() {
        return femaleNames;
    }

    public void setFemaleNames(String[] femaleNames) {
        this.femaleNames = femaleNames;
    }
}
