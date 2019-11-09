package com.example.tetris.TetrisBlock;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class LineBlock extends TetrisBlock{

    private final int HORIZONTAL_RECTS = 4;
    private final int VERTICAL_RECTS = 1;
    private final int COLOR = Color.MAGENTA;
    private final int SPACE = 10;

    private ArrayList<Rectangle> rectangles;

    public LineBlock(Canvas canvas, Paint paint, int screenWidth, int rectWidth){
        this.rectangles = new ArrayList<>();
        this.rectWidth = rectWidth;
        this.screenWidth = screenWidth;
        this.xPosition =  this.getRandomXPosition(screenWidth, getRightSidePosition() - getxPosition());
        this.drawLineBlock(canvas, paint, rectWidth, this.xPosition, this.yPosition);
    }

    public void drawLineBlock(Canvas canvas, Paint paint, int rectangleWidth, int x, int y){
        paint.setColor(COLOR);
        for(int i = 0; i < HORIZONTAL_RECTS; i++){
            Rectangle rect = this.getRectangle(canvas, paint, rectangleWidth ,x + rectangleWidth * i, y);
            this.rectangles.add(rect);
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

    public ArrayList<Rectangle> getRectangles(){
        return this.rectangles;
    }
}
