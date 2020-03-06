package red.man10.man10texture

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Man10Texture : JavaPlugin(),Listener {

    val prefix = "mtex"


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player)return false

        if (!sender.hasPermission("man10tex.op"))return false

        openInventory(sender,0)

        return false
    }

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(this,this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun openInventory(p:Player,n:Int){

        var number = n

        if (number <0)number = 0

        val inv = Bukkit.createInventory(null,54,prefix)

        for (i in number..number+44){
            val item = ItemStack(Material.DIAMOND_HOE,1)
            val meta = item.itemMeta
            meta.setCustomModelData(i)
            meta.setDisplayName("§a§lCustom Model Data = $i")
            meta.isUnbreakable = true
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS)
            item.itemMeta = meta
            inv.addItem(item)
        }
        //45 ~53
        val btn = ItemStack(Material.REDSTONE_BLOCK)
        var meta = btn.itemMeta
        meta.setDisplayName("§c§l-10000")
        btn.itemMeta = meta
        inv.setItem(45,btn)
        meta.setDisplayName("§c§l-1000")
        btn.itemMeta = meta
        inv.setItem(46,btn)
        meta.setDisplayName("§c§l-100")
        btn.itemMeta = meta
        inv.setItem(47,btn)
        meta.setDisplayName("§c§l-45")
        btn.itemMeta = meta
        inv.setItem(48,btn)

        btn.type = Material.LAPIS_BLOCK
        meta = btn.itemMeta
        meta.setDisplayName("§9§l+45")
        btn.itemMeta = meta
        inv.setItem(50,btn)
        meta.setDisplayName("§9§l+100")
        btn.itemMeta = meta
        inv.setItem(51,btn)
        meta.setDisplayName("§9§l+1000")
        btn.itemMeta = meta
        inv.setItem(52,btn)
        meta.setDisplayName("§9§l+10000")
        btn.itemMeta = meta
        inv.setItem(53,btn)

        val numberData = ItemStack(Material.COMPASS)
        val m = numberData.itemMeta
        m.setDisplayName(number.toString())
        numberData.itemMeta = m
        inv.setItem(49,numberData)

        p.openInventory(inv)

    }

    @EventHandler
    fun clickInv(e:InventoryClickEvent){

        val p = e.whoClicked as Player

        if (e.view.title != prefix)return

        e.isCancelled = true

        val number = e.inventory.getItem(49)!!.itemMeta.displayName.toInt()

        if (e.slot <45){
            p.inventory.addItem(e.currentItem)
            return
        }

        when(e.slot){
            45-> openInventory(p,number - 10000)
            46-> openInventory(p,number - 1000)
            47-> openInventory(p,number - 100)
            48-> openInventory(p,number - 45)

            50-> openInventory(p,number + 45)
            51-> openInventory(p,number + 100)
            52-> openInventory(p,number + 1000)
            53-> openInventory(p,number + 10000)

            else -> return
        }

    }
}