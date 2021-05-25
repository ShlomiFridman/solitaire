package application;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DrawGrid extends GridPane implements Deck{
	
	private LinkedList<Card> cards;
	private LinkedList<Card> deckList;
	private StackPane drawInfo;
	private ImageView img;
	private Label deckLabel,info;
	private int cntr;
	
	public DrawGrid(Label info) {
		this.info=info;
		drawInfo = new StackPane();
		cards = new LinkedList<>();
		deckList = new LinkedList<>();
		deckLabel = new Label();
		ClassLoader classLoader = getClass().getClassLoader();
		URL str = classLoader.getResource("back.png");
		img = new ImageView(new Image(str.toString()));
		reset();
		gridSetup();
		updateLabels();
	}
	
	private void gridSetup() {
		GridPane.setHalignment(drawInfo,HPos.CENTER);
		GridPane.setMargin(drawInfo,new Insets(5));
		img.setFitHeight(120);
		img.setPreserveRatio(true);
		drawInfo.getChildren().add(img);
		drawInfo.getChildren().add(deckLabel);
		drawInfo.setPrefHeight(140);
		deckLabel.setTextFill(Color.WHITE);
		this.add(drawInfo,0,0);
	}
	
	private void eventSetup() {
		drawInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (isDone()) return;
				drawFromDeck();
			}
			
		});
		drawInfo.setOnMousePressed(ev->{
			deckLabel.getStyleClass().add("drawSelect");
		});
		drawInfo.setOnMouseReleased(ev->{
			deckLabel.getStyleClass().remove("drawSelect");
		});
	}
	
	private void updateLabels() {
		deckLabel.setText((deckList.size()>0)? deckList.size()+" Cards":"");
		if (this.getChildren().size()>1)
			this.getChildren().remove(1);
		if (cards.size()>0){
			GridPane.setHalignment(cards.peekLast().getLabel(),HPos.CENTER);
			GridPane.setMargin(cards.peekLast().getLabel(),new Insets(5));
			this.add(cards.peekLast().getLabel(),1,0);
		}
	}
	
	private void drawFromDeck() {
		if (size()==0) return;
		if (deckList.isEmpty())
			cardsToDeck();
		cards.addLast(deckList.removeLast());
		if (!cards.peekLast().isFlipped())
			cards.peekLast().show();
		updateLabels();
	}
	
	private void cardsToDeck() {
		this.info.setText(String.format("%d Shuffles", ++cntr));
		deckList.addAll(cards);
		Deck.shuffle(deckList);
		cards.clear();
		updateLabels();
	}

	@Override
	public Card peek() {
		return cards.peekLast();
	}

	@Override
	public Card draw() {
		Card c = cards.removeLast();
		updateLabels();
		return c;
	}

	@Override
	public List<Card> draw(int num) {
		List<Card> list= new LinkedList<>();
		while (num>0 && cards.size()>0) {
			list.add(cards.removeLast());
			num--;
		}
		while (num-->0)
			list.add(deckList.removeLast());
		updateLabels();
		return list;
	}

	@Override
	public List<Card> drawAll() {
		List<Card> list= new LinkedList<>();
		while (deckList.size()>0)
			list.add(deckList.getLast());
		updateLabels();
		return list;
	}

	@Override
	public void add(Card c) {
		deckList.add(c);
		updateLabels();
	}

	@Override
	public void add(List<Card> list) {
		deckList.addAll(list);
		updateLabels();
	}

	@Override
	public List<Card> copyFrom(int ind) {
		return cards.subList(cards.size()-1, cards.size());
	}

	@Override
	public boolean isEmpty() {
		return deckList.isEmpty() && cards.isEmpty();
	}

	@Override
	public int size() {
		return deckList.size()+cards.size();
	}

	@Override
	public void setDeck(List<Card> deck) {
		deckList.clear();
		deckList.addAll(deck);
		updateLabels();
	}

	@Override
	public List<Card> getDeck() {
		List<Card> list = new LinkedList<>();
		for (int i=0;i<deckList.size();i++)
			list.add(deckList.get(i));
		for (int i=0;i<cards.size();i++)
			list.add(cards.get(i));
		return list;
	}

	@Override
	public boolean canAdd(Card c) {
		return false;
	}

	@Override
	public void reset() {
		cards.clear();
		deckList.clear();
		deckList.addAll(Deck.newDeck(true));
		eventSetup();
		updateLabels();
	}

	@Override
	public int indexOf(Label l) {
		return this.getChildren().indexOf(l);
	}

	@Override
	public boolean isDone() {
		return cards.isEmpty() && deckList.isEmpty();
	}

	public int getCntr() {
		return cntr;
	}

}
