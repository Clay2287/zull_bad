import java.util.Stack;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  This class is part of the "The Lost City" application.
 *  "The Lost City" is a very simple, text based adventure game.
 *
 *  This class creates all rooms, creates the parser and starts
 *  the game.  It also evaluates and executes the commands that
 *  the parser returns.
 *
 * @author  Michael Kolling and David J. Barnes + E. Leblanc
 * @version 1.0 (Jan 2003) DB edited (2019) + 05/04/2025
 */
public class GameEngine
{
    private Timer         aCountdownTimer;
    private int           aTimeLeft;
    private Parser        aParser;
    private UserInterface aGui;
    private Room          aOuter = new Room("Outside the city", "Images/exit.gif");
    private Room          aTrap  = new Room("in a trapped state due to the alien disease", "Images/placeholder.gif");
    private Player        aPlayer;
    private boolean       aGameOver;

    /**
     * Constructor for objects of class GameEngine
     */
    public GameEngine()
    {
        this.aTimeLeft = 90;
        this.aParser = new Parser();
        this.startTimer();
        this.aGameOver = false;
        this.aPlayer = new Player("player1", this.aGui);
        this.createRooms();
    }

    /**
     * Sets the GUI for the game and prints the welcome message.
     * @param pUserInterface The user interface to be set.
     */
    public void setGUI(final UserInterface pUserInterface, final String vName)
    {
        this.aGui = pUserInterface;
        this.aPlayer = new Player(vName, this.aGui);

        this.createRooms();

        this.printWelcome();
    }


    /**
     * Prints out the opening message for the player.
     */
    private void printWelcome()
    {
        this.aGui.println(" ");
        this.aGui.println("Welcome to the Lost City! You are Benjamin, a Russian scientist.");
        this.aGui.println("The Lost City  is a new, incredibly boring adventure game.");
        this.aGui.println(" ");
        this.aGui.println("This city is empty, only ruins of a previous life remains...");
        this.aGui.println("You need to escape this city to save yourself, Find the exit!");
        this.aGui.println(" ");
        this.aGui.println(" You need to escape in less than 1 minute and 30 seconds. ");
        this.aGui.println("Type '" + CommandWord.help + " ' if you need help");
        this.aGui.println(" ");
        this.printLocationInfo();
        if ( this.aPlayer.getCurrentRoom().getImageName() != null )
            this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room  vDowntown = new Room("in the town center", "Images/downtown_resized.gif");
        Room      vBase = new Room("in the Underground Base", "Images/base_resized.gif");
        Room    vTunnel = new Room("in the Underground Tunnel", "Images/tunnel_resized.gif");
        Room      vRuin = new Room("in a heap of ruins", "Images/ruins_resized.gif");
        Room    vBridge = new Room("on the Bridge", "Images/bridge_resized.gif");
        Room vDowntown2 = new Room("in the city's business district", "Images/downtown2_resized.gif");
        Room  vBuilding = new Room("in a building", "Images/building.gif");
        Room    vOffice = new Room("in an office", "Images/office.gif");
        // Base exits and Items
        vBase.setExit("south", vRuin);
        vBase.setExit("west", vDowntown);
        vBase.setExit("east", vTunnel);
        vBase.addItem("crowbar", new Item("a crowbar", 5));
        vBase.addItem("helmet", new Item("a helmet", 2));

        // Downtown exits and Items
        vDowntown.setExit("east", vBase);
        vDowntown.setExit("south", vBridge);
        vDowntown.addItem("rucksack", new Item("a rucksack", 0));

        // Tunnel exits and Items
        vTunnel.setExit("west", vBase);
        vTunnel.setExit("east", vDowntown2);

        //  Ruins exits and Items
        vRuin.setExit("north", vBase);
        vRuin.setExit("west", vBridge);
        vRuin.addItem("key", new Item("a key", 5));

        // Bridge exits and Items
        vBridge.setExit("east", vRuin);
        vBridge.setExit("south", aOuter);
        vBridge.setExit("north", vDowntown);
        vBridge.addItem("cake", new Item("a piece of cake", 0));

        // Downtown2 exits and Items
        vDowntown2.setExit("west", vTunnel);
        vDowntown2.setExit("south", vBuilding);
        vDowntown2.addItem("shovel", new Item("a shovel", 6));


        vBuilding.setExit("north", vDowntown2);
        vBuilding.setExit("south", vOffice);

        vOffice.setExit("north", vBuilding);

        aOuter.setExit("north", vBridge);
        this.aPlayer.setCurrentRoom(vBase);
    }

