import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/* import java.io.File; */


public class Menus {
    private JFrame frame=new JFrame();
    private String [] gameNames;
    private GameLauncher [] gameLaunchers;

    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int FONT_SIZE = 21;
    private static final int ELEMENT_HEIGHT = 50;
    private static final int ELEMENT_WIDTH = WIDTH - 100;
    private static final int STRUT_SIZE = (HEIGHT - ELEMENT_HEIGHT * 7) / 7;
    private static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);
    private static boolean [] visibilities ;


    public Menus() {
        //frame.setVisible(true);
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ClassLoader CL = ClassLoader.getSystemClassLoader();
        int gameCount = new DirReader().getDirCount();
        gameLaunchers=new GameLauncher[gameCount];
        gameNames=new String[gameCount];
        visibilities=new boolean[gameCount];
        Arrays.fill(visibilities,true);

        for(int i = 0; i< gameCount; i++){
            gameNames[i]=new DirReader().getDirNames().get(i);
            try {

                URLClassLoader loader=URLClassLoader.newInstance(new URL[]{new URL(new DirReader().getDirNames().get(i))});
                Class c=loader.loadClass(gameNames[i]);
                gameLaunchers[i]=(GameLauncher) loader;
            } catch (Exception e) {
                e.printStackTrace();
            }

            }
         // make frame visible


    }


    public void mainMenu() {
        //JPanel panel = new JPanel();
        JPanel panel = commonElements("Solitaire Pro");

        /* Set up menu buttons*/
        JButton[] menuButtons = new JButton[visibilities.length];   //create array of menu buttons
        for (int i = 0; i < visibilities.length; i++) { // iterate through the number of visible buttons to set up each button
            if (visibilities[i]) {
                String name=gameNames[i];
                menuButtons[i] = new JButton(name);
                menuButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
                menuButtons[i].setPreferredSize(new Dimension(ELEMENT_WIDTH, ELEMENT_HEIGHT));
                menuButtons[i].setFont(font);
                menuButtons[i].addActionListener((ActionEvent e)->{
                    gameMenu(name);
                });
                panel.add(Box.createVerticalStrut(STRUT_SIZE)); // adds the strut for the buttons
                panel.add(menuButtons[i]); // adds the button
            }
        }

        panel.add(Box.createVerticalStrut(STRUT_SIZE));

        JPanel bottomRow = new JPanel(); // Bottom row set up. Separate panel for transferability to other menus

        JButton optionsButton = new JButton("Options");
        optionsButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
        optionsButton.setFont(font);
        bottomRow.add(optionsButton);

        JButton exitButton = new JButton("Exit Game");
        exitButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
        exitButton.setFont(font);
        bottomRow.add(exitButton);

        panel.add(bottomRow);
        panel.add(Box.createVerticalGlue());

        frame.add(panel); // add full panel to frame
        frame.setVisible(true);

        /*
         Test line
        System.out.print("Getting there");
        end Test line
        */
    } // End of mainMenu method

    public void optionsMenu() {
        JPanel panel = new JPanel();
        panel = commonElements("Options");

        /*Set up labels and select boxes for game */
        String [] GameNames=new String[]{"Bonanza Creek","Betsy Ross","Binary Star"}; // create an array for the name
        //JLabel[] gameLabels = new JLabel[GameNames.length]; //create array of game name labels
        JCheckBox[] VisSetter = new JCheckBox[GameNames.length];
        for (int i = 0; i < GameNames.length; i++) { // iterate through the number of visible buttons to set up each button

            VisSetter[i] = new JCheckBox(GameNames[i], true);
            VisSetter[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            VisSetter[i].setPreferredSize(new Dimension(ELEMENT_WIDTH, ELEMENT_HEIGHT));
            VisSetter[i].setFont(font);
            panel.add(Box.createVerticalStrut(STRUT_SIZE)); // adds the strut for the buttons
            panel.add(VisSetter[i]); // adds the button

        }
        panel.add(Box.createVerticalStrut(STRUT_SIZE));

        /* Set up bottom row of game menu. */
        JPanel bottomRow = new JPanel(); // Bottom row set up. Separate panel for transferability to other menus

        JButton optionsButton = new JButton("Return to Main Menu");
        optionsButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
        optionsButton.setFont(font);
        bottomRow.add(optionsButton);

        panel.add(bottomRow);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);

    }// end of Main menu method

    private void gameMenu(String gameName) {
        JPanel panel = new JPanel();
        panel= commonElements("gameName");

        String[] ButtonTitles = new String[]{"New Game", "Continue Game"}; // create an array for the name
        JButton[] gameMenuButtons = new JButton[ButtonTitles.length]; //create array of menu buttons
        for (int i = 0; i < ButtonTitles.length; i++) { // iterate through the number of visible buttons to set up each button
            gameMenuButtons[i] = new JButton(ButtonTitles[i]);
            gameMenuButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            gameMenuButtons[i].setPreferredSize(new Dimension(ELEMENT_WIDTH, ELEMENT_HEIGHT));
            gameMenuButtons[i].setFont(font);
            GameLauncher thisLauncher=gameLaunchers[i];
            gameMenuButtons[i].addActionListener((ActionEvent a)->{
                thisLauncher.startGame(null);

            });
            panel.add(Box.createVerticalStrut(STRUT_SIZE)); // adds the strut for the buttons
            panel.add(gameMenuButtons[i]); // adds the button
        }





        panel.add(Box.createVerticalStrut(STRUT_SIZE));

        /*JLabel highscoresLabel = new JLabel();
        //highscoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        highscoresLabel.setPreferredSize(new Dimension(ELEMENT_WIDTH, ELEMENT_HEIGHT));
        highscoresLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        panel.add(highscoresLabel);

        JButton clearHSButton = new JButton("Clear");
        clearHSButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
        clearHSButton.setFont(font);
        panel.add(clearHSButton);*/
        panel.add(highScoresPanel());



        panel.add(Box.createVerticalStrut(STRUT_SIZE));

        /* Set up bottom row of game menu. */
        JPanel bottomRow = new JPanel(); // Bottom row set up. Separate panel for transferability to other menus

        JButton optionsButton = new JButton("Main Menu");
        optionsButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
        optionsButton.setFont(font);
        bottomRow.add(optionsButton);

        /*
         *
         * Not sure if this exit button is necessary.
         */
            /*JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
            exitButton.setFont(font);
            bottomRow.add(exitButton);*/

        panel.add(bottomRow);
        panel.add(Box.createVerticalGlue());

        frame.add(panel);
        frame.setVisible(true);


    } // End of gameMenu method

    public JPanel commonElements(String menuTitle) {
        /* Instantiate the JPanel for the main menu*/
        JPanel panel=new JPanel();


        /* Set the layout for the main menu JPanel*/
        BoxLayout layout = new BoxLayout(panel, BoxLayout.PAGE_AXIS);
        panel.setLayout(layout);
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);
        //add the glue
        panel.add(Box.createVerticalGlue());

        /* Set up the game menu title */
        JLabel titleLabel = new JLabel(menuTitle, SwingConstants.CENTER);
        titleLabel.setFont(font); //Menu title font
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //Menu title alignment
        titleLabel.setPreferredSize(new Dimension(ELEMENT_WIDTH, ELEMENT_HEIGHT)); //size of label box
        panel.add(titleLabel); //adds the title to panel

        return panel;
    } // End of commonElement

    @NotNull
    public static JPanel highScoresPanel(){
        JPanel hSPanel=new JPanel();
        JLabel highscoresLabel = new JLabel();
        //highscoresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        highscoresLabel.setPreferredSize(new Dimension(ELEMENT_WIDTH/2, ELEMENT_HEIGHT));
        highscoresLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        hSPanel.add(highscoresLabel);

        JButton clearHSButton = new JButton("Clear");
        clearHSButton.setPreferredSize(new Dimension(ELEMENT_WIDTH / 2, ELEMENT_HEIGHT));
        clearHSButton.setFont(font);
        hSPanel.add(clearHSButton);
        return hSPanel;
    }


    public static void main (String[]args){

        Menus test = new Menus();
        test.mainMenu();



        }
    }

