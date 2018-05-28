package AT.MSev.LagControl;

import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChunkBook {
    public static ArrayList<ChunkBook> All = new ArrayList<ChunkBook>();
    public static HashMap<Chunk, ChunkBook> Booked = new HashMap<Chunk, ChunkBook>();
    Location Logical;
    public float AverageLagRate = 1;

    public ChunkBook(Chunk c)
    {
        Logical = new Location(c.getWorld(), c.getX(), 0, c.getZ());
        Booked.put(c, this);
    }

    public static void Add(Chunk c)
    {
        ChunkBook cb = FromChunk(c);
        if( cb != null )
        {
            All.add(cb);
        }
        else
        {
            ChunkBook newBooked = new ChunkBook(c);
            All.add(newBooked);
        }
    }

    public static void Remove(Chunk c)
    {
        ChunkBook cb = FromChunk(c);
        All.remove(cb);
    }
    //Allow Y not distinct
    /*
    static ChunkBook FromChunk(Chunk c)
    {
        if(Booked.containsKey(c)) return Booked.get(c);
        return null;
    }
    */

    static ChunkBook FromChunk(Chunk c)
    {
        for(Chunk k : Booked.keySet())
        {
            if(k.getZ() == c.getZ() && k.getX() == c.getX())
            {
                return Booked.get(k);
            }
        }
        return null;
    }

    String ToState()
    {
        return Logical.getWorld().getName() +
                ";" + String.format("%.0f", Logical.getX()) +
                ";" + String.format("%.0f", Logical.getZ()) +
                ";" + AverageLagRate;
    }
}
