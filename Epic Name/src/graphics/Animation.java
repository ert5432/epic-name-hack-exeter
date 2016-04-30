package graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 
    private int currentFrame;               
    private int animationDirection;         
    private int totalFrames;                
    
    private boolean hasLooped=false;
    private int loopCount=0;

    private boolean stopped;                

    private List<Frame> frames = new ArrayList<Frame>();    

    public Animation(BufferedImage[] frames, int frameDelay) {  
        this.frameDelay = frameDelay;
        this.stopped = true;

        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0;
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = 1;
        this.totalFrames = this.frames.size();

    }

    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }

    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }
    

    /**
	 * @return the hasLooped
	 */
	public boolean getHasLooped() {
		return hasLooped;
	}

	/**
	 * @param hasLooped the hasLooped to set
	 */
	public void setHasLooped(boolean hasLooped) {
		this.hasLooped = hasLooped;
	}
	

	/**
	 * @return the loopCount
	 */
	public int getLoopCount() {
		return loopCount;
	}

	/**
	 * @param loopCount the loopCount to set
	 */
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}

	public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }

    private void addFrame(BufferedImage frame, int duration) {
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    public void update() {
        if (!stopped) {
            frameCount++;

            if (frameCount > frameDelay) {
                frameCount = 0;
                currentFrame += animationDirection;

                if (currentFrame > totalFrames - 1) {
                	hasLooped =true;
                	loopCount++;
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }
}
