package functionality;

public class Clock {

	private long gameStartTick;
	private long lastTick; // In time, ms
	private long currentTick;
	private int mainSecondsCounter;
	private int mainMillisCounter;
	
	public Clock() {
		this.gameStartTick = System.currentTimeMillis();
		this.lastTick = System.currentTimeMillis();
		this.currentTick = System.currentTimeMillis();
		this.mainSecondsCounter = 0;
		this.mainMillisCounter = 0;
	}

	private long timeSinceLastTickInMillis() {
		this.currentTick = System.currentTimeMillis();
		return currentTick-lastTick;
	}
	
	private void updateTime() {
		this.lastTick = System.currentTimeMillis();
	}
	
	public int getTimeSinceStartOfGameInSeconds() {
		return this.mainSecondsCounter;
	}
	
	public int getTimeSinceStartInMillis() {
		return this.mainMillisCounter;
	}
	
	public boolean frameShouldChange() {
		if (this.timeSinceLastTickInMillis() >= Constants.FRAME_MINIMUM_MILLIS) {
			this.updateTime();
			this.mainMillisCounter = (int)(this.currentTick - this.gameStartTick);
			this.mainSecondsCounter = this.mainMillisCounter / 1000;
			return true;
		}
		return false;
	}

	public long getCurrentTime() {
		return System.currentTimeMillis();
	}
	
}
