package defaultPackage.GUI;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**************************************************************************
 *                                                                        *
 *                       GUI component by Tyrael                          *
 *               MenuButton, with an amazing animation effect.            *
 *                                                                        *
 *                       Copyright (c) 2020 LYL                           *
 *                            @author LYL                                 *
 *                            @version 1.0                                *
 **************************************************************************/
public class MenuButton extends StackPane {
    private double height = 12;
    private double width = 20;
    private static long defaultAnimDuration = 250;

    private Rectangle up = new Rectangle();
    private Rectangle md = new Rectangle();
    private Rectangle dw = new Rectangle();

    private int clickCount = 0;

    /**
     * This constructor set a new button with default height, width.
     * With the basic button style. The default background color is gray,
     * {@code new Color(.0, .0, .0, 1)}.
     *
     * */
    MenuButton(){
        this.setAlignment(Pos.CENTER);
        this.up.setWidth(this.width);
        this.md.setWidth(this.width);
        this.dw.setWidth(this.width);
        this.up.setHeight(2);
        this.md.setHeight(2);
        this.dw.setHeight(2);

        setMargin(up,new Insets(0,0,this.height,0));
        setMargin(dw,new Insets(this.height,0,0,0));

        this.getChildren().addAll(up,md,dw);
    }

    /**
     * This constructor set a new button with height, width.
     * With the basic button style. The default background color is gray,
     * {@code new Color(.0, .0, .0, 1)}.
     *
     * the height suggest set which height/2%2==0 && height%2==0
     * */
    MenuButton(double width, double height){
        this.setAlignment(Pos.CENTER);
        this.height = height;
        this.width = width;
        this.up.setWidth(width);
        this.md.setWidth(width);
        this.dw.setWidth(width);
        this.up.setHeight(2);
        this.md.setHeight(2);
        this.dw.setHeight(2);

        setMargin(up,new Insets(0,0,height,0));
        setMargin(dw,new Insets(height,0,0,0));

        this.getChildren().addAll(up,md,dw);
    }

    /**
     * Set the Button location by taken X and Y.
     * The anchor at top left.
     *
     * @param X the point at x-axis
     * @param Y the point at y-axis
     * */
    void setLocation(double X, double Y) {
        this.setLayoutX(X);
        this.setLayoutY(Y);
    }

    /**
     * Set the size for button. Also called for resize the button
     * by {@code resize()} in {@code main gui class}.
     *
     * @param width the width for this component
     * @param height the height for this component
     * */
    void setSize(double width, double height){
        this.width = width;
        this.height = height;
        this.up.setWidth(width);
        this.md.setWidth(width);
        this.dw.setWidth(width);
    }

    /**
     * Style setting
     * Set a new display color for button.
     * This method will override the default color which
     * defined in constructors. This method also may called
     * in listener for change color dynamically.
     *
     * The color value rage is 0.0 ~ 1.0
     *
     * @param r the red channel for bar color
     * @param g the green channel for bar color
     * @param b the blue channel for bar color
     * @param a the alpha channel for bar color
     * */
    void setColor(double r, double g, double b, double a){
        Color color = new Color(r,g,b,a);
        this.up.setFill(color);
        this.md.setFill(color);
        this.dw.setFill(color);
    }

    /**
     * Style setting
     * Set a duration for animation.
     *
     * @param duration the duration for animation
     * */
    void setAnimDuration(long duration){
        defaultAnimDuration = duration;
    }

    /**
     * Style setting
     * Set the weight for button
     *
     * @param weight the button weight
     * */
    void setButtonWeight(double weight){
        this.up.setHeight(weight);
        this.md.setHeight(weight);
        this.dw.setHeight(weight);
    }

    /**
     * Should called by listener. Execute animation and
     * calculate what it called for.
     *
     * @return boolean which is it called for close or not.*/
    boolean onClick(){
        if(clickCount==1){
            animation(defaultAnimDuration,false);
            clickCount=0;
            return false;
        }else{
            animation(defaultAnimDuration, true);
            clickCount++;
            return true;
        }
    }

    /**
     * Animation !
     * This animation contain 5 phases of animation
     * 1. hide middle bar.                            -
     * 2. move the up bar down.                        | - run concurrently.       --
     * 3. move the down bar up.                       -
     *                                                                                |  -- swap when move from close to menu (mTOC is false).
     * 4. rotate down bar 45 degree clockwise.        -
     *                                                 | - run concurrently.       --
     * 5. rotate up bar 45 degree anticlockwise.      -
     * */
    private void animation(long duration, boolean mTOc){
        FadeTransition disappear = new FadeTransition(Duration.millis(duration), this.md);
        TranslateTransition moveUP = new TranslateTransition(Duration.millis(duration), this.dw);
        TranslateTransition moveDW = new TranslateTransition(Duration.millis(duration), this.up);
        RotateTransition rotateUP = new RotateTransition(Duration.millis(duration), this.dw);
        RotateTransition rotateDW = new RotateTransition(Duration.millis(duration), this.up);
        ParallelTransition rotatePT = new ParallelTransition();
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(disappear, moveDW, moveUP);
        rotatePT.getChildren().addAll(rotateUP,rotateDW);

        disappear.setAutoReverse(false);
        if(mTOc){
            disappear.setFromValue(1.0f);
            disappear.setToValue(0.0f);

            moveDW.setToY(this.height/2);
            moveUP.setToY(-this.height/2);
            rotateUP.setToAngle(45);
            rotateDW.setToAngle(-45);
            pt.play();
            pt.setOnFinished(event -> rotatePT.play());
        }
        else{
            disappear.setFromValue(0.0f);
            disappear.setToValue(1.0f);

            moveDW.setToY(0);
            moveUP.setToY(0);
            rotateUP.setToAngle(0);
            rotateDW.setToAngle(0);
            rotatePT.play();
            rotatePT.setOnFinished(event -> pt.play());
        }
    }

    void setTheme(String style){
        if(style.equals("DARK")){
            this.up.setFill(Color.WHITE);
            this.md.setFill(Color.WHITE);
            this.dw.setFill(Color.WHITE);
        }
    }
}
