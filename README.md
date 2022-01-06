# Stable Marriage Matchmaking Algorithm

This project builds upon the previous project (schools-admissions-matchmaking-algo), where a program was built to match students and schools using principles from the stable marriage problem (SMP). This previous project created a framework for matching students to high schools, and allowed matches to happen between schools and students only. 

This version of the project generalized the situation so that this code serves as base to match any two groups, not just students and schools. Participants in the matching are allowed to have more than one match (e.g., schools can have more than one opening for students). The remainder of this section provides an overview of SMP and the student-school matching problem from the previous project.

# Stable Marriage Problem (SMP)
SMP is described as follows. Say you have a group of n men and n women, and they need to be matched together in marriage. The men have ranked all the women in order of preference (1 is most preferred, n is least preferred), and the women have similarly ranked all the men in order of preference. The matching solution should make sure that each man is married to exactly one woman and vice versa, and that the marriages are stable. A stable matching solution means that there is no one person who would rather be with someone else who would also prefer to be with them. In other words, no two people would want to have an affair with each other.

# Main Objectives
In this project, I have achieved three key objectives:

  1. Made the scenario more realistic by allowing schools to accept 
  multiple students

  2. Generalized the code using inheritance so that the matching 
  framework can be used for any two groups of participants, not just 
  students and schools
  
  3. Compared student-optimal and school-optimal solutions, since we will
  try doing the matching first with students and suitors, and then with
  schools as suitors. This comparison allowed us to see how different the
  solutions are. 

# User Experience
All statistics and calculations are printed in a tabular format so users can easily see the comparison between student-optimal and school-optimal solutions. The program does not require manual user input for participant information as it takes in text files as inputs.

# Learnings
The key learnings from this project are inheritance and polymorphism in Java.

# Demo
Two text files (students10.txt and schools10.txt) are included as sample input files you can utilize to see how this program works. 
