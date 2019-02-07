package com.coffeefirst.graphics;

public class Screen extends Render {
	
	private Render test;

	public Screen(int width, int height) {
		super(width, height);
		
	}
	
	public void render() {
		draw(test, 0, 0);
	}

}
