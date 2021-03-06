package otherwisejunk.signaturecraft.spigot.plugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import otherwisejunk.signaturecraft.spigot.plugin.Utils.CommandConstants;
import otherwisejunk.signaturecraft.spigot.plugin.Utils.TagUtils;

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
            return true;
        }

        final Player player = (Player) sender;
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        //If the triggering player doesn't have an item in hand we don't care, either.
        if (itemInHand == null || itemInHand.getType() == Material.AIR) {            
            return true;
        }

        if(command.getName().equalsIgnoreCase(CommandConstants.SignatureCommand)){
            getLogger().info("Is itemInHand null? "+String.valueOf(itemInHand == null));
                ItemStack modifiedItem = TagUtils.AddSignatureToItem(itemInHand, player, getLogger());
                player.getInventory().remove(itemInHand);
                player.getInventory().addItem(modifiedItem);
                return true;
        }
        else if(command.getName().equalsIgnoreCase(CommandConstants.RemoveSignatureCommand)){            
            ItemStack modifiedItem = TagUtils.RemoveSignatureFromItem(itemInHand, player, getLogger());
            player.getInventory().remove(itemInHand);
                player.getInventory().addItem(modifiedItem);
                return true;            
        }
        return true;
    }

}