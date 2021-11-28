import java.util.ArrayList;

public class Student extends Participant{
    private double GPA = -1.0;
    private int ES = -1;

    // constructors
    public Student(){

    }

    public Student(String name, double GPA, int ES){
    	super.setName(name);
    }

    // getters and setters
    public double getGPA(){
        return this.GPA;
    }

    public int getES(){
        return this.ES;
    }

    public void setGPA(double GPA){
        this.GPA = GPA;
    }

    public void setES(int ES){
        this.ES = ES;
    }

    public void editInfo (ArrayList <School> H, boolean canEditRankings ){ // user info

    }

    public void print(ArrayList<? extends Participant> H){ // print student row

    }

    public boolean isValid(){ // check if this student has valid info
        return false;
    }
}
