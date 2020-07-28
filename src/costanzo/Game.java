package costanzo;

import org.bukkit.ChatColor;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static costanzo.Main.round;

public class Game extends BukkitRunnable {

    public static Utils util = new Utils();

    public void run(){

        Main.getInstance().spawn(round);
        if(round%5 == 0){
            //checkDogs()
        }else{
            checkZombies();
        }

    }

    public void checkZombies(){
        ArrayList<Zombie> x = Main.getInstance().zombie.zombieList;

        if(Main.getInstance().zombie.isComplete == true) {
            int total = x.size();
            int current = 0;
            for (Zombie b : x) {
                if (b.isDead()) {
                    current++;
                }
            }
            if (total == current) {
                Main.getInstance().round++;
                util.broadcast(ChatColor.GREEN, "-- Round " + Main.getInstance().round + " --");
                Main.getInstance().zombie.toSpawnTot = 0;
            }
        }
    }
}
