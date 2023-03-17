package smeen.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import smeen.component.code.CodeSelector;


public class MainView extends VBox {

    private final MenuBar menuBar;
    private final Menu fileMenu;

    private final CodeSelector codeSelector;

    public MainView(){
        setMinSize(600, 400);

        fileMenu = new Menu("File");

        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open...");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save as...");
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem);

        menuBar = new MenuBar(fileMenu);

        codeSelector = new CodeSelector();

        getChildren().addAll(menuBar, codeSelector);
    }

}
