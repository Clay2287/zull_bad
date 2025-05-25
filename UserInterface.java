import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

import javax.swing.*;

/**
 * This class implements a simple graphical user interface with a text entry
 * area, a text output area and an optional image.
 * 
 * @author Michael Kolling + Eden Leblanc + T Leblanc
 * @version 1.0 (Jan 2003) DB edited (2019) + 20/05/2025
 */
public class UserInterface implements ActionListener
{
    private GameEngine  aEngine;

    private JLabel      aTimer;
    // private Set<String> aItemSet;
    private JFrame      aMyFrame;
    private JFrame      aButtonFrame;
    // private JFrame      aItemFrame;

    private JTextField  aEntryField;
    private JTextArea   aLog;
    private JLabel      aImage;

    private JButton     aButtonNorth;
    private JButton     aButtonSouth;
    private JButton     aButtonWest;
    private JButton     aButtonEast;
    private JButton     aButtonBack;
    private JButton     aButtonClear;
    private JButton     aButtonLook;
    private JButton     aButtonEat;
    private JButton     aButtonQuit;
    private JButton     aButtonTp;
    private JButton     aButtonCharge;
    private JButton     aButtonRestart;
    // private JButton     aButtonTake;
    // private JButton     aButtonDrop;
    // private JPanel      aItemList = new JPanel();
    // private JPanel      vButtonItem    = new JPanel();