    /**
     * Interprets and executes the given command.
     * @param pCommand The command string entered by the player.
     */
    public void interpretCommand( final Command pCommand )
    {
        this.aGui.println( "> " + pCommand.getCommandWord() +" " + pCommand.getSecondWord());

        CommandWord vCommandWord = pCommand.getCommandWord();
        switch (vCommandWord) {
            case help -> this.printHelp();
            case go -> this.goRoom(pCommand);
            case quit ->
            {
                this.aGui.println("Thanks for playing! Goodbye.");
                this.endGame();

            }
            case back -> this.back(pCommand);
            case look -> this.aPlayer.look();
            case eat -> this.aPlayer.eat(pCommand);
            case test -> this.test(pCommand);
            case take -> this.aPlayer.take(pCommand);
            case drop -> this.aPlayer.drop(pCommand);


            case backpack -> this.aPlayer.backpack();
            case teleport -> this.aPlayer.teleport();
            case charge   -> this.aPlayer.charge();
            case unknown  -> this.aGui.println("I don't know what you mean");
            default -> throw new IllegalArgumentException("Unknown command: " + vCommandWord);
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        this.aGui.println("You are alone, in a deserted, post-apocalyptic city.");
        this.aGui.println("You wander around " + this.aPlayer.getCurrentRoom().getDescription());
        this.aGui.println(" ");
        this.aGui.println("Your command words are:");
        this.aGui.println(this.aParser.getCommandString());
    }

    /**
     * Moves the player to a new room if the direction is valid.
     * @param pCommand The command containing the direction.
     */
    private void goRoom( final Command pCommand )
    {
        if ( !pCommand.hasSecondWord() ) {
            this.aGui.println( "Go where?" );
            return;
        }

        String vDirection = pCommand.getSecondWord();
        Room vNextRoom = this.aPlayer.getCurrentRoom().getExit( vDirection );

        if ( vNextRoom == null ) this.aGui.println( "You can't go that way!" );
        else {
            this.aPlayer.setPreviousRoom(this.aPlayer.getCurrentRoom());
            this.aPlayer.setCurrentRoom(vNextRoom);
            this.aGui.println( this.aPlayer.getCurrentRoom().getLongDescription() );
            if ( this.aPlayer.getCurrentRoom().getImageName() != null )
                this.aGui.showImage( this.aPlayer.getCurrentRoom().getImageName() );
        }
        this.checkVictory();
    }


    /**
     * Moves the player back to the previous room if possible.
     * @param pCommand The command object containing the back action.
     */
    private void back(final Command pCommand)
    {
        if (pCommand.hasSecondWord()) {
            if(pCommand.getSecondWord().equals(""))
            {
                this.aPlayer.setCurrentRoom(this.aPlayer.getPreviousRoom());
                this.aPlayer.removePreviousRoom();
                this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
                this.printLocationInfo();
                return;
            }
            this.aGui.println("You can't back in a particular place!");
            return;
        }

        if (this.aPlayer.getPreviousRooms().isEmpty()) {
            this.aGui.println("There is no previous room.");
            return;
        } else {
            this.aPlayer.setCurrentRoom(this.aPlayer.getPreviousRoom());
            this.aPlayer.removePreviousRoom();
            this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
            this.printLocationInfo();
        }
    }

    /**
     * Prints the description of the current room and its associated image.
     */
    private void printLocationInfo()
    {

        this.aGui.println(this.aPlayer.getCurrentRoom().getLongDescription());
    }

    /**
     * Method to test commands from a text file.
     *
     * @param pNameFile The command containing the file name to test
     */
    private void test(final Command pNameFile)
    {
        if(!pNameFile.hasSecondWord()) {
            this.aGui.println(" I Need a file name ! ");
            return;
        }

        String vFileName = pNameFile.getSecondWord();
        try {
            Scanner vS = new Scanner (new File (vFileName + ".txt"));
            this.aGui.println("Testing " + vFileName + " ...");
            while (vS.hasNextLine()) {
                String line = vS.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            Command command = this.aParser.getCommand(line);
            interpretCommand(command);
            }
        } catch (final FileNotFoundException pE) {
            this.aGui.println("No such file exists");
        }

    }


    /**
     * Checks if the command has a second word; prints message if missing.
     *
     * @param pCommand The command to check
     * @param pMessage The message to print if second word is missing
     * @return true if command has a second word, false otherwise
     */
    private boolean checkSecondWord(final Command pCommand, final String pMessage)
    {
        if (!pCommand.hasSecondWord()) {
            this.aGui.println(pMessage);
            return false;
        }
        return true;
    }

    /**
     * Checks if the player has won the game by reaching the outside room with the key.
     * If time runs out, restarts the game.
     */
    private void checkVictory()
    {
        if (this.getTimeLeft() <= 0)
        {
            this.restartGame();
        }

        if (this.aPlayer.getCurrentRoom().equals(this.aOuter))
        {
            if(this.aPlayer.hasItem("key"))
            {
                this.aGui.println("With the key, you open the gate and escape. You won the game! Congratulations!");
                this.endGame();
            } else {
                this.aGui.println("You don't have the key, go back and find it to open the gate...");
            }
        }
    }


    /**
     * Returns the amount of time left in seconds.
     *
     * @return the remaining time left
     */
    public int getTimeLeft()
    {
        return this.aTimeLeft;
    }

    /**
     * Updates the timer display on the GUI.
     */
    private void updateTimerDisplay()
    {
        if (this.aGui != null) {
            this.aGui.updateTimer(this.aTimeLeft);
        }
    }

    /**
     * Starts the countdown timer for the game.
     * Timer ticks every second, reducing time left and ending the game when time runs out.
     */
    private void startTimer()
    {
        // Timer updates every second
        if (this.aCountdownTimer != null && this.aCountdownTimer.isRunning()) {
            this.aCountdownTimer.stop();
        }
        this.aTimeLeft = 90;
        this.aGameOver = false;
        this.aCountdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!GameEngine.this.aGameOver) {
                    if (GameEngine.this.aTimeLeft > 0) {
                        GameEngine.this.aTimeLeft--;
                        GameEngine.this.updateTimerDisplay();
                    } else {
                        GameEngine.this.endTimeLimit();
                    }
                }
            }
        });
        this.aCountdownTimer.start();
    }

    /**
     * Called when time runs out to end the game and notify the player.
     */
    private void endTimeLimit()
    {
        if(!aGameOver){
            this.aGameOver = true;
            this.aCountdownTimer.stop();
            this.aGui.println("Time's up! You lost..");
            this.aGui.println( "Restart the game and try again." );
            this.endGame();
        }
    }

    /**
     * Ends the game
     */
    private void endGame()
    {
        this.aCountdownTimer.stop();
        this.aGui.enable( false );
        this.aGui.disableButtons(true);
    }

    /**
     * Restarts the game.
     */
    public void restartGame()
    {
        this.aPlayer = new Player("player1", this.aGui);
        this.createRooms();
        this.aGui.clearLog();
        this.aGui.println("The game has been restarted. Good luck!");
        this.printWelcome();

        this.aGameOver = false;
        this.aTimeLeft = 90;
        this.startTimer();


        this.aGui.showImage(this.aPlayer.getCurrentRoom().getImageName());
        this.aGui.enable(true);
        this.aGui.disableButtons(false);
        this.aGui.updateTimer(this.aTimeLeft);

    }

    /**
     * Returns the player's current room.
     *
     * @return the current Room object of the player
     */
    public Room getCurrentRoom()
    {
        return this.aPlayer.getCurrentRoom();
    }

    /**
     * Resets the player location to a free room if trapped by alien virus.
     */
    public void trapped()
    {
        this.aPlayer.setCurrentRoom(this.aPlayer.getFreeRoom());
        this.aGui.showImage(this.aPlayer.getFreeRoom().getImageName());
    }

    /**
     * Returns the trap room object.
     *
     * @return the trap Room object
     */
    public Room getTrap()
    {
        return this.aTrap;
    }
}
