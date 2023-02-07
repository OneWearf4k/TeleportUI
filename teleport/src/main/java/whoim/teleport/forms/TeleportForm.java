package whoim.teleport.forms;

import whoim.teleport.Main;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.network.protocol.ModalFormResponsePacket;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.response.FormResponseData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.nukkit.level.Location;

public class TeleportForm implements Listener {
  
  private Main plugin;
  
  public TeleportForm(Main plugin){
    this.plugin = plugin;
  }
  
  public Main getPlugin(){
    return plugin;
  }
  
  public String getPrefix(){
    return plugin.getPrefix();
  }
  
  static int TPFORM = 0xAAA0001;
  static int TPAFORM = 0xAAA0002;
  static int CONFIRMUIFORM = 0xAAA0003;
  
  public void TpForm(Player p){
    FormWindowCustom window = new FormWindowCustom("Tp");
    for(Player pp : getPlugin().getServer().getOnlinePlayers().values()){
      List<String> pl = Arrays.asList(pp.getName());
      window.addElement(new ElementDropdown("Выберите игрока:", pl));
        p.showFormWindow(window, TPFORM);
    }
  }
  
  public void TpaForm(Player p){
    FormWindowCustom window = new FormWindowCustom("Tpa");
    for(Player pp : getPlugin().getServer().getOnlinePlayers().values()){
      List<String> pl = Arrays.asList(pp.getName());
      window.addElement(new ElementDropdown("Выберите игрока:", pl));
        p.showFormWindow(window, TPAFORM);
    }
  }
  
  public void ConfirmTpa(Player p, String pname){
    FormWindowModal window = new FormWindowModal(pname, "Вы хотите, чтобы "+pname+" телепортировался к вам?", "Принять", "Отклонить");
    p.showFormWindow(window, CONFIRMUIFORM); 
  }
  
  @EventHandler
  public void handleFormResponse(PlayerFormRespondedEvent e){
    Player p = e.getPlayer();
    if(e.getWindow() instanceof FormWindowCustom){
      FormWindowCustom window = (FormWindowCustom) e.getWindow();
      if(e.getFormID() == TPFORM){
        if(e.wasClosed()){
          return;
        }
        String response = window.getResponse().getDropdownResponse(0).getElementContent();
        Player player = getPlugin().getServer().getPlayer(response);
        if(player instanceof Player){
          p.teleport((Location)player);
          p.sendMessage(this.getPrefix()+"§a Телепортация к§e "+player.getName()+"§a успешно выполнена");
          return;
        }else{
          p.sendMessage(this.getPrefix()+"§c Игрок не в сети");
          return;
        }
      }
      if(e.getFormID() == TPAFORM){
        if(e.wasClosed()){
          return;
        }
        String response = window.getResponse().getDropdownResponse(0).getElementContent();
        Player player = getPlugin().getServer().getPlayer(response);
        if(player instanceof Player){
          ConfirmTpa(player, p.getName());
          p.sendMessage(this.getPrefix()+"§e Вы успешно отправили запрос на телепортацию");
          return;
        }else{
          p.sendMessage(this.getPrefix()+"§c Игрок не в сети");
          return;
        }
      } 
    }
    if(e.getWindow() instanceof FormWindowModal){
      FormWindowModal window = (FormWindowModal) e.getWindow();
      if(e.getFormID() == CONFIRMUIFORM){
        if(e.wasClosed()){
          return;
        }
        if(window.getResponse().getClickedButtonId() == 0){
          String pname = window.getTitle();
          Player pp = getPlugin().getServer().getPlayer(pname);
          p.teleport((Location)pp);
          p.sendMessage(this.getPrefix()+"§e "+pp.getName()+"§a Вы успешно приняли телепортацию");
          pp.sendMessage(this.getPrefix()+"§a Вы успешно телепортировались");
        }
        if(window.getResponse().getClickedButtonId() == 1){
          String pname = window.getTitle();
          Player pp = getPlugin().getServer().getPlayer(pname);
          pp.sendMessage(this.getPrefix()+"§e Вам было отказано в телепортации");
          p.sendMessage(this.getPrefix()+"§e Вы отказали в телепортации");
          return;
        }
      }
    }
  }
}