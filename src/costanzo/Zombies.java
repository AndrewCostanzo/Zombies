package costanzo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

import java.util.ArrayList;

public class Zombies {

    public static int toSpawnTot = 0;
    public static int count = 0;

    public Spawns spawn;
    public Utils util;

    public boolean isComplete = false;
    public ArrayList<Zombie> zombieList = new ArrayList<Zombie>();

    // Spawn information
    public ArrayList<Location> spawnPoints;
    public ArrayList<Integer> spawnRates;
    public World w;

    public Zombies(Utils util, World w){
        this.util = util;
        this.w = w;
        spawn = new Spawns();
    }

    public void spawnZombies(int r){
        int total = r * Bukkit.getOnlinePlayers().size() * 3; // Determine number of zombies

        // Determine how many zombies to spawn at a time
        if(toSpawnTot == 0) {
            spawn.determineDoorSpawns();
            this.spawnPoints = spawn.getSpawnPoints();
            spawn.spawnRates = new ArrayList<Integer>();
            spawn.determineSpawnRate(total);
            this.spawnRates = spawn.getSpawnRates();
            isComplete = false;
        }

        if(toSpawnTot == total){
            isComplete = true;
            count = 0;
            spawn.spawnRates = null;
        }

        if(spawnRates != null) {
            for (Integer g : spawnRates) {
                util.broadcast(ChatColor.GREEN, "Will spawn: " + g);
            }
        }else{
            util.broadcast(ChatColor.GOLD,"Spawn rates are null");
        }




        util.broadcast(ChatColor.GREEN,"Zombies to spawn: " + total + " Zombies spawned so far: " + toSpawnTot);
        if(isComplete != true) {
            toSpawnTot += spawnRates.get(count);
            for (int i = 0; i < spawnRates.get(count); i++) {
                util.broadcast(ChatColor.GREEN,"I: " + i + " Spawn rates: " + spawnRates.get(count));
                Zombie x = (Zombie) w.spawnEntity(spawn.randomLocation(spawnPoints), EntityType.ZOMBIE);
                zombieList.add(x);
            }
            count++;
        }
    }
}



