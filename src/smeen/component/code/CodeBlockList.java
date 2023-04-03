package smeen.component.code;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import smeen.global.SmeenConstants;
import smeen.views.MainView;

/**
 * Assume that the CodeBlockList is either being dragged or in CodeArea only.
 */
public class CodeBlockList extends VBox {

    public CodeBlockList(MainView main) {
        addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
            startFullDrag();

            // set the position of the node from relative to the parent to relative to the scene.
            Point2D pos = localToScene(0, 0);
            relocate(pos.getX(), pos.getY());

            main.getCodeArea().getChildren().remove(this);
            main.draggingBlockProperty().set(this);
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
        Bounds a = localToScene(getBoundsInLocal());
        Bounds b = other.localToScene(other.getBoundsInLocal());

        // the x intersect should be at least 50% of the smaller bounds's width
        double l = Math.max(a.getMinX(), b.getMinX()), r = Math.min(a.getMaxX(), b.getMaxX());
        if(Math.max(0, r - l) < 0.5 * Math.min(a.getWidth(), b.getWidth()))
            return false;

        // the other CodeBlockList should be below and has distance not more than threshold
        double dist = b.getMinY() - a.getMaxY();
        return dist > 0 && dist <= SmeenConstants.SNAP_THRESHOLD;
    }

}
