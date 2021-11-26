import java.util.ArrayList;

public class Student extends Participant{
    private double GPA;
    private int ES;

    // constructors
    public Student(){

    }

    public Student(String name, double GPA, int ES, int nSchools){

    }

    // getters and setters
    public double getGPA(){
        return 0.00;
    }

    public int getES(){
        return 0;
    }

    public void setGPA(double GPA){

    }

    public void setES(int ES){

    }

    public void editInfo (ArrayList <School> H, boolean canEditRankings ){ // user info

    }

    public void print(ArrayList<? extends Participant> H){ // print student row

    }

    public boolean isValid(){ // check if this student has valid info
        return false;
    }
}
