import java.util.Stack;

/**
 * Write a description of class Player here.
 *
 * @author E Leblanc
 * @version 08/04/2025
 */
public class Player
{
    private String aName;
    private Room aCurrentRoom;
    private UserInterface aGui;
    private Stack<Room> aPreviousRooms;
    private ItemList aCurrentItems;
    private int aTotalWeight;
    private int aMaxWeight;
    private Room aBeamer;
    private int  aChargeCount;
    private Room aFreeRoom;
    /**
     * Constructor for objects of class Player
     */
    public Player(final String pName, final UserInterface pGui)
    {
        this.aName = pName;
        this.aPreviousRooms = new Stack<Room>();
        this.aCurrentItems = new ItemList();
        this.aGui = pGui;
        this.aTotalWeight = 0;
        this.aMaxWeight = 10;
        this.aChargeCount = 1;
    }

    /**
     * Sets the user interface for this player.
     *
     * @param pGui The user interface to set.
     */
    public void setGUI(final UserInterface pGui)
    {
        this.aGui = pGui;
    }


    /**
     * Returns the player's name.
     *
     * @return The player's name.
     */
    public String getName()
    {
        return this.aName;
    }

    /**
     * Returns the current room where the player is.
     *
     * @return The current Room.
     */
    public Room getCurrentRoom()
    {
        return this.aCurrentRoom;
    }

    /**
     * Sets the player's current room.
     *
     * @param pRoom The room to set as current.
     */
    public void setCurrentRoom(final Room pRoom)
    {
        this.aCurrentRoom = pRoom;
    }


    /**
     * Returns the stack of previously visited rooms.
     *
     * @return Stack of Rooms visited previously.
     */
    public Stack <Room> getPreviousRooms ()
    {
        return this.aPreviousRooms;
    }

    /**
     * Pushes a room onto the previous rooms stack.
     *
     * @param pRoom The room to push onto the stack.
     */

    public void setPreviousRoom(final Room pRoom){
        this.aPreviousRooms.push(pRoom);
    }


    /**
     * Removes the most recent room from the previous rooms stack.
     */
    public void removePreviousRoom()
    {
        this.aPreviousRooms.pop();
    }

    /**
     * Moves the player to a connected room in the specified direction.
     * The current room is pushed onto the previous rooms stack.
     *
     * @param pStringRoom The name/direction of the next room to move to.
     */
    public void goRoom (final String pStringRoom)
    {
        Room vNextRoom = this.aCurrentRoom.getExit(pStringRoom);
        this.aPreviousRooms.push(this.aCurrentRoom);
        this.aCurrentRoom = vNextRoom;
    }

    /**
     * Moves the player back to the most recently visited room, if any.
     * Prints a message if there is no previous room.
     */
    public void back()
    {
        if(this.aPreviousRooms.isEmpty()) {
            this.aGui.println("There is no previous room.");
            return;
        }
        this.aCurrentRoom = this.aPreviousRooms.pop();
    }


    /**
     * Returns the most recently visited room without removing it from the stack.
     *
     * @return The previous room.
     */
    public Room getPreviousRoom(){
        return this.aPreviousRooms.peek();
    }

    /**
     * Attempts to take an item from the current room and add it to the player's inventory.
     * Performs checks such as:
     * - If the item exists in the room.
     * - If the player has the shovel before taking the key.
     * - Weight limits based on the maximum carrying capacity.
     *
     * @param pCommand The name of the item to take.
     */
    public void take(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            this.aGui.println("Take what?");
            return;
        }
        String pNom = pCommand.getSecondWord();
        Item vItem = this.aCurrentRoom.getItem(pNom);
        if (vItem == null) {
            this.aGui.println("No such item here.");
            return;
        }
        if(pNom.equals("cake")) {
            this.aGui.println("You can't just store a piece of cake in your backpack...");
            return;
        }
        if (pNom.equals("key")) {
            if (!this.aCurrentItems.containsItem("shovel")) {
                this.aGui.println(" you can't dig up the key without the shovel !");
                return;
            }
        }
        int vTemp = this.aTotalWeight + vItem.getWeight();
        if (pNom.equals("rucksack")) this.aMaxWeight = 20;
        if ( vTemp > this.aMaxWeight) {
            this.aGui.println("You can't carry that item, drop an item, or find the bigger backpack ...");
            return;
        }

