package whoim.teleport.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import whoim.teleport.Main;

public class TpaCommand extends Command {
  
  private final Main plugin;
  
  public TpaCommand(Main plugin){
    super("Tpaui", "Teleport a player to another player's position");
    this.setPermission("use.tpa");
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
    if(!sender.hasPermission("use.tpa")){
      sender.sendMessage(this.getPrefix()+"§c У вас недостаточно прав");
      return false;
    }
    if(sender instanceof Player){
      plugin.getFormUI().TpaForm((Player) sender);
      return true;
    }
    return true;
  }
}