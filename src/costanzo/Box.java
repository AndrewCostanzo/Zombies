package costanzo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Box {

    //Box
    public Map<String,Inventory> box = new HashMap<String,Inventory>();
    public int cycle = 0;
    public int mboxCounter = 0;
    public  Player p;
    public boolean canOpen = true;
    public Utils util;

    public Box(Utils util){
        this.util = util;
    }

    public void openBox(Player p){
        Inventory inv = null;

        long before = util.readFile();
        long current = util.getCurrentTime();
        long diff = current-before;

        if(diff > 10000) {
            canOpen = true;
            if (box.get(p.getName()) != null) {
                inv = box.get(p.getName());
                MysteryBox mbox = new MysteryBox(inv);
                this.p = p;
                mbox.runTaskTimer(Main.getInstance(),0,20);
            } else {
                inv = Bukkit.createInventory(null, 9, "Mystery Box");
                box.put(p.getName(), inv);
                MysteryBox mbox = new MysteryBox(inv);
                this.p = p;
                mbox.runTaskTimer(Main.getInstance(),0,20);
            }
            p.openInventory(inv);
            util.writeFile(current);
        }else{
            util.broadcast(ChatColor.RED,"Cannot open the box right now, someone is inside.");
        }
    }
}
