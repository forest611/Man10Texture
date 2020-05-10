package red.man10.man10texture

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Damageable
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.java.JavaPlugin
import java.lang.Exception

class Man10Texture : JavaPlugin(),Listener {

    val prefix = "mtex"
    val mapCmd = HashMap<Player,Int>()
    val mapDamage = HashMap<Player,Int>()


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player)return false

        if (!sender.hasPermission("man10tex.op"))return false

        if (args.isEmpty()){
            openInventory(sender,mapCmd[sender]?:0,Material.DIAMOND_HOE)
            return true
        }

        if (args[0] == "hand"){
            openInventory(sender,0,sender.inventory.itemInMainHand.type)
            return true
        }

        //手持ちアイテムのcmdチェック
        if (args[0] == "get"){
            try {
                val id = sender.inventory.itemInMainHand.itemMeta.customModelData
                sender.sendMessage("§a§l$id")
            }catch (e:Exception){
                logger.info(e.message)
                sender.sendMessage("§a§l0")
            }
            return true
        }

        if (args[0] == "damage") {

            openDamage(sender, mapDamage[sender]?:0)

        }

        return false
    }

    override fun onEnable() {
        // Plugin startup logic
        server.pluginManager.registerEvents(this,this)

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    fun openInventory(p:Player,n:Int,type:Material){

        var number = n

        if (number <0)number = 0

        val inv = Bukkit.createInventory(null,54,prefix + number)

        for (i in number..number+44){
            val item = ItemStack(type,1)
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
        meta.setDisplayName("§c§l-50p")
        btn.itemMeta = meta
        inv.setItem(45,btn)
        meta.setDisplayName("§c§l-10p")
        btn.itemMeta = meta
        inv.setItem(46,btn)
        meta.setDisplayName("§c§l-5p")
        btn.itemMeta = meta
        inv.setItem(47,btn)
        meta.setDisplayName("§c§l-1p")
        btn.itemMeta = meta
        inv.setItem(48,btn)

        btn.type = Material.LAPIS_BLOCK
        meta = btn.itemMeta
        meta.setDisplayName("§9§l+1p")
        btn.itemMeta = meta
        inv.setItem(50,btn)
        meta.setDisplayName("§9§l+5p")
        btn.itemMeta = meta
        inv.setItem(51,btn)
        meta.setDisplayName("§9§l+10p")
        btn.itemMeta = meta
        inv.setItem(52,btn)
        meta.setDisplayName("§9§l+50p")
        btn.itemMeta = meta
        inv.setItem(53,btn)

        val numberData = ItemStack(Material.COMPASS)
        val m = numberData.itemMeta
        m.setDisplayName(number.toString())
        m.persistentDataContainer.set(NamespacedKey(this,"type"), PersistentDataType.STRING,"cmd")
        numberData.itemMeta = m
        inv.setItem(49,numberData)

        p.openInventory(inv)

    }

    fun openDamage(p:Player,n:Int){

        var number = n

        if (number <0)number = 0
        if (number >1530)number = 1530

        val inv = Bukkit.createInventory(null,54,prefix + number)

        for (i in number..number+44){

            if (i >= 1562)break
            val item = ItemStack(Material.DIAMOND_HOE,1,i.toShort())
            val meta = item.itemMeta
            meta.setDisplayName("§a§lDamage = $i")
            meta.lore = mutableListOf("§a§l${i.toDouble() / 1562.0}")
            meta.isUnbreakable = true
//            if (meta is Damageable){
//                (meta as Damageable).damage(i.toDouble())
//            }
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS)
            item.itemMeta = meta
            inv.addItem(item)
        }
        //45 ~53
        val btn = ItemStack(Material.REDSTONE_BLOCK)
        var meta = btn.itemMeta
//        meta.setDisplayName("§c§l-50p")
//        btn.itemMeta = meta
//        inv.setItem(45,btn)
        meta.setDisplayName("§c§l-10p")
        btn.itemMeta = meta
        inv.setItem(46,btn)
        meta.setDisplayName("§c§l-5p")
        btn.itemMeta = meta
        inv.setItem(47,btn)
        meta.setDisplayName("§c§l-1p")
        btn.itemMeta = meta
        inv.setItem(48,btn)


        btn.type = Material.LAPIS_BLOCK
        meta = btn.itemMeta
        meta.setDisplayName("§9§l+1p")
        btn.itemMeta = meta
        inv.setItem(50,btn)
        meta.setDisplayName("§9§l+5p")
        btn.itemMeta = meta
        inv.setItem(51,btn)
        meta.setDisplayName("§9§l+10p")
        btn.itemMeta = meta
        inv.setItem(52,btn)
//        meta.setDisplayName("§9§l+50p")
//        btn.itemMeta = meta
//        inv.setItem(53,btn)

        val numberData = ItemStack(Material.COMPASS)
        val m = numberData.itemMeta
        m.setDisplayName(number.toString())
        m.persistentDataContainer.set(NamespacedKey(this,"type"), PersistentDataType.STRING,"damage")
        numberData.itemMeta = m
        inv.setItem(49,numberData)

        p.openInventory(inv)

    }

    @EventHandler
    fun clickInv(e:InventoryClickEvent){

        val p = e.whoClicked as Player

        if (e.view.title.indexOf(prefix) != 0)return

        if (!p.hasPermission("man10tex.op"))return

        e.isCancelled = true

        val number = e.inventory.getItem(49)!!.itemMeta.displayName.toInt()

        val item = e.inventory.getItem(0)!!

        if (e.slot <45){
            if (e.slotType != InventoryType.SlotType.CONTAINER)return
            p.inventory.addItem(e.currentItem)
            return
        }

        val type = (e.inventory.getItem(49)?:return).itemMeta.persistentDataContainer[NamespacedKey(this,"type"), PersistentDataType.STRING]!!

        if (type == "damage"){
            when(e.slot){
                46-> openDamage(p,number - 450)
                47-> openDamage(p,number - 225)
                48-> openDamage(p,number - 45)

                50-> openDamage(p,number + 45)
                51-> openDamage(p,number + 225)
                52-> openDamage(p,number + 450)

                else -> return
            }

            return
        }

        when(e.slot){
            45-> openInventory(p,number - 2250,item.type)
            46-> openInventory(p,number - 450,item.type)
            47-> openInventory(p,number - 225,item.type)
            48-> openInventory(p,number - 45,item.type)

            50-> openInventory(p,number + 45,item.type)
            51-> openInventory(p,number + 225,item.type)
            52-> openInventory(p,number + 450,item.type)
            53-> openInventory(p,number + 2250,item.type)

            else -> return
        }

    }

    @EventHandler
    fun close(e:InventoryCloseEvent){

        val p = e.player as Player

        if (e.view.title.indexOf(prefix) != 0)return

        if (!p.hasPermission("man10tex.op"))return

        val number = e.inventory.getItem(49)!!.itemMeta.displayName.toInt()

        if ((e.inventory.getItem(49)?:return).itemMeta.persistentDataContainer[NamespacedKey(this,"type"), PersistentDataType.STRING]!! == "damage"){
            mapDamage[p] = number
            return
        }

        mapCmd[p] = number

    }
}