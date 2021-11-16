package defaultPackage.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**************************************************************************
 *                                                                        *
 *                       GUI component by Tyrael                          *
 *         ProgressBar, which could be any style and flexible.            *
 *                                                                        *
 *                       Copyright (c) 2020 LYL                           *
 *                            @author LYL                                 *
 *                            @version 1.0                                *
 **************************************************************************/
public class ProgressBar extends GridPane {
    private double width = 0;
    private double height = 0;
    private double progress = 0;
    private boolean showText = false;
    private StackPane progressField = new StackPane();
    private Rectangle background = new Rectangle();
    private Rectangle current = new Rectangle();
    private Label showProgress = new Label("0.0%");
    private static final Color defaultBackgroundColor = new Color(.8, .8, .8, .8);
    private static final Color defaultProgressColor = new Color(.16, .4, .82, 1);

    ProgressBar() {
    }

    /**
     * This constructor set a basic progress bar with height, width but
     * without the display for progress in text.
     * With the basic style. The default background color is gray,
     * {@code new Color(.8, .8, .8, .8)}. And the progress bar with
     * {@code new Color(.16,.4,.82,1)}. The default arc width, height is 10.
     *
     * @param height button height
     * @param width  button width
     */
    ProgressBar(double width, double height) {
        this.setHgap(5);
        this.progressField.setAlignment(Pos.CENTER_LEFT);
        this.height = height;
        this.width = width;
        this.background.setArcWidth(10);
        this.background.setArcHeight(10);
        this.current.setArcWidth(10);
        this.current.setArcHeight(10);
        this.background.setFill(defaultBackgroundColor);
        this.current.setFill(defaultProgressColor);
        progressField.getChildren().addAll(background, current);
        this.add(progressField, 0, 0);
    }

    /**
     * This constructor set a basic progress bar with height, width but
     * the display for progress in text could be define later.
     * With the basic style. The default background color is gray,
     * {@code new Color(.8, .8, .8, .8)}. And the progress bar with
     * {@code new Color(.16,.4,.82,1)}. The default arc width, height is 10.
     *
     * @param height   button height
     * @param width    button width
     * @param showText define the show progress in text or not
     */
    ProgressBar(double width, double height, boolean showText) {
        this.setHgap(5);
        this.progressField.setAlignment(Pos.CENTER_LEFT);
        this.height = height;
        this.width = width;
        this.showText = showText;
        this.background.setArcWidth(10);
        this.background.setArcHeight(10);
        this.current.setArcWidth(10);
        this.current.setArcHeight(10);
        this.background.setFill(defaultBackgroundColor);
        this.current.setFill(defaultProgressColor);
        progressField.getChildren().addAll(background, current);
        if (!showText)
            this.add(progressField, 0, 0);
        else {
            this.add(progressField, 0, 0);
            this.add(showProgress, 1, 0);
        }
    }

    /**
     * Set the Button location by taken X and Y.
     * The anchor at top left.
     *
     * @param X the point at x-axis
     * @param Y the point at y-axis
     */
    void setLocation(double X, double Y) {
        this.setLayoutX(X);
        this.setLayoutY(Y);
    }

    /**
     * Set the progress in text. Taken the progress in double.
     * Set progress to label text.
     *
     * @param progress the current progress from the object which have progress
     */
    void setShowText(double progress) {
        String progressNum = (Math.round(progress * 1000) / 10.0) + "";
        if(progressNum.contains("100")) progressNum = "100";
        if (progressNum.length() > 4) progressNum = progressNum.substring(0, 4);
        String text = progressNum + "%";
        this.showProgress.setText(text);
    }

    /**
     * Set the size for button. Also called for resize the button
     * by {@code resize()} in {@code main gui class}.
     *
     * @param width  the width for this component
     * @param height the height for this component
     */
    void setSize(double width, double height) {
        this.width = width;
        this.height = height;
        if (!this.showText)
            this.background.setWidth(width);
        else
            this.background.setWidth(width - 50);
        setUpdate();
        this.background.setHeight(height);
        this.current.setHeight(height);
    }

    /**
     * Set progress to progress 0
     */
    void reset() {
        this.current.setWidth(0);
        this.progress = 0;
        this.showProgress.setText("0.0%");
    }

    /**
     * Update the progress bar run when the progress updated
     * from the object.
     *
     * @param progress the new progress from object
     */
    void setUpdate(double progress) {
        this.progress = progress;
        this.current.setWidth(this.background.getWidth() * progress);
        if (this.showText) setShowText(progress);
    }

    double pre_progress = 0.0;

    void setUpdate(double progress, boolean MT) {
        if (MT) {
            if (progress >= pre_progress) {
                setUpdate(pre_progress);
                pre_progress = progress;
            }
        } else
            setUpdate(progress);
    }

    /**
     * Inner method. Used for reset the size when main stage size changed.
     */
    private void setUpdate() {
        this.current.setWidth(this.background.getWidth() * this.progress);
    }

    public double getComponentWidth() {
        return this.width;
    }

    /**
     * Style setting
     * Set the Arc width, height to the progress bar.
     * Width should == Height.
     *
     * @param width  the new value for arc width
     * @param height the new value for arc height
     */
    void setArc(double width, double height) {
        this.background.setArcWidth(width);
        this.background.setArcHeight(height);
        this.current.setArcWidth(width);
        this.current.setArcHeight(height);
    }

    /**
     * Style setting
     * Set a new display color for progress bar.
     * This method will override the default color which
     * defined in constructors. This method also may called
     * in listener for change color dynamically.
     * <p>
     * The color value rage is 0.0 ~ 1.0
     *
     * @param r the red channel for bar color
     * @param g the green channel for bar color
     * @param b the blue channel for bar color
     * @param a the alpha channel for bar color
     */
    void setColor(double r, double g, double b, double a) {
        Color progressColor = new Color(r, g, b, a);
        this.current.setFill(progressColor);
    }

    /**
     * Style setting
     * Set a new background color for progress bar.
     * This method will override the default color which
     * defined in constructors. This method also may called
     * in listener for change color dynamically.
     * <p>
     * The color value rage is 0.0 ~ 1.0
     *
     * @param r the red channel for background color
     * @param g the green channel for background color
     * @param b the blue channel for background color
     * @param a the alpha channel for background color
     */
    void setBackgroundColor(double r, double g, double b, double a) {
        Color backgroundColor = new Color(r, g, b, a);
        this.background.setFill(backgroundColor);
    }
}
