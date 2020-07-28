package costanzo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Spawns {

    public ArrayList<Location> door1 = new ArrayList<Location>();
    public boolean doorOne = false;
    public static ArrayList<Location> spawnPoints = new ArrayList<Location>();
    public static ArrayList<Integer> spawnRates = new ArrayList<Integer>();

    public void determineDoorSpawns(){
        World w = Bukkit.getWorld("world");

        // Starting room
        spawnPoints.add(new Location(w,26.0,101.0+2,2.0));
        spawnPoints.add(new Location(w,14.0,101.0+2,14.0));
        spawnPoints.add(new Location(w,11,101.0+2,6.0));

        // Door One is open
        if(doorOne == true){
            spawnPoints.add(new Location(w,2.0,101.0+2,32.0));
            spawnPoints.add(new Location(w,6.0,101.0+2,26.0));
            spawnPoints.add(new Location(w,26.0,101.0+2,32.0));
        }

    }

    public int determineSpawnRate(int total){
        if(total == 0){
            return total;
        }
        int rnd = (int)(Math.random() * total)+1;
        spawnRates.add(rnd);
        rnd= total - rnd;
        return determineSpawnRate(rnd);
    }

    public List<Block> getDoor1(){
        World w = Bukkit.getWorld("world");
        door1.add(new Location(w,11.0,104.0,19.0));
        door1.add(new Location(w,13.0,102.0,19.0));
        return blocksFromTwoPoints(door1.get(0),door1.get(1));
    }

    public static Location randomLocation(ArrayList<Location> list){
        int random = (int)(Math.random() * list.size());
        return list.get(random);
    }

    public List<Block> blocksFromTwoPoints(Location loc1, Location loc2){
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    public ArrayList<Location> getSpawnPoints(){
        return this.spawnPoints;
    }

    public ArrayList<Integer> getSpawnRates(){
        return this.spawnRates;
    }
}
