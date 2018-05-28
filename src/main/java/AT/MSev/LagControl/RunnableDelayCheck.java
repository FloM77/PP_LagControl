package AT.MSev.LagControl;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static org.bukkit.Bukkit.getLogger;

public class RunnableDelayCheck implements Runnable {
    LagControl control;

    public RunnableDelayCheck(LagControl plugin)
    {
        control = plugin;
        LastTime = System.currentTimeMillis();
    }

    public void run() {
        float lagRate = (float)(TickToMillis(control.DelayCheckScheduledTime)) / ActualTime();
        getLogger().info(lagRate+"");

        //Booked Rate Math
        for(ChunkBook cb : ChunkBook.All)
        {
            cb.AverageLagRate = (cb.AverageLagRate + lagRate) / 2;
        }
    }

    long LastTime;
    long ActualTime()
    {
        long thisTime = System.currentTimeMillis();
        long taken = thisTime - LastTime;
        LastTime = System.currentTimeMillis();
        return taken;
    }

    long TickToMillis(long tick)
    {
        return tick * 50;
    }
}
