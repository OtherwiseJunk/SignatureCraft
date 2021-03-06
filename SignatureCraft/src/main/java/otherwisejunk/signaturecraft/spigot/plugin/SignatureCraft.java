package otherwisejunk.signaturecraft.spigot.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class SignatureCraft extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("[SignatureCraft] Mod successfully enabled.");
    }
    @Override
    public void onDisable() {
        getLogger().info("[SignatureCraft] Mod Disabled.");
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
     final String label, final String[] args){
        //If the triggering message is from the Console we don't care.
        if (! (sender instanceof Player)) {
            sender.sendMessage(this.getMessage("ConsoleSender"));
            return true;
        }

        final Player player = (Player) sender;
        final ItemStack itemInHand = player.getItemInHand();
        //If the triggering player doesn't have an item in hand we don't care, either.
        if (itemInHand == null) {            
            return true;
        }
    }

}