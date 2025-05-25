import java.util.HashMap;
import java.util.Set;

/**
 *  This class is part of the "The Lost City".
 *  "The Lost City" is a very simple, text based adventure game.
 * 
 *  This class manages all the items that a player has in his inventory, but also the items that a room contains.
 * 
 * @author  E. Leblanc 
 * @version 09/04/2025
 */
public class ItemList
{
    private HashMap<String, Item> aItems;

    public ItemList()
    {
        this.aItems = new HashMap<>();
    }

    /**
     * Ajoute un item à la liste.
     * @param pName Le nom de l'item.
     * @param pItem L'objet Item à ajouter.
     */
    public void addItem(final String pName, final Item pItem)
    {
        this.aItems.put(pName, pItem);
    }

    /**
     * Récupère un item par son nom.
     * @param pName Le nom de l'item.
     * @return L'item trouvé, ou null si l'item n'existe pas.
     */
    public Item getItem(final String pName)
    {
        return this.aItems.get(pName);
    }

    /**
     * permet de retourner les descriptions d'item
     * @return Set<String>
     */
    public Set<String> getKeys()
    {
        return this.aItems.keySet();
    }
    
    /**
     * Supprime un item par son nom.
     * @param pName Le nom de l'item à supprimer.
     */
    public void removeItem(final String pName)
    {
        this.aItems.remove(pName);
    }

    /**
     * Vérifie si un item existe dans la liste.
     * @param pName Le nom de l'item.
     * @return true si l'item existe, false sinon.
     */
    public boolean containsItem(final String pName)
    {
        return this.aItems.containsKey(pName);
    }

    /**
     * Retourne une chaîne contenant tous les noms des aItems.
     *  explication : utilisation d'un stringbuilder en interne
     * @return Une liste des noms des aItems sous forme de chaîne.
     */
    public String getItemKeys()
    {
        return String.join(", ", aItems.keySet());
    }

    /**
     *
     * @return true si il n'y a pas d'items, false si il y en a
     */
    public boolean isEmpty()
    {
        return this.aItems.isEmpty();
    }
}
