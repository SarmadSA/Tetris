package com.example.tetris.TetrisBlock;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

public abstract class TetrisBlock {

    protected int screenWidth;
    protected int rectWidth;
    protected int xPosition = 0;
    protected int yPosition = 0;
    protected boolean isOnGround = false;

    /**
     * Draws the smallest uit int the game, that is a rectangle.
     * This rectangle can be used to draw the rest tetris blocks
     *
     * @param canvas
     */
    protected Rectangle getRectangle(Canvas canvas, Paint paint, int rectangleWidth, int leftPosition, int topPosition){
        return new Rectangle(canvas, paint, rectangleWidth, leftPosition, topPosition);
    }

    protected int getRandomXPosition(int upperLimit, int blockWidth){
        Random random = new Random();
        return (random.nextInt(upperLimit - blockWidth) / rectWidth) * rectWidth;
    }


    /**
     * Calculates and returns the position of the right side of the line-block
     *
     * @return the position of the right side of the line-block
     */
    public abstract int getRightSidePosition();

    /**
     * Calculates and returns the position of the bottom side of the line-block
     *
     * @return the position of the bottom side of the line-block
     */
    public abstract int getBottomSidePosition();

    /**
     * Returns the number of vertical rectangles of the block
     * (One rectangle is defined by TetrisBlock.drawRectangle(params...))
     *
     * @return
     */
    public abstract int getVerticalRects();


    public int getxPosition(){
        return this.xPosition;
    }

    public void setxPosition(int position){
        this.xPosition = position;
    }

    public int getyPosition(){
        return this.yPosition;
    }

    public void setyPosition(int position){
        this.yPosition = position;
    }

    public boolean isOnGround(){
        return isOnGround;
    }

    public void setOnGround(boolean onGround){
        isOnGround = onGround;
    }
}
