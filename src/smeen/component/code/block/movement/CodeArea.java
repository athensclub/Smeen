package smeen.component.code.block.movement;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import smeen.component.code.CodeBlockList;
import smeen.views.MainView;

public class CodeArea extends Pane {

    private MainView main;

    public CodeArea(MainView main) {
        this.main = main;
        setBackground(Background.fill(Color.WHITE));
    }

    /**
     * Find any CodeBlockList inside this CodeArea that the current dragging CodeBlockList
     * in MainView can be snapped to, or null if there's no dragging CodeBlockList or none
     * of the CodeBlockList inside this CodeArea can snap.
     *
     * @return the CodeBlockList that the current dragging CodeBlockList in MainView can be snapped to, or null if there's none.
     */
    public CodeBlockList getSnappableCodeBlockList() {
        CodeBlockList block = main.draggingBlockProperty().get();
        if (block == null)
            return null;

        return getChildren().stream()
                .filter(node -> node instanceof CodeBlockList)
                .map(node -> (CodeBlockList) node)
                .filter(b -> b.canSnap(block))
                .findAny().orElse(null);
    }

}
