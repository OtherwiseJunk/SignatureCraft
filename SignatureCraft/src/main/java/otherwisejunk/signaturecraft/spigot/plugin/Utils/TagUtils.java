package otherwisejunk.signaturecraft.spigot.plugin.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTItem;
import otherwisejunk.signaturecraft.spigot.plugin.Model.PlayerSignature;


public final class TagUtils{
    public static ItemStack AddSignatureToItem(ItemStack item, PlayerSignature Signature){        
        NBTItem nbtItem = new NBTItem(item);    
        ArrayList<PlayerSignature> signatures = new ArrayList<PlayerSignature>();        

        String signatureCSV = GetSignatureFromItem(nbtItem);
        // If we have an existing object for this tag, add the user to the existing tag!
        if(signatureCSV != ""){            
            signatures = ArrayListFromCSV(signatureCSV);
            
            // UNLESS they're already there, of course.
            if(!ContainsUserSignature(signatures, Signature.Username)){
                signatures.add(Signature);                
            }
            else{
                PlayerSignature signature = GetSignatureByUsername(signatures, Signature.Username);
                if(!signature.Message.equals(Signature.Message)){
                    signatures.remove(signature);
                    signature.Message = Signature.Message;
                    signatures.add(signature);
                }
            }
        }
        // Otherwise, make one with just them !!!
        else{
            signatures.add((Signature));
        }
        ArrayList<String> signatureLines = BuildSignatureLines(signatures);
        nbtItem = SetSignatureForItem(nbtItem, String.join(",", signatureLines));
        
        item = nbtItem.getItem();
        ItemMeta imd = item.getItemMeta();

        imd.setLore(GetSignatureLore(signatureLines));
        item.setItemMeta(imd);
        return item;
    }
    public static ArrayList<PlayerSignature> ArrayListFromCSV(String csv){     

        List<String> fixedLengthList = new ArrayList<String>(Arrays.asList(csv.split(",")));
        fixedLengthList.removeAll(TagConstants.EmptyListValues);

        List<PlayerSignature> signatures = new ArrayList<PlayerSignature>();
        for (String string : fixedLengthList) {
           if(string.contains(":")){
               String[] signatureLine = string.split(":");
               String signatureUsername = signatureLine[0].trim();
               String signatureMessage = signatureLine[1].trim();

               signatures.add(new PlayerSignature(signatureUsername,signatureMessage));
           }
           else{
               signatures.add(new PlayerSignature(string));
           }
        }
        return new ArrayList<PlayerSignature>(signatures);
    }
    public static ItemStack RemoveSignatureFromItem(ItemStack item, String username){
        NBTItem nbtItem = new NBTItem(item);
        ArrayList<PlayerSignature> signatures = new ArrayList<PlayerSignature>();     

        String signatureCSV = GetSignatureFromItem(nbtItem);
        // If we have an existing object for this tag, remove the user!
        if(signatureCSV != null){
            signatures = ArrayListFromCSV(signatureCSV);

            if(ContainsUserSignature(signatures, username)){
                PlayerSignature Signature = GetSignatureByUsername(signatures, username);
                
                signatures.remove(Signature);
                ArrayList<String> signatureLines = BuildSignatureLines(signatures);

                nbtItem = SetSignatureForItem(nbtItem, String.join(",",signatureLines));
        
                item = nbtItem.getItem();
                ItemMeta imd = item.getItemMeta();
                if(signatures.size() > 0){                    
                    imd.setLore(GetSignatureLore(signatureLines));                    
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
            if(!username.trim().isEmpty()){
                loreList.add(username);
            }
        }

        return loreList;
    }

    public static boolean ContainsUserSignature(final List<PlayerSignature> signatures, final String username){
        for(PlayerSignature signature : signatures){
            if(signature.Username.equals(username)){
                return true;
            }
        }
        return false;
    }    

    public static PlayerSignature GetSignatureByUsername(final List<PlayerSignature> signatures, final String username){
        for(PlayerSignature signature : signatures){
            if(signature.Username.equals(username)){
                return signature;
            }
        }
        return null;
    }

    public static ArrayList<String> BuildSignatureLines(ArrayList<PlayerSignature> list){        
        ArrayList<String> signatureStrings = new ArrayList<String>();
        
        for (PlayerSignature signature : list){
            signatureStrings.add(signature.toString());
        }

        return signatureStrings;
    }
}