package alexandre.thauvin.gym3000x;

public class Course {

    private String title;
    private String hour;
    private String professor;

    public Course(){

    }

    public Course(String title, String hour, String professor){
        this.title = title;
        this.hour = hour;
        this.professor = professor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHour() {

        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getProfessor() {

        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
