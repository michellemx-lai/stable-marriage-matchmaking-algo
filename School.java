import java.util.ArrayList;
import java.util.Collections;

public class School extends Participant{
    private double alpha; // GPA weight

    // constructors
    public School(){

    }

    public School(String name, double alpha, int maxMatches, int nStudents){
        super(name, maxMatches, nStudents);
        this.alpha = alpha;
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
        //do something
    }

    public void calcRankings(ArrayList <Student> S){ // calc rankings from alpha
		ArrayList<Integer> tempRankings = new ArrayList<Integer>(); //erase all pre-existing rankings
		
		int nStudents = S.size();

		ArrayList < Double > compositeScores = new ArrayList < Double > ();

		for (int i = 0; i < nStudents; i++) { //loop over every student
			Student student = S.get(i);
			Double studentCompositeScore = alpha * student.getGPA() + (1 - alpha) * student.getES(); //use formula to calc composite 
			compositeScores.add(studentCompositeScore); //add the composite score to the array in the same order as the students
		}
		
		ArrayList < Double > sortedCompositeScores = new ArrayList<Double>(compositeScores); //make a copy of the composite scores array 
		Collections.sort(sortedCompositeScores); //sort the array of composite scores in ascending order
		Collections.reverse(sortedCompositeScores); //reverse the array so composite scores are sorted in descending order
		
		//remove all duplicated scores from the sorted composite scores
		ArrayList < Double > uniqueSortedCompositeScores = new ArrayList<Double>(BasicFunctions.removeDuplicates(sortedCompositeScores)); //make a copy of the composite scores array 
					
		for (int i = 0; i < uniqueSortedCompositeScores.size(); i++) { //loop over unique and sorted composite scores
			Double uniqueCompositeScore = uniqueSortedCompositeScores.get(i);
			int nCorrespondingStudents = Collections.frequency(compositeScores, uniqueCompositeScore);
			
			for (int j = 0; j < compositeScores.size(); j++) { //loop over all composite scores
				
				if (nCorrespondingStudents > 0 && String.valueOf(uniqueCompositeScore).equals(String.valueOf(compositeScores.get(j)))) { //compare the values of the composite scores after converting both of them into strings

					int correspondingStudentIndex = j + 1;
					
					tempRankings.add(correspondingStudentIndex);
				
					nCorrespondingStudents --;
					
				}
			}	
		}
		
		super.setRankings(tempRankings);
    }

    @Override
    public void print(ArrayList <? extends Participant> S){ // print school row
		System.out.format("%-27s%4s%8.2f", super.getName(), super.getMaxMatches(), alpha); //print name, weight, and assigned student(s)
		super.print(S); //call super-class to print matches (if they exist) and rankings (if they exist)
    }

    public boolean isValid(){ // check if this school has valid info
        boolean schoolIsValid = false;
        
		if ((alpha >= 0.0 && alpha <= 1.0) && super.getMaxMatches() > 0) { //alpha must be between 0 and 1, and the number of openings must be positive
		    schoolIsValid = true;
		}
		
		return schoolIsValid;
    }

}
