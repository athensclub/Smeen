package smeen.component.code;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import smeen.util.Copyable;
import smeen.views.MainView;

public abstract class CodeBlock extends HBox implements Copyable<CodeBlock> {

    private MainView main;
    public CodeBlock(MainView main){
        this.main = main;

        setAlignment(Pos.CENTER);
        setBackground(Background.fill(Color.NAVY));
        setSpacing(10);
        setPadding(new Insets(10,10,5,5));

        addEventFilter(MouseEvent.DRAG_DETECTED, e -> {
            CodeBlock copy = copy();
            Point2D pos = localToScene(0,0);
            copy.relocate(pos.getX(), pos.getY());
            copy.setBackground(Background.fill(Color.RED));
            main.draggingBlockProperty().set(copy);
        });
    }

    public MainView getMain() {
        return main;
    }

}
