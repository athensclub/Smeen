package smeen.views;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import smeen.component.code.CodeBlock;
import smeen.component.code.CodeSelector;


public class MainView extends Pane {
    private final MenuBar menuBar;
    private final Menu fileMenu;
    private final CodeSelector codeSelector;
    private final ObjectProperty<CodeBlock> draggingBlock;

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

        codeSelector = new CodeSelector(this);

        root.getChildren().addAll(menuBar, codeSelector);

        getChildren().addAll(root);

        final double[] oldPos = {-1, -1}; // oldPos[0] = x, oldPos[1] = y
        final boolean[] dragging = { false };
        addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            if(draggingBlock.get() == null)
                return;

            if(!dragging[0]){
                oldPos[0] = e.getX();
                oldPos[1] = e.getY();
                dragging[0] = true;
                return;
            }

            double dx = e.getX() - oldPos[0];
            double dy = e.getY() - oldPos[1];

            CodeBlock block = draggingBlock.get();
            block.relocate(block.getLayoutX() + dx, block.getLayoutY() + dy);

            oldPos[0] = e.getX();
            oldPos[1] = e.getY();
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            dragging[0] = false;
            draggingBlock.set(null);
        });
    }

    public ObjectProperty<CodeBlock> draggingBlockProperty() {
        return draggingBlock;
    }

}
