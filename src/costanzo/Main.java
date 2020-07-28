package costanzo;

import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class Main extends JavaPlugin implements Listener{

    //Round
    public static int round = 0;

    //Helper classes
    public static Utils util = new Utils();
    public static Main instance;

    public static Zombies zombie;
    public static Dogs dogs;

    public static Box box = new Box(util);
    public static Spawns spawn = new Spawns();

    public static Main getInstance(){
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;
        getServer().getPluginManager().registerEvents(this,this);
        getLogger().info("Tutorial has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Tutorial has been disabled");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        World w = Bukkit.getWorld("world");
        if(!(sender instanceof Player)){
            sender.sendMessage("Only players may execute this command");
            return true;
        }

        Player p = (Player)sender;
        String command = cmd.getName().toLowerCase();

        Game x = new Game();
        if(command.equals("start")){
            round=1; //set initial round
            zombie = new Zombies(util, w);
            dogs =  new Dogs(w);
            x.runTaskTimer(getInstance(),0,200);
        }

        if(command.equals("stopz")){
            try { Bukkit.getScheduler().cancelTasks(getInstance()); }
            catch(Exception e){ util.broadcast(ChatColor.RED, "Cannot stop lobby, not started."); }
        }

        if(command.equals("mysterybox")){
            box.openBox(p);
        }
        return false;
    }



    /* Spawn Main
     * Spawns zombies based on round number, spawns half at a time
     *
     * Input: Round
     * Output: List of zombies
     */
    public void spawn(int r){
        if(r%5 == 0){
            //dogs.spawnDogs(r);
            zombie.isComplete = false;
        }else{
            zombie.spawnZombies(r);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block block = e.getClickedBlock();
            if(block.getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) block.getState();
                String[] lines = sign.getLines();
                if (lines[0].equalsIgnoreCase("Wood Sword")) {
                    util.broadcast(ChatColor.GOLD, e.getPlayer().getDisplayName() + " is trying to buy a wooden sword", e.getPlayer());
                    PlayerInventory inv = e.getPlayer().getInventory();

                    if (e.getPlayer().getInventory().contains(Material.ROTTEN_FLESH, 5)) {
                        util.broadcast(ChatColor.GREEN, "Buy was successful", e.getPlayer());
                        e.getPlayer().getInventory().removeItem(new ItemStack(Material.ROTTEN_FLESH, 5));
                        inv.addItem(new ItemStack(Material.WOOD_SWORD, 1));
                    } else {
                        util.broadcast(ChatColor.RED, "Item Buy was unsuccessful", e.getPlayer());
                    }
                } else if (lines[0].equalsIgnoreCase("Door 1")) {

                    if (e.getPlayer().getInventory().contains(Material.ROTTEN_FLESH, 10)) {
                        util.broadcast(ChatColor.GREEN, "Door Buy was successful", e.getPlayer());
                        e.getPlayer().getInventory().removeItem(new ItemStack(Material.ROTTEN_FLESH, 10));
                        sign.setType(Material.AIR);
                        List<Block> list = spawn.getDoor1();
                        for (Block b : list) {
                            b.setType(Material.AIR);
                        }
                        zombie.spawn.doorOne = true;
                    } else {
                        util.broadcast(ChatColor.RED, "Door Buy was unsuccessful", e.getPlayer());
                    }

                } else if (lines[0].equalsIgnoreCase("Mystery Box")) {
                    util.broadcast(ChatColor.GOLD, "Status of box: " + box.canOpen);

                    if (e.getPlayer().getInventory().contains(Material.ROTTEN_FLESH, 8)) {
                        if (box.canOpen != false) {
                            util.broadcast(ChatColor.GREEN, "Box Buy was successful", e.getPlayer());
                            e.getPlayer().getInventory().removeItem(new ItemStack(Material.ROTTEN_FLESH, 8));
                            e.getPlayer().performCommand("mysterybox");
                        } else {
                            e.getPlayer().performCommand("mysterybox");
                        }
                        box.canOpen = false;
                    } else {
                        util.broadcast(ChatColor.RED, "Box Buy was unsuccessful", e.getPlayer());
                    }
                } else {
                    util.broadcast(ChatColor.RED, "You must wait your turn with the box");
                }
            }
        }
    }
}
