package com.example.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tetris.TetrisBlock.LBlock;
import com.example.tetris.TetrisBlock.LineBlock;
import com.example.tetris.TetrisBlock.RectangleBlock;
import com.example.tetris.TetrisBlock.TetrisBlock;

import java.util.ArrayList;
import java.util.Random;

public class TetrisView extends SurfaceView implements SurfaceHolder.Callback {

    private int screenWidth;
    private int screenHeight;
    private int rectangleWidth; //Screen-width/10 to fit exactly 10 blocks

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); //TODO move paint out?

    private TetrisThread tetrisThread;

    private ArrayList<TetrisBlock> tetrisBlocks; //ArrayList containing all tetris blocks to be drown on screen.

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        tetrisThread = new TetrisThread(holder, this);
        this.tetrisBlocks = new ArrayList<>();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#141414")); //Set background color
        if(this.lost()) { //if game over
            this.showGameOverText(canvas);
        } else {
            if (!this.tetrisThread.isPaused()) {
                if (this.tetrisBlocks.size() > 0) { //Check if the previews block is on the ground, and spawn a new block
                    TetrisBlock lastBlock = tetrisBlocks.get(tetrisBlocks.size() - 1); //Get the last block
                    if (lastBlock.isOnGround()) { //If last block is on ground, add a new block
                        tetrisBlocks.add(this.getRandomTetrisBlock(canvas)); //Add a new block
                    }
                } else {
                    tetrisBlocks.add(this.getRandomTetrisBlock(canvas));
                }
                this.updateBlockPositionY(tetrisBlocks.get(tetrisBlocks.size() - 1));
            }
            this.updateGame(canvas, this.paint); //Redraw all blocks in the arrayList, with the new updated positions.
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        tetrisThread.setRunning(true);
        tetrisThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        this.rectangleWidth = width/10;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        this.tetrisThread.setRunning(false);
        while (retry) {
            try {
                this.tetrisThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        System.out.println("*** Sufrace destroyed all resources!");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//    	Log.i(SnakeActivity.TAG, event.toString());
        if (event.getAction()==MotionEvent.ACTION_DOWN) {
            int x = (int)event.getX(0);
            int y = (int)event.getY(0); //We may not need the y //TODO: use y to rotate the block maybe?
            if(!this.tetrisThread.isPaused()) {
                this.moveTetrisBlockX(x, this.tetrisBlocks.get(tetrisBlocks.size() - 1));
            }
        }
        return true;
    }

    /**
     * Redraws every block with the new updated positions
     * @param canvas
     * @param paint
     */
    private void updateGame(Canvas canvas, Paint paint){
        for(TetrisBlock block: tetrisBlocks){
            if(block instanceof LineBlock){
                LineBlock lineBlock = (LineBlock) block;
                lineBlock.drawLineBlock(canvas, paint, this.rectangleWidth, block.getxPosition(), block.getyPosition());
            } else if(block instanceof RectangleBlock){
                RectangleBlock rectangleBlock = (RectangleBlock) block;
                rectangleBlock.drawRectangleBlock(canvas, paint, this.rectangleWidth, block.getxPosition(), block.getyPosition());
            }  else if(block instanceof LBlock){
                LBlock lBlock = (LBlock) block;
                lBlock.drawLBlock(canvas, paint, this.rectangleWidth, block.getxPosition(), block.getyPosition());
            }
        }
    }


    private void updateBlockPositionY(TetrisBlock tetrisBlock){
        if(tetrisBlock.getBottomSidePosition() > this.getGroundLevel(tetrisBlock)){
            tetrisBlock.setyPosition(this.getGroundLevel(tetrisBlock));
            tetrisBlock.setOnGround(true);
        } else {
            tetrisBlock.setyPosition(tetrisBlock.getyPosition() + this.rectangleWidth);
        }
    }

   private void moveTetrisBlockX(int tuchPositionX, TetrisBlock block) {
       //Move block only if it is not touching the ground
       if (!block.isOnGround()) {
           if (tuchPositionX > block.getRightSidePosition()) { //If the click is to the right
               if (block.getRightSidePosition() < this.screenWidth) { //Check if moving the block to the right is not outside of the screen
                   block.setxPosition(block.getxPosition() + this.rectangleWidth);
               } else {
                   block.setxPosition(this.screenWidth - this.rectangleWidth);
               }
           } else if (tuchPositionX < block.getxPosition()) { //If the click is to the left
               block.setxPosition(block.getxPosition() - this.rectangleWidth);
           }
       }
   }

    /**
     * Returns the highets y corrdinate of overlapping, if no overlapping, returns ground y (screen height)
     * @param movingBlock
     * @return
     */
   private int getGroundLevel(TetrisBlock movingBlock){
       int groundLevelY = this.screenHeight - this.rectangleWidth * movingBlock.getVerticalRects();
       int highestOverlappingYPoint = 0;
        for(int i = this.tetrisBlocks.size()-2; i >= 0; i--){ //-2 because we dont need the last element as we already passe it as parameter //TODO break after you find the firs overlapping
            TetrisBlock currentElement = this.tetrisBlocks.get(i);
            if(movingBlock instanceof LBlock && movingBlock.getxPosition() + rectangleWidth <= currentElement.getxPosition()){
                groundLevelY = this.screenHeight - this.rectangleWidth;
            }
                if(movingBlock.getxPosition() < currentElement.getRightSidePosition() && movingBlock.getRightSidePosition() > currentElement.getRightSidePosition()){
                    highestOverlappingYPoint = currentElement.getyPosition();
                    break;
                }
                else if(movingBlock.getRightSidePosition() > currentElement.getxPosition() && movingBlock.getxPosition() < currentElement.getxPosition()){
                    highestOverlappingYPoint = currentElement.getyPosition();
                    break;
                } else {
                    if(movingBlock.getClass().equals(currentElement.getClass())){ //If both block are same type
                        if(movingBlock.getxPosition() == currentElement.getxPosition() && movingBlock.getRightSidePosition() == currentElement.getRightSidePosition()){
                            highestOverlappingYPoint = currentElement.getyPosition();
                            break;
                        }
                    } else { //if blocks are of different types
                        if((movingBlock.getxPosition() >= currentElement.getxPosition() && movingBlock.getRightSidePosition() <= currentElement.getRightSidePosition())
                                || (movingBlock.getxPosition() <= currentElement.getxPosition() && movingBlock.getRightSidePosition() >= currentElement.getRightSidePosition())){
                            highestOverlappingYPoint = currentElement.getyPosition();
                            break;
                        }
                    }
                }
            }

       if(highestOverlappingYPoint > 0){
           return (groundLevelY - (this.screenHeight - highestOverlappingYPoint));
       }
       return groundLevelY;
   }

   private TetrisBlock getRandomTetrisBlock(Canvas canvas){
       TetrisBlock block = null;
       Random random = new Random();
       float randomFloat = random.nextFloat();

       if(randomFloat <= 0.3){
           block = new LineBlock(canvas, this.paint, this.screenWidth, this.rectangleWidth);
       } else if(randomFloat > 0.3 && randomFloat < 0.6){
           block = new RectangleBlock(canvas, this.paint, this.screenWidth, this.rectangleWidth);
       } else if(randomFloat >= 0.6){
           block = new LBlock(canvas, this.paint, this.screenWidth, this.rectangleWidth);
       }

       return block;
   }

   public TetrisThread getTetrisThread(){
       return this.tetrisThread;
   }

   private boolean lost(){
       boolean lost = false;
       if(this.tetrisBlocks.size() > 0) {
           TetrisBlock lastBlock = this.tetrisBlocks.get(this.tetrisBlocks.size() - 1);
           if (this.getGroundLevel(lastBlock) <= 100) {
               lost = true;
           }
       }
       return lost;
   }

   private void showGameOverText(Canvas canvas){
       paint.setColor(Color.WHITE);
       paint.setTextSize(100);
       canvas.drawText("Game Over", (this.screenWidth/2) - 250, this.screenHeight/2, paint);
   }
}