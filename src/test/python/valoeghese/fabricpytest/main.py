from java.lang import System
from net.minecraft.item import Item
from net.minecraft.util.registry import Registry

System.out.println("Hello, Fabric World!")

item_settings = Item.Settings().maxCount(4)

custom_item = Item(item_settings)

Registry.register(Registry.ITEM, "fabricpy:item", custom_item)
