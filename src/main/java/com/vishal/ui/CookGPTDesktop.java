package com.vishal.ui;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
public class CookGPTDesktop {
    @FXML
    private ListView<String> menuItemsListView;

    @FXML
    private TextArea textArea;

    public void initialize() {
        // Initialize the ListView with menu items
        menuItemsListView.getItems().addAll("Add Recipe", "Upload PDF", "Query Recipe");

        // Set a default text in the text area
        textArea.setText("Welcome to CookGPT!");
    }

}
