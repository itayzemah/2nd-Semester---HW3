
//itay zemah
//312277007
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Optional;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AfekaInstruments extends Application {
	public static final int WIN_H = 600;
	public static final int WIN_W = 1000;
	private AfekaInventory inventory = new AfekaInventory();
	private ArrayList<MusicalInstrument> searchList = new ArrayList<>();
	private BorderPane layout = new BorderPane();
	private RightPane rightPane = new RightPane();
	private LeftPane leftPane = new LeftPane();
	private CenterPane centerPane = new CenterPane(searchList);
	private TopPane topPane = new TopPane(inventory, centerPane);
	private BottomPane bottomPane = new BottomPane();
	private int currentIndexToShow = 0;

	public BorderPane getLayout() {
		return layout;
	}

	public AfekaInventory getInventory() {
		return inventory;
	}

	public BorderPane getMainPane(ArrayList<MusicalInstrument> list) {
		currentIndexToShow = 0;
		layout.setRight(rightPane);
		layout.setLeft(leftPane);
		layout.setTop(topPane);
		layout.setCenter(centerPane);
		layout.setBottom(bottomPane);
		centerPane.showTheInstrumentInTheCurrentIndex(list, getcurrentIndexToShow());
		setANDRequestFocus();
		layout.setOnKeyPressed(e -> {
			keyboardsEvents(e);
		});
		buttonsEvents();
		
		return layout;

	}

	public void buttonsEvents() {
		bottomPane.getAddBtn().setOnAction(e -> addInstrument());
		bottomPane.getDelBtn().setOnAction(e -> deleteInstrument());
		bottomPane.getClearBtn().setOnAction(e -> clearInstruments());
		searchMethod();
		leftPane.getBtnL().setOnAction(e -> {
			if (getcurrentIndexToShow() < getSearchList().size()) {
				setcurrentIndexToShow(getcurrentIndexToShow() - 1);

				if (getcurrentIndexToShow() == -1) {
					setcurrentIndexToShow(getSearchList().size() - 1);
				}
				centerPane.showTheInstrumentInTheCurrentIndex(getSearchList(), currentIndexToShow);
			}
		});
		rightPane.getBtnR().setOnAction(e -> {
			if (getcurrentIndexToShow() < inventory.getList().size() - 1) {
				setcurrentIndexToShow(getcurrentIndexToShow() + 1);

				centerPane.showTheInstrumentInTheCurrentIndex(getSearchList(), currentIndexToShow);
			} else {
				setcurrentIndexToShow(0);
				centerPane.showTheInstrumentInTheCurrentIndex(getSearchList(), currentIndexToShow);
			}
		});
	}

	public void keyboardsEvents(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			searchMethod();
			setSearchList(topPane.createListFromSearchStr(topPane.getSreachTFText()));
			topPane.search(topPane.getSreachTFText());
			centerPane.showTheInstrumentInTheCurrentIndex(getSearchList(), 0);
		} else if (e.getCode() == KeyCode.A) {
			setANDRequestFocus();
			addInstrument();
			setANDRequestFocus();
		}

		else if (e.getCode() == KeyCode.DELETE)
			deleteInstrument();
	}

	public void searchMethod() {
		setcurrentIndexToShow(0);
		topPane.getBtnGo().setOnAction(e -> setSearchList(topPane.createListFromSearchStr(topPane.getSreachTFText())));
		topPane.search(topPane.getSreachTFText());
	}

	public void setANDRequestFocus() {
		layout.setFocusTraversable(true);
		layout.requestFocus();
	}

	public int getcurrentIndexToShow() {
		return this.currentIndexToShow;
	}

	public void setcurrentIndexToShow(int i) {
		if (i < getSearchList().size()) {
			this.currentIndexToShow = i;
		} else if (i >= getSearchList().size()) {
			this.currentIndexToShow = 0;
		} else {
			this.currentIndexToShow = getSearchList().size() - 1;
		}
	}

	public File getFileNameDialog() {
		File file;
		Optional<String> result;
		TextInputDialog dialog = new TextInputDialog();
		do {
			dialog.setTitle("confirmation");
			dialog.setHeaderText("Load Instrument From File");
			dialog.setContentText("Please Enter file name / path:");
			Alert alert = new Alert(AlertType.ERROR);
			result = dialog.showAndWait();
			if (!result.isPresent())
				System.exit(1);

			file = new File(result.get());
			if (!file.exists()) {
				alert.setTitle("Error");
				alert.setHeaderText("File Error");
				alert.setContentText("Can not read from fle, please try again");

				alert.showAndWait();
			}
		} while (!file.exists());
		return file;

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		boolean ifFileOK = false;
		do {
			try {
				ifFileOK = true;
				loadInstrumentsFromFile(getFileNameDialog(), inventory.getList());
				setSearchList(inventory.getList());
			} catch (InputMismatchException | IllegalArgumentException ex) {
				System.err.println("\n" + ex.getMessage());
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Price Error");
				alert.setContentText(ex.getMessage());
				ifFileOK = false;
				alert.showAndWait();

			}
		} while (!ifFileOK);

		Scene primaryScene = new Scene(getMainPane(getSearchList()));
		primaryStage.setScene(primaryScene);
		primaryStage.setHeight(WIN_H);
		primaryStage.setWidth(WIN_W);
		primaryStage.setTitle("Pane");
	    primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static MusicalInstrument searchForInstrument(Scanner keyboard, AfekaInventory inventory) {
		System.out.println("Brand: ");
		String searchBrand = keyboard.nextLine();
		Number searchPrice = null;
		do {
			try {
				System.out.println("Price: ");
				searchPrice = new Double(keyboard.nextDouble());
			} catch (InputMismatchException ex) {
				System.err.print("Price must be a positive number!\n");

			}
			keyboard.nextLine(); // clear the buffer
		} while (searchPrice == null);
		if (!inventory.isSort()) {
			inventory.SortByBrandAndPrice(inventory.getList());
		}
		int searchResult = inventory.binnarySearchByBrandAndPrice(inventory.getList(), searchBrand, searchPrice);
		if (searchResult == -1) {
			return null;
		}
		return inventory.getList().get(searchResult);

	}

	public static File getInstrumentsFileFromUser(Scanner keyboard) {
		boolean stopLoop = true;
		File file;

		do {
			System.out.println("Please enter instruments file name / path:");
			String filepath = keyboard.nextLine();
			file = new File(filepath);
			stopLoop = file.exists() && file.canRead();

			if (!stopLoop)
				System.out.println("\nFile Error! Please try again\n\n");
		} while (!stopLoop);

		return file;
	}

	public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments) {
		Scanner scanner = null;

		try {

			scanner = new Scanner(file);

			addAllInstruments(allInstruments, loadGuitars(scanner));

			addAllInstruments(allInstruments, loadBassGuitars(scanner));

			addAllInstruments(allInstruments, loadFlutes(scanner));

			addAllInstruments(allInstruments, loadSaxophones(scanner));

		} catch (FileNotFoundException ex) {
			System.err.println("\nFile Error! File was not found");
			System.exit(2);
		} finally {
			scanner.close();
		}

	}

	public static ArrayList<Guitar> loadGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			guitars.add(new Guitar(scanner));

		return guitars;
	}

	public static ArrayList<Bass> loadBassGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			bassGuitars.add(new Bass(scanner));

		return bassGuitars;
	}

	public static ArrayList<Flute> loadFlutes(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			flutes.add(new Flute(scanner));

		return flutes;
	}

	public static ArrayList<Saxophone> loadSaxophones(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);

		for (int i = 0; i < numOfInstruments; i++)
			saxophones.add(new Saxophone(scanner));

		return saxophones;
	}

	public static void addAllInstruments(ArrayList<MusicalInstrument> instruments,
			ArrayList<? extends MusicalInstrument> moreInstruments) {
		for (int i = 0; i < moreInstruments.size(); i++) {
			instruments.add(moreInstruments.get(i));
		}
	}

	public static void printInstruments(ArrayList<MusicalInstrument> instruments) {
		for (int i = 0; i < instruments.size(); i++)
			System.out.println(instruments.get(i));
	}

	public static int getNumOfDifferentElements(ArrayList<MusicalInstrument> afekaInstrumentsArrToCount) {
		ArrayList<MusicalInstrument> temp = new ArrayList<MusicalInstrument>();
		addAllInstruments(temp, afekaInstrumentsArrToCount);
		for (int currentIndex = 0; currentIndex < temp.size(); currentIndex++) {
			for (int i = currentIndex + 1; i < temp.size(); i++) {
				if (temp.get(currentIndex).equals(temp.get(i))) {
					i--; // because the instrument has deleted, I need to check
							// the same index witch is the next Instrument.
					temp.remove(i);
				}

			}
		}

		return temp.size();
	}

	public static MusicalInstrument getMostExpensiveInstrument(ArrayList<MusicalInstrument> instruments) {
		double maxPrice = 0;
		MusicalInstrument mostExpensive = (MusicalInstrument) instruments.get(0);

		for (int i = 0; i < instruments.size(); i++) {
			MusicalInstrument temp = (MusicalInstrument) instruments.get(i);

			if (temp.getPrice().doubleValue() > maxPrice) {
				maxPrice = temp.getPrice().doubleValue();
				mostExpensive = temp;
			}
		}

		return mostExpensive;
	}

	public ArrayList<MusicalInstrument> getSearchList() {
		return searchList;
	}

	public void setSearchList(ArrayList<MusicalInstrument> searchList) {
		this.searchList = searchList;
	}

	private void addInstrument() {
		AddStage add = new AddStage();
		add.startAdd(inventory.getList());
	}

	private void clearInstruments() {
		inventory.removeAll(getSearchList());
		setcurrentIndexToShow(getcurrentIndexToShow() + 1);
		centerPane.showTheInstrumentInTheCurrentIndex(getSearchList(), getcurrentIndexToShow());
	}

	private void deleteInstrument() {
		if (getSearchList().size() != 0) {
			getSearchList().remove(currentIndexToShow);
			setcurrentIndexToShow(getcurrentIndexToShow() + 1);
			centerPane.showTheInstrumentInTheCurrentIndex(getSearchList(), getcurrentIndexToShow());
		}
	}

}
