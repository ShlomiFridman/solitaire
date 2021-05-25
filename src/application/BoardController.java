package application;

import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BoardController {
	
	private Stage stage;
	private CardsPane[] panes;
	private DrawGrid drawGrid;
	private SetStack[] sets;
	private CardEvent ev;
	private Clock clock;
	private boolean startClock;
	
	public void setup() {
		ev = new CardEvent();
		clock = new Clock(clockLabel);
		drawGrid = new DrawGrid(this.info);
		panes = new CardsPane[7];
		sets = new SetStack[4];
		setEvents();
		upperBoard.getChildren().add(drawGrid);
		AnchorPane.setLeftAnchor(drawGrid, 5.0);
		AnchorPane.setTopAnchor(drawGrid, 0.0);
		AnchorPane.setBottomAnchor(drawGrid, 0.0);
		for (int i=0;i<7;i++) {
			panes[i] = new CardsPane();
			panes[i].setOnMouseDragReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					ev.mouseReleased(event);
					stage.sizeToScene();
				}
				
			});
		}
		panes[0].getStyleClass().add("firstPane");
		for (int i=0;i<4;i++) {
			sets[i] = new SetStack();
			setGrid.add(sets[i], i, 0);
			GridPane.setMargin(sets[i], new Insets(5));
			sets[i].setOnMouseDragReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					ev.mouseReleased(event);
				}
				
			});
		}
		board.getChildren().addAll(panes);
		resetBtn.setOnMouseClicked(ev->{
			if (ev.getClickCount()!=2)
				return;
			reset();
		});
		resetBtn.setOnMouseEntered(ev->{
			resetBtn.getStyleClass().add("hover");
		});
		resetBtn.setOnMouseExited(ev->{
			resetBtn.getStyleClass().remove("hover");
		});
		resetBtn.setOnMousePressed(ev->{
			resetBtn.getStyleClass().add("clicked");
		});
		resetBtn.setOnMouseReleased(ev->{
			resetBtn.getStyleClass().remove("clicked");
		});
		draw();
		clock.reset();
		stage.sizeToScene();
	}

    public void setStage(Stage stage) {
		this.stage = stage;
	}
    
    public void setEvents() {
		Iterator<Card> it = drawGrid.getDeck().iterator();
		Card tmp;
		while (it.hasNext()){
			tmp = it.next();
			tmp.getLabel().setOnMousePressed(ev);
			tmp.getLabel().setOnDragDetected(ev);
			tmp.getLabel().setOnMouseReleased(ev);
			tmp.getLabel().setOnMouseDragReleased(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					ev.mouseReleased(event);
					stage.sizeToScene();
					checkWin();
				}
				
			});
			tmp.getLabel().setOnMouseClicked(ev->{
				if (ev.getClickCount()!=2) return;
				Label l = (Label) ev.getSource();
				Deck d = (Deck) l.getParent();
				if (!d.peek().getLabel().equals(l)) return;
				Card c = d.peek();
				for (SetStack s:sets)
					if (s.canAdd(c)) {
						s.add(d.draw(1));
						stage.sizeToScene();
						checkWin();
						break;
					}
			});
		}
    }
    
    public boolean isDone() {
    	for (CardsPane tmp : panes)
    		if (!tmp.isDone())
    			return false;
		return drawGrid.isDone();
    }
    
    public void checkWin() {
    	if (!startClock) {
    		clock.start();
			startClock=true;
    	}
    	else {
			if (!isDone()) return;
			board.setMouseTransparent(true);
			startClock=false;
			info.setText(info.getText()+"\nYou Win");
			clock.pause();
    	}
    }
    
    @FXML
    private void reset() {
    	drawGrid.reset();
    	for (CardsPane tmp: panes)
    		tmp.reset();
    	for (SetStack tmp: sets)
    		tmp.reset();
		setEvents();
    	draw();
		clock.reset();
		startClock=false;
		info.setText("Good Luck");
		if (board.isMouseTransparent())
			board.setMouseTransparent(false);
		stage.sizeToScene();
    }
    
    
    
    public void draw() {
		for (int i=0;i<7;i++) {
			panes[i].add(drawGrid.draw(i+1));
			panes[i].peek().flip();
		}
    }
    
	@FXML
    private VBox root;

	@FXML
    private AnchorPane upperBoard;

    @FXML
    private GridPane setGrid;

    @FXML
    private HBox board;
    
    @FXML
    private Button resetBtn;
    
    @FXML
    private Label clockLabel,info;

}
