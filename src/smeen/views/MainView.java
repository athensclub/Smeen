package smeen.views;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class MainView extends VBox {

    private final MenuBar menuBar;
    private final Menu fileMenu;

    public MainView(){
        fileMenu = new Menu("File");

        MenuItem open = new MenuItem("Open...");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save as...");
        fileMenu.getItems().addAll(open, save, saveAs);

        menuBar = new MenuBar(fileMenu);

        getChildren().add(menuBar);
    }

}
