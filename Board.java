import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.*;
//Class the JFrame Board
class Board implements ActionListener{
    private int width  = 1000;
    private int height = 750;
    private int size   = 9;
    private int amt    = 18;
    String gameMode    = "9x9";
    Sudoku.GameMode mode = Sudoku.GameMode.normalGame;
    Sudoku sudoku;

    JFrame frame = new JFrame("Sudoku");
        GridBagLayout grid   = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        Dimension buttonDimension = new Dimension(125, 50);

    JPanel homeScreen = new JPanel( grid );
        JLabel header       = new JLabel("Sudoku");
        JButton startButton = new JButton("Start");

    JPanel gameScreen = new JPanel( grid );
        JPanel gamePanel = new JPanel( grid );
            JTextField gameText[][] = new JTextField[9][9]; 
        JPanel gameOptionsPanel = new JPanel( grid );
            JLabel gameModeText      = new JLabel(gameMode);
            JButton pauseButton      = new JButton("Pause");
            JButton checkButton      = new JButton("Check Board");
            JButton giveUpButton     = new JButton("Give Up");
            JButton exitToMainButton = new JButton("Exit to Main");

    JPanel settingsScreen = new JPanel( grid );
        JButton nineNineButton = new JButton("9x9");
        JButton fourFourButton = new JButton("4x4");
        JButton hyperButton    = new JButton("Hyper");
        JLabel amtText         = new JLabel( gameMode + ": " + amt );
        JButton beginButton    = new JButton( "Begin" );
        JSlider numbers        = new JSlider( JSlider.HORIZONTAL, 0, 81, amt );

    //Fonts
    Font bold = new Font( "bold", 3, 14 );
    Font normal = new Font( "normal", 0, 14);

    private float time;
    Board (int width, int height, int size){
        this.width = width;
        this.height = height;
        makeFrame( this.width, this.height, this.size );
    }

    Board(){
        makeFrame(width, height, size);
    }

