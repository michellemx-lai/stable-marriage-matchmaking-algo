import java.util.ArrayList;

public class SMPSolver {
    public ArrayList <Participant> S = new ArrayList <>(); // suitors
    public ArrayList <Participant> R = new ArrayList <>(); // receivers
    private double avgSuitorRegret; // average suitor regret
    private double avgReceiverRegret; // average receiver regret
    private double avgTotalRegret; // average total regret
    private boolean matchesExist; // whether or not matches exist
    private boolean stable; // whether or not matching is stable
    private long compTime; // computation time
    private boolean suitorFirst; // whether to print suitor stats first

    public SMPSolver(){ // constructor

    }

    // getters
    public double getAvgSuitorRegret(){
        return this.avgSuitorRegret;
    }

    public double getAvgReceiverRegret(){
        return this.avgReceiverRegret;
    }

    public double getAvgTotalRegret(){
        return this.avgTotalRegret;
    }

    public boolean matchesExist(){
        return this.matchesExist;
    }

    public boolean isStable(){
        return this.stable;
    }

    public long getTime(){
        return this.compTime;
    }

    public int getNSuitorOpenings(){ //add up the total suitor openings by looping through all suitors and getting their individual number of openings
    	int nTotalSuitorOpenings = 0; //initialize
    	
    	for (int i = 0; i < this.S.size(); i ++) {
    		Participant suitor = S.get(i);
    		nTotalSuitorOpenings += suitor.getMaxMatches();
    	}
        return nTotalSuitorOpenings;
    }

    public int getNReceiverOpenings(){ //add up the total receiver openings by looping through all receivers and getting their individual number of openings
    	int nTotalReceiverOpenings = 0; //initialize
    	
    	for (int i = 0; i < this.R.size(); i ++) {
    		Participant receiver = R.get(i);
    		nTotalReceiverOpenings += receiver.getMaxMatches();
    	}
        return nTotalReceiverOpenings;
    }

    // setters
    public void setMatchesExist(boolean b){
        this.matchesExist = b;
    }

    public void setSuitorFirst(boolean b){
        this.suitorFirst = b;
    }

    public void setParticipants(ArrayList <? extends Participant> S, ArrayList <? extends Participant> R){
    
    }

    // methods for matching
    public void clearMatches(){ // clear out existing matches
    	for (int i = 0; i < this.S.size(); i ++) {
    		Participant suitor = S.get(i);
    		suitor.clearMatches(); //clear matches for suitors
    	}
    	
    	for (int i = 0; i < this.R.size(); i ++) {
			Participant receiver = R.get(i);
			receiver.clearMatches(); //clar matches for receivers
    	}
    	
    }

    public boolean matchingCanProceed(){ // check that matching rules are satisfied
    	boolean matchingCanProceed = true;
    	
    	int nSuitors = S.size();
    	int nReceivers = R.size();
    	
    	if (nSuitors == 0) { //check if students are loaded
    		System.out.print("ERROR: No suitors are loaded!");
    		matchingCanProceed = false;
    	}
    	else if (nReceivers == 0) { //check if schools are loaded
    		System.out.print("ERROR: No receivers are loaded!");
    		matchingCanProceed = false;
    	}

    	else { //check if the number of suitor openings is equal to number of receiver openings
    		int nTotalSuitorOpenings = 0; //loop over each suitor 
    		for (int i = 0; i < nSuitors; i ++) { 
    			Participant suitor = S.get(i); 
    			nTotalSuitorOpenings += suitor.getMaxMatches(); //add the number of openings of this suitor to the total number of suitor openings
    		}
    		
    		int nTotalReceiverOpenings = 0;
    		for (int i = 0; i < nReceivers; i ++) { //do the same for receivers
    			Participant receiver = R.get(i); 
    			nTotalReceiverOpenings += receiver.getMaxMatches(); //add the number of openings of this receiver to the total number of receiver openings
    		}
    		if (nTotalReceiverOpenings != nTotalSuitorOpenings) { //if the suitor and receiver openings are not equal, print an error message
        		System.out.print("**error openings must be equal**");
        		matchingCanProceed = false;
    		}
    	}

		return matchingCanProceed;
    }

    public boolean match(){ // Gale-Shapley algorithm to match; students are suitors
        return false;
    }

    private boolean makeProposal(int suitor, int receiver){ // suitor proposes
        return false;
    }

    private void makeEngagement(int suitor, int receiver, int oldSuitor){ // make suitor-receiver engagement, break receiver-oldSuitor engagment

    }

    public void calcRegrets(){ // calculate regrets

    }

    public boolean determineStability(){ // calculate if a matching is stable
        return false;
    }

    // print methods
    public void print(){ // print the matching results and statistics

    }

    public void printMatches(){ // print matches

    }

    public void printStats(){  // print matching statistics

    }

    public void printStatsRow(String rowHeading){ // print stats as row

    }

    // reset everything
    public void reset(){
        
    }

}