        this.aTotalWeight += vItem.getWeight();
        this.aCurrentItems.addItem(pNom, vItem);
        this.aCurrentRoom.removeItem(pNom);
        this.aGui.println("You took the " + pNom);

    }

    /**
     * Drops an item from the player's inventory into the current room.
     * Adjusts the total carried weight accordingly.
     *
     * @param pCommand The command "drop item" with the second word being the item to drop.
     */
    public void drop(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            this.aGui.println("Drop what?");
            return;
        }
        String pNom = pCommand.getSecondWord();
        Item vItem = this.aCurrentRoom.getItem(pNom);
        if(vItem == null) {
            this.aGui.println("No such item in the inventory");
            return;
        }
        this.aCurrentRoom.addItem(pNom, vItem);
        this.aCurrentItems.removeItem(pNom);
        this.aTotalWeight -= vItem.getWeight();
        this.aGui.println("You dropped " + pNom);
    }

    /**
     * Displays the contents of the player's backpack including the items,
     * current total weight carried, and maximum carrying capacity.
     * If the backpack is empty, notifies the player.
     */
    public void backpack()
    {
        if(this.aCurrentItems.isEmpty()) {
            this.aGui.println("You have no items in  your backpack");
            return;
        }
        StringBuilder vInv = new StringBuilder("Items in your backpack :");
        vInv.append(this.aCurrentItems.getItemKeys());
        this.aGui.println(vInv.toString());
        this.aGui.println("Backpack weight :" + this.aTotalWeight);
        this.aGui.println("You can only carry a weight of " + this.aMaxWeight);
    }

    /**
     * Checks whether the player currently has an item with the given name in their inventory.
     *
     * @param pItemName The name of the item to check.
     * @return true if the item is in the player's inventory, false otherwise.
     */
    public boolean hasItem(final String pItemName)
    {
        return this.aCurrentItems.containsItem(pItemName);
    }

    /**
     * Charges the beamer device with the player's current room, enabling teleportation later.
     * Decreases the available charge count.
     * If no charges are left, informs the player.
     */
    public void charge()
    {
        if (this.aChargeCount < 1) {
            this.aGui.println("You have no charges left, you can't use the beamer !");
            return;
        }
        this.aChargeCount--;
        this.aBeamer = this.aCurrentRoom;
        this.aGui.println("You just charged the beamer with the current room ! ");
        this.aGui.println("You can now teleport " + this.aBeamer.getDescription());
    }

    /**
     * Teleports the player to the room stored in the beamer.
     * If the beamer is not charged, instructs the player to charge it first.
     * After teleporting, clears the beamer charge and updates the UI.
     */
    public void teleport()
    {
        if (this.aBeamer == null) {
            this.aGui.println("You didn't charge the beamer before using it !");
            this.aGui.println("use the 'charge' command to charge the current room into the beamer");
        }

        this.aCurrentRoom = this.aBeamer;
        this.aBeamer = null;
        this.aGui.showImage( this.aCurrentRoom.getImageName());

        this.aGui.println("You just teleported " + this.aCurrentRoom.getDescription());
        this.aGui.println("Your beamer has been used, you can't use it anymore...");
    }

    /**
     * Returns the Room currently stored in the beamer (teleport target).
     *
     * @return The room stored in the beamer, or null if none.
     */
    public Room getBeamer()
    {
        return this.aBeamer;
    }


    /**
     * Prints the detailed description of the player's current room.
     */
    public void look()
    {
        this.aGui.println(this.aCurrentRoom.getLongDescription());
        return;
    }

    /**
     * Handles the player eating an item specified by the command.
     * If the item is "cake", special effects happen including infection and trapping.
     * Otherwise, informs the player they are no longer hungry.
     *
     * @param pCommand The command containing the item to eat.
     */
    public void eat(final Command pCommand)
    {
        if(!pCommand.hasSecondWord()) {
            this.aGui.println("Eat what?");
            return;
        }
        if(pCommand.getSecondWord().equals("cake")){
            this.aCurrentRoom.removeItem("cake");
            this.aGui.println("You ate the piece of cake!");
            this.aGui.println("Oop! the cake was infected and You got an alien virus...");
            this.aFreeRoom = this.aCurrentRoom;
            this.setCurrentRoom(this.aGui.getTrap());
            this.aGui.showImage(this.aGui.getTrap().getImageName());
            this.aGui.trapped();
            return;

        }

        this.aGui.println("You have eaten now and you are not hungry anymore.");
        return;
    }

    /**
     * Returns the room where the player was set free from an effect or trap.
     *
     * @return The free room reference.
     */

    public Room getFreeRoom()
    {
        return this.aFreeRoom;
    }
}
