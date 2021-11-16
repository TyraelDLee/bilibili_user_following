package defaultPackage.GUI;

import defaultPackage.*;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**************************************************************************
 *                                                                        *
 *                         PortSniffer v 1.0                              *
 *                         Main class for GUI                             *
 *                                                                        *
 *                  Backup class, now move to sniffGUI                    *
 *                       Copyright (c) 2020 LYL                           *
 *                            @author LYL                                 *
 *                            @version 1.0                                *
 **************************************************************************/
public class MainGUI extends Application implements Obs {
    private static final double WINDOW_HEIGHT = 500.0;
    private static final double WINDOW_WIDTH = 800.0;
    private static double ZOOMFACTOR = 1;
    private static final String ints = "0123456789";
    private static final String title = "b站用户成分查询";

    private ServiceWorker worker = new ServiceWorker();
    private Number mainStageHeight = WINDOW_HEIGHT, mainStageWidth = WINDOW_WIDTH;
    private TextField uid_In = new TextField();
    private TextField sess_In = new TextField();
    private ImageButton startButton = new ImageButton(100, 80, "Start", .95, .82, .38, .8);
    private ImageButton clearButton = new ImageButton(100, 80, "Cancel");
    private ShowPane showPane = new ShowPane(WINDOW_WIDTH, WINDOW_HEIGHT - 150);

    private String currentType = "sniff";

    private final Group root = new Group();

    private void setZoomFactor(Number mainStageWidth, Number mainStageHeight) {
        double width = mainStageWidth.doubleValue() / WINDOW_WIDTH, height = mainStageHeight.doubleValue() / WINDOW_HEIGHT;
        if (width < height) ZOOMFACTOR = Math.round(width * 100.0) / 100.0;
        else ZOOMFACTOR = Math.round(height * 100.0) / 100.0;
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(title);
        primaryStage.setMinHeight(420);
        primaryStage.setMinWidth(740);
        Scene mainStage = new Scene(root, mainStageWidth.doubleValue(), mainStageHeight.doubleValue());
        worker.reg(this);

        //-- resize listener start  --//
        mainStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            mainStageHeight = newValue;
            setZoomFactor(mainStageWidth, mainStageHeight);
            resize();
        });
        mainStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            mainStageWidth = newValue;
            setZoomFactor(mainStageWidth, mainStageHeight);
            resize();
        });
        //-- resize listener end --//

        //-- component initial start --//
        resize();
        uid_In.setPromptText("输入要查询的uid");
        sess_In.setPromptText("SESSDATA(可选)");
        //set style//
        uid_In.setStyle("-fx-background-color: transparent;-fx-border-style: solid;-fx-border-width: 0 0 2 0;-fx-border-color: #999999;");
        sess_In.setStyle("-fx-background-color: transparent;-fx-border-style: solid;-fx-border-width: 0 0 2 0;-fx-border-color: #999999;");
        //-- component initial end --//

        //-- component listener setting start --//
        uid_In.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER)
                request(primaryStage);
        });
        startButton.setOnMouseEntered(event -> startButton.setOver(1.0));
        startButton.setOnMouseExited(event -> startButton.setOver(0.8));
        startButton.setOnMouseClicked(event -> request(primaryStage));
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        uid_In.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue && firstTime.get()){
                showPane.requestFocus();
                firstTime.setValue(false);
            }
        });
        showPane.setContext("(由于b站隐私设置，最多只能查询250个关注，如果要查询全部关注请输入SESSDATA，SESSDATA可在bilibili.com的Cookie中找到)");
        root.getChildren().addAll(startButton, clearButton, showPane, uid_In, sess_In);
        primaryStage.setScene(mainStage);
        primaryStage.show();
    }

    //X: width, Y: height
    private void resize() {
        uid_In.setMinSize(120, 25);
        uid_In.setMaxSize(300, 40);
        uid_In.setPrefSize(120 * ZOOMFACTOR, 25 * ZOOMFACTOR);
        uid_In.setLayoutX((mainStageWidth.doubleValue() - uid_In.getPrefWidth()) / 2 - 300* (ZOOMFACTOR<1?1:ZOOMFACTOR));
        uid_In.setLayoutY(45);

        sess_In.setMinSize(200, 25);
        sess_In.setMaxSize(600, 40);
        sess_In.setPrefSize(300 * ZOOMFACTOR, 25 * ZOOMFACTOR);
        sess_In.setLayoutX((mainStageWidth.doubleValue() - uid_In.getPrefWidth()) / 2 - 160* (ZOOMFACTOR<1?1:ZOOMFACTOR));
        sess_In.setLayoutY(45);

        //settingButton.setLocation(50, 20);
        startButton.setLocation((mainStageWidth.doubleValue() + uid_In.getPrefWidth()) / 2 + 150 - 100, 45);
        if (25 * ZOOMFACTOR <= 20) startButton.setSize(100, 25);
        else startButton.setSize(100, (25 * ZOOMFACTOR)<40?25 * ZOOMFACTOR:40);

        clearButton.setLocation((mainStageWidth.doubleValue() + uid_In.getPrefWidth()) / 2 + 280 - 100, 45);
        if (25 * ZOOMFACTOR <= 20) clearButton.setSize(100, 25);
        else clearButton.setSize(100, (25 * ZOOMFACTOR)<40?25 * ZOOMFACTOR:40);

        showPane.setSize(mainStageWidth.doubleValue() - 50, mainStageHeight.doubleValue() - 200);
        showPane.setLocation(25, 150);
    }

    @Override
    public void update(String showText) {
        showPane.setContext(showText);
    }

    private void request(Stage primaryStage) {
        uid_In.setStyle("-fx-background-color: transparent;-fx-border-style: solid;-fx-border-width: 0 0 2 0;-fx-border-color: #999999");
        uid_In.setPromptText("输入要查询的uid");
        primaryStage.setTitle("查询中...");
        showPane.clear();
        if(sess_In.getText().length()==0) worker.setSESSDATA(sess_In.getText());
        worker.setUid(uid_In.getText());
        System.out.println(uid_In.getText());
        //set the sniffer attributes.
        showPane.setContext("这个人关注了:");
        showPane.setContext(worker.sentRequest());
        primaryStage.setTitle("查询完成");
    }

}