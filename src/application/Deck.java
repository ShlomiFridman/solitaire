package application;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.Label;

public interface Deck {
	
	public Card peek();
	
	public Card draw();
	
	public List<Card> draw(int num);
	
	public List<Card> drawAll();
	
	public void add(Card c);
	
	public void add(List<Card> list);
	
	public List<Card> copyFrom(int ind);
	
	public boolean isEmpty();
	
	public int size();
	
	public void setDeck(List<Card> deck);
	
	public List<Card> getDeck();
	
	public boolean canAdd(Card c);
	
	public int indexOf(Label l);
	
	public void reset();
	
	public boolean isDone();

	@Override
	public String toString();
	
	public static void shuffle(List<Card> deck) {
		Collections.shuffle(deck);
	}

	public static List<Card> newDeck(boolean shuffle) {
		List<Card> res = new LinkedList<>();
		for (Suit s:Suit.values())
			for (Rank r:Rank.values())
				res.add(new Card(s,r));
		if (shuffle)
			shuffle(res);
		return res;
	}
}
