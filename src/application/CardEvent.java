package application;

import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CardEvent implements EventHandler<MouseEvent>{
	
	private static LinkedList<Card> toAdd = new LinkedList<>();
	private static Deck from;
	private static Stage stage;

	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType()==MouseEvent.MOUSE_PRESSED)
			mousePressed(event);
		else if (event.getEventType()==MouseEvent.DRAG_DETECTED)
			((Label) event.getSource()).startFullDrag();
		else if (event.getEventType()==MouseEvent.MOUSE_RELEASED) {
			if (!toAdd.isEmpty())
				toAdd.peekFirst().getLabel().getStyleClass().remove("selected");
		}
	}
	
	public void mouseReleased(MouseEvent event) {
		if (toAdd.isEmpty()) return;
		Deck to=null;
		if (event.getSource() instanceof Label) {
			Label l = (Label) event.getSource();
			to = (Deck) l.getParent();
		}
		else if (event.getSource() instanceof Deck)
			to = (Deck) event.getSource();
		if (to!=null && to.canAdd(toAdd.peekFirst())) {
			from = toAdd.peekFirst().getDeck();
			if (from instanceof CardsPane)
				from.draw(from.size()-from.indexOf(toAdd.peekFirst().getLabel()));
			else
				from.draw();
			to.add(toAdd);
		}
		toAdd.peekFirst().getLabel().getStyleClass().remove("selected");
		toAdd.clear();
		stage.sizeToScene();
	}
	
	public void mousePressed(MouseEvent event) {
		toAdd.clear();
		Label l = (Label) event.getSource();
		from = (Deck) l.getParent();
		List<Card> c = from.copyFrom(from.indexOf(l));
		if (!c.get(0).isFlipped()) return;
		toAdd.addAll(c);
		toAdd.peekFirst().getLabel().getStyleClass().add("selected");
	}

	public static void setStage(Stage stage) {
		CardEvent.stage = stage;
	}

}
