# gale-shapley-many-to-many

This project builds upon the previous project, where a program was built to match students and schools using principles from the stable marriage problem (SMP), and allowing matching to occur only betwen schools and students. This version of the project will generalize the situation so that this code serves as base to match any two groups, not just students and schools. Participants in the matching will also be allowed to have more than one match (e.g., schools can have more than one opening for students). The remainder of this section provides an overview of SMP and the student-school matching problem from the previous project.

SMP is described as follows. Say you have a group of n men and n women, and they need to be matched together in marriage. The men have ranked all the women in order of preference (1 is most preferred, n is least preferred), and the women have similarly ranked all the men in order of preference. The matching solution should make sure that each man is married to exactly one woman and vice versa, and that the marriages are stable. A stable matching solution means that there is no one person who would rather be with someone else who would also prefer to be with them. In other words, no two people would want to have an affair with each other.

In this project, I have done three things:

  1. Make the scenario more realistic by allowing schools to accept 
  multiple students

  2. Generalize the code using inheritance so that the matching 
  framework can be used for any two groups of participants, not just 
  students and schools
  
  3. Compare student-optimal and school-optimal solutions, since we will
  try doing the matching first with students and suitors, and then with
  schools as suitors. This comparison allows us to see how different the
  solutions are. 

