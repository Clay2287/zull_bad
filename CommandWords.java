import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration table of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kolling and David J. Barnes + D. Bureau + E. Leblanc
 * @version 2008.03.30 + 2019.09.25
 */
public class CommandWords
{
    // a constant array that will hold all valid command words
    private HashMap<String, CommandWord> aValidCommands;
    

    public CommandWords()
    {
        this.aValidCommands = new HashMap<>();
        this.aValidCommands.put("go",       CommandWord.go);
        this.aValidCommands.put("help",     CommandWord.help);
        this.aValidCommands.put("quit",     CommandWord.quit);
        this.aValidCommands.put("eat",      CommandWord.eat);
        this.aValidCommands.put("look",     CommandWord.look);
        this.aValidCommands.put("back",     CommandWord.back);
        this.aValidCommands.put("test",     CommandWord.test);
        this.aValidCommands.put("take",     CommandWord.take);
        this.aValidCommands.put("drop",     CommandWord.drop);
        this.aValidCommands.put("backpack", CommandWord.backpack);
        this.aValidCommands.put("teleport", CommandWord.teleport);
        this.aValidCommands.put("charge",   CommandWord.charge);
    }
    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand( final String pString )
    {
        return this.aValidCommands.containsKey(pString);
    } // isCommand()
    
    public String getCommandList(){
        StringBuilder list = new StringBuilder();
        for(String command : aValidCommands.keySet()) {
            list.append(command).append(", ");
        }
        list.delete(list.length() - 2, list.length());
        return list.toString();
    }

    /**
     *
     * @param pCommandWord
     * @return the CommandWord
     */
    public CommandWord getCommandWord(String pCommandWord)
    {
        CommandWord vCommand = aValidCommands.get(pCommandWord);
        if(vCommand != null) {
            return vCommand;
        }
        else {
            return CommandWord.unknown;
        }
    }
} // CommandWords
