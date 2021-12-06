import java.util.ArrayList;

public class SMPSolver {
    public ArrayList <Participant> S = new ArrayList <>(); // suitors
    public ArrayList <Participant> R = new ArrayList <>(); // receivers
    private double avgSuitorRegret = 0.0; // average suitor regret
    private double avgReceiverRegret = 0.0; // average receiver regret
    private double avgTotalRegret = 0.0; // average total regret
    private boolean matchesExist = false; // whether or not matches exist
    private boolean stable = true; // whether or not matching is stable
    private long compTime = 0; // computation time
    private boolean suitorFirst = false; // whether to print suitor stats first

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
    
    	for (int i = 0; i < S.size(); i ++) {  //create a dummy arraylist filled with participant objects, of the same size as S
    		this.S.add(new Participant());
    	}
    	for (int i = 0; i < R.size(); i ++) {  //create a dummy arraylist filled with participant objects, of the same size as H
    		this.R.add(new Participant());
    	}
    	
    	for (int i = 0; i < S.size(); i ++) { 
    		this.S.set(i, S.get(i));
    	}
    	for (int i = 0; i < R.size(); i ++) {
    		this.R.set(i, R.get(i));
    	}
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
    	
        avgSuitorRegret = 0.0; // reset average suitor regret
        avgReceiverRegret = 0.0; // reset average receiver regret
        avgTotalRegret = 0.0; // reset average total regret
        matchesExist = false; // reset whether or not matches exist
        stable = true; // reset whether or not matching is stable
        compTime = 0; // reset computation time

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
        		System.out.print("\r\n"
        				+ "ERROR: The number of suitor and receiver openings must be equal!\r\n"
        				+ "\r\n"
        				+ "");
        		matchingCanProceed = false;
    		}
    	}

		return matchingCanProceed;
    }

    public boolean match(){ // Gale-Shapley algorithm to match; students are suitors	
		long startTime = System.currentTimeMillis(); //Get current time

    	if (matchesExist == true){
    		for (int i = 0; i < S.size(); i ++) {
    			S.get(i).clearMatches();
    		}
    		for (int i = 0; i < R.size(); i ++) {
    			R.get(i).clearMatches();
    		}
    		
    	}
    	
    	boolean matchingHappened = true;
    	
        ArrayList<Integer> freeSuitors = new ArrayList<Integer>(R.get(0).getRankings()); //place all the free suitors into an ArrayList. Currently all suitors are free
        //ArrayList<Integer> freeReceivers = new ArrayList<Integer>(S.get(0).getRankings()); //place all the free receivers into an ArrayList. Currently all receivers are free

    	do { //while there are still free suitors left...
    		int freeSuitorIndex = freeSuitors.get(0); //arbitrarily get the first free suitor in the list
    		Participant freeSuitorObj = S.get(freeSuitorIndex - 1); //get the suitor object corresponding to the first free suitor in the freeSuitors list
    	
    		boolean proposalAccepted = false;
    		int i = 0;
    		do { //while the suitor is not full, the suitor's proposal is rejected, and there are still free suitors...
        		//this suitor proposes to their most preferred free receiver 
        		int mostPreferredReceiverIndex = freeSuitorObj.getRankings().get(i); 
        		
        		proposalAccepted = makeProposal(freeSuitorIndex, mostPreferredReceiverIndex); //call the makeProposal method, an engagement is made if possible
        		
	    		if (freeSuitorObj.isFull() == true) {
	    			freeSuitors.remove(0);
	    		}
	    		
	    		//check to see if a suitor was freed during the engagement process (if there is, add them back into the free suitors ArrayList)
    			for (int j = 0; j < R.get(0).getRankings().size(); j++) { //for every suitor 
    				int suitorIndex = R.get(0).getRankings().get(j); //get their suitor index
    			    Participant suitorObj = S.get(suitorIndex - 1); //get their corresponding suitor object
    			    if (suitorObj.isFull() == false && freeSuitors.contains(suitorIndex) == false) { //if a free suitor is not found in the free suitors ArrayList, add it into the ArrayList!
    			    	freeSuitors.add(suitorIndex);
    			    }
    			}
	    		    
	    		i ++;
    		} while(freeSuitors.contains(freeSuitorIndex) && proposalAccepted == false && freeSuitors.size() > 0);
    		    		
    	} while (freeSuitors.size() > 0);
    	    	
    	matchesExist = true;
    	
    	compTime = System.currentTimeMillis() - startTime; // Get elapsed time in ms

    	return matchingHappened;
    }
    
    private boolean makeProposal(int suitor, int receiver){ // suitor proposes
    	boolean proposalAccepted = true;
    	
    	Participant receiverObj = R.get(receiver - 1);
    	
		if (receiverObj.isFull() == false) { //if the receiver is free, then the pair becomes engaged directly
			makeEngagement(suitor, receiver, 0); //the 0 is an indicator that there is no old suitor

		} //end of if-statement
		else { //otherwise, if the receiver is already engaged, compare the ranks of the proposing suitor and the engaged suitor
			int worstEngagedSuitorIndex = receiverObj.getWorstMatch();
			int worstEngagedSuitorRank = receiverObj.getRanking(worstEngagedSuitorIndex);
			int proposingSuitorRank = receiverObj.getRanking(suitor);
			
			if (proposingSuitorRank < worstEngagedSuitorRank) { //if the receiver prefers the proposing suitor over the engaged suitor
				makeEngagement(suitor, receiver, worstEngagedSuitorIndex); //the pair becomes engaged, and the worst engaged suitor will become unmatched 
			}
			else {
				proposalAccepted = false;
			}
		} //end of else statement
		
		return proposalAccepted;
    }
    

    private void makeEngagement(int suitor, int receiver, int oldSuitor){ // make suitor-receiver engagement, break receiver-oldSuitor engagement
		//note: the old suitor is the worst match, if oldSuitor is 0 that means the receiver still has openings left (no need to break receiver-oldSuitor engagement
    	Participant receiverObj = R.get(receiver - 1); //get the corresponding receiver object using index
		Participant suitorObj = S.get(suitor - 1); //get the corresponding suitor object using index

    	if (oldSuitor != 0) { //if all the openings are full, unmatch the worst ranked suitor with the new suitor (carry out unmatching)
        	Participant oldSuitorObj = S.get(oldSuitor - 1); //get the corresponding old suitor object using index
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
				int suitorRegret = 0;
								
				for (int j = 0; j < nSuitorMatches; j++) { //loop through each match in the list of matches
				
			        int matchedReceiverIndex = suitor.getMatch(j);
	        		int singleMatchRegret = suitor.getSingleMatchedRegret(matchedReceiverIndex);

	        		suitorRegret += singleMatchRegret; //add this suitor's regret to the total suitor regret	
				}
				suitor.setRegret(suitorRegret); //set the suitor's regrets
	        }
	        
	        for (int i = 0; i < nSuitors; i++) { 
	        	totalSuitorRegret += S.get(i).getRegret();
	        }

	        for (int i = 0; i < nReceivers; i++) { 
		        //set regrets once matching is completed
		    	Participant receiver = R.get(i);
				int nReceiverMatches = receiver.getMaxMatches();
				int receiverRegret = 0;
				
				for (int j = 0; j < nReceiverMatches; j++) { //loop through each match in the list of matches
				
			        int matchedSuitorIndex = receiver.getMatch(j);
			        int singleMatchRegret = receiver.getSingleMatchedRegret(matchedSuitorIndex);
	        		
			        receiverRegret += singleMatchRegret; //add this receiver's regret to the total receiver regret
				}
        		receiver.setRegret(receiverRegret); 
	        }
	        
	        for (int i = 0; i < nReceivers; i++) { 
	        	totalReceiverRegret += R.get(i).getRegret();
	        }

		    //calculate average regrets
			avgSuitorRegret = Double.valueOf(totalSuitorRegret)/ Double.valueOf(nSuitors);	    
			avgReceiverRegret = Double.valueOf(totalReceiverRegret) / Double.valueOf(nReceivers);
			avgTotalRegret = (Double.valueOf(totalSuitorRegret) + Double.valueOf(totalReceiverRegret)) / (nSuitors + nReceivers);
        }	
		
		 determineStability();
    }

    public boolean determineStability(){ // calculate if a matching is stable
    	
    	boolean stable = true;
    	int nReceivers = R.size();
    	
    	//loop through each receiver 
		for (int i = 0; i < nReceivers; i++) { 
			int receiverIndex = i + 1;
			Participant receiverObj = R.get(i); //create the corresponding receiver object
			
			//get the index and rank of the receiver's current worst match
			int worstMatchedSuitorIndex = receiverObj.getWorstMatch(); //get the index of the worst matched suitor
			int worstMatchedSuitorRank = receiverObj.getRanking(worstMatchedSuitorIndex); //get the corresponding rank of the worst matched suitor
	
			//get the index and rank of each of the receiver's preferred match(es)
			//every suitor that have the smaller rank than the worstMatchedSuitorRank is more preferred by the receiver
			
			for (int j = 0; j < worstMatchedSuitorRank - 1; j++) {
				int preferredSuitorIndex = receiverObj.getRankings().get(j); //get the index of the preferred student
				Participant preferredSuitor = S.get(preferredSuitorIndex - 1); //create the student object
				
				//get the index and rank of the preferred suitor's worst matched receiver
				int preferredSuitorWorstMatchedReceiverIndex = preferredSuitor.getWorstMatch(); 
				int preferredSuitorWorstMatchedReceiverRank = preferredSuitor.getRanking(preferredSuitorWorstMatchedReceiverIndex); //get the rank of the preferred suitor's worst matched receiver
				    
			    //loop through receivers that the preferred suitor prefers more than its worst matched receiver
				 for (int k = 0; k < preferredSuitorWorstMatchedReceiverRank - 1; k++) { 
					 int preferredReceiverIndex = preferredSuitor.getRankings().get(k); //get the index of the preferred school
					 if (receiverIndex == preferredReceiverIndex) { //if the preferred student of the school also prefers this school more than their match
		    		 stable = false; //then the match is unstable
					 } // end of if-statement
				 } //end of for-loop
			} //end of for-loop
		} //end of for-loop
		return stable;
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
	    	//if an arbitrary student in the S arrayList does not have a matched school, then display an error message
			System.out.println("Matches: ");
			System.out.println("--------");
		
			
	        if (suitorFirst == true) { //print receivers first (receiver: suitor matches)
				for (int i = 0; i < R.size(); i++) {
					Participant receiver = R.get(i);
					System.out.print(receiver.getName() + ": ");
					
					for (int j = 0; j < receiver.getMaxMatches(); j++) { //loop over each matched suitor in the receiver's matches 
						int matchedSuitorIndex = receiver.getMatch(j); //get the index of the matched suitor
						Participant matchedSuitor = S.get(matchedSuitorIndex - 1); //get the corresponding suitor object using the index
						
						//print the list of student matches
						if (j != receiver.getMaxMatches() - 1) { //print a comma after the suitor unless it's the last student
							System.out.print(matchedSuitor.getName() + ", ");
						}
						else {
							System.out.println(matchedSuitor.getName()); //just print the suitor's name
						}
					}
				}	
	        }
	        else{ //print suitors first (suitor: receiver matches)    	 
	         	//print suitors first
				for (int i = 0; i < S.size(); i++) {
					Participant suitor = S.get(i);
					System.out.print(suitor.getName() + ": ");
					
					for (int j = 0; j < suitor.getMaxMatches(); j++) { //loop over each matched receiver in the suitor's matches 
						int matchedReceiverIndex = suitor.getMatch(j); //get the index of the matched receiver
						Participant matchedReceiver = R.get(matchedReceiverIndex- 1); //get the corresponding receiver object using the index
						
						//print the list of suitor matches
						if (j != suitor.getMaxMatches() - 1) { //print a comma after the receiver unless it's the last student
							System.out.print(matchedReceiver.getName() + ", ");
						}
						else {
							System.out.println(matchedReceiver.getName()); //just print the receiver's name
						}
					}
				}
	        }
    	}
    }

    public void printStats(){  // print matching statistics
    	if (matchesExist == true) {
		    //print whether the matching is stable or unstable
			if (stable == true) {
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
    	String printStable = "";

    	if (stable == true) {
    		printStable += "Yes";
    	}
    	else {
    		printStable += "No";
    	}
    	
    	//check if we should print avg suitor regret first or avg receiver regret first
    	if (suitorFirst == true) {
			System.out.format("%-24s%4s%21.2f%21.2f%21.2f  %19s\n", rowHeading, printStable, avgSuitorRegret, avgReceiverRegret, avgTotalRegret, compTime); //attempt to do spacing
    	}
    	else {
			System.out.format("%-24s%4s%21.2f%21.2f%21.2f  %19s\n", rowHeading, printStable, avgReceiverRegret, avgSuitorRegret, avgTotalRegret, compTime); //attempt to do spacing
    	}
    }

    // reset everything
    public void reset(){ //parameters??
        S = new ArrayList <Participant>(); // reset suitors
        R = new ArrayList <Participant>(); // reset receivers
        avgSuitorRegret = 0.0; // reset average suitor regret
        avgReceiverRegret = 0.0; // reset average receiver regret
        avgTotalRegret = 0.0; // reset average total regret
        matchesExist = false; // reset whether or not matches exist
        stable = true; // reset whether or not matching is stable
        compTime = 0; // reset computation time
    }

}
