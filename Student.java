import java.util.ArrayList;

public class Student extends Participant{
    private double GPA = -1.0;
    private int ES = -1;

    // constructors
    public Student(){
        super.setMaxMatches(1); //max matches for student is 1
    }

    public Student(String name, double GPA, int ES, int nSchools){
        super(name, 1, nSchools);
    	this.GPA = GPA;
    	this.ES = ES;
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

    public void editInfo (ArrayList <School> H, boolean canEditRankings){ // user info

    }

    @Override
    public void print(ArrayList<? extends Participant> H){ // print student row
		//print name, weight, and assigned Student 
		System.out.format("%-27s%8.2f%4s", super.getName(), GPA, ES);
    	
    	super.print(H); //call super-class to print matches (if they exist) and rankings (if they exist)
    }

    public boolean isValid(){ // check if this student has valid info
        return false;
    }
}
