import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    public void editInfo (ArrayList <School> H, boolean canEditRankings) throws IOException { // user info
		System.out.print("Name: ");
		super.setName(BasicFunctions.cin.readLine()); //get user input for name
		GPA = BasicFunctions.getDouble("GPA: ", 0.00, 4.0); //get user input for GPA
		ES = BasicFunctions.getInteger("Extracurricular score: ", 0, 5); //get user input for ES
		super.setMaxMatches(BasicFunctions.getInteger("Maximum number of matches: ", 1, Integer.MAX_VALUE)); //get user input for alpha
    }

    @Override
    public void print(ArrayList<? extends Participant> H){ // print student row
		//print name, weight, and assigned Student 
		System.out.format("%-44s%4.2f  %2d", super.getName(), GPA, ES);
    	
    	super.print(H); //call super-class to print matches (if they exist) and rankings (if they exist)
    }

    public boolean isValid(){ // check if this student has valid info
    	boolean studentIsValid = true;
	    
	    for (int i = 0; i < super.getRankings().size(); i++) { 
	    	if (Collections.frequency(super.getRankings(), super.getRankings().get(i)) > 1 || super.getRankings().get(i) > super.getNParticipants()) { //school is invalid if it is ranked more than once
    			studentIsValid = false;
	    	}
	    }
	   
	    if (GPA < 0.0 || GPA > 4.0 || ES < 0 || ES > 5 || super.getRankings().size() != super.getNParticipants()) { //student is invalid if either GPA or extracurricular score it not between 0.0 and 4.0, and 0 and 5, respectively
	    	studentIsValid = false;
	    }

	    
	    return studentIsValid;
    }
}
