package costanzo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class Utils {

    // to add color, and a method with a specific player
    public static void broadcast(ChatColor c, String message){
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(c + message);
        }
    }

    public static void broadcast(ChatColor c, String message, Player p){
        p.sendMessage(c + message);
    }

    public static long readFile(){
        try{
            File x = new File("date.txt");
            Scanner scn = new Scanner(x);
            while(scn.hasNextLine()){
                String data = scn.nextLine();
                return Long.parseLong(data);
            }

        }catch(Exception e){
            System.out.println(e);
        }
        return 0L;
    }

    public static void writeFile(long current){
        try{
            long epochTime = current;
            FileWriter fw = new FileWriter("date.txt");
            fw.write("" + epochTime);
            fw.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static long getCurrentTime(){
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        String currentTime = sdf.format(today);
        try{
            Date date = sdf.parse(currentTime);
            long epochTime = date.getTime();
            return epochTime;
        }catch(Exception e){
            System.out.println(e);
        }
        return 0L;
    }
}
