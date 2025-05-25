/**
 * Classe Command - une commande du jeu d'aventure Zuul.
 *
 * @author Alban Fournier--Cibray
 */
public class Command
{ 
    private CommandWord aCommandWord;
    private String aSecondWord;
    
    /**
     * Constructeur de la classe Command.
     * 
     * @param pCommandWord Le mot principal de la commande.
     * @param pSecondWord Le second mot de la commande (peut être null).
     */
    public Command(final CommandWord pCommandWord, final String pSecondWord)
    {
        this.aCommandWord = pCommandWord;
        this.aSecondWord = pSecondWord;
    }
    
    /**
     * Retourne le mot principal de la commande.
     * 
     * @return Le mot principal de la commande.
     */
    public CommandWord getCommandWord()
    {
        return this.aCommandWord;
    }
    
    /**
     * Retourne le second mot de la commande.
     * 
     * @return Le second mot de la commande, ou null s'il n'existe pas.
     */
    public String getSecondWord()
    {
        return this.aSecondWord;
    }
    
    /**
     * Vérifie si la commande contient un second mot.
     * 
     * @return true si un second mot est présent, false sinon.
     */
    public boolean hasSecondWord()
    {
        return (this.aSecondWord != null);
    }
    
    /**
     * Vérifie si la commande est inconnue (absence de mot principal).
     * 
     * @return true si le mot principal est null, false sinon.
     */
    public boolean isUnknown()
    {
        return (this.aCommandWord == CommandWord.unknown);
    }
}// Command
    