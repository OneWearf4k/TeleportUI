package whoim.teleport.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import whoim.teleport.Main;

public class TpCommand extends Command {
  
  private final Main plugin;
  
  public TpCommand(Main plugin){
    super("Tp", "Teleport a player to another player's position");
    this.setPermission("use.tp");
    this.plugin = plugin;
  }
  
  public String getPrefix(){
    return plugin.getPrefix();
  }
  
  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args){
    if(!this.testPermission(sender)){
      return false;
    }
    if(!sender.hasPermission("use.tp")){
      sender.sendMessage(this.getPrefix()+"§c У вас недостаточно прав!");
      return false;
    }
    if(sender instanceof Player){
      plugin.getFormUI().TpForm((Player) sender);
      return true;
    }
    return true;
  }
}