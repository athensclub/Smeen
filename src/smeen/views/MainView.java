package smeen.views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import smeen.component.code.CodeBlockList;
import smeen.component.code.CodeSelector;
import smeen.component.code.block.movement.CodeArea;

/**
 * We mostly assume that MainView is root of the scene and every positions relative to the scene
 * is the same as the positions relative to the root.
 */
public class MainView extends Pane {
    private final MenuBar menuBar;
    private final Menu fileMenu;
    private final CodeSelector codeSelector;

    private final CodeArea codeArea;
    private final ObjectProperty<CodeBlockList> draggingBlock;

    public MainView() {
        draggingBlock = new SimpleObjectProperty<>();
        draggingBlock.addListener((obs, oldv, newv) -> {
            getChildren().remove(oldv);
            if (newv != null) {
                getChildren().add(newv);
            }
        });

        setMinSize(600, 400);

        VBox root = new VBox();
        root.prefWidthProperty().bind(widthProperty());
        root.prefHeightProperty().bind(heightProperty());

        fileMenu = new Menu("File");

        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open...");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save as...");
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem);

        menuBar = new MenuBar(fileMenu);

        HBox content = new HBox();

        codeSelector = new CodeSelector(this);
        codeSelector.prefWidthProperty().bind(widthProperty().divide(2));
        codeSelector.prefHeightProperty().bind(heightProperty());

        codeArea = new CodeArea(this);
        codeArea.prefWidthProperty().bind(widthProperty().divide(2));
        codeArea.prefHeightProperty().bind(heightProperty());

        content.getChildren().addAll(codeSelector, codeArea);

        root.getChildren().addAll(menuBar, content);

        getChildren().addAll(root);

        // handle dragging code block from selector
        final double[] oldPos = {-1, -1}; // oldPos[0] = x, oldPos[1] = y
        final boolean[] dragging = {false};
        final CodeBlockList[] lastShownSnappable = {null};
        addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            if (draggingBlock.get() == null)
                return;

            // first call of the drag event, set the oldPos to calculate in next iteration.
            if (!dragging[0]) {
                oldPos[0] = e.getX();
                oldPos[1] = e.getY();
                dragging[0] = true;
                return;
            }

            // calculate delta mouse x and delta mouse y.
            double dx = e.getX() - oldPos[0];
            double dy = e.getY() - oldPos[1];

            // move the dragging block by the delta.
            CodeBlockList block = draggingBlock.get();
            block.relocate(block.getLayoutX() + dx, block.getLayoutY() + dy);

            // handle code block snapping with other block list
            CodeBlockList snappable = codeArea.getSnappableCodeBlockList();
            if (lastShownSnappable[0] != null && lastShownSnappable[0] != snappable)
                lastShownSnappable[0].showSnapHintProperty().set(false);
            if (snappable != null)
               snappable.showSnapHintProperty().set(true);
            lastShownSnappable[0] = snappable;

            // set the oldPos to use for next iteration calculation.
            oldPos[0] = e.getX();
            oldPos[1] = e.getY();
        });

        addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            CodeBlockList block = draggingBlock.get();

            // destroy dragging block when released.
            dragging[0] = false;
            draggingBlock.set(null);

            // reset snapping data
            if(lastShownSnappable[0] != null){
                lastShownSnappable[0].showSnapHintProperty().set(false);
                lastShownSnappable[0] = null;
            }

            // add to code area if mouse is inside
            Point2D mousePos = new Point2D(e.getX(), e.getY());
            mousePos = codeArea.sceneToLocal(mousePos);
            if (block != null && codeArea.getBoundsInLocal().contains(mousePos)) {
                // set block position from relative to scene to relative to CodeArea.
                Point2D blockPos = new Point2D(block.getLayoutX(), block.getLayoutY());
                blockPos = codeArea.sceneToLocal(blockPos);
                block.relocate(blockPos.getX(), blockPos.getY());

                codeArea.getChildren().add(block);
            }
        });
    }

    public ObjectProperty<CodeBlockList> draggingBlockProperty() {
        return draggingBlock;
    }

    public CodeArea getCodeArea() {
        return codeArea;
    }
}