    private void makeFrame(int w, int h, int s){
        frame.setLayout( grid ); //set layout
        frame.setSize(w, h); //set size
        frame.getContentPane().setBackground(Colors.teaGreen); //set background color

        homeScreen.setPreferredSize(new Dimension(850, 650));
        homeScreenSetup();
        homeScreen.setBackground( Colors.teaGreen );
        c.gridx = 0;
        c.gridy = 0;
        frame.add( homeScreen, c );
        
        gameScreen.setPreferredSize( new Dimension( 850, 650 ) );
        gameScreen.setBackground( Colors.teaGreen );
        c.gridx = 0;
        c.gridy = 0;
        frame.add(gameScreen, c);
        gameScreen.setVisible(false);

        settingsScreen.setPreferredSize( new Dimension( 850, 650 ) );
        settingsSetup();
        settingsScreen.setBackground( Colors.teaGreen );
        c.gridx = 0;
        c.gridy = 0;
        frame.add( settingsScreen, c );
        settingsScreen.setVisible( false );

        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );
    }
    
    public void homeScreenSetup(){
        header.setPreferredSize( new Dimension( 250, 100) );
        header.setHorizontalAlignment( JTextField.CENTER );
        c.gridx = 0;
        c.gridy = 0;
        homeScreen.add(header, c);

        startButton.setPreferredSize( new Dimension( 150,  50) );
        startButton.setHorizontalAlignment( JTextField.CENTER );
        startButton.addActionListener( this );
        c.gridx = 0;
        c.gridy = 1;
        homeScreen.add( startButton, c );
    }

    public void gameSetup(int size){
        gamePanel.setPreferredSize(new Dimension(500, 500));
        c.gridx = 0;
        c.gridy = 0;
        gameScreen.add( gamePanel, c );
        for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                int num = sudoku.getGrid(row, col);
                gameText[row][col] = new JTextField( num + "" );
                if (num != 0){
                    gameText[row][col].setEditable(false);
                    gameText[row][col].setFont( bold );
                }
                if ( ( row<3 && (col<3||col>5) ) || ( row>5 && (col<3||col>5)) || ( (row>=3&&row<=5) && (col>=3&&col<6) ) ){
                    gameText[row][col].setBackground( Colors.flame );
                } else {
                    gameText[row][col].setBackground( Colors.peach );
                }
                gameText[row][col].setHorizontalAlignment(JTextField.CENTER);
                gameText[row][col].setPreferredSize(new Dimension(50, 50));
                c.gridx = row;
                c.gridy = col;
                gamePanel.add( gameText[row][col], c );
            } 
        }
        gameOptionsPanel.setPreferredSize(new Dimension(250, 500));
        c.gridx = 1;
        c.gridy = 0;
        gameScreen.add( gameOptionsPanel , c );

        gameModeText.setText(amt + "");
        gameModeText.setPreferredSize(buttonDimension);
        gameModeText.setHorizontalAlignment(JLabel.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        gameOptionsPanel.add( gameModeText, c );

        pauseButton.setPreferredSize(buttonDimension);
        c.gridx = 0;
        c.gridy = 1;
        gameOptionsPanel.add( pauseButton, c );

        checkButton.setPreferredSize(buttonDimension);
        checkButton.addActionListener( this );
        c.gridx = 0;
        c.gridy = 2;
        gameOptionsPanel.add( checkButton, c );
        
        giveUpButton.setPreferredSize(buttonDimension);
        giveUpButton.addActionListener( this );
        c.gridx = 0;
        c.gridy = 3;
        gameOptionsPanel.add( giveUpButton, c );

        exitToMainButton.setPreferredSize(buttonDimension);
        exitToMainButton.addActionListener( this );
        c.gridx = 0;
        c.gridy = 4;
        gameOptionsPanel.add( exitToMainButton, c );

        time = System.currentTimeMillis();
    }

    public void settingsSetup(){
        nineNineButton.setPreferredSize(new Dimension(150, 50));
        nineNineButton.addActionListener( this );
        c.gridx = 0;
        c.gridy = 0;
        settingsScreen.add(nineNineButton , c);

        fourFourButton.setPreferredSize(new Dimension(150, 50));
        fourFourButton.addActionListener( this );
        c.gridx = 1;
        c.gridy = 0;
        settingsScreen.add(fourFourButton , c);

        hyperButton.setPreferredSize(new Dimension(150, 50));
        hyperButton.addActionListener( this );
        c.gridx = 2;
        c.gridy = 0;
        settingsScreen.add(hyperButton , c);
        
        amtText.setPreferredSize(new Dimension(150, 50));
        amtText.setHorizontalAlignment(JTextField.CENTER);
        c.gridx = 1;
        c.gridy = 1;
        settingsScreen.add(amtText, c);

        numbers.setPreferredSize( new Dimension(450, 50));
        numbers.addChangeListener( new ChangeListener() {
            public void stateChanged(ChangeEvent e) { 
                amt = numbers.getValue();
                amtText.setText(gameMode + ": " + amt);
            }
        } );
        c.gridx = 1;
        c.gridy = 2;
        settingsScreen.add(numbers, c);

        beginButton.setPreferredSize(new Dimension(150, 50));
        beginButton.setHorizontalAlignment(JTextField.CENTER);
        beginButton.addActionListener(this);
        c.gridx = 1;
        c.gridy = 3;
        settingsScreen.add(beginButton, c);
    }
    
    /*--------------------------------------------------
    void resetBoard(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (text[i][j].isEditable() == true){
                    gridS.get(i).set(j, 0);
                    text[i][j].setText( "" );
                }
            }
        }
    }
    //--------------------------------------------------
    

    public static void main(String [] args){
        Board b = new Board();
    }*/

    public void actionPerformed(ActionEvent e){ 
        if (e.getSource() == startButton){
            homeScreen.setVisible(false);
            settingsScreen.setVisible(true);
        }
        if (e.getSource() == beginButton){

            sudoku = new Sudoku(size, amt, mode);
            gameSetup(size);
            settingsScreen.setVisible(false);
            gameScreen.setVisible(true);
        }
        if (e.getSource() == nineNineButton){
            mode = Sudoku.GameMode.normalGame;
            size = 9;
            gameMode = "9x9";
            amtText.setText(gameMode + ": " + amt);
            numbers.setMaximum(81);
            numbers.setValue(20);
        }
        if (e.getSource() == fourFourButton){
            mode = Sudoku.GameMode.fourGame;
            size = 4;
            gameMode = "4x4";
            amtText.setText(gameMode + ": " + amt);
            numbers.setMaximum(16);
            numbers.setValue(4);
        }
        if (e.getSource() == hyperButton){
            mode = Sudoku.GameMode.hyperGame;
            size = 9;
            gameMode = "Hyper";
            amtText.setText(gameMode + ": " + amt);
            numbers.setMaximum(81);
            numbers.setValue(20);
        }
        if (e.getSource() == checkButton){
            //todo
            if ( sudoku.isCorrect( sudoku.getGrid(), size) ){
                gameModeText.setText("Correct!");
            } else {
                gameModeText.setText("Wrong!");
            }
        }
        if (e.getSource() == giveUpButton){
            //todo
            sudoku.setPreemtiveSet( sudoku.getGrid(), size);
            sudoku.solve( sudoku.getGrid(), 0, 0, size);
            for (int row = 0; row < size; row++){
            for (int col = 0; col < size; col++){
                Integer num = sudoku.getGrid(row, col);
                gameText[row][col].setText( num.toString() + "" );
            }}
        }
        if (e.getSource() == exitToMainButton){
            gameScreen.setVisible(false);
            homeScreen.setVisible(true);
        }
    }
}