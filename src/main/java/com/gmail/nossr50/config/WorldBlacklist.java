package com.gmail.nossr50.config;

import com.gmail.nossr50.mcMMO;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Blacklist certain features in certain worlds
 */
public class WorldBlacklist {
    private static Set<UUID> blacklist; // Firestarter :: faster blacklist
    private final mcMMO plugin;

    private final String blackListFileName = "world_blacklist.txt";

    public WorldBlacklist(mcMMO plugin)
    {
        this.plugin = plugin;
        blacklist = new HashSet<>(); // Firestarter :: faster blacklist
        init();
    }

    public void init()
    {
        //Make the blacklist file if it doesn't exist
        File blackListFile = new File(plugin.getDataFolder() + File.separator + blackListFileName);

        try {
            if(!blackListFile.exists())
                blackListFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Load up the blacklist
        loadBlacklist(blackListFile);
        //registerFlags();
    }

    private void loadBlacklist(File blackListFile) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(blackListFile);
            bufferedReader = new BufferedReader(fileReader);

            String currentLine;

            while((currentLine = bufferedReader.readLine()) != null)
            {
                if(currentLine.length() == 0)
                    continue;

                // Firestarter start :: faster blacklist
                World world = Bukkit.getWorld(currentLine);

                if (world == null) {
                    continue;
                }

                if (!blacklist.contains(world.getUID())) {
                    blacklist.add(world.getUID());
                }
                // Firestarter end
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close readers
            closeRead(bufferedReader);
            closeRead(fileReader);
        }

        plugin.getLogger().info(blacklist.size()+" entries in mcMMO World Blacklist");
    }

    private void closeRead(Reader reader) {
        if(reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isWorldBlacklisted(World world)
    {
        return blacklist.contains(world.getUID()); // Firestarter :: faster blacklist
    }
}
