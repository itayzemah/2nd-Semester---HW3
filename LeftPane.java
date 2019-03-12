import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

class LeftPane extends VBox {

	private Button btnL = new Button("", new ImageView(new Image("image/left.gif")));
	public Button getBtnL() {
		return btnL;
	}

	final int INSET = 10;

	public LeftPane() {
		setSpacing(40);
		getChildren().add(btnL);
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(INSET));

	}

}
