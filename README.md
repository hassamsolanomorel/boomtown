# BoomTown - Assignmment 

This public repos contains my submission for the BoomTown assignment given on June 18th, 2018. 

There are two ways to view run the assignment: 

## Method 1 - Eclipse
The included "Solano_BoomTown.zip" file can be directly imported into Eclipse and run as is.
1. Open Eclipse
2. Click File -> Import
3. Under the General folder select "Projects from Folder or Archive"
4. Click Next
5. Next to Input Source press "Archive"
6. Select "Solano_Boomtown.zip" from your download location
7. Check only "Solano_BoomTown.zip_expanded/Solano_BoomTown" from the list
8. Click Finish
9. Run main in "BoomTown_Main.java"

## Method 2 - Command Line 
There are additional copies of the source .java and .jar files within the "src" folder. The BoomTown_Main.java can be
run from the command line by following these steps (instructions are given for Linux based OSs):

1. Open Terminal
2. CD to the src folder 
3. (Compile) javac -cp gson-2.8.5.jar:json-simple-1.1.1.jar *.java
4. (Run) java -cp gson-2.8.5.jar:json-simple-1.1.1.jar:. BoomTown_Main

In both methods a new file named "output.txt" will be created in the src folder after running BoomTown_Main. 
