package com.example.tetris.TetrisBlock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RectangleBlock extends TetrisBlock {
    private final int HORIZONTAL_RECTS = 2;
    private final int VERTICAL_RECTS = 2;
    private final int COLOR = Color.GREEN;


    public RectangleBlock(Canvas canvas, Paint paint, int screenWidth, int rectWidth){
        this.rectWidth = rectWidth;
        this.screenWidth = screenWidth;
        this.xPosition =  this.getRandomXPosition(screenWidth, getRightSidePosition() - getxPosition());
        this.drawRectangleBlock(canvas, paint, rectWidth, this.xPosition, this.yPosition);
    }

    public void drawRectangleBlock(Canvas canvas, Paint paint, int rectangleWidth, int x, int y){
        paint.setColor(COLOR);
        for(int i = 0; i < HORIZONTAL_RECTS; i++){
            this.getRectangle(canvas, paint, rectangleWidth ,x + rectangleWidth * i, y);
        }
        for(int i = 0; i < VERTICAL_RECTS; i++){
            this.getRectangle(canvas, paint, rectangleWidth,x + rectangleWidth * i, y + rectangleWidth);
        }
    }

    /**
     * Calculates and returns the position of the right side of the line-block
     *
     * @return the position of the right side of the line-block
     */
    public int getRightSidePosition(){
        return this.xPosition + (this.rectWidth * this.HORIZONTAL_RECTS);
    }

    /**
     * Calculates and returns the position of the bottom side of the line-block
     *
     * @return the position of the bottom side of the line-block
     */
    public int getBottomSidePosition(){
        return this.yPosition + (this.rectWidth * this.VERTICAL_RECTS);
    }

    public int getVerticalRects() {
        return VERTICAL_RECTS;
    }
}
