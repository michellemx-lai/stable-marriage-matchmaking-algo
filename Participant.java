import java.io.IOException;
import java.util.ArrayList;

public class Participant {
    private String name;
    private ArrayList<Integer> rankings = new ArrayList<Integer>();
    private ArrayList<Integer> matches = new ArrayList<Integer>();
    private int regret;
    private int maxMatches;

    // constructors
    public Participant(){
    }

    public Participant(String name, int maxMatches, int nParticipants){ 
        this.name = name;
        this.maxMatches = maxMatches;
    }

    // getters
    public String getName(){
        return this.name;
    }

    public int getRanking(int i){ //given the participant's index
    	
    	int rank = 0; //initialize rank
    	
		for (int j = 0; j < this.rankings.size(); j++) { //if the participant index matches the index in the rankings, we found the participant's rank 
			if (i == rankings.get(j)) {
				rank = j + 1;
			}
		}
		
        return rank;
    }

    public ArrayList<Integer> getRankings(){ //I made
        return this.rankings;
    }

    public int getMatch(int i){
    	int matchIndex = matches.get(i);
		
		return matchIndex;
    }

    public int getRegret(){ //add up all the regrets in the matchs!!
    	return this.regret;
    }

    public int getMaxMatches(){
        return this.maxMatches;
    }

    public int getNMatches(){
        return this.matches.size(); // returns length of matches ArrayList?
    }

    public int getNParticipants(){ //returns length of rankings ArrayList
        return this.rankings.size();
    }

    public boolean isFull(){
    	boolean openingsAreFilled = false;
    	
    	if (this.maxMatches == matches.size()) {
    		openingsAreFilled = true;
    	}
        return openingsAreFilled;
    }

    // setters
    public void setName(String name){
        this.name = name;
    }

    public void setRanking(int i, int r){
		this.rankings.set(r - 1, i);
    }
    
    public void setRankings(ArrayList<Integer> rankings){ //I made
        this.rankings = rankings;
    }

    public void setMatch(int m){
    	this.matches.add(m);
    }

    public void setRegret(int r){
		this.regret = r;
    }
    
    public void setNParticipants(int n){ 
    	//do nothing
    }
    
    public void setMaxMatches(int n){
        this.maxMatches = n;
    }

    // methods to handle matches
    public void clearMatches(){ // clear all matches
        this.matches.clear();
    }

    public int findRankingByID(int k){ // find rank of participant k
		int ranking = getRanking(k);
		return ranking;
    }

    public int getWorstMatch(){ // find the worst-matched participant
    	int worstRankedMatchRank = 0; //initialize 
    	int worstRankedMatchIndex = rankings.size() + 900; //initialize
    	
    	for (int i = 0; i < matches.size(); i ++) { //loop over each match 
    		int matchIndex = matches.get(i); //get the match index
    		int matchRank = getRanking(matchIndex); //get the corresponding rank of the match
    		
    		if (matchRank > worstRankedMatchRank) { //if this match is lesser preferred than the current least preferred match
    			worstRankedMatchIndex = matchIndex; //then it becomes the worst match
    			worstRankedMatchRank = matchRank; //assign its corresponding rank to be the worst rank
    		}
    	}

        return worstRankedMatchIndex;
    }

    public void unmatch(int k){ // remove the match with participant k
        this.matches.remove((Integer)k);
    }

    public boolean matchExists(int k){ // check if match to participant k exists
        boolean matchExists = false;
        
        if (this.matches.contains(k)) {
        	matchExists = true;
        }
        
    	return matchExists;
    }

    public int getSingleMatchedRegret(int k){ // get regret from match with k
    	int singleMatchedRegret = -1;
    	
		for (int j = 0; j < rankings.size(); j++) { //loop through the participant's rankings
			if (k == rankings.get(j)) { //find the rank of the match, the rank is the ArrayList index of the match + 1
				singleMatchedRegret = j; //the regret is rank - 1 therefore the regret is the ArrayList index of the match 
			}
		}
		return singleMatchedRegret;
    }

    public void calcRegret(){ // calculate total regret over all matches
    	int singleMatchedRegret = -1;

		for (int i = 0; i < matches.size(); i++) {
			singleMatchedRegret = getSingleMatchedRegret(matches.get(i));
			this.regret += singleMatchedRegret;
		}
    }

    // methods to edit data from the user
    public void editInfo(ArrayList <? extends Participant> P){
        //do something
    }

    public void editRankings(ArrayList <? extends Participant> P) throws IOException {
		rankings.clear(); //erase all pre-existing student rankings of schools
		
		for (int i = 0; i < P.size(); i++) { //make an arrayList with a size equal to the number of participants
			rankings.add(0); 
		}

		//loop over each participant in the ArrayList P
		for (int i = 0; i < P.size(); i++) {
			int participantIndex = i + 1; 
			
			//index the school object from the list of schools 
			Participant participant = P.get(i);
					
			boolean rankAvailable = false;
			//while the user keeps assigning the same rank to more than 1 participant, run the loop
			do {
				//ask user to type in an assigned rank to the corresponding schools
				int assignedRank = BasicFunctions.getInteger(participant.getName() + ": ", 1, P.size());
				
				if (rankings.get(assignedRank - 1) != 0){ //if the slot for the assigned rank is taken
					//then an error message is shown (rank has already been used, it's unavailable)
					System.out.println("ERROR: Rank " + String.valueOf(assignedRank) + " is already used!");
				}
				
				//otherwise the assigned rank is equal to a rank that has not been assigned
				else { 
					//the participant index is added to the corresponding index in the ranking array according to the assigned ranking
					setRanking(participantIndex, assignedRank);
					
					rankAvailable = true; //and then the rank is available
				}
			} while (rankAvailable == false); //the loop continues until the user enters an available rank
		}
    }

    // print methods
    public void print(ArrayList <? extends Participant> P){ //print matching and matches
		if (matches.size() != 0) {
			String matchNames = getMatchNames(P); //print matches (assigned participant(s))
			System.out.format("  %-27s", matchNames);
		}
		else {
			System.out.format("  %-27s", "-");
		}
		
    	if (rankings.size() != 0) {
			printRankings(P); //print rankings (preferred participant order)
		}
		else {
			System.out.format("%-23s\n", "-");
		}
    }

    public void printRankings(ArrayList <? extends Participant> P){
		for (int i = 0; i < rankings.size(); i++) { //loop over each Student
			
			int participantIndex = rankings.get(i);
			
			String participantName = P.get(participantIndex - 1).getName();
			
			System.out.print(participantName);
			
			if (i != rankings.size() - 1) {
				System.out.print(", "); //print a comma after the participant name unless it's the last participant in the list
			}
		}
    }

    public String getMatchNames (ArrayList <? extends Participant> P){
    	String matchNames = "";
    	
		for (int i = 0; i < matches.size(); i ++) {
			
			int matchIndex = matches.get(i);
			String matchName = P.get(matchIndex - 1).getName();
											
			matchNames += matchName;
			
			if (i != matches.size() - 1) { //print a comma after the match unless it's the last match in the list
				matchNames += ", ";
			}
		}
    	return matchNames;
    }

    // check if this participant has valid info
    public boolean isValid(){
        return false;
    }
}

