import java.util.ArrayList;

public class School extends Participant{
    private double alpha; // GPA weight

    // constructors
    public School(){

    }

    public School(String name, double alpha, int maxMatches){
        super(name, maxMatches);
    }

    // getters and setters
    public double getAlpha(){
        return this.alpha;
    }

    public void setAlpha(double alpha){
        this.alpha = alpha;
    }

    // get new info from the user; cannot be inherited or overridden from parent
    public void editSchoolInfo(ArrayList <Student> S, boolean canEditRankings){

    }

    public void calcRankings(ArrayList <Student> S){ // calc rankings from alpha

    }

    public void print(ArrayList <? extends Participant> S){ // print school row

    }

    public boolean isValid(){ // check if this school has valid info
        return false;
    }

}
