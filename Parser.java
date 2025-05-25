
import java.util.StringTokenizer;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This parser reads user input and tries to interpret it as an "Adventure"
 * command. Every time it is called it reads a line from the terminal and
 * tries to interpret the line as a two word command. It returns the command
 * as an object of class Command.
 *
 * The parser has a set of known command words. It checks user input against
 * the known commands, and if the input is not one of the known commands, it
 * returns a command object that is marked as an unknown command.
 * 
 * @author  Michael Kolling and David J. Barnes + D.Bureau + E Leblanc + T Leblanc
 * @version 2008.03.30 + 2013.09.15 + 11/04/2025 + 11/04/2025
 */
public class Parser 
{
    private CommandWords aValidCW;  // (voir la classe CommandWords)
    

    /**
     * Constructeur par defaut qui cree les 2 objets prevus pour les attributs
     */
    public Parser() 
    {
        this.aValidCW = new CommandWords();
    } // Parser()

    /**
     * @return The next command from the user.
     */
    public Command getCommand(final String pInputLine) 
    {
        String[] words = pInputLine.trim().split("\\s+");
        String vWord1 = (words.length > 0) ? words[0].toLowerCase() : "";
        String vWord2 = (words.length > 1) ? words[1].toLowerCase() : "";

        if (this.aValidCW.isCommand(vWord1)) {
            return new Command(this.aValidCW.getCommandWord(vWord1), vWord2);
        } else {
            return new Command(CommandWord.unknown, "");
        }
        
    
    }
        
    public String getCommandString()
    {
        return this.aValidCW.getCommandList();
    }
    
    
} // Parser
