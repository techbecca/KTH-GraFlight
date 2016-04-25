
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application{
	
	Stage window;
	Button button;
	Scene scene;
	
	public static void main(String[] args) {
		
		//method inside Application class, sets up program as javafx application and then calls the start function
		launch(args);
	}
	
	@Override
	//Stage is the window
	public void start(Stage primaryStage) throws Exception {
		 
        primaryStage.setScene(scene);
        
        //Scrollbar code
        ScrollBar sb = new ScrollBar();
        sb.setMax(500);
        sb.setMin(0);
        sb.setValue(100);
        sb.setUnitIncrement(30);
        sb.setBlockIncrement(35);
      
        sb.setOrientation(Orientation.HORIZONTAL);
        
		window = primaryStage;
		window.setTitle("ToolBar");
		
		//close window with the "X" button in the right corner
		window.setOnCloseRequest(e-> {
			//consumes the event,takes care of event
			e.consume();
			closeProgram();
		});
		
		//make checkboxes
		CheckBox box1 = new CheckBox("Pattern 1");
		CheckBox box2 = new CheckBox("Pattern 2");
		CheckBox box3 = new CheckBox("Pattern 3");
		CheckBox box4 = new CheckBox("Pattern 4");
		CheckBox box5 = new CheckBox("Pattern 5");
		
		button = new Button("Apply");
		button.setLayoutX(100);
		button.setLayoutY(150);
		
		//Layout of window, boxes appear vertically (VBox)
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20,20,20,20));
		layout.getChildren().addAll(box1, box2, box3, box4, box5, button, sb);
		
		//Scene handles the content in the stage (window)
		Scene scene = new Scene(layout, 300, 300);
		//specify the scene to be used for stage, and show for the user
		window.setScene(scene);
		window.show();	
	}
	
	//Closes the toolbar window
	private void closeProgram(){
		Boolean answer = ConfirmBox.display("Confirm", "Are you sure you want to close the toolbar?");
		if (answer){
			window.close();
		}
	}
	
	
	
	
}
	
