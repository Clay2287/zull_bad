
/**
 * This class is part of the "The Lost City" application. 
 *  "The Lost City" is a very simple, text based adventure game.
 *  
 *  This class manages every Item object in the game with 
 *
 * @author  Eden Leblanc
 * @version 1.2 (   05/04/2025  )
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String aDescription;
    private int aWeight;

    /**
     * Constructor for the item class
     */
    public Item(final String pDescription, final int pWeight)
    {
        // initialise instance variables
        this.aDescription = pDescription;
        this.aWeight = pWeight;
    }
    
    /**
     * returns the item's description
     */
    public String getDescription()
    {
        return this.aDescription;
    }
    
    /**
     * returns the item's description its weight
     */
    public String itemDescription()
    {
        return "There is " + this.aDescription + '\n' + "It's weight : " + this.aWeight;
    }

    /**
     *
     * @return the weight of the item.
     */
    public int getWeight()
    {
        return this.aWeight;
    }

}
