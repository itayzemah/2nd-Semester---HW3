import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AddStage {
	private GridPane addPane = new GridPane();
	private Scene addscene = new Scene(addPane, 400, 350);
	public final String[] instrumentsArray = new String[] { "Guitar", "Bass", "Flute", "Saxophone" };
	private ObservableList<String> instrumentsObservableList = FXCollections
			.observableArrayList(Arrays.asList(instrumentsArray));
	private ComboBox<String> instrumentsCBox = new ComboBox<String>(instrumentsObservableList);
	final int INSET = 12;
	private TextField brandTF = new TextField();
	private TextField priceTF = new TextField();
	private TextField numOfStringsTF = new TextField();
	private Button addBtn = new Button("add");
	private HBox instrumentsCBoxHBox = new HBox();
	private CheckBox isFretless = new CheckBox();
	private Stage addStage = new Stage();
	private String erorStr = "All fields must be full";

	public void startAdd(ArrayList<MusicalInstrument> list) {
		addPane.setAlignment(Pos.CENTER);
		instrumentsCBoxHBox.getChildren().add(instrumentsCBox);
		instrumentsCBoxHBox.setAlignment(Pos.TOP_CENTER);
		addPane.getChildren().add(instrumentsCBoxHBox);
		instrumentsCBox.setPromptText("Choose instrument Type");
		instrumentsCBox.setOnAction(e -> {
			clearAddPaneANDAddDefults();
			int indexOfInstrumentsCBox = instrumentsCBox.getSelectionModel().getSelectedIndex();
			ChangeAccordingInstrument(indexOfInstrumentsCBox, list);
		});
		addStage.setAlwaysOnTop(true);
		addStage.setScene(addscene);
		addStage.show();

	}

	private void clearAddPaneANDAddDefults() {
		instrumentsCBoxHBox.setAlignment(Pos.TOP_CENTER);
		addPane.setPadding(new Insets(INSET));
		addPane.setVgap(20);
		addPane.getChildren().clear();
		addPane.setAlignment(Pos.CENTER);
		addPane.addRow(1, new Label(" Brand: "), brandTF);
		addPane.addRow(2, new Label(" Price: "), priceTF);
		addPane.addRow(0, instrumentsCBoxHBox);
		addPane.add(addBtn, 1, 6);

	}

	private void ChangeAccordingInstrument(int indexOfInstrumentsCBox, ArrayList<MusicalInstrument> list) {

		switch (indexOfInstrumentsCBox) {
		case (0):
			getGuitarAddingPane(list);
			setANDRequestFocus();
			break;
		case (1):
			getBassAddingPane(list);
			setANDRequestFocus();

			break;
		case (2):
			getFluteAddingPane(list);
			setANDRequestFocus();

			break;
		case (3):
			getSaxophoneAddingPane(list);
			setANDRequestFocus();
			break;
			
		}
	}

	public void addInstrument(ArrayList<? super MusicalInstrument> target, MusicalInstrument insrumentToAdd)
			throws InputMismatchException, NumberFormatException {
		target.add(0, insrumentToAdd);
	}

	private void generalExceptionsChecks(){
		double d=0; // just for check
		try{
			d = Double.parseDouble(priceTF.getText());
			
			
		}catch (NumberFormatException ex){
			
			throw new InputMismatchException("Price must be a positive number! you can not put String");
		}
		if (brandTF.getText().isEmpty() || priceTF.getText().isEmpty()) {
			throw new InputMismatchException("All fields must be full");
		}
	}
	private void getSaxophoneAddingPane(ArrayList<MusicalInstrument> list) {
		brandTF.setPromptText("Ex: Levit");
		priceTF.setPromptText("Ex: 100");
		addBtn.setOnAction(e -> {
			try {
				generalExceptionsChecks();
				addInstrument(list, new Saxophone(brandTF.getText(), Double.valueOf(priceTF.getText())));
				addStage.close();
			} catch (InputMismatchException | IllegalArgumentException ex) {
				regularException(ex);
			}
		});
	}

	private void getFluteAddingPane(ArrayList<MusicalInstrument> list) {
		brandTF.setPromptText("Ex: Levit");
		priceTF.setPromptText("Ex: 300");
		ObservableList<String> fluteMateialOL = FXCollections
				.observableArrayList(Arrays.asList(WindInstrument.WIND_INSTRUMENT_MATERIAL));
		ComboBox<String> fluteMaterialCBox = new ComboBox<String>(fluteMateialOL);
		fluteMaterialCBox.setPromptText("Material");
		ObservableList<String> fluteTypesOL = FXCollections.observableArrayList(Arrays.asList(Flute.FLUET_TYPE));
		ComboBox<String> fluteTypesCBox = new ComboBox<String>(fluteTypesOL);
		fluteTypesCBox.setPromptText("Type");
		addPane.addRow(3, new Label(" Material: "), fluteMaterialCBox);
		addPane.addRow(4, new Label(" Flute Type: "), fluteTypesCBox);
		addBtn.setOnAction(e -> {

			try {
				generalExceptionsChecks();
				addInstrument(list,
						new Flute(brandTF.getText(), Double.valueOf(priceTF.getText()),
								WindInstrument.WIND_INSTRUMENT_MATERIAL[fluteMaterialCBox.getSelectionModel()
										.getSelectedIndex()],
								Flute.FLUET_TYPE[fluteTypesCBox.getSelectionModel().getSelectedIndex()]));
//				if (brandTF.getText().isEmpty() | priceTF.getText().isEmpty()) {
//					throw new InputMismatchException("All fields must be full");
//				}
				addStage.close();
			} catch (InputMismatchException | IllegalArgumentException ex) {
				regularException(ex);

			} catch (ArrayIndexOutOfBoundsException ex) {
				comboBoxError();

			}

		});

	}

	private void getBassAddingPane(ArrayList<MusicalInstrument> list) {
		brandTF.setPromptText("Ex: Fender Jazz");
		priceTF.setPromptText("Ex: 3000");
		numOfStringsTF.setPromptText("EX: 4");
		addPane.addRow(3, new Label(" Num of Strings: "), numOfStringsTF);
		addPane.addRow(4, new Label(" Fretless "), isFretless);
		addBtn.setOnAction(e -> {
			try {
				generalExceptionsChecks();
				addInstrument(list, new Bass(brandTF.getText(), Double.valueOf(priceTF.getText()),
						Integer.valueOf(numOfStringsTF.getText()), isFretless.isSelected() ? true : false));
				if ( numOfStringsTF.getText().isEmpty()) {
					throw new InputMismatchException("All fields must be full");
				}
				addStage.close();
			} catch (InputMismatchException | IllegalArgumentException ex) {
				regularException(ex);
			}

		});
	}

	private void getGuitarAddingPane(ArrayList<MusicalInstrument> list) {
		brandTF.setPromptText("Ex: Gibson");
		priceTF.setPromptText("Ex: 5700");
		numOfStringsTF.setPromptText("EX: 4");
		ObservableList<String> guitarTypesOS = FXCollections.observableArrayList(Arrays.asList(Guitar.GUITAR_TYPE));
		ComboBox<String> guitarTypesCBox = new ComboBox<String>(guitarTypesOS);
		guitarTypesCBox.setPromptText("Type");
		addPane.addRow(3, new Label(" Num of Strings: "), numOfStringsTF);
		addPane.addRow(4, new Label(" Guitar Type: "), guitarTypesCBox);
		addBtn.setOnAction(e -> {
			try {
				generalExceptionsChecks();
				addInstrument(list,
						new Guitar(brandTF.getText(), Double.valueOf(priceTF.getText()),
								Integer.valueOf(numOfStringsTF.getText()),
								Guitar.GUITAR_TYPE[guitarTypesCBox.getSelectionModel().getSelectedIndex()]));
				if ( numOfStringsTF.getText().isEmpty()) {
					throw new InputMismatchException("All fields must be full");
				}
				addStage.close();
			} catch (InputMismatchException | IllegalArgumentException ex) {
				regularException(ex);
			} catch (ArrayIndexOutOfBoundsException ex) {
				comboBoxError();

			}

		});
	}

	public void regularException(RuntimeException ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("field Error");
		alert.setContentText(ex.getMessage());
		alert.initOwner(addStage);
		alert.showAndWait();
	}

	public void comboBoxError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Choose Error");
		alert.setContentText(erorStr);
		alert.initOwner(addStage);
		alert.showAndWait();
	}

	public void setANDRequestFocus() {
		addPane.setFocusTraversable(true);
		addPane.requestFocus();
	}
}
