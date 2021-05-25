package application;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class Card{
	
	private Rank rank;
	private Suit suit;
	private Label label;
	private boolean isFlipped,isFree;
	private String img;
	
	public Card(Suit suit,Rank rank) {
		super();
		this.suit=suit;
		this.rank=rank;
		this.img=rank.img+suit.img;
		labelSetup();
	}

	public Deck getDeck() {
		if (label.getParent() instanceof Deck)
			return (Deck) label.getParent();
		return null;
	}
	
	
	private void labelSetup() {
		label = new Label();
		label.setAlignment(Pos.CENTER);
//		label.setPadding(new Insets(10));
		label.setMinWidth(Region.USE_PREF_SIZE);
		updateText();
	}
	
	public Suit getSuit() {
		return suit;
	}
	public Rank getRank() {
		return rank;
	}

	public Label getLabel() {
		return label;
	}

	public boolean isFlipped() {
		return isFlipped;
	}
	
	public void show() {
		this.isFlipped = true;
		this.isFree = true;
		updateText();
	}

	public void flip() {
		this.isFlipped = !this.isFlipped;
		this.isFree = !this.isFree;
		updateText();
	}

	public boolean isFree() {
		return isFree;
	}
	
	private void updateText() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			URL str = classLoader.getResource(String.format("%s.png", isFlipped? this.img:"back"));
			Image img = new Image(str.toString());
			ImageView view = new ImageView(img);
			view.setFitHeight(120);
			view.setPreserveRatio(true);
			label.setGraphic(view);
		} catch(Exception e) {
			e.printStackTrace();
			label.setText(isFlipped? toString():"-");
		}
	}
	@Override
	public String toString() {
		return String.format("%s %s(%d)", suit,rank,rank.val);
	}

}
