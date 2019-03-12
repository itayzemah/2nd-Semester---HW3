import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

public class CenterPane extends GridPane {
	private Label typeLable = new Label("Type:");
	private Label brandLable = new Label("Brand:");
	private Label priceLable = new Label("Price:");
	private TextField typeTf = new TextField();
	
	private TextField brandTf = new TextField();

	private TextField priceTf = new TextField();
	
	public final int gpGap = 15;


	public CenterPane(ArrayList<MusicalInstrument> list) {
		typeTf.setEditable(false);
		brandTf.setEditable(false);
		priceTf.setEditable(false);
		priceTf.setPromptText("No Item");
		brandTf.setPromptText("No Item");
		typeTf.setPromptText("No Item");
		addRow(0, typeLable, typeTf);
		addRow(1, brandLable, brandTf);
		addRow(2, priceLable, priceTf);
		setAlignment(Pos.CENTER);
		setHgap((gpGap));
		setVgap(gpGap);
		setANDRequestFocus();
	}

	public void showTheInstrumentInTheCurrentIndex(ArrayList<MusicalInstrument> list, int currentIndexToShow) {
		if (list.size() != 0) {

			brandTf.setText(list.get(currentIndexToShow).getBrand());
			String price = String.valueOf(list.get(currentIndexToShow).getPrice());
			priceTf.setText(price);
			String type = list.get(currentIndexToShow).getClass().getCanonicalName();
			typeTf.setText(type);
			setANDRequestFocus();
		}
		else{
			priceTf.setText("");
			brandTf.setText("");
			typeTf.setText("");
			
			setANDRequestFocus();

		}
	}
	public TextField getTypeTf() {
		return typeTf;
	}
	public TextField getBrandTf() {
		return brandTf;
	}
	public TextField getPriceTf() {
		return priceTf;
	}
	public void setANDRequestFocus() {
		this.setFocusTraversable(true);
		this.requestFocus();
	}
}
