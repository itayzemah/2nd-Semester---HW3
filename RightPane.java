import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

class RightPane extends VBox {

	private Button btnR = new Button("", new ImageView(new Image("image/right.gif")));
	public Button getBtnR() {
		return btnR;
	}

	final int INSET = 10;

	public RightPane() {
		setSpacing(40);
		getChildren().add(btnR);
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(INSET));
		
	}

}
