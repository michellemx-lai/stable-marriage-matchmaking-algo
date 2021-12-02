import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pro5_laimic12 {
	public static void main(String[] args) throws IOException, FileNotFoundException, NumberFormatException {

		String menuInput = "";
		//initialize variables
		ArrayList<Student> S = new ArrayList<Student>(); //create an ArrayList to store students
		ArrayList<School> H = new ArrayList<School>(); //create an ArrayList to store schools

	    ArrayList<Student> S2 = new ArrayList<Student>(); //create an ArrayList to store a copy of students
		ArrayList<School> H2 = new ArrayList<School>(); //create an ArrayList to store a copy schools
	
    	SMPSolver smpSolverStudentSuitors = new SMPSolver();
    	SMPSolver smpSolverSchoolSuitors = new SMPSolver();
		
		do {
			//print an error message and ask user for a valid string input each time the user provides an invalid input. 
            displayMenu();
            
			boolean valid;
			do {
				valid = true;
				
				//invalid if the input is not a string
				try {
					menuInput = BasicFunctions.cin.readLine();
				}
				catch(IOException e) {
					valid = false;
				}
				catch(NumberFormatException e) {
					valid = false;
				}
	
				//error message 
				if (!valid || !((menuInput.equalsIgnoreCase("L") || menuInput.equalsIgnoreCase("E") || menuInput.equalsIgnoreCase("P") || menuInput.equalsIgnoreCase("M") || menuInput.equalsIgnoreCase("D") || menuInput.equalsIgnoreCase("X") || menuInput.equalsIgnoreCase("R") || menuInput.equalsIgnoreCase("Q")))) {
					valid = false;
					System.out.print("\n"
							+ "ERROR: Invalid menu choice!\n"
							+ "\n"
							+ "");
				}
			} while (valid == false);
			
			//call functions based on the user's menu input
			if (menuInput.equalsIgnoreCase("L")) {

				int nNewSchools = loadSchools(H); //load new schools
				int nSchools = H.size();
			
		        if (nNewSchools != 0) { //if any new schools were added
		        	S.clear(); //clear all students since their number of rankings are no longer equal to the number of schools
		        }
		        
				int nNewStudents = loadStudents(S,H);
								
				if (nNewStudents != 0) {
					
	    			//recalculate school rankings
					for (int i = 0; i < nSchools; i++) {
		    			School school = H.get(i);
		    			school.calcRankings(S);
		    				    			
		    			school.setNParticipants(S.size());
					}
				}			    
				
				H2 = copySchools(H);
			    S2 = copyStudents(S);
			    
    			if (smpSolverStudentSuitors.matchesExist() == true && (nNewSchools != 0 || nNewStudents != 0)) {
    				smpSolverStudentSuitors.reset();
    				smpSolverSchoolSuitors.reset(); 
    			}

		    	smpSolverStudentSuitors.setParticipants(S,H);
		    	smpSolverStudentSuitors.setSuitorFirst(true);

		    	smpSolverSchoolSuitors.setParticipants(H2,S2);
		    	smpSolverSchoolSuitors.setSuitorFirst(false); //do not print suitor stats first because schools are the suitors
			}
	
			else if (menuInput.equalsIgnoreCase("E")) {
				/*
				editData(S2,H2);

				smpSolverStudentSuitors.reset(S2,H2); 
				smpSolverSchoolSuitors.reset(S2,H2); 
				*/
			}
	
			else if (menuInput.equalsIgnoreCase("P")) {
				int nStudents = S.size();
				int nSchools = H.size();
				
				if (nStudents == 0) { //print error message if no students are loaded
					System.out.print("\nERROR: No students are loaded!\n");
				}
				else {
			        System.out.print("STUDENTS:\n"
			        		+ "\n"
			        		+ "");
			        
			        printStudents(S,H);
				}

				if (nSchools == 0) { //print error message if no schools are loaded
					System.out.print("\nERROR: No schools are loaded!\n");
				}
				else {
			        System.out.print("SCHOOLS:\n"
			        		+ "\n"
			        		+ "");
			        printSchools(S,H);
				}
			}
	
			else if (menuInput.equalsIgnoreCase("M")) {
				
				boolean matchingCanProceed = smpSolverStudentSuitors.matchingCanProceed();

				if (matchingCanProceed == true) {
					
					
					//match with students as suitors
					long startStudentSuitors = System.currentTimeMillis(); //Get current time
			        smpSolverStudentSuitors.match();
			    	long elapsedTimeStudentSuitors = System.currentTimeMillis() - startStudentSuitors; // Get elapsed time in ms
			    	smpSolverStudentSuitors.calcRegrets();
			    	
			    	smpSolverStudentSuitors.printStats(); //print regrets and stability
					
			        System.out.print(S.size() + " matches made in " + elapsedTimeStudentSuitors + "ms!\n" //print time elapsed
			        		+ "\n"
			        		+ "");
					

			        //match with schools as suitors
					long startSchoolSuitors = System.currentTimeMillis(); //Get current time
			        smpSolverSchoolSuitors.match();
			    	long elapsedTimeSchoolSuitors = System.currentTimeMillis() - startSchoolSuitors; // Get elapsed time in ms
			    	smpSolverSchoolSuitors.calcRegrets();

			    	smpSolverSchoolSuitors.printStats(); //print regrets and stability
					
			        System.out.print(S.size() + " matches made in " + elapsedTimeSchoolSuitors + "ms!\n" //print time elapsed
			        		+ "\n"
			        		+ "");

				}
			}
	
			else if (menuInput.equalsIgnoreCase("D")) {
				if (smpSolverStudentSuitors.matchesExist() == false) { 
					System.out.print("ERROR: No matches exist!");
				}
				else {
					smpSolverStudentSuitors.print();
					smpSolverSchoolSuitors.print();
				}
			}
			
			
			else if (menuInput.equalsIgnoreCase("X")) {
				if (smpSolverStudentSuitors.matchesExist() == false) { 
					System.out.print("ERROR: No matches exist!");
				}
				else {
					printComparison(smpSolverStudentSuitors,smpSolverSchoolSuitors);
				}			
			}
	
			else if (menuInput.equalsIgnoreCase("R")) {
				smpSolverStudentSuitors.reset();
				smpSolverSchoolSuitors.reset();
				S.clear();
				H.clear();
				S2.clear();
				H2.clear();
				
				System.out.print("Database cleared!\n"
						+ "\n"
						+ "");
			}
	
			else if (menuInput.equalsIgnoreCase("Q")) {
	            System.out.print(" \n"
	            		+ "Arrivederci!\n"
	            		+ "");
				System.exit(0);
			}
		} while (menuInput.equalsIgnoreCase("Q") == false);
	}
	
	//display the main menu...and get user input for menu option
    public static void displayMenu() throws IOException, NumberFormatException {
		System.out.println("JAVA STABLE MARRIAGE PROBLEM v2\n"
				+ "\n"
				+ "L - Load students and schools from file\n"
				+ "E - Edit students and schools\n"
				+ "P - Print students and schools\n"
				+ "M - Match students and schools using Gale-Shapley algorithm\n"
				+ "D - Display matches and statistics\n"
				+ "R - Reset database\n"
				+ "Q - Quit\n"
				+ "\n"
				+ "Enter choice: ");

    } //end of displayMenu()

    //load school info from a user-provided file. Add new schools to list of existing schools. Return # new schools.
    public static int loadSchools(ArrayList<School> H) throws IOException, FileNotFoundException, NumberFormatException {

    	//initialize variables
    	int nNewSchools = 0;
    	String fileName = BasicFunctions.getFile("Enter school file name (0 to cancel): ");

    	
    	if (fileName.equals("0") == false) {
    		//tokenize comma separated file
	    	String line;
        	BufferedReader fin = new BufferedReader(new FileReader(fileName));
	    	int nSchoolsInFile = 0; //counter variable, total valid AND invalid new students in the file
    		
	        do {
	        	line = fin.readLine();
	        	if (line != null) {   
	    			nSchoolsInFile ++; //the number of schools increases by 1
	        		
	        		String[] splitString = line.split(",");	//split up the line at each comma
	        		String name; //the first element is the name of the school
	        		Double alpha;
	        		int nMaxMatches = 0; 
	        		
	        		if (splitString.length == 3) { //make sure the school is valid (has name, alpha, and number of openings)
	        			
	    				name = splitString[0]; //the first element is the name
	    				alpha = Double.parseDouble(splitString[1]); //the second element is the GPA weight (alpha) 
	    				nMaxMatches = Integer.parseInt(splitString[2]); //the third element is number of openings
		        	    
	    				School school = new School(name, alpha, nMaxMatches, 0); //create a new school object with the name and alpha
	    				
	    				//make sure the school is valid 
	    				if (school.isValid() == true) { //alpha must be between 0 and 1
			    			H.add(school); //add this school to the list of schools
			    			nNewSchools ++; //the number of (valid) new schools added increases by 1
	    				}
	        		}
	        	}
	        } while(line != null);
	        
	        fin.close(); //close the file 

	        System.out.print("\n"
	        		+ nNewSchools + " of " + nSchoolsInFile + " schools loaded!\n"
	        				+ "\n"
	        				+ "");
    	}
    	
    	return nNewSchools;
    }
    
    
    //load student info from a user-provided file. Add new students to list of existing students. Return # of new students. 
    public static int loadStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException, FileNotFoundException, NumberFormatException {
    	
    	int nNewStudents = 0; //counter variable, total valid new students 
    	int nSchools = H.size();
    	int nStudentsInFile = 0; //counter variable, total valid AND invalid new students in the file
        
    	//checked if nSchools = nRankings, and that ranks are not reused
        String fileName = BasicFunctions.getFile("Enter student file name (0 to cancel): ");
        
        if (fileName.equals("0") == false) {
	        		
	        if (nSchools == 0) { //if there are no schools added, then just count the number of students in file, no new students would be added
		    	//initialize variables
		    	String line;
	        	BufferedReader fin = new BufferedReader(new FileReader(fileName));
		    	
		        //tokenize comma separated file
		        do {
		        	line = fin.readLine();
	
		        	if (line != null) {      	
		                nStudentsInFile ++; //add 1 to the total number of students in file
		        	}
		        } while(line != null);
		        
		        fin.close(); //close the file 
	        }
	
	        else {
		        if (fileName.equals("0") == false) {
			    	//initialize variables
			    	String line;
		        	BufferedReader fin = new BufferedReader(new FileReader(fileName));
		        	
			        //tokenize comma separated file
			        do {
			        	line = fin.readLine();
		
			        	if (line != null) {      	
			                nStudentsInFile ++; //add 1 to the total number of students in file
			                
			                //initialize variables
			        		Double GPA;
			        		int ES;
			        		String name;
			        		String[] splitString = line.split(",");	//tokenize comma separated items, split up at comma
			        		
			        		name = splitString[0]; //the first element is the name of the school
			        		GPA = Double.parseDouble(splitString[1]); //the second element is the GPA weight (alpha) 
			        		ES = Integer.parseInt(splitString[2]); //the second element is the GPA weight (alpha) 
			        	    int nRankings = splitString.length - 3; //the number of elements is equal to the number of elements in splitString minus 3 (slots taken up by name, GPA, ES)
		        	        int schoolIndex = 0;
			        	    
			        	    Student student = new Student(name, GPA, ES, nSchools); //create a new student object
		        	    	
		        	    	if (splitString.length >= 3) { //student is not valid unless it has rankings
	        	        	    
			        	    	ArrayList <Integer> tempRankings = new ArrayList <Integer> ();		        	    	
					        	 
				        	    for (int i = 0; i < nRankings; i++) { //add all the rank
				        	    	schoolIndex = Integer.parseInt(splitString[3 + i]);
				        	    	tempRankings.add(schoolIndex);
				        	    }
				        	    
			    	    		student.setRankings(tempRankings); //assign the school rankings to the student
                                
			    	    		if (student.isValid() == true) {
			    	    			nNewStudents ++;
			    	    			S.add(student); //only add the student to the list if the student is valid
			    	    		}
		        	    	} // end of if-statement
		        	    	
			        	} // end of if-statement
			        	
			        } while(line != null);
			        
			        fin.close(); //close the file 
		        }
	        }
	        
	        System.out.print("\n"
	        		+ nNewStudents + " of " + nStudentsInFile + " students loaded!\n"
	        				+ "\n"
	        				+ "");
	        
        }
        
    	return nNewStudents; //return the number of new schools
    }
    
    /*
    //display sub-menu to edit students and schools
    public static void editData(ArrayList<Student> S, ArrayList<School> H) throws IOException, NumberFormatException {
		int nStudents = S.size();
		int nSchools = H.size();
		
    	boolean valid;
		String menuInput = "";

		//print an error message and ask user for a valid string input each time the user provides an invalid input. 
		do {
			valid = true;
	    	System.out.print("\n"
	    			+ "Edit data\n"
	    			+ "---------\n"
	    			+ "S - Edit students\n"
	    			+ "H - Edit high schools\n"
	    			+ "Q - Quit\n"
	    			+ "\n"
	    			+ "Enter choice: ");

			//invalid if the input is not a string
			try {
				menuInput = BasicFunctions.cin.readLine();
			}
			catch(IOException e) {
				valid = false;
			}
			catch(NumberFormatException e) {
				valid = false;
			}

			//error message 
			if (!valid || !(menuInput.equalsIgnoreCase("S") || menuInput.equalsIgnoreCase("H") || menuInput.equalsIgnoreCase("Q"))) {
				valid = false;
				System.out.print("\n"
						+ "ERROR: Invalid menu choice!\n"
						+ "\n"
						+ "");
			}
		} while (! valid );
		
		//call functions based on the user's menu input
		if (menuInput.equalsIgnoreCase("S")) {
			//if there are no students loaded, print an error message and display sub-menu
			if (nStudents == 0) {
				System.out.print("\nERROR: No Students are loaded!\n");
				editData(S, H); //bring user back to sub-menu; display sub-menu to edit data
			}
			else {
				editStudents(S, H);
			}
		}

		else if (menuInput.equalsIgnoreCase("H")) {
			//if there are no schools loaded, print an error message and display sub-menu
			if (nSchools == 0) {
				System.out.print("\nERROR: No schools are loaded!\n");
				editData(S, H); //bring user back to sub-menu; display sub-menu to edit data
			}
			else {
				editSchools(S, H);
			}
		}

		else if (menuInput.equalsIgnoreCase("Q")) {
			System.out.print("\n");
			displayMenu();
		}
    }
    */
    
    /*

    //sub-area to edit students. Update the edited student's regret if necessary. Any existing school rankings & regrets are recalculated after editing a student.
    public static void editStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException {
    	int nStudents = S.size();
    	int nSchools = H.size();
		int studentIndex = 1; //initialize student index
		
		do {
			printStudents(S, H); //print current Student info
			
			//get user input for the index of the Student they want to edit
			studentIndex = BasicFunctions.getInteger("Enter student (0 to quit): \n", 0, nStudents); //assign student index to user input for the student to be edited
	        
			//if input for student index is 0, then bring user back to sub-menu; display sub-menu to edit data
			if (studentIndex == 0){
				editData(S, H); 
			}
			
			//otherwise, edit students (editing rankings is optional)
			else {			
				Student student = S.get(studentIndex - 1);
				boolean valid = true;

					
				student.editInfo(H, false); //allow user to just edit general student info but not ranking, for now
					
				
				//ask user if they want to edit rankings (yes or no)
				String userInput = "";	//initialize user input 
				
				//print an error message and ask user for a valid string input each time the user provides an invalid input
				do {
					System.out.print("Edit rankings (y/n): ");
					//invalid if the input is not a string
					try {
						userInput = BasicFunctions.cin.readLine();
						valid = true;
					}
					catch(IOException e) {
						valid = false;
					}
					catch(NumberFormatException e) {
						valid = false;
					}
					if (!valid || !(userInput.equalsIgnoreCase("Y") || userInput.equalsIgnoreCase("N"))) {
						System.out.print("ERROR: Choice must be 'y' or 'n'!");
						valid = false;
					}
				} while ( valid == false );
				
				//error message 
				if (userInput.equalsIgnoreCase("Y")) { 
					student.editRankings(H); //edit the student's rankings


			        //erase then recalculate every school's ranking of students
					for (int i = 0; i < nSchools; i++) { //loop over each School
						School school = H.get(i);
						school.eraseRankings(); //erase the school's rankings of students
					}
					
					//recalculate regrets for both schools and students, and clear rankings of schools
					for (int i = 0; i < nSchools; i++) {
						
						//recalculate regrets for students
						int matchedStudentIndex = H.get(i).getStudent();
						int matchedStudentRank = H.get(i).getRanking(matchedStudentIndex);
						H.get(i).setRegret(matchedStudentRank);
						
						//recalculate regrets for schools
						int matchedSchoolIndex = S.get(i).getSchool();
						int matchedSchoolRank = S.get(i).getRanking(matchedSchoolIndex); 
						S.get(i).setRegret(matchedSchoolRank);
					}
				}	
			}
			System.out.println("\n");
		} while ( studentIndex != 0 );
    }
    */
    
    /*
    
    //sub-area to edit schools. Update the edited school's existing rankings and regrets.
    public static void editSchools(ArrayList<Student> S, ArrayList<School> H) throws IOException{
		int nSchools = H.size();
		int schoolIndex = 1; //initialize school index
		
		do {	
			printSchools(S, H); //print current School info
			
			schoolIndex = BasicFunctions.getInteger("Enter School (0 to quit): \n", 0, nSchools); //get user input for which student is to be edited
			
			//if there are no schools loaded, 
			if (nSchools == 0) {
				System.out.print("\nERROR: No schools are loaded!\n"); //then print an error message
				editData(S, H); //and then bring user back to sub-menu; display sub-menu
			}
			
			//if input is 0, quit and go to sub-menu
			else if (schoolIndex == 0){ //or if the user chooses 0 to quit
				editData(S, H); //then bring user back to sub-menu; display sub-menu
			}
			
			//otherwise, edit school
			else {
				School school = H.get(schoolIndex - 1);
				school.eraseRankings(); //erase the pre-existing rankings of this school
				school.editInfo(S); //edit the info of this school
				school.calcRankings(S); //recalculate the rankings for all schools
				
				//recalculate regrets for the edited school
				int matchedStudentIndex = school.getStudent();
				int matchedStudentRank = school.getRanking(matchedStudentIndex);
				school.setRegret(matchedStudentRank);
			} 
		} while (schoolIndex != 0 );
    }
    
    */
    
    //print students to screen (include matched school if one exists)
    public static void printStudents(ArrayList<Student> S, ArrayList<School> H) throws IOException{
    	
    	int nStudents = S.size();
    	
		System.out.format("%-3s %-27s%8s%4s  %-27s%-22s\n", " #", "Name", "GPA", "ES", "Assigned school", "Preferred school order");
		
		for (int i = 0; i < 94; i++) {
			System.out.print("-");
		}
        
		System.out.print("\n");
		
		for (int i = 0; i < nStudents; i++) {
			Student student = S.get(i); //create a student object 
			int studentIndex = i + 1; 
			System.out.print(" " + studentIndex + ". "); //print the student index 
			student.print(H); 

			if (i != nStudents - 1) { //print a line after the row unless it's the last row
				System.out.print("\n");
			}
		}

		System.out.print("\n");
		
		for (int i = 0; i < 94; i++) {
			System.out.print("-");
		}  
		
		System.out.println("\n");
    }
    
    //print schools to screen (include matched student if one exists)
    public static void printSchools(ArrayList<Student> S, ArrayList<School> H) {

    	int nSchools = H.size();
    	
		System.out.format("%-3s %-27s%8s%4s  %-27s%-22s\n", " #", "Name", "# Spots", "Weight", "Assigned student", "Preferred student order");
		
		for (int i = 0; i < 94; i++) {
			System.out.print("-");
		}
        
		System.out.print("\n");
		
		for (int i = 0; i < nSchools; i++) {
			School school = H.get(i); //create a school object
			int schoolIndex = i + 1;
			System.out.print(" " + schoolIndex + ". ");
			school.print(S);
			
			if (i != nSchools - 1) { //print a line after the row unless it's the last row
				System.out.print("\n");
			}
		}
		
		System.out.print("\n");
		
		for (int i = 0; i < 94; i++) {
			System.out.print("-");
		}
		
		System.out.println("\n");
    }    
    public static void printComparison(SMPSolver GSS, SMPSolver GSH) { //print comparison of student-optimal and school-optimal solutions
		GSS.setSuitorFirst(false); //students are suitors in this case
		GSH.setSuitorFirst(true); //schools are suitors in this case
		 
		System.out.print("\n"
					+ "Solution              Stable    Avg school regret   Avg student regret     Avg total regret       Comp time (ms)\n"
					+ "----------------------------------------------------------------------------------------------------------------");
					
		GSS.printStatsRow("Student optimal");
		GSH.printStatsRow("School optimal"); 
		
		String stableWinner;
		String avgSchoolRegretWinner;
		String avgStudentRegretWinner;
		String avgTotalRegretWinner;
		String compTimeWinner;
		
		if (GSS.isStable() == true && GSH.isStable() == false) {
			stableWinner = "Student-opt";
		}
		else if (GSS.isStable() == false && GSH.isStable() == true) {
			stableWinner = "School-opt";
		}
		else {
			stableWinner = "Tie";
		}
		
		if (GSS.getAvgReceiverRegret() > GSH.getAvgReceiverRegret()) {
			avgSchoolRegretWinner = "School-opt";
		}
		else if (GSS.getAvgReceiverRegret() < GSH.getAvgReceiverRegret()) {
			avgSchoolRegretWinner = "Student-opt";
		}
		else {
			avgSchoolRegretWinner = "Tie";
		}
		
		if (GSS.getAvgSuitorRegret() > GSH.getAvgSuitorRegret()) {
			avgStudentRegretWinner = "School-opt";
		}
		else if (GSS.getAvgSuitorRegret() < GSH.getAvgSuitorRegret()) {
			avgStudentRegretWinner = "Student-opt";
		}
		else {
			avgStudentRegretWinner = "Tie";
		}
		
		if (GSS.getAvgTotalRegret() > GSH.getAvgTotalRegret()) {
			avgTotalRegretWinner = "School-opt";
		}
		else if (GSS.getAvgTotalRegret() < GSH.getAvgTotalRegret()) {
			avgTotalRegretWinner = "Student-opt";
		}
		else {
			avgTotalRegretWinner = "Tie";
		}
		
		if (GSS.getTime() > GSH.getTime()) {
			compTimeWinner = "School-opt";
		}
		else if (GSS.getTime() < GSH.getTime()) {
			compTimeWinner = "Student-opt";
		}
		else {
			compTimeWinner = "Tie";
		}
		
		System.out.print("----------------------------------------------------------------------------------------------------------------\n"
		+ "WINNER                   " + stableWinner + "                  " + avgSchoolRegretWinner + "                  " + avgStudentRegretWinner + "                  " + avgTotalRegretWinner + "           " + compTimeWinner + "\n"
		+ "----------------------------------------------------------------------------------------------------------------");
    }
    
    public static ArrayList<School> copySchools(ArrayList<School> P){ //create independent copy of School ArrayList        		
     	ArrayList<School> newList = new ArrayList<School>();
    	for (int i = 0; i < P.size(); i ++) {
    		String name = P.get(i).getName();
    		double alpha = P.get(i).getAlpha();
    		int maxMatches = P.get(i).getMaxMatches();
    		int nStudents = P.get(i).getNParticipants();
    		School temp = new School(name, alpha, maxMatches, nStudents);
			temp.setRankings(P.get(i).getRankings());
    		newList.add(temp);
    	}
    	return newList;
    }
    
    public static ArrayList<Student> copyStudents(ArrayList<Student> P){
    	ArrayList<Student> newList = new ArrayList<Student>();
    	for (int i = 0; i < P.size(); i ++) {
    		String name = P.get(i).getName();
    		double GPA = P.get(i).getGPA();
    		int ES = P.get(i).getES();
    		int nSchools = P.get(i).getNParticipants();
    		Student temp = new Student(name, GPA, ES, nSchools);
			temp.setRankings(P.get(i).getRankings());
    		newList.add(temp);
    	}
    	return newList;
    }

}
