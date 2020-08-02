package uk.co.ciaranmoran.chestsorter;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class ChestListener implements Listener {

    private String randomString() {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private ItemStack[] sortItems(ItemStack[] items) {
        Arrays.sort(items, (a,b) -> {
            if (a == null && b == null) {
                return 0;
            } else if (a == null) {
                return 1;
            } else if (b == null) {
                return -1;
            }

            if (a.isSimilar(b)) {
                return b.getAmount() - a.getAmount();
            } else {
                return a.getType().compareTo(b.getType());
            }
        } );
        return items;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.LEFT_CLICK_BLOCK
                && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK
                && e.getClickedBlock() != null
                && e.getClickedBlock().getType() == Material.CHEST) {
            Chest chest = (Chest) e.getClickedBlock().getState();
            if (chest.getInventory().getViewers().size() > 0) {
                e.getPlayer().sendMessage("Can't sort an open chest!");
            } else {
                chest.setLock(randomString());
                chest.getInventory().setContents(sortItems(chest.getInventory().getContents()));
                chest.setLock(null);
            }

        }
    }
}
