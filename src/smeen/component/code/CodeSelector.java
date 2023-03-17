package smeen.component.code;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import smeen.global.Fonts;

public class CodeSelector extends ScrollPane {

    public CodeSelector(){
        VBox root = new VBox();

        Label moveLabel = new Label("เคลื่อนไหว");
        moveLabel.setFont(Fonts.BASIC_BOLD_FONT);

        root.getChildren().addAll(moveLabel);

        setContent(root);
    }

}
