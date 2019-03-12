import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

class BottomPane extends BorderPane {
	private Button addBtn = new Button("Add");
	private Button delBtn = new Button("del");
	private Button clearBtn = new Button("clear");
	public final int INSETS_SPACING = 20;
	HBox upperHbox = new HBox();
	HBox lowerHbox = new HBox();
	Text bottomlabel = new Text();
	Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1),
			e -> bottomlabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					+ " Afeka Instruments Music Store $$$ ON SALE!!! $$$ Guitars, Basses, Flutes, "
					+ "Saxophones and more!")));

	public BottomPane() {

		bottomlabel.setFont(Font.font("Courier", FontWeight.BOLD, 15));
		bottomlabel.setFill((Color.RED));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		datePathTransition();

		upperHbox.getChildren().addAll(addBtn, delBtn, clearBtn);
		upperHbox.setAlignment(Pos.TOP_CENTER);
		upperHbox.setSpacing(INSETS_SPACING);
		upperHbox.setPadding(new Insets(INSETS_SPACING));
		lowerHbox.getChildren().add(bottomlabel);
		lowerHbox.setAlignment(Pos.BOTTOM_CENTER);
		setCenter(upperHbox);
		setBottom(lowerHbox);
		setPadding(new Insets(INSETS_SPACING));
		setANDRequestFocus();

	}

	private void datePathTransition() {
		PathTransition transition = new PathTransition(Duration.seconds(10), new Line(0, 0, AfekaInstruments.WIN_W, 0));

		transition.setNode(bottomlabel);
		transition.setCycleCount(Animation.INDEFINITE);
		transition.setAutoReverse(true);
		transition.play();
		bottomlabel.setOnMouseEntered(e -> {
			transition.pause();

		});
		bottomlabel.setOnMouseExited(e -> transition.play());

	}

	public Button getAddBtn() {
		return addBtn;
	}

	public Button getDelBtn() {
		return delBtn;
	}

	public Button getClearBtn() {
		return clearBtn;
	}

	public void setANDRequestFocus() {
		upperHbox.setFocusTraversable(true);
		upperHbox.requestFocus();
	}
}