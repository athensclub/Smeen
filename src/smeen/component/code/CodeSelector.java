package smeen.component.code;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import smeen.component.code.block.movement.MoveCodeBlock;
import smeen.global.Fonts;
import smeen.views.MainView;

public class CodeSelector extends ScrollPane {

    private MainView main;

    public CodeSelector(MainView main) {
        this.main = main;
        VBox root = new VBox();

        Label moveLabel = new Label("เคลื่อนไหว");
        moveLabel.setFont(Fonts.BASIC_BOLD_FONT);

        CodeBlock move = new MoveCodeBlock(main);
        addMouseDragEventHandler(move);

        root.getChildren().addAll(moveLabel, move);

        setContent(root);
    }

    private void addMouseDragEventHandler(CodeBlock block) {
        block.addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
            startFullDrag();

            CodeBlockList toDrag = new CodeBlockList(main);
            CodeBlock copy = block.copy();
            toDrag.getChildren().add(copy);

            // set the position of the node from relative to the parent to relative to the scene.
            Point2D pos = block.localToScene(0, 0);
            toDrag.relocate(pos.getX(), pos.getY());

            main.draggingBlockProperty().set(toDrag);
        });
    }

}
