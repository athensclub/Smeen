package smeen.component.code;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import smeen.component.code.block.movement.MoveCodeBlock;
import smeen.global.Fonts;
import smeen.views.MainView;

public class CodeSelector extends ScrollPane {

    public CodeSelector(MainView main){
        VBox root = new VBox();

        Label moveLabel = new Label("เคลื่อนไหว");
        moveLabel.setFont(Fonts.BASIC_BOLD_FONT);

        CodeBlock move = new MoveCodeBlock(main);

        root.getChildren().addAll(moveLabel, move);

        setContent(root);
    }

}
