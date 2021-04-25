package otherwisejunk.signaturecraft.spigot.plugin.Model;

public class PlayerSignature {
    public String Username;
    public String Message;

    public PlayerSignature(String username){
        Username = username;
        Message = "";
    }

    public PlayerSignature(String username, String message){
        Username = username;
        Message = message;
    }

    public String toString(){
        if(Message == null || Message == ""){
            return Username;
        }
        return String.format("%s: %s", Username, Message);
    }
}
