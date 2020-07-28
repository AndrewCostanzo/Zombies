package costanzo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

import java.util.ArrayList;

public class Dogs {

    public World w;
    public Spawns spawn;
    public Utils util;

    public ArrayList<Location> spawnPoints;
    public ArrayList<Integer> spawnRates;

    public int count = 0;
    public int totalSpawned = 0;
    public boolean isComplete = false;
    public ArrayList<Wolf> wolfList = new ArrayList<Wolf>();

    public Dogs(Utils util,World w){
        this.w = w;
        spawn = new Spawns();
        this.util = util;
    }

    public void spawnDogs(int round){
        int total = round * Bukkit.getOnlinePlayers().size() * 2;

        if(totalSpawned == 0){
            setSpawns(total);
            isComplete = false;
        }

        if(totalSpawned == total){
            isComplete = true;
            count=0;
            spawn.spawnRates = null;
        }

        // Debugging
        if(spawnRates != null) {
            for (Integer g : spawnRates) {
                util.broadcast(ChatColor.GREEN, "Will spawn: " + g);
            }
        }else{
            util.broadcast(ChatColor.GOLD,"Spawn rates are null");
        }

        //Debugging
        util.broadcast(ChatColor.GREEN,"Wolves to spawn: " + total + " Wolves spawned so far: " + totalSpawned);
        if(isComplete != true){
            totalSpawned+=spawnRates.get(count);
            for(int i=0; i< spawnRates.get(count); i++){
                Wolf woof = (Wolf) w.spawnEntity(spawn.randomLocation(spawnPoints), EntityType.WOLF);
                woof.setAngry(true);
                Player p = util.getRandomPlayer();
                woof.setTarget((LivingEntity)p);
                woof.damage(0, p);
                wolfList.add(woof);
            }
        }
    }

    public void setSpawns(int total){
        spawn.determineDoorSpawns();
        this.spawnPoints = spawn.getSpawnPoints();
        spawn.spawnRates = new ArrayList<Integer>();
        spawn.determineSpawnRate(total);
        this.spawnRates = spawn.getSpawnRates();
    }
}
