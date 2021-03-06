public final class TagUtils{
    public static boolean ItemHasSignatures(ItemStack item){
        return false;
    }
    public static void AddSignatureToItem(ItemStack item, Player signer){
        var nmsItem = CraftItemStack.asNMSCopy(item);
        
        if(ItemHasSignatures(item)){
            NBTTagCompound object = GetSignatureFromItem(item);
        }
    }
    public static string GetSignatureFromItem(ItemStack item){
        return new NBTTagCompound();
    }
}