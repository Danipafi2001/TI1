package ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Cinema;

public class CinemaGUI {

	//----------------------------------------
	//Constants
	//----------------------------------------
	public final static String CHAIR = " -fx-border-color: black; -fx-background-radius: 50%; -fx-border-radius: 50%";
	public final static String CHAIRBLUE = "-fx-background-color: blue;" + CHAIR;
	public final static String CHAIRWHITE = "-fx-background-color: white;" + CHAIR;
	public final static String CHAIRLIME = "-fx-background-color: lime;" + CHAIR;
	public final static String FILE = "data/IDs.txt";

	//----------------------------------------
	//FXML imports
	//----------------------------------------
	@FXML
	public VBox mainPane;
	@FXML
	private TextField txtID, movieName, audienceName, audienceID;
	@FXML
	private Spinner<String> spinnerHours, spinnerMins, spinnerHoursDuration, spinnerMinsDuration;
	@FXML
	private DatePicker datePicker;
	@FXML
	private GridPane gridpane;
	@FXML
	private ComboBox<String> boxCinemas, audienceFuntion;

	//----------------------------------------
	//Attributes
	//----------------------------------------
	private Cinema cinema;
	private String[] users;
	private Button[][] chairs;
	private boolean[][] audience;

	//----------------------------------------
	//Methods
	//----------------------------------------

	//Constructor
	public CinemaGUI() {
		cinema = new Cinema();
	}

