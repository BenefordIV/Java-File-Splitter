/**
 * This program gives examples on how to use file stream methods such as a Buffered streams and File streams.
 * It is a gui that asks the user to point to a text file (in this case I have a text file in my project file for demonstrative purposes
 * but you can use any text file that you want as long as you give it a direct path.). It does this by creating a file object based
 * on the information typed into the gui and a number (how many times the user wants to split the file). 
 * 
 * This information is stored into a few different variables and are called whenever I need to make a new BufferedInput/Output(FileInput/Output)Streams
 * When the event handler splitFiles() is invoked it will take the information, store it into the objects and variables that are designated to it
 * and then proceed to loop through the file X times to take the data and dump it into new files.
 * 
 * Most of the code in the event handler was left over from the Chapter 17 Prog 10 homework that we were told to rewrite. Since I only needed to change a few things for it
 * to work the way I wanted it to. These changes include;
 * 			Changing the int numberOfPieces to int numberOfPieces = Integer.parseInt(tfUserNumber.getText());
 * 			Creating at minimum two file objects outside of the filestreams. This was only for my benefit so I mentally picture what is going on
 * 			Removing the  "if (args.length != 2) {
      							System.out.println("Usage: java Exercise17_10 SourceFile numberOfPieces");
      							System.exit(1);"
      		Changing the args[0] values
      		Moving all of the splitting functionallity into an event handler rather than in the main method
      		Creating the gui
    }

 * @author benst
 */

import java.io.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FileSplitter extends Application{
	private TextField tfUserFile = new TextField();
	//This textfield has to be static in order to take the information and store it correctly in the variable in the event handler.
	private static TextField tfUserNumber = new TextField();
	private Button btConfirm = new Button("Confirm");
	//Alert in case anything goes wrong with the program.
	private Alert alert = new Alert(null);
	
	//start method that will create the program
	@Override
	public void start(Stage ps){
		//Gridpane to make everything pretty
		GridPane p1 = new GridPane();
		p1.setAlignment(Pos.CENTER);
		
		//Instructions for how to use the program
		p1.add(new Label("To use this program, please type into the first box \"bob.txt\"\n and in the following box please type in the number of files you wish to create"), 0, 0);
		
		//adding the textfields to the GridPane
		p1.add(new Label("Text file you wish to split"), 0, 1);
		p1.add(tfUserFile, 1, 1);
		
		p1.add(new Label("The number of files you wish to split into"), 0, 2);
		p1.add(tfUserNumber, 1, 2);
		
		//Creatinga  borderPane to add everything nicely
		BorderPane bp = new BorderPane();
		bp.setLeft(p1);
		bp.setBottom(btConfirm);
		
		//EventHandler that will invoke the splitFile() method.
		btConfirm.setOnAction(e -> {
			try {
				splitFile();
			} catch (IOException e1) {
				alert.setContentText("IOException thrown \n File not found.");
				alert.showAndWait();
			}
		});
		//building the scene
		Scene scene = new Scene(bp, 600, 150);
		ps.setScene(scene);
		ps.setTitle("File Splitter");
		ps.show();
	}
	
	//splitFile() function that is mostly just a copy from the author's chapter 17 program 10 assignment.
	public void splitFile() throws IOException{
//		System.out.println("test 2");
		File file = new File(tfUserFile.getText());
		System.out.println(file.exists());
		//file.createNewFile();
	    try (
	    		//input stream that will create a bufferedinputstream that takes a fileinputstream of the file object
	    		//that is created at the start of the event handler.
	      BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
	    		
	    ) {
//	    	System.out.println("test 3");
	    	
	    	//Storing the amount of times they want to split their files
	    	int numberOfPieces = Integer.parseInt(tfUserNumber.getText());
	 
	      //takes the size of the file and stores it
	      long fileSize = input.available();
	      
	      //takes the size of the file / number that the user input
	      int splitFileSize = (int)
	        Math.ceil(1.0 * fileSize / numberOfPieces);
	    
	      //for loop that will create new files based on the amount of numbers that the user input into the text box 
	      for (int i = 1; i <= numberOfPieces; i++) {
	    	  ///The new files are then named whatever the file name originally was (in the demo case it's bob.txt) with a ".1" afterwards
	    	  //To demonstrate the times that it has split
	    	  File newFile = new File(file + "." + i);
	    	  
	        try (
	        		//outputstream that will create a new file based on the name of the file + i (looped integer).
	          BufferedOutputStream output = new BufferedOutputStream(
	            new FileOutputStream(newFile));
	        ) {
	          int value;
	          int count = 0;
	          // What is wrong if these two conditions are placed in a different order?
	          //while loop that will look at the data of the file and split it inot the correct amount of files
	          while (count++ < splitFileSize && (value = input.read()) != -1) {
	            output.write(value);
	            
	          }
	        }
	      }
	    }
	}
	
	//main method that will run the program
  public static void main(String[] args) throws Exception {
	  launch(args);
  }
}