package fr.rammex.prisonUltimateCool.manager;

import fr.rammex.prisonUltimateCool.models.ItemPage;
import org.bukkit.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemPageManager {
    private static List<ItemPage> itemsPageList = new ArrayList<>();

    public static void addItemPage(ItemPage itemPage){
        itemsPageList.add(itemPage);
    }

    public static List<ItemPage> getItemsPageList(){
        return itemsPageList;
    }

    // TODO : remplacer par le futur système InventoryPage
    public static List<ItemPage> getItemsPageFromInventoryName(String inventoryName){
        // les ids sont faite de ce style : nomdumenu_nomitem
        List<ItemPage> items = new ArrayList<>();

        for(ItemPage itemPage : itemsPageList){
            if (itemPage.getId().contains(inventoryName)){
                items.add(itemPage);
            }
        }

        return items;
    }
}
