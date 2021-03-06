package application;

import java.util.Iterator;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CardsPane extends Pane implements Deck {
	
	private static Stage stage;
	CardList cards;
	
	public CardsPane(){
		cards = new CardList(this.getChildren());
		buildPane();
	}
	
	private void buildPane() {
		this.setPrefWidth(100);
		this.setPadding(new Insets(5));
		this.getStyleClass().add("cardsPane");
	}

	@Override
	public Card peek() {
		return cards.peek();
	}

	@Override
	public Card draw() {
		return cards.remove();
	}

	@Override
	public List<Card> draw(int num) {
		List<Card> res = cards.removeFrom(size()-num);
		if (!cards.isEmpty() &&!cards.peek().isFlipped())
			cards.peek().show();;
		return res;
	}
	
	public List<Card> drawFrom(int index){
		return cards.removeFrom(index);
	}

	@Override
	public List<Card> drawAll() {
		return cards.removeAll();
	}

	@Override
	public void add(Card c) {
		cards.add(c);
	}

	@Override
	public void add(List<Card> list) {
		if (cards.contains(list.get(0)))
			return;
		Iterator<Card> it = list.iterator();
		Card tmp;
		while (it.hasNext()) {
			tmp=it.next();
			tmp.getLabel().setLayoutY(40*cards.size()+10);
			tmp.getLabel().setLayoutX(10);
			add(tmp);
		}
		stage.sizeToScene();
	}

	@Override
	public List<Card> copyFrom(int ind) {
		return cards.subList(ind, size());
	}

	@Override
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	@Override
	public int size() {
		return cards.size();
	}

	@Override
	public void setDeck(List<Card> deck) {
		cards.clear();
		cards.addAll(deck);
	}

	@Override
	public List<Card> getDeck() {
		return cards;
	}

	@Override
	public boolean canAdd(Card c) {
		if (size()==0) return true;
		Card last = cards.peek();
		return Rank.isLower(c.getRank(), last.getRank()) && Suit.diffColor(c.getSuit(), last.getSuit());
	}

	@Override
	public void reset() {
		cards.clear();
	}
	
	@Override
	public boolean isDone() {
		return this.size()==0;
	}
	
	@Override
	public int indexOf(Label l) {
		return this.getChildren().indexOf(l);
	}

	@Override
	public String toString() {
		return cards.toString();
	}

	public static void setStage(Stage stage) {
		CardsPane.stage = stage;
	}
	
}
