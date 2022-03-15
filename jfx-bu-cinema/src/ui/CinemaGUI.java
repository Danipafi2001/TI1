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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Cinema;

public class CinemaGUI {

	@FXML
	public VBox mainPane;
	@FXML
	private Label alert;
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

	public final static String FILE = "data/IDs.txt";

	private String[] users;
	
	private boolean[][] audience;

	private Integer indexi, indexj;

	private Cinema cinema;

	public CinemaGUI() {
		cinema = new Cinema();
	}

	@FXML
	public void idNumber(KeyEvent event) {
		if (!Character.isDigit(event.getCharacter().charAt(0))) {
			event.consume();
		}
	}

	@FXML
	public void signIn(ActionEvent event) throws IOException {
		for(int i=0; i<users.length; i++) {
			if(txtID==null || users[i].equals(txtID.getText()))
				loaderPane("FXML/lobby.fxml");
		}
	}

	@FXML
	public void logOut(ActionEvent event) throws IOException {
		loaderPane("FXML/signin.fxml");
	}

	@FXML
	public void registerMovie(ActionEvent event) throws IOException {
		loaderPane("FXML/register-movie.fxml");

		datePicker.setValue(LocalDate.now());
		
		ObservableList<String> hoursDuration = FXCollections.observableArrayList();
		for(int i=0; i<=5; i++)
			hoursDuration.add(String.format("%02d", i));
		SpinnerValueFactory<String> valuesHoursDuration = new SpinnerValueFactory.ListSpinnerValueFactory<>(hoursDuration);
		valuesHoursDuration.setValue("01");
		valuesHoursDuration.setWrapAround(true);
		spinnerHoursDuration.setValueFactory(valuesHoursDuration);

		ObservableList<String> minsDuration = FXCollections.observableArrayList();
		for(int i=0; i<=59; i++)
			minsDuration.add(String.format("%02d", i));
		SpinnerValueFactory<String> valuesMinsDuration = new SpinnerValueFactory.ListSpinnerValueFactory<>(minsDuration);
		valuesMinsDuration.setValue("30");
		valuesMinsDuration.setWrapAround(true);
		spinnerMinsDuration.setValueFactory(valuesMinsDuration);

		ObservableList<String> hours = FXCollections.observableArrayList();
		for(int i=0; i<=23; i++)
			hours.add(String.format("%02d", i));
		SpinnerValueFactory<String> valuesHours = new SpinnerValueFactory.ListSpinnerValueFactory<>(hours);
		valuesHours.setValue(String.format("%02d", LocalDateTime.now().getHour()));
		valuesHours.setWrapAround(true);
		spinnerHours.setValueFactory(valuesHours);

		ObservableList<String> mins = FXCollections.observableArrayList();
		for(int i=0; i<=59; i++)
			mins.add(String.format("%02d", i));
		SpinnerValueFactory<String> valuesMins = new SpinnerValueFactory.ListSpinnerValueFactory<>(mins);
		valuesMins.setValue(String.format("%02d", LocalDateTime.now().getMinute()));
		valuesMins.setWrapAround(true);
		spinnerMins.setValueFactory(valuesMins);
		
		boxCinemas.getItems().addAll("Small Room", "Middle Room");
		boxCinemas.setValue("Small Room");
	}

	@FXML
	public void addMovie(ActionEvent event) {
		String name = movieName.getText();
		if(name.trim().equals("")) {
			movieName.setText("");
			alert.setText("Movie name cannot be empty");
			alert.setVisible(true);
		} else {
			String room = boxCinemas.getValue();
			String startTime = spinnerHours.getValue()+":"+spinnerMins.getValue()+":00";
			LocalDate date = datePicker.getValue();
			LocalTime start = LocalTime.parse(startTime);
			LocalDateTime s = LocalDateTime.of(date, start);
			if(s.isBefore(LocalDateTime.now())) {
				
			} else {
				LocalDateTime e = s.plusHours(Integer.parseInt(spinnerHoursDuration.getValue())).plusMinutes(Integer.parseInt(spinnerMinsDuration.getValue()));
				System.out.println(s);
				System.out.println(e);
				cinema.addFuntion(name, room, s, e);
			}
		}
	}
	
	@FXML
    public void addAudience(ActionEvent event) throws IOException {
		if(cinema.funtionsSize()>0) {
			loaderPane("FXML/add-audience.fxml");
			
			
		}
    }
	
	@FXML
	public void funtionChairs(ActionEvent event) {
		
	}
	
	@FXML
    public void selectChairs(ActionEvent event) throws IOException {
		String name = audienceName.getText();
		String id = audienceID.getText();
		if(name.trim().equals("")) {
			
		} else if(id.trim().equals("")){
			
		} else {
			loaderPane("FXML/select-chairs.fxml");
			String[] names = cinema.funtionNames();
			audienceFuntion.getItems().addAll(names);
			audienceFuntion.setValue(names[names.length-1]);
			audience = cinema.chairBussy(audienceFuntion.getSelectionModel().getSelectedIndex());
			gridpane = new GridPane();
			gridpane.setAlignment(Pos.CENTER);
			for(int i=0; i<4; i++) {
				for(int j=0; j<7; j++) {
					indexi = i;
					indexj = j;
					if(audience[i][j]) {
						Button temp = new Button("");
						temp.setPrefSize(100, 100);
						temp.setStyle("-fx-background-color: blue; -fx-border-color: black; -fx-background-radius: 50px; -fx-border-radius: 50px");
						gridpane.add(temp, j, i);
					} else {
						Button temp = new Button("");
						temp.setPrefSize(100, 100);
						temp.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-background-radius: 50px; -fx-border-radius: 50px");
						temp.setOnAction(new EventHandler<ActionEvent>() {
						    @Override public void handle(ActionEvent e) {
						    	if(temp.getStyle().equals("-fx-background-color: white; -fx-border-color: black; -fx-background-radius: 50px; -fx-border-radius: 50px")) {
						    		temp.setStyle("-fx-background-color: lime; -fx-border-color: black; -fx-background-radius: 50px; -fx-border-radius: 50px");
									audience[indexi][indexj] = true;
						    	} else if(temp.getStyle().equals("-fx-background-color: lime; -fx-border-color: black; -fx-background-radius: 50px; -fx-border-radius: 50px")) {
						    		temp.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-background-radius: 50px; -fx-border-radius: 50px");
									audience[indexi][indexj] = false;
						    	}
						    	
						    }
						});
						gridpane.add(temp, j, i);
					}
				}
			}
			VBox temp = (VBox)mainPane.getChildren().get(0);
			temp.getChildren().set(2, gridpane);
		}
    }
	
	@FXML
    public void saveChanges(ActionEvent event) {
		cinema.save(audience, audienceFuntion.getSelectionModel().getSelectedIndex());
    }

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

	public void loaderPane(String fmxl) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fmxl));
		fxmlLoader.setController(this);
		Parent root = fxmlLoader.load();
		mainPane.getChildren().clear();
		mainPane.getChildren().add(root);
	}
}