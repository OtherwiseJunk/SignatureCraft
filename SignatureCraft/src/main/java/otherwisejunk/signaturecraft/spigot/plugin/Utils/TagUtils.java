package otherwisejunk.signaturecraft.spigot.plugin.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;


public final class TagUtils{
    public static ItemStack AddSignatureToItem(ItemStack item, Player signer, Logger logger){
        logger.info("Is itemStack null? "+String.valueOf(item == null));
        NBTItem nbtItem = new NBTItem(item);    
        ArrayList<String> usernames = new ArrayList<String>();
        String signerName = signer.getName();
        logger.info("Signer's Name: "+signerName);

        String signatureCSV = GetSignatureFromItem(nbtItem);
        logger.info("SignatureCSV: "+signatureCSV);
        // If we have an existing object for this tag, add the user to the existing tag!
        if(signatureCSV != null){            
            usernames = ArrayListFromCSV(signatureCSV);
            
            // UNLESS they're already there, of course.
            if(!usernames.contains(signerName)){
                usernames.add(signerName);                
            }
        }
        // Otherwise, make one with just them !!!
        else{
            usernames.add((signerName));
        }
        
        nbtItem = SetSignatureForItem(nbtItem, String.join(",", usernames));
        
        item = nbtItem.getItem();
        ItemMeta imd = item.getItemMeta();

        imd.setLore(GetSignatureLore(usernames));
        item.setItemMeta(imd);
        return item;
    }
    public static ArrayList<String> ArrayListFromCSV(String csv){        
        List<String> fixedLengthList = Arrays.asList(csv.split(","));
        return new ArrayList<String>(fixedLengthList);
    }
    public static ItemStack RemoveSignatureFromItem(ItemStack item, Player signer, Logger logger){
        NBTItem nbtItem = new NBTItem(item);
        ArrayList<String> usernames = new ArrayList<String>();     


        String signatureCSV = GetSignatureFromItem(nbtItem);
        logger.info("SignatureCSV: "+signatureCSV);
        // If we have an existing object for this tag, remove the user!
        if(signatureCSV != null){
            usernames = ArrayListFromCSV(signatureCSV);

            if(usernames.contains(signer.getName())){
                usernames.remove(signer.getName());
                nbtItem = SetSignatureForItem(nbtItem, String.join(",",usernames));
        

                item = nbtItem.getItem();
                ItemMeta imd = item.getItemMeta();
                if(usernames.size() > 0){
                    
                    imd.setLore(GetSignatureLore(usernames));                    
                }
                else{
                    imd.setLore(null);
                }
                item.setItemMeta(imd);
            }
        }        
        
        return item;
    }

    public static String GetSignatureFromItem(NBTItem nbtItem){
        return nbtItem.getString(TagConstants.SignaturecraftTag);
    }

    public static NBTItem SetSignatureForItem(NBTItem nbtItem, String tagCSV){
        nbtItem.setString(TagConstants.SignaturecraftTag,tagCSV);
        return nbtItem;
    }

    public static List<String> GetSignatureLore(List<String> usernames){
        List<String> loreList = new ArrayList<String>();
        loreList.add("Signed by:");

        for (String username: usernames){
            if(!username.isBlank() && !username.isEmpty()){
                loreList.add(username);
            } 
        }

        return loreList;
    }
}