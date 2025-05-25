import java.util.HashMap;
import java.util.Set;

/**
 * Classe Room - un lieu du jeu d'aventure Zuul.
 *
 * @author E. LEBLANC
 * @version 11-02-2025
 */
public class Room
{
    private String aDescription;
    private HashMap<String, Room> aExits;
    private ItemList aItems;
    private String aImageName;
    
    /**
     * Constructeur pour la classe Room.
     *
     * @param pDescription La description de la pièce.
     */
    public Room(final String pDescription, final String pImage)
    {   
        this.aDescription = pDescription;
        this.aExits = new HashMap<String, Room>();
        this.aItems = new ItemList();
        this.aImageName = pImage;
    }
    
    /**
     * Retourne la description de la pièce.
     *
     * @return La description de la pièce.
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    /**
     * Définit une sortie pour cette pièce.
     *
     * @param pDirection La direction de la sortie (ex: "north").
     * @param pNeighbor  La pièce associée à cette sortie.
     */
    public void setExit(final String pDirection,final Room pNeighbor)
    {
        aExits.put(pDirection, pNeighbor);
    }
    
    public boolean isExit(final Room pRoom)
    {
        if(this.aExits.containsValue(pRoom)) {
            return true;
        }
        return false;
    }
    /**
     * Retourne la sortie correspondant à la direction donnée.
     *
     * @param pDirection La direction de la sortie.
     * @return La pièce associée ou null si aucune sortie n'existe.
     */
    public Room getExit(final String pDirection)
    {
        return (aExits.get(pDirection));
    }
    
    /**
     * Retourne une chaîne contenant les sorties disponibles.
     *
     * @return Les directions disponibles sous forme de chaîne.
     */
    public String getExitString()
    {
        StringBuilder vChaine = new StringBuilder("Exits : ");
        Set<String> vKey = this.aExits.keySet();
        for( String vExits : vKey) {
            vChaine.append(vExits).append(" ");
        }
        return vChaine.toString();
    }
    
    /**
     * returns the Description of the current Item.
     */
    public String getItemString ()
    {
        StringBuilder returnString = new StringBuilder("Items: ");
        String itemNames = this.aItems.getItemKeys();
    
        if (itemNames.isEmpty()) {
            returnString.append("None");
        } else {
            returnString.append(itemNames);
        }
    
        return returnString.toString();
    }
    
    /**
     * returns the Description of the current Room.
     */
    public String getLongDescription()
    {
        StringBuilder vDescription = new StringBuilder("You are " + this.getDescription() + ".\n" + this.getExitString() + ".\n");
        vDescription.append(this.aItems.getItemKeys());
        return vDescription.toString();
    }
    
    /**
     * returns the name of the image file associated to the current Room.
     */
    public String getImageName()
    {
        return this.aImageName;
    }
    
    /**
     * Links an Item to the current room
     * @param pNom the name of the item
     * @param pItem constructs said Item with its description and weight
     */
    public void addItem(final String pNom, final Item pItem)
    {
        this.aItems.addItem(pNom, pItem);
    }
    
    /**
     * @param pNom the name of the item
     * @return the name of the Item.
     *
     */
    public Item getItem(final String pNom)
    {
        return this.aItems.getItem(pNom);
    }

    /**
     * removes the item from the Room
     * @param pNom the name of the item
     *
     */
    public void removeItem(final String pNom)
    {
        this.aItems.removeItem(pNom);
    }

    /**
     * @return the list of items in the room.
     *
     */
    public ItemList getItemList()
    {
        return this.aItems;
    }
} // Room