    private Parser      aParser;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
        this.aEngine.setGUI(this, "player1");
        this.aParser = new Parser();
        
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     */
    public void print( final String pText )
    {
        if( this.aLog == null) return;

        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println( final String pText )
    {
        if( this.aLog == null) return;

        this.print( pText + "\n" );
    } // println(.)

    /**
     * Show an image file in the interface.
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL );
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage(.)

    /**
     * Enable or disable input in the input field.
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( ! pOnOff ) { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        this.aImage = new JLabel();

        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(300, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );

        

        this.aMyFrame = new JFrame( "Benjamin in the lost city" );
        // this.aItemFrame = new JFrame("Items");
        this.aButtonFrame = new JFrame("Buttons");

        this.aMyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.aButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.aItemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        JPanel vPanel         = new JPanel();
        JPanel vPanelButtons  = new JPanel();
        JPanel vPanelTimer    = new JPanel();
        

        this.aTimer = new JLabel("Time Left: 1:30", JLabel.CENTER);
        this.aTimer.setFont(new Font("Arial", Font.BOLD, 20));
        this.aEntryField    = new JTextField( 34 );

           this.aButtonNorth   = new JButton("Go north!");
           this.aButtonSouth   = new JButton("Go south!");
           this.aButtonWest    = new JButton("Go west!");
           this.aButtonEast    = new JButton("Go east!");
           this.aButtonBack    = new JButton("Go back!");
           this.aButtonClear   = new JButton("Clear Log");
           this.aButtonRestart = new JButton("Restart Game");
           this.aButtonLook    = new JButton("Look");
           this.aButtonEat     = new JButton("Eat");
           this.aButtonQuit    = new JButton("Quit");
           this.aButtonTp      = new JButton("Teleport");
           this.aButtonCharge  = new JButton("Charge");
        // this.aButtonTake    = new JButton("Take");
        // this.aButtonDrop    = new JButton("Drop");


        JButton[] vTabButtons = new JButton[12];
        vTabButtons[0]      = this.aButtonNorth;
        vTabButtons[1]      = this.aButtonSouth;
        vTabButtons[2]      = this.aButtonWest;
        vTabButtons[3]      = this.aButtonEast;
        vTabButtons[4]      = this.aButtonBack;
        vTabButtons[5]      = this.aButtonClear;
        vTabButtons[6]      = this.aButtonRestart;
        vTabButtons[7]      = this.aButtonLook;
        vTabButtons[8]      = this.aButtonEat;
        vTabButtons[9]      = this.aButtonQuit;
        vTabButtons[10]     = this.aButtonTp;
        vTabButtons[11]     = this.aButtonCharge;
        
        for(JButton vButton : vTabButtons) {
            vButton.setBackground(Color.WHITE);
            this.HoverEffect(vButton);
        }

        this.aButtonQuit.setBackground(Color.RED);

        vPanelTimer.setLayout( new GridLayout() );
        vPanelTimer.add(this.aTimer);

        vPanel.setLayout( new BorderLayout() ); // ==> 5 places seulement 
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vPanelTimer, BorderLayout.WEST );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );

        vPanelButtons.setLayout(new GridLayout(3, 4));
        vPanelButtons.add(this.aButtonLook);
        vPanelButtons.add(this.aButtonNorth);
        vPanelButtons.add(this.aButtonEat);
        vPanelButtons.add(this.aButtonCharge);
        vPanelButtons.add(this.aButtonWest);
        vPanelButtons.add(this.aButtonBack);
        vPanelButtons.add(this.aButtonEast);
        vPanelButtons.add(this.aButtonQuit);
        vPanelButtons.add(this.aButtonClear);
        vPanelButtons.add(this.aButtonSouth);
        vPanelButtons.add(this.aButtonRestart);
        vPanelButtons.add(this.aButtonTp);
        
        // this.vButtonItem.add(this.aButtonTake);
        // this.vButtonItem.add(this.aButtonDrop);

        // String vButtonString = "helmet";
        // this.aItemList.setLayout(new CardLayout());
        // this.updateItemCards(); 

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );
        this.aButtonFrame.getContentPane().add(vPanelButtons, BorderLayout.CENTER);
        // this.aItemFrame.getContentPane().add(this.aItemList, BorderLayout.NORTH);
        // this.aItemFrame.getContentPane().add(vButtonItem);
        // this.aItemFrame.setPreferredSize( new Dimension(300, 200) );


        // Traitement des actions de chaque bouttons.
        this.aEntryField.addActionListener(this);
        this.aButtonNorth.addActionListener(this);
        this.aButtonSouth.addActionListener(this);
        this.aButtonWest.addActionListener(this);
        this.aButtonEast.addActionListener(this);
        this.aButtonBack.addActionListener(this);
        this.aButtonClear.addActionListener(this);
        this.aButtonRestart.addActionListener(this);
        this.aButtonLook.addActionListener(this);
        this.aButtonEat.addActionListener(this);
        this.aButtonQuit.addActionListener(this);
        this.aButtonTp.addActionListener(this);
        this.aButtonCharge.addActionListener(this);
        // fait pour l'interface item
        // this.aButtonTake.addActionListener(this);
        // this.aButtonDrop.addActionListener(this);



        // Traitement des fenêtre, les afficher.
        this.aMyFrame.pack();
        this.aButtonFrame.pack();
        // this.aItemFrame.pack();

        this.aMyFrame.setLocation(500, 100);
        this.aButtonFrame.setLocation(1300, 300);
        // this.aItemFrame.setLocation(1300, 600);


        this.aMyFrame.setVisible( true );
        this.aButtonFrame.setVisible(true);
        // this.aItemFrame.setVisible(true);



        this.aEntryField.requestFocus();
    } // createGUI()


    /**
     * Applies an effect that changes the buttons color when the mouse cursor is on top of it
     * @param pButton The Button that we want to affect
     */
    private void HoverEffect(final JButton pButton) 
    {
        if (pButton == this.aButtonQuit) return;
        pButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent pE) {
            pButton.setBackground(Color.LIGHT_GRAY); // Change background on hover
            }
    
            @Override
            public void mouseExited(MouseEvent pE) {
                pButton.setBackground(Color.WHITE); // Revert to original color when mouse exits
            }
        
        });
    }

    /**
     * Updates the timer, used each second with a decrement for pTimeLeft.
     * @param pTimeLeft
     */
    public void updateTimer(int pTimeLeft)
    {
        int minutes = pTimeLeft / 60;
        int seconds = pTimeLeft % 60;
        this.aTimer.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
    }

    
    /**
     * Actionlistener interface for entry textfield.
     */
    public void actionPerformed( final ActionEvent pE )
    {
        if (pE.getSource() == this.aButtonBack) {
            Command vCommand = this.aParser.getCommand("back");
            this.aEngine.interpretCommand(vCommand);
        }
        else if (pE.getSource() == this.aButtonNorth) {
            Command vCommand = this.aParser.getCommand("go north");
            this.aEngine.interpretCommand(vCommand);
        } else if (pE.getSource() == this.aButtonSouth) {
            Command vCommand = this.aParser.getCommand("go south");
            this.aEngine.interpretCommand(vCommand);
        } else if (pE.getSource() == this.aButtonWest) {
            Command vCommand = this.aParser.getCommand("go west");
            this.aEngine.interpretCommand(vCommand);
        } else if (pE.getSource() == this.aButtonEast) {
            Command vCommand = this.aParser.getCommand("go east");
            this.aEngine.interpretCommand(vCommand);
        } else if(pE.getSource() == this.aButtonLook) {
            Command vCommand = this.aParser.getCommand("look");
            this.aEngine.interpretCommand(vCommand);
        } else if(pE.getSource() == this.aButtonEat) {
            Command vCommand = this.aParser.getCommand("eat");
            this.aEngine.interpretCommand(vCommand);
        } else if(pE.getSource() == this.aButtonQuit) {
            Command vCommand = this.aParser.getCommand("quit");
            this.aEngine.interpretCommand(vCommand);
        } else if(pE.getSource() == this.aButtonTp) {
            Command vCommand = this.aParser.getCommand("teleport");
            this.aEngine.interpretCommand(vCommand);
        } else if(pE.getSource() == this.aButtonCharge) {
            Command vCommand = this.aParser.getCommand("charge");
            this.aEngine.interpretCommand(vCommand);
        } else if(pE.getSource() == this.aButtonClear) {
            this.aLog.setText("");
        } else if(pE.getSource()  == this.aButtonRestart) {
            this.aEngine.restartGame();
        }
        else {
            this.processCommand();
        }
    } // actionPerformed(.)


    /**
     * disables button if pDisable is true and enable them when is false.
     * @param pDisable boolean parameter
     */
    public void disableButtons(boolean pDisable)
    {
        this.aButtonNorth.setEnabled(!pDisable);
        this.aButtonSouth.setEnabled(!pDisable);
        this.aButtonWest.setEnabled(!pDisable);
        this.aButtonEast.setEnabled(!pDisable);
        this.aButtonBack.setEnabled(!pDisable);
        this.aButtonClear.setEnabled(!pDisable);
        this.aButtonEat.setEnabled(!pDisable);
        this.aButtonLook.setEnabled(!pDisable);
        this.aButtonQuit.setEnabled(!pDisable);
        this.aButtonTp.setEnabled(!pDisable);
        this.aButtonCharge.setEnabled(!pDisable);
    }

    /**
     * A command has been entered. Read the command and do whatever is 
     * necessary to process it.
     */
    public void processCommand()
    {
        String vInput = this.aEntryField.getText();
        if (vInput.isEmpty()) {
            this.println("Please enter a valid command.");
            return;
        }
        Command vCommand = this.aParser.getCommand(vInput);
        if (vCommand.isUnknown()) {
            this.println("Unknown command: " + vInput);
        } else {
            this.aEngine.interpretCommand(vCommand);
        }
        this.aEntryField.setText("");
    } // processCommand()

    /**
     * Clears the text window of the interface
     */
    public void clearLog() 
    {
        this.aLog.setText("");
    }

    /**
     * When the cake is eaten, this method is ran and a window is created
     * with a riddle you need to solve to escape. Window with a text input and OK / CANCEL buttons
     */
    public void trapped()
    {
        JTextField vInput = new JTextField(15);
        JPanel vPanel = new JPanel();
        vPanel.setLayout(new FlowLayout());
        String enigme = "If Friday = 6511, Sunday = 6713 and Monday = 617, Wednesday = ?";
        String answer = "9312";
        vPanel.add(new JLabel(enigme));
        vPanel.add(vInput);

        // Je n'ai pas imaginé cette ligne moi-même, imaginé avec mon frère en consultant la javadoc.
        int vResult = JOptionPane.showConfirmDialog(null, vPanel, "Solve the riddle to escape ! ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(vResult == JOptionPane.OK_OPTION) {
            String vOutput = vInput.getText();
            if (vOutput.equals(answer)) {
                this.println("Correct answer, you are freed from the alien trap");
                this.aEngine.trapped();
            } else {
                this.println("Wrong answer, try again!");
                this.trapped();
            }
        }
    }

    /**
     * Returns the trap room object. This method is here to transit between GameEngine and Player since
     * Player doesn't initialize a GameEngine object.
     *
     * @return the trap Room object
     */
    public Room getTrap()
    {
        return this.aEngine.getTrap();
    }

