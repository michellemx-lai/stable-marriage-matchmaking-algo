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
    	int matchIndex = -1;
    	
		for (int j = 0; j < this.matches.size(); j++) { //loop through all the matches in the matches ArrayList
			if (i == matches.get(j)) {
				matchIndex = j + 1;
			}
		}
		
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
    
    public void setNParticipants(int n){ // fill rankings ArrayList with placeholder participant indices
    	this.rankings.clear();
    	for (int i = 0; i < n; i ++) {
    		this.rankings.add(0);
    	}
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
    	int worstRankedMatchIndex = this.matches.get(matches.size() - 1);

        return worstRankedMatchIndex;
    }

    public void unmatch(int k){ // remove the match with participant k
        this.matches.remove(k);
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
    	
		for (int j = 0; j < rankings.size(); j++) {
			if (k == rankings.get(j)) {
				singleMatchedRegret = j - 1;
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

    public void editRankings(ArrayList <? extends Participant> P){
        //do something
    }

    // print methods
    public void print(ArrayList <? extends Participant> P){ //print matching and matches
		if (matches.size() != 0) {
			getMatchNames(P); //print matches (assigned participant(s))
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

