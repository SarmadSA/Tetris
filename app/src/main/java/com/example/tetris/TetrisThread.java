package com.example.tetris;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class TetrisThread extends Thread {

    private final static int sleepInterval = 100;
    private boolean running = false;
    private boolean paused = false;
    private SurfaceHolder surfaceHolder;
    private TetrisView tetrisView;


    public TetrisThread(SurfaceHolder surfaceHolder, TetrisView tetrisView){
        this.surfaceHolder = surfaceHolder;
        this.tetrisView = tetrisView;
    }

    @Override
    public void run(){
        Canvas canvas;

        while(running){

            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder){
                    this.tetrisView.onDraw(canvas);
                }
            } finally {
                if(canvas != null){
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            this.sleepFor(this.sleepInterval);

        }

    }

    private void sleepFor(int interavl){
        try{
            sleep(interavl);
        } catch (Exception e){
            Log.i(TetrisActivity.TAG,"sleepFor interrupted");
        }
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public void setPaused(boolean paused){
        synchronized (this.surfaceHolder){
            this.paused = paused;
        }
    }

    public boolean isPaused(){
        synchronized (this.surfaceHolder){
            return paused;
        }
    }

}
