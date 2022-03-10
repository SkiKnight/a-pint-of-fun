package game.utility.core;

public class Clock {

    public float time;      //tracks the time from game start in seconds
    public float delT;      //time passed in seconds after the last fram was rendered
    private long startSystemTime, currentSystemTime, lastFrameTime;
    private boolean firstTick;

    public Clock()
    {
        time = 0f;
        startSystemTime = currentSystemTime = lastFrameTime = System.currentTimeMillis();
        firstTick = true;
    }

    public void tick(){
        if(firstTick){
            delT = 0;
            lastFrameTime = System.currentTimeMillis();
            firstTick = false;
            return;
        }

        currentSystemTime = System.currentTimeMillis();
        delT = (currentSystemTime - lastFrameTime) / 1000f ;
        lastFrameTime = currentSystemTime;
        time += delT;
    }

    public long startTimeStamp(){
        return startSystemTime;
    }
}
