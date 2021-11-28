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
    	//IDKKK DOES IT HAVE TO DO WITH THE HINT???
    }

    // methods for matching
    public void clearMatches(){ // clear out existing matches
    	for (int i = 0; i < this.S.size(); i ++) {
    		Participant suitor = S.get(i);
    		suitor.clearMatches(); //clear matches for suitors
    	}
    	
    	for (int i = 0; i < this.R.size(); i ++) {
			Participant receiver = R.get(i);
			receiver.clearMatches(); //clear matches for receivers
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
    	//reset(S,R);
    	    	
        //ArrayList<Integer> freeReceivers = new ArrayList<Integer>(S.get(0).getRankings()); //place all the free receivers into an ArrayList
        ArrayList<Integer> freeSuitors = new ArrayList<Integer>(R.get(0).getRankings()); //place all the free suitors into an ArrayList
		int proposingSuitorIndex = 0; //initialize
		
    	do {
			proposingSuitorIndex = freeSuitors.get(0); //get the first free suitor from the ArrayList of free suitors
			//Participant proposingSuitorObj = S.get(proposingSuitorIndex - 1);
			int nSuitorActiveOpenings = getNSuitorOpenings(); //determine the number of openings this suitor has
			    		
	        ArrayList<Integer> freeReceivers = new ArrayList<Integer>(S.get(proposingSuitorIndex - 1).getRankings()); //place all the free suitors into an ArrayList

    		//for (int j = 0; j < freeReceivers.size(); j++) { //loop through all the receivers (recall nSuitors = nReceivers) from the most preferred to least preferred
	        int j = 0; //look at the highest ranked free receiver
	        do {
		        int receiverIndex = freeReceivers.get(j);
        		Participant receiverObj = R.get(receiverIndex - 1); //get the corresponding receiver object
    			int nReceiverActiveOpenings = getNReceiverOpenings(); //determine the number of openings this receiver has
        		
    			if (nReceiverActiveOpenings != 0) { //if the receiver is free, then the pair becomes engaged
    				makeEngagement(proposingSuitorIndex, receiverIndex, 0); //the 0 indicates an old suitor does not exist
    				nSuitorActiveOpenings --;
    				nReceiverActiveOpenings --;
    			} //end of if-statement
    			else { //otherwise, if the receiver is already engaged, compare the ranks of the proposing suitor and the engaged suitor
    				int worstSuitorMatchIndex = receiverObj.getWorstMatch();
    			    int worstSuitorMatchRank = receiverObj.getRanking(worstSuitorMatchIndex); //get ranking of the receiver's worst suitor match
    				int proposingSuitorRank = receiverObj.getRanking(proposingSuitorIndex); //get receiver's ranking of the proposing suitor 
    				
    				if (proposingSuitorRank < worstSuitorMatchRank) { //if the receiver prefers the proposing suitor over the engaged suitor
    					makeEngagement(proposingSuitorIndex, receiverIndex, worstSuitorMatchIndex); //the pair becomes engaged
        				freeSuitors.set(0,worstSuitorMatchIndex); //replace the suitor who proposed with the suitor who was abandoned in the free list of suitors
        				
        				nSuitorActiveOpenings --;
    				}
    			} //end of else statement
    			
    			j++;
	        } while (nSuitorActiveOpenings > 0); //while the proposing suitor is still free
    		//} //end of for-loop
    		
    	} while (freeSuitors.size() > 0);

    	//return value
    	boolean matchingHappened = true;
    	matchesExist = true;
    	return matchingHappened;
    }
    
    /*
    private boolean makeProposal(int suitor, int receiver){ // suitor proposes
        return false;
    }
    */

    private void makeEngagement(int suitor, int receiver, int oldSuitor){ // make suitor-receiver engagement, break receiver-oldSuitor engagement
		//note: the old suitor is the worst match, if oldSuitor is 0 that means the receiver still has openings left (no need to break receiver-oldSuitor engagement
    	Participant receiverObj = R.get(receiver - 1); //get the corresponding receiver object using index
		Participant suitorObj = S.get(suitor - 1); //get the corresponding suitor object using index

    	if (oldSuitor != 0) { //if all the openings are full, unmatch the worst ranked suitor with the new suitor (carry out unmatching)
        	Participant oldSuitorObj = S.get(oldSuitor - 1); //get the corresponding old suitor object using  index
            receiverObj.unmatch(oldSuitor); // remove the receiver's match with old suitor 
            oldSuitorObj.unmatch(receiver); // remove the old suitor's match with receiver
    	}
    	
        receiverObj.setMatch(suitor);
        suitorObj.setMatch(receiver);	
    }

    public void calcRegrets(){ // calculate regrets
    	
    	//initialize variables
		int totalReceiverRegret = 0;
		int totalSuitorRegret = 0;
		int nSuitors = S.size();
		int nReceivers = R.size();
				
		if (matchesExist == true) {
			
	        //set regrets
	        for (int i = 0; i < nSuitors; i++) { 
		        //set regrets once matching is completed
		    	Participant suitor = S.get(i);
				int nSuitorMatches = suitor.getMaxMatches();
	    		ArrayList<Integer> suitorRankings = new ArrayList<Integer>(suitor.getRankings());
				
				for (int j = 0; j < nSuitorMatches;j++) { //loop through each match in the list of matches
				
			        int matchedReceiverIndex = suitor.getMatch(j);
			        
			        for (int k = 0; k < suitorRankings.size(); k++) { //loop through the suitor's rankings of receivers
			        	if (matchedReceiverIndex == suitorRankings.get(k)) { //if we find the matched receiver in the suitor's list of rankings
			        		int matchedReceiverRank = k + 1;
			        		suitor.setRegret(matchedReceiverRank); //set the suitor's regrets
			        		
			        		totalSuitorRegret += suitor.getRegret(); //add this suitor's regret to the total suitor regret
			        	}
			        }
				}
	        }
	       
	        for (int i = 0; i < nReceivers; i++) { 
		        //set regrets once matching is completed
		    	Participant receiver = R.get(i);
				int nReceiverMatches = receiver.getMaxMatches();
		        ArrayList<Integer> receiverRankings = new ArrayList<Integer>(receiver.getRankings());
				
				for (int j = 0; j < nReceiverMatches;j++) { //loop through each match in the list of matches
				
			        int matchedSuitorIndex = receiver.getMatch(j);
			        
			        for (int k = 0; k < receiverRankings.size(); k++) { //loop through the receiver;s rankings of suitors
			        	if (matchedSuitorIndex == receiverRankings.get(k)) { //if we find the matched suitor in the receiver;s list of rankings
			        		int matchedSuitorRank = k + 1;
			        		receiver.setRegret(matchedSuitorRank); //set the receiver's regrets
			        		
			        		totalReceiverRegret += receiver.getRegret(); //add this receiver's regret to the total receiver regret
			        	}
			        }
				}
	        }

		    //calculate average regrets
			avgSuitorRegret = Double.valueOf(totalSuitorRegret)/ Double.valueOf(nSuitors);	    
			avgReceiverRegret = Double.valueOf(totalReceiverRegret) / Double.valueOf(nReceivers);
			avgTotalRegret = (avgSuitorRegret + avgReceiverRegret) / 2.00;
        }	
    }

    public boolean determineStability(){ // calculate if a matching is stable
        return true;
    }

    // print methods
    public void print(){ // print the matching results and statistics
	    printMatches();
		System.out.print("\n"
				+ "");
	    printStats();
    }

    public void printMatches(){ // print matches (school:student matches)
    	if (matchesExist == true) {
	        if (S.get(0) instanceof School) { //if schools are the suitors
	        	System.out.print("school optimal whatever"); //use suitorsFirst variable to do this
	        }
	        else {
	        	System.out.print("student optimal whatever"); 
	        }
    		
	    	//if an arbitrary student in the S arrayList does not have a matched school, then display an error message
			System.out.println("Matches: ");
			System.out.println("--------");
		
	        if (S.get(0) instanceof School) { //if schools are the suitors
	        	//print suitors first
				for (int i = 0; i < S.size(); i++) {
					Participant suitor = S.get(i);
					System.out.print(suitor.getName() + ": ");
					
					for (int j = 0; j < getNSuitorOpenings(); j++) { //loop over each matched student in the school's matches ArrayList
						int matchedReceiverIndex = suitor.getMatch(j); //get the index of the matched student
						Participant matchedReceiver = S.get(matchedReceiverIndex- 1); //get the corresponding student object using the index
						
						//print the list of student matches
						System.out.print(matchedReceiver.getName()); //print the student's name
						if (i != getNSuitorOpenings() - 1) { //print a comma after the student unless it's the last student
							System.out.print(", ");
						}
					}
				}
	        	
	        }
	        else if (R.get(0) instanceof School) { //if students are the receivers
	        	//print receivers first
	        	
				for (int i = 0; i < R.size(); i++) {
					Participant receiver = R.get(i);
					System.out.print(receiver.getName() + ": ");
					
					for (int j = 0; j < getNReceiverOpenings(); j++) { //loop over each matched student in the school's matches ArrayList
						int matchedSuitorIndex = receiver.getMatch(j); //get the index of the matched student
						Participant matchedSuitor = S.get(matchedSuitorIndex - 1); //get the corresponding student object using the index
						
						//print the list of student matches
						System.out.print(matchedSuitor.getName()); //print the student's name
						if (i != getNReceiverOpenings() - 1) { //print a comma after the student unless it's the last student
							System.out.print(", ");
						}
					}
				}		
	        }
    	}
    }

    public void printStats(){  // print matching statistics
    	if (matchesExist == true) {
	    	boolean matchingIsStable = isStable();
	    	
		    //print whether the matching is stable or unstable
			if (matchingIsStable == true) {
				System.out.println("Stable matching? Yes");
			}
			else {
				System.out.println("Stable matching? No");
			}
	        
			//print average regrets
			System.out.format("Average suitor regret: %.2f%n", Math.round(avgSuitorRegret * 100.0)/100.0); //rounded to 2 decimals
			System.out.format("Average receiver regret: %.2f%n", avgReceiverRegret); //rounded to 2 decimals
			System.out.format("Average total regret: %.2f%n", avgTotalRegret); //rounded to 2 decimals
	        System.out.print("\n");
    	}
    }

    public void printStatsRow(String rowHeading){ // print stats as row
        System.out.print("rowHeading");
        //print the rest of the stats in a row
        
        //determine the winner and stuff, then print the last row indicating that
    }

    // reset everything
    public void reset(){
        
    }

}
