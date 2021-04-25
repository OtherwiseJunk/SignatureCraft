package otherwisejunk.signaturecraft.spigot.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import otherwisejunk.signaturecraft.spigot.plugin.Model.PlayerSignature;
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
        if (itemInHand == null || itemInHand.getItemMeta() == null) {            
            return true;
        }        
        
        if(command.getName().equalsIgnoreCase(CommandConstants.SignatureCommand)){
            PlayerSignature signature = new PlayerSignature(sender.getName());
            
            if (args.length > 0){
                signature.Message = String.join(" ",args);
                if(signature.Message.length() > CommandConstants.MaxMessageLength){
                    sender.sendMessage(String.format("Message signature may not exceed %d characters. Please use a smaller message.",CommandConstants.MaxMessageLength));
                    return false;
                }               
            }
            
            getLogger().info("Is itemInHand null? "+String.valueOf(itemInHand == null));
                ItemStack modifiedItem = TagUtils.AddSignatureToItem(itemInHand, signature);
                player.getInventory().remove(itemInHand);
                player.getInventory().addItem(modifiedItem);
                return true;
        }
        else if(command.getName().equalsIgnoreCase(CommandConstants.RemoveSignatureCommand)){ 
            ItemStack modifiedItem = TagUtils.RemoveSignatureFromItem(itemInHand, sender.getName());
            player.getInventory().remove(itemInHand);
                player.getInventory().addItem(modifiedItem);
                return true;            
        }
        return true;
    }

}