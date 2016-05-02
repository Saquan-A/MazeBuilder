/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazebuilder;

import static com.sun.javafx.fxml.expression.Expression.add;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.PrintStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author saqua
 */
public class MazeBuilder extends Application {
    
    private final static int BTN_SIZE = 50;
    private int num;
    private boolean modify;
    int numCols;
    int numRows;
    MyHandler myHandler = new MyHandler();
    Button[][] arrayBtn; 
    
    
    
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane border = new BorderPane();
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        HBox hbox = new HBox();

      numRows = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Number of rows!"));
      numCols = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Number of columns!"));
      
      
      
        arrayBtn = new Button[numRows][numCols];
         for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Button btnlist = new Button();
                btnlist.setPrefSize(BTN_SIZE, BTN_SIZE);
                btnlist.setStyle("-fx-background-color: blue");
                btnlist.setOnAction(myHandler);
                arrayBtn[row][col] = btnlist;
                grid.add(btnlist, row, col);

            }
        }
         
         

        
        Button btn2 = new Button("Exit");
        btn2.setOnAction(myHandler);
       
        
        Button btn = new Button("Save");
        btn.setOnAction(myHandler);
       
        
       
        
        Pane root = new Pane();
        
        hbox.getChildren().add(btn);
        hbox.getChildren().add(btn2);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(5, 5, 5, 5));
        
        border.setBottom(hbox);
        border.setCenter(grid);
        
        
        
        Scene scene = new Scene(border, BTN_SIZE*numCols, BTN_SIZE*numRows);
        
        primaryStage.setTitle("Maze Builder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
public String Save(){
    String save = "";
    
    for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                
                if(arrayBtn[row][col].getStyle().indexOf("blue") > -1){
                    save += "0";
                    if(row == numRows){
                        save += "\n";
                    }
                }
                
                
                    if(arrayBtn[row][col].getStyle().indexOf("white") > -1){
                    save += "1";
                    if(row == numRows){
                        save += "\n";
                    }
                }
                
                
                
                    if(arrayBtn[row][col].getStyle().indexOf("green") > -1){
                    save += "S";
                    if(row == numRows){
                        save += "\n";
                    }
                }
                   if(arrayBtn[row][col].getStyle().indexOf("red") > -1){
                    save += "E";
                    if(row == numRows){
                        save += "\n";
                    }
                }

            }
        }
    
    return save;
}
     private char getColorChar(Button b )
    {
         if (b.getStyle().indexOf("blue") > -1) {
                    return '0';
                }
                else if (b.getStyle().indexOf("white") > -1) {
                    return '1';
                }
                else if (b.getStyle().indexOf("green") > -1) {
                    return 'S';
                }
                else {
                    return 'E';
                }
    }
    
    //Inner class lister for buttons
    public class MyHandler implements EventHandler<ActionEvent>
    {
        @Override
        public void handle( ActionEvent e)
        {
	    //e.getSource() returns the button that was clicked.
            Button b = (Button) e.getSource();

            if (b.getText().equals("Save")) {
                //create output file
                 JFrame parentFrame = new JFrame();
    
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            System.out.println("Save as file: " + fileToSave.getAbsolutePath() );
                try {
                     PrintStream output = new PrintStream(new File(fileChooser.getSelectedFile()+".txt"));
                    System.setOut(output);
                     output.print(Save());
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }

            }
            else if (b.getText().equals("Exit")) {
                //exit the application
               System.exit(0);

            }
            else { //a maze button was clicked

                char val = getColorChar(b);
                if (val == '0') {  //the button is currently blue so change it to white
                    b.setStyle("-fx-background-color: white;");
                }
                else if (val == '1') {
                    b.setStyle("-fx-background-color: green;");
                }
                else if (val == 'S') {
                     b.setStyle("-fx-background-color: red;");
                }
                else {
                     b.setStyle("-fx-background-color: blue;");
                }
                
            
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
   
    
}
