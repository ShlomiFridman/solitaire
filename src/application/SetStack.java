package application;

import java.util.Iterator;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class SetStack extends StackPane implements Deck {
	
	CardList cards;
	
	public SetStack() {
		cards = new CardList(this.getChildren());
	}

	public void stackPaneSetup() {
		this.setPrefSize(200,200);
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
		return cards.removeFrom(num);
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
		cards.addAll(list);
		Iterator<Card> it = list.iterator();
		Card tmp;
		while (it.hasNext()) {
			tmp=it.next();
			tmp.getLabel().setLayoutY(0);
			tmp.getLabel().setLayoutX(0);
			add(tmp);
		}
	}

	@Override
	public List<Card> copyFrom(int ind) {
		return cards.subList(ind, cards.size());
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
		return cards.subList(0, cards.size());
	}

	@Override
	public boolean canAdd(Card c) {
		if (cards.size()==0)
			return (c.getRank().val==1);
		if (cards.size()==13) return false;
		return Rank.isHigher(c.getRank(), cards.peek().getRank()) && c.getSuit().equals(cards.peek().getSuit());
	}

	@Override
	public int indexOf(Label l) {
		return this.getChildren().indexOf(l);
	}

	@Override
	public void reset() {
		cards.clear();
	}

	@Override
	public boolean isDone() {
		return this.size()==13;
	}

}
