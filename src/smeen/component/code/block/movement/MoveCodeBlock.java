package smeen.component.code.block.movement;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.util.converter.IntegerStringConverter;
import smeen.component.code.CodeBlock;
import smeen.global.Fonts;
import smeen.views.MainView;

public class MoveCodeBlock extends CodeBlock {

    private TextField amount;
    public MoveCodeBlock(MainView main){
        super(main);
        Label first = new Label("เคลื่อนที่");
        first.setTextFill(Color.WHITE);
        first.setFont(Fonts.SMALL_REGULAR_FONT);

        amount = new TextField();
        amount.setPrefWidth(30);
        amount.setPrefHeight(20);
        amount.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        amount.setFont(Fonts.EXTRA_SMALL_REGULAR_FONT);

        Label second = new Label("ก้าว");
        second.setTextFill(Color.WHITE);
        second.setFont(Fonts.SMALL_REGULAR_FONT);

        getChildren().addAll(first, amount, second);
    }

    @Override
    public CodeBlock copy() {
        MoveCodeBlock copy = new MoveCodeBlock(getMain());
        copy.amount.setText(amount.getText());
        return copy;
    }
}
