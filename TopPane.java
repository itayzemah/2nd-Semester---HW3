import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TopPane extends HBox {
	private TextField sreachTF = new TextField();

	private Button btnGo = new Button("", new ImageView(new Image("image/GO.gif")));
	private AfekaInventory inventory;
	public final int BTN_MAX_SIZE = 80;
	public final int TOP_INSET = 7;
	private Pane centerPane;

	public TopPane(AfekaInventory inventory, Pane centerPane) {
		this.centerPane = centerPane;
		this.inventory = inventory;
		btnGo.setMaxWidth(BTN_MAX_SIZE);
		sreachTF.setPromptText("search:...");
		sreachTF.setPrefWidth(AfekaInstruments.WIN_W - btnGo.getMaxWidth() - TOP_INSET);
		setAlignment(Pos.CENTER);
		getChildren().addAll(sreachTF, btnGo);
		setSpacing(TOP_INSET);
		setPadding(new Insets(TOP_INSET));
		System.out.println(inventory.toString());
		setANDRequestFocus();
		btnGo.setOnAction(e -> {
			search(sreachTF.getText());
			setANDRequestFocus();
		});
		centerPane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				search(sreachTF.getText());
				setANDRequestFocus();
			}
		});
	}

	public void search(String searchStr) {
		if (searchStr.isEmpty()) {  //there was not instruction about empty search.
			((CenterPane) centerPane).showTheInstrumentInTheCurrentIndex(inventory.getList(), 0);
		}
		((CenterPane) centerPane).showTheInstrumentInTheCurrentIndex(createListFromSearchStr(searchStr), 0);
	}

	public ArrayList<MusicalInstrument> createListFromSearchStr(String searchStr) {
		ArrayList<MusicalInstrument> searchList = new ArrayList<>();
		for (int i = 0; i < inventory.getList().size(); i++) {
			if (inventory.getList().get(i).toString().toLowerCase().contains(searchStr.toLowerCase())) {
				searchList.add(inventory.getList().get(i));
			}
		}
		return searchList;
	}

	public void setANDRequestFocus() {
		this.setFocusTraversable(true);
		this.requestFocus();
	}

	public String getSreachTFText() {
		return sreachTF.getText();
	}
	public Button getBtnGo() {
		return btnGo;
	}
}
