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

    public CodeBlock(MainView main) {
        this.main = main;

        setAlignment(Pos.CENTER);
        setBackground(Background.fill(Color.NAVY));
        setSpacing(10);
        setPadding(new Insets(10, 10, 5, 5));

        addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
            startFullDrag();

            CodeBlock copy = copy();
            // set the position of the node from relative to the parent to relative to the scene.
            Point2D pos = localToScene(0, 0);
            copy.relocate(pos.getX(), pos.getY());

            // if dragged from code area, remove it and only follow mouse
            // we must calculate relative positions before removing (calling localToScene must be above this code)
            main.getCodeArea().getChildren().remove(this);

            main.draggingBlockProperty().set(copy);
        });
    }

    public MainView getMain() {
        return main;
    }

}
