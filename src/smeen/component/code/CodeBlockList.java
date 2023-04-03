package smeen.component.code;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import smeen.global.SmeenConstants;
import smeen.views.MainView;

/**
 * Assume that the CodeBlockList is either being dragged or in CodeArea only.
 *
 * Do not add the CodeBlock component directly to this component getChildren(). Instead,
 * add it using getCodeList().getChildren().add/addAll.
 */
public class CodeBlockList extends VBox {

    private VBox codeList;

    private Pane snapHintContainer;

    private BooleanProperty showSnapHint;

    public CodeBlockList(MainView main) {
        codeList = new VBox();
        snapHintContainer = new Pane();
        getChildren().addAll(codeList, snapHintContainer);

        // prevent adding code block directly to this component children list.
        getChildren().addListener((ListChangeListener<? super Node>) c -> {
            while(c.next()){
                if(c.wasAdded() && c.getAddedSubList().stream().anyMatch(node -> node instanceof  CodeBlock))
                    throw new IllegalStateException("Do not add the CodeBlock component directly to CodeBlockList's getChildren(). Instead, add it using getCodeList().getChildren().add/addAll.");
            }
        });

        addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
            startFullDrag();

            // set the position of the node from relative to the parent to relative to the scene.
            Point2D pos = localToScene(0, 0);
            relocate(pos.getX(), pos.getY());

            main.getCodeArea().getChildren().remove(this);
            main.draggingBlockProperty().set(this);
        });

        // handle showing snap hint
        showSnapHint = new SimpleBooleanProperty(false);
        showSnapHint.addListener((obs, oldv, newv) -> {
            if(newv){
                if(!snapHintContainer.getChildren().isEmpty())
                    throw new IllegalStateException("Call show snap hint on CodeBlockList that is already showing snap hint.");
                Rectangle hint = new Rectangle(codeList.getChildren().get(codeList.getChildren().size() - 1).getBoundsInLocal().getWidth(), 40, Color.GREY);
                snapHintContainer.getChildren().add(hint);
            }else{
                snapHintContainer.getChildren().clear();
            }
        });
    }

    /**
     * Check whether the other CodeBlockList is below this CodeBlockList with close enough distance
     * to snap the other CodeBlockList under this.
     *
     * @param other the CodeBlockList to check.
     * @return whether the other CodeBlockList can be snapped under this block.
     */
    public boolean canSnap(CodeBlockList other) {
        Bounds a = codeList.localToScene(codeList.getBoundsInLocal());
        Bounds b = other.localToScene(other.getBoundsInLocal());

        // the x intersect should be at least 35% of the smaller bounds's width
        double l = Math.max(a.getMinX(), b.getMinX()), r = Math.min(a.getMaxX(), b.getMaxX());
        if(Math.max(0, r - l) < 0.35 * Math.min(a.getWidth(), b.getWidth()))
            return false;

        // the other CodeBlockList should have distance not more than threshold
        double dist = b.getMinY() - a.getMaxY();
        return dist > -SmeenConstants.SNAP_THRESHOLD && dist <= SmeenConstants.SNAP_THRESHOLD;
    }

    public BooleanProperty showSnapHintProperty() {
        return showSnapHint;
    }

    public VBox getCodeList() {
        return codeList;
    }
}