// Code potentiel que j'avais fais pour tenter de faire un CardLayout pour les items ( sans succès, et manque de temps)

    // public void updateItemCards() {
    //     this.aItemSet = aEngine.getCurrentRoom().getItemList().getKeys();
    //     String[] vItemArray = this.aItemSet.toArray(new String[]);
    //     this.aItemList.removeAll();
    //     for (String vItemName : aItemSet) {
            
    //         this.aItemList.add(this.vButtonItem, vItemName);
    //     }

    //     this.addComponentToPane(this.aItemList, vItemArray);
    //     this.aItemFrame.revalidate();
    //     this.aItemFrame.repaint();
    // }

    // public void addComponentToPane(Container pane, String[] comboBoxItems) 
    // {
    //     JPanel cards = new JPanel();
    //     JPanel comboBoxPane = new JPanel();
    //     JComboBox cb = new JComboBox(comboBoxItems);
    //     cb.setEditable(false);
    //     comboBoxPane.add(cb);
        
    //     //Create the "cards".
    //     JPanel card1 = new JPanel();
    //     card1.add(new JButton("take"));
    //     card1.add(new JButton("drop"));
        
    //     //Create the panel that contains the "cards".
    //     cards = new JPanel(new CardLayout());
    //     for (int i = 0; i <  comboBoxItems.length; i++) {
    //         cards.add(card1, comboBoxItems[i]);
    //     }
        
    //     pane.add(comboBoxPane, BorderLayout.PAGE_START);
    //     pane.add(cards, BorderLayout.CENTER);
    // }
} // UserInterface 
