package AT.MSev.LagControl;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getLogger;

public class Handler implements Listener {
    @EventHandler
    public void OnLoadChunk(ChunkLoadEvent e)
    {
        getLogger().info(""+e.getChunk().getZ());
        ChunkBook.Add(e.getChunk());
    }

    @EventHandler
    public void OnUnLoadChunk(ChunkUnloadEvent e)
    {
        ChunkBook.Remove(e.getChunk());
    }
}
