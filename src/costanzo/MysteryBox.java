package costanzo;


import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MysteryBox extends BukkitRunnable {

    public static Utils util = new Utils();
    public static Inventory inv;
    public static ItemStack item;
    public MysteryBox(Inventory inv){
        this.inv = inv;
    }
    public void run(){
        Player p = Main.getInstance().box.p;
        if(Main.getInstance().box.p.getName() == null){
            return;
        }else{
            //Inventory inv = Main.getInstance().box.box.get(p.getInventory());
            if(Main.getInstance().box.cycle == 0){
                item = new ItemStack(Material.WOOD_AXE,1);
                inv.setItem(4,item);
                Main.getInstance().box.cycle++;
            }else{
                item = new ItemStack(Material.WOOD_SWORD,1);
                inv.setItem(4,item);
                Main.getInstance().box.cycle--;
            }
            //add more items here ( no hassle! )
        }
        Main.getInstance().box.mboxCounter++;
        if(Main.getInstance().box.mboxCounter > 5){
            p.getInventory().addItem(item);
            p.closeInventory();
            this.cancel();
            Main.getInstance().box.mboxCounter=0;
        }
    }

}
