package com.fa13.build.model;

public class VideoPlayer extends BasicPlayer implements VideoPrimitive {
	public static enum PlayerStatus {
		PS_NORMAL,
		PS_TACKLE
	};
	private double x = 0, y = 0;
	
	public VideoPlayer() {
		super();
	}

	public VideoPlayer(PlayerAmplua position) {
		super(position);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