	//Loader id's method
	public void loaderIDS() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(FILE));
		String ids = "";
		boolean stop = false;
		while(!stop) {
			String line = br.readLine();
			if(line==null)
				stop = true;
			else
				ids += line + ",";
		}
		users = ids.split(",");
		br.close();
	}

	//Only number method
	@FXML
	public void onlyNumbers(KeyEvent event) {
		if (!Character.isDigit(event.getCharacter().charAt(0)))
			event.consume();
	}

	//Sign in method
	@FXML
	public void signIn(ActionEvent event) throws IOException {
		boolean correct = false;
		for(int i=0; i<users.length; i++) {
			if(txtID==null || users[i].equals(txtID.getText())) {
				loaderPane("FXML/lobby.fxml");
				correct = true;
			}
		}
		if(!correct)
			launchAlert("ID is not found on system", "Error");
	}

	//Loader pane method
	public void loaderPane(String fmxl) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fmxl));
		fxmlLoader.setController(this);
		Parent root = fxmlLoader.load();
		mainPane.getChildren().clear();
		mainPane.getChildren().add(root);
	}

	//Launch alert method
	private void launchAlert(String msg, String type) {
		//Create an error alert
		Alert alert = new Alert(Alert.AlertType.ERROR);
		//Set icon
		Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(getClass().getResourceAsStream("PNG/icon.png")));
		//Set title
		alert.setTitle("BU Cinema");
		//Set header text
		alert.setHeaderText(type);
		//Set error graphic
		alert.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("PNG/"+type.toLowerCase()+".png"))));
		//Set content text
		alert.setContentText(msg);
		//Header style
		alert.getDialogPane().getChildren().get(0).setStyle("-fx-background-color: -fx-box-border, linear-gradient(white, derive(white, 30%)); -fx-font-size: 36px");
		//Content style
		alert.getDialogPane().setStyle("-fx-background-color: white; -fx-font-size: 24px");
		//Button style
		Button btn = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
		btn.setStyle("-fx-background-color: blue; -fx-font-size: 24px; -fx-text-fill: white; -fx-font-weight: bold");
		btn.setText("Ok");
		//Alert launch
		alert.showAndWait();
	}

	//Log out method
	@FXML
	public void logOut(ActionEvent event) throws IOException {
		loaderPane("FXML/signin.fxml");
	}

	//Register movie method
	@FXML
	public void registerMovie(ActionEvent event) throws IOException {
		loaderPane("FXML/register-movie.fxml");
		datePicker.setValue(LocalDate.now());
		spinnerValues(5, "01", spinnerHoursDuration);
		spinnerValues(59, "29", spinnerMinsDuration);
		spinnerValues(23, String.format("%02d", LocalDateTime.now().getHour()), spinnerHours);
		spinnerValues(59, String.format("%02d", LocalDateTime.now().getMinute()), spinnerMins);
		boxCinemas.getItems().addAll("Small Room", "Middle Room");
		boxCinemas.setValue("Small Room");
	}

	//Set spinner values method 
	private void spinnerValues(int max, String value, Spinner<String> spinner) {
		ObservableList<String> listValues = FXCollections.observableArrayList();
		for(int i=0; i<=max; i++)
			listValues.add(String.format("%02d", i));
		SpinnerValueFactory<String> spinnerValues = new SpinnerValueFactory.ListSpinnerValueFactory<>(listValues);
		spinnerValues.setValue(value);
		spinnerValues.setWrapAround(true);
		spinner.setValueFactory(spinnerValues);
	}

	//Save movie function
	@FXML
	public void saveFunction(ActionEvent event) {
		String name = movieName.getText();
		if(name.trim().equals("")) {
			movieName.setText("");
			launchAlert("Movie name cannot be empty", "Error");
		} else {
			String room = boxCinemas.getValue();
			String startTime = spinnerHours.getValue()+":"+spinnerMins.getValue()+":00";
			LocalDate date = datePicker.getValue();
			LocalTime start = LocalTime.parse(startTime);
			LocalDateTime s = LocalDateTime.of(date, start);
			if(s.isBefore(LocalDateTime.now())) {
				launchAlert("Select a later date time than actually date time", "Error");
			} else {
				int hours = Integer.parseInt(spinnerHoursDuration.getValue());
				int mins = Integer.parseInt(spinnerMinsDuration.getValue());
				LocalDateTime e = s.plusHours(hours).plusMinutes(mins);
				if(cinema.saveFuntion(name, room, s, e))
					launchAlert("The funtion has been created successfully", "Check");
				else
					launchAlert("Date time already selected", "Error");
			}
		}
	}

	//Load register audience frame
	@FXML
	public void registerAudience(ActionEvent event) throws IOException {
		if(cinema.numberFuntions()>0)
			loaderPane("FXML/add-audience.fxml");
		else
			launchAlert("There is not any cinema funtion created", "Error");
	}

	//Load select chairs frame
	@FXML
	public void selectChairs(ActionEvent event) throws IOException {
		String name = audienceName.getText();
		String id = audienceID.getText();
		if(id.trim().equals("")) {
			launchAlert("ID can't be empty", "Error");
		} else if(name.trim().equals("")){
			launchAlert("Name can't be empty", "Error");
		} else {
			loaderPane("FXML/select-chairs.fxml");
			String[] names = cinema.funtionNames();
			audienceFuntion.getItems().addAll(names);
		}
	}

	//View matrix method
	@FXML
	public void viewMatrix(ActionEvent event) {
		audience = cinema.occupiedChairs(audienceFuntion.getSelectionModel().getSelectedIndex());
		gridpane = new GridPane();
		gridpane.setAlignment(Pos.CENTER);
		buildMatrix();
	}

	//Build matrix method
	private void buildMatrix() {
		int rows = cinema.getRows(audienceFuntion.getSelectionModel().getSelectedIndex());
		String[] pantalla = {"P","A","N","T","A","L","L","A"};
		chairs = new Button[rows][7];
		for(int i=0; i<rows+2; i++) {
			for(int j=0; j<7+1; j++) {
				if(i==1 && j==0) {

				}else if(i == 0 ) {
					Label temp = new Label(pantalla[j]);
					temp.setPrefSize(75, 37.5);
					temp.setStyle("-fx-alignment: baseline-center; -fx-font-size: 24px; -fx-background-color: blue; -fx-text-fill: white; -fx-font-weight: bold");
					gridpane.add(temp, j, i);
				}
				else if(i == 1 ) {
					Label temp = new Label((char)('1'+j-1)+"");
					temp.setPrefSize(75, 75);
					temp.setStyle("-fx-alignment: baseline-center; -fx-font-size: 24px");
					gridpane.add(temp, j, i);
				}else if(j==0) {
					Label temp = new Label((char)('A'+i-2)+"");
					temp.setPrefSize(75, 75);
					temp.setStyle("-fx-alignment: baseline-center; -fx-font-size: 24px");
					gridpane.add(temp, j, i);
				} else if(audience[i-2][j-1]) {
					Button temp = new Button("");
					temp.setPrefSize(75, 75);
					temp.setStyle(CHAIRBLUE);
					chairs[i-2][j-1] = temp;
					gridpane.add(temp, j, i);
				} else {
					Button temp = new Button("");
					temp.setPrefSize(75, 75);
					temp.setStyle(CHAIRWHITE);
					temp.setOnAction(new EventHandler<ActionEvent>() {
						@Override public void handle(ActionEvent e) {
							if(temp.getStyle().equals(CHAIRWHITE)) {
								temp.setStyle(CHAIRLIME);
							} else if(temp.getStyle().equals(CHAIRLIME)) {
								temp.setStyle(CHAIRWHITE);
							}
						}
					});
					chairs[i-2][j-1] = temp;
					gridpane.add(temp, j, i);
				}
			}
		}
		VBox temp = (VBox)mainPane.getChildren().get(0);
		temp.getChildren().set(2, gridpane);
	}

	//Save changes method
	@FXML
	public void saveChanges(ActionEvent event) {
		if(audienceFuntion.getValue()!=null) {
			int rows = cinema.getRows(audienceFuntion.getSelectionModel().getSelectedIndex());
			for(int i=0; i<rows; i++) {
				for(int j=0; j<7; j++) {
					if(!audience[i][j]) {
						if(chairs[i][j].getStyle().equals(CHAIRLIME)) {
							audience[i][j] = true;
						}
					}
				}
			}
			cinema.saveOccupancy(audience, audienceFuntion.getSelectionModel().getSelectedIndex());
			buildMatrix();
		} else 
			launchAlert("Select a function", "Error");
	}
}