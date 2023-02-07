package whoim.teleport;

import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.Plugin;

import whoim.teleport.forms.TeleportForm;
import whoim.teleport.commands.TpaCommand;
import whoim.teleport.commands.TpCommand;

public class Main extends PluginBase {
  
  private TeleportForm form;
  private String prefix = "§f[§6Teleport§f]§r ";
  
  public void onEnable(){
    this.form = new TeleportForm(this);
    getServer().getPluginManager().registerEvents(new TeleportForm(this), this);
    getServer().getCommandMap().register("Main", new TpCommand(this));
    getServer().getCommandMap().register("Main", new TpaCommand(this));
    getLogger().info("Teleport Enable!");
  }
  
  public String getPrefix(){
    return this.prefix;
  }
  
  public TeleportForm getFormUI(){
    return form;
  }
}