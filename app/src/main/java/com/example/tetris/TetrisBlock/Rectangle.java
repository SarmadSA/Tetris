package com.example.tetris.TetrisBlock;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rectangle {

    private int xPosition;
    private int yPosition;
    private int rectWidth;
    private int rectHeight;

    public Rectangle(Canvas canvas, Paint paint, int rectangleWidth, int x, int y){
        this.xPosition = x;
        this.yPosition = y;
        this.rectWidth = rectangleWidth;
        canvas.drawRect(this.xPosition, this.yPosition,this.rectWidth + this.xPosition,this.rectWidth + this.yPosition, paint);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}
