package com.coffeefirst.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
//import java.util.Random;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = -6375095366694704958L;

	private static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;
    
//    private Random r;
    private Handler handler;

    public Game() {
    	handler = new Handler();
    	
    	this.addKeyListener(new KeyInput(handler));
        
    	new Window(WIDTH, HEIGHT, "Let's build a game", this);
        
//    	r = new Random();
    	
    	handler.addObject(new Player(WIDTH/2-16, HEIGHT/2-16, ID.Player));
    }

    public synchronized void start() {
        if (running) return;

        thread = new Thread(this);
        thread.start();

        running = true;
    }

    public synchronized void stop() {
        if (!running) return;

        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
    	this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTick = 60.0;
        double ns = 1000000000 /  amountOfTick;
        double delta = 0;
        long timer = System.currentTimeMillis();
//        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
            
//            frames++;
            
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: " + frames);
//                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
    	handler.tick();
    }

    private void render() {
    	BufferStrategy bs = this.getBufferStrategy();
    	if (bs == null) {
    		createBufferStrategy(3);
    		return;
    	}
    	
    	Graphics g = bs.getDrawGraphics();
    	
    	g.setColor(Color.green);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
		handler.render(g);
    	
    	g.dispose();
    	bs.show();
    }

    public static void main(String[] args) {
        new Game();
    }



}

