import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

public class PlaceShips {
    int startX;
    int startY;
    int endX;
    int endY;
    boolean drag = false;

    public boolean isValidPlacement(PlayerBoard pboard){
        if(startX<0 ||startX>9 || endX<0 || endX>9 || startY<0 || startY>9 || endY<0 || endY>0){
            return false;
        }
        else{
            for(int i = startX; i<=endX; i++){
                for (int j = startX; j<= endX; j++){
                    if (pboard.getBoardElement(i,j)==3){
                        return false;
                    }
                }
            }

        }
        return true;
    }

    public void handle(MouseDragEvent m){
        EventType<MouseDragEvent> event = m.getEventType();
        Label l = (Label) m.getSource();
        if (event.equals(MouseDragEvent.MOUSE_DRAG_ENTERED)){
            if(!drag) {
                startX = GridPane.getRowIndex(l);
                startY = GridPane.getColumnIndex(l);
                System.out.println(startX+" " + startY);
                drag = true;
            }
        }
        else if(event.equals(MouseDragEvent.MOUSE_DRAG_OVER)){
            endX = GridPane.getRowIndex(l);
            endY = GridPane.getColumnIndex(l);;
            System.out.println(endX +" "+ endY);
            drag = false;
        }

    }

    public void handle(MouseEvent m){
        Label l = (Label) m.getSource();
        if (m.getEventType().equals(MouseEvent.MOUSE_PRESSED)){
            startX = GridPane.getRowIndex(l);
            startY = GridPane.getColumnIndex(l);
            System.out.println(startX+" " + startY);
        }

    }


    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

}
