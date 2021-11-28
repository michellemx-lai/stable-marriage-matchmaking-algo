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

    public Participant(String name, int maxMatches){ 
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
    	
        return 0;
    }

    public int getRegret(){ //add up al the regrets in the matchs!!
        return 0;
    }

    public int getMaxMatches(){
        return 0;
    }

    public int getNMatches(){
        return 0;
    }

    public int getNParticipants(){
        return 0;
    }

    public boolean isFull(){
        return false;
    }

    // setters
    public void setName(String name){

    }

    public void setRanking(int i, int r){

    }
    
    public void setRankings(ArrayList<Integer> rankings){ //I made
        this.rankings = rankings;
    }

    public void setMatch(int m){
    	this.matches.add(m);
    }

    public void setRegret(int r){

    }
    
    /*
    public void setNParticipants(int n){ // set rankings array size
    }

*/
    public void setMaxMatches(int n){

    }

    // methods to handle matches
    public void clearMatches(){ // clear all matches

    }

    public int findRankingByID(int k){ // find rank of participant k
        return 0;
    }

    public int getWorstMatch(){ // find the worst-matched participant
        return 0;
    }

    public void unmatch(int k){ // remove the match with participant k

    }

    public boolean matchExists(int k){ // check if match to participant k exists
        return false;
    }

    public int getSingleMatchedRegret(int k){ // get regret from match with k
        return 0;
    }

    public void calcRegret(){ // calculate total regret over all matches

    }

    // methods to edit data from the user
    public void editInfo(ArrayList <? extends Participant> P){

    }

    public void editRankings(ArrayList <? extends Participant> P){

    }

    // print methods
    public void print(ArrayList <? extends Participant> P){

    }

    public void printRankings(ArrayList <? extends Participant> P){

    }

    public String getMatchNames (ArrayList <? extends Participant> P){
        return null;
    }

    // check if this participant has valid info
    public boolean isValid(){
        return false;
    }
}

