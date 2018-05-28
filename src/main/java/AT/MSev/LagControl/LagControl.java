package AT.MSev.LagControl;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.Map;

public class LagControl extends JavaPlugin {
    public static NamespacedKey key;
    public static LagControl Config;
    long DelayCheckScheduledTime = 20L;
    @Override
    public void onEnable() {
        key = new NamespacedKey(this, this.getDescription().getName());
        Config = this;
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new RunnableDelayCheck(this), DelayCheckScheduledTime, DelayCheckScheduledTime);
        getServer().getPluginManager().registerEvents(new Handler(), this);

        new Thread(new Runnable() {
            public void run() {

                try {
                    while(true) {

                        Socket clientSocket = serverSocket.accept();
                        final PrintWriter out =
                                new PrintWriter(clientSocket.getOutputStream(), true);
                        final BufferedReader in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));
                        getLogger().info("LC Data request from " + clientSocket.getInetAddress().getHostAddress());
                        out.println(ChunkBook.Booked.size());
                        new Thread(new Runnable() {
                            public void run() {
                                try {

                                    for(Map.Entry<Chunk, ChunkBook> e : ChunkBook.Booked.entrySet())
                                    {
                                        out.println(e.getValue().ToState());
                                    }
                                    out.println("END");

                                }catch (Exception e) { getLogger().info(e.getMessage()); }
                            }
                        }).start();
                    }

                } catch (Exception e) { getLogger().info(e.getMessage()); }

            }
        }).start();
    }
    static ServerSocket serverSocket;
    static {
        try {
            serverSocket = new ServerSocket(7777);
        } catch(Exception e) { }
    }
    @Override
    public void onDisable() {
        try {
            serverSocket.close();
        }catch (Exception e) {}
    }
}
