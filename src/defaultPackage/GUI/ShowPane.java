package defaultPackage.GUI;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**************************************************************************
 *                                                                        *
 *                       GUI component by Tyrael                          *
 *       ShowPane, which extended from basic ScrollPane layout.           *
 *                                                                        *
 *                       Copyright (c) 2020 LYL                           *
 *                            @author LYL                                 *
 *                            @version 1.0                                *
 **************************************************************************/
public class ShowPane extends ScrollPane {
    private GridPane contextPane = new GridPane();
    private double width = 0, height = 0;
    private Label context = new Label();
    private int RowIndex = 0;
    private static final int MAX_ITEM = 300;
    public static final String TRANSPARENT = "show-pane-transparent";
    public static final String DARK = "show-pane-dark";

    /**
     * If you use this component under JavaFX 8 style.
     * You might need additional stylesheet to remove
     * the useless LIGHT GREY background color
     *
     * {@code .show-pane {
     *     -fx-background-color: transparent;
     * }
     * }
     * then add this in your main class{
     * @code showPane.getStyleClass().add("show-pane");
     * mainStage.getStylesheets().add(getClass().getResource("YOUR STYLESHEET FILE").toExternalForm());
     * }*/
    ShowPane(){}

    ShowPane(double width, double height){
        this.width = width;
        this.height = height;
        setSize(width, height);

    }

    void setLocation(double X, double Y) {
        this.setLayoutX(X);
        this.setLayoutY(Y);
    }


    void setFont(double size) {
        this.context.setFont(Font.font(size));
    }

    void setSize(double width, double height) {
        this.setMinSize(width, height);
        this.setMaxSize(width, height);
    }

    void setContext(String con){
        if(contextPane.getChildren().size()>=MAX_ITEM) {
            int remove_leng = contextPane.getChildren().size() - MAX_ITEM;
            contextPane.getChildren().remove(0, remove_leng);
        }
        context = new Label(con);
        contextPane.add(context, 0, RowIndex);
        this.setContent(contextPane);
        this.RowIndex++;
    }

    void clear(){
        this.RowIndex = 0;
        contextPane.getChildren().clear();
    }

    /**
     * Set the color theme for background.
     * Could be transparent, dark or default.
     *
     * @param style a style relate to css file.
     * */
    void setTheme(String style){
        this.getStyleClass().add(style);
        this.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
    }
}
