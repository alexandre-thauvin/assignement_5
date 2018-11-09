package alexandre.thauvin.gym3000x;

public class Trainer {

    private String name;
    private String qualification;

    public Trainer(){

    }

    public Trainer(String name, String qualification){
        this.name = name;
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
