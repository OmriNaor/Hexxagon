/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author omri
 */
public class HexxagonWindow extends JFrame implements Constants, MouseListener {

    private boolean isChosen;
    private String playerToPlay;
    private JButton computerPlay;
    private ComputerPlayer cp;
    private Board b;
    private final JLabel[][] label;
    private final JLabel scorePlayer1;
    private final JLabel scorePlayer2;
    private JPanel panGameBoard;
    private JPanel panScore;
    private int difficulty;
    private static final String STRING_TO_SPLIT = ",";
    private static final ImageIcon IMG_EMPTY = createImageIcon("/Images/empty.png");
    private static final ImageIcon IMG_REG = createImageIcon("/Images/regimg.png");
    private static final ImageIcon IMG_PLAYER1 = createImageIcon("/Images/player.png");
    private static final ImageIcon IMG_PLAYER2 = createImageIcon("/Images/pc.png");
    private static final ImageIcon IMG_changeToPlayer2 = createImageIcon("/Images/changeToWhite.gif");
    private static final ImageIcon IMG_changeToPlayer1 = createImageIcon("/Images/changeToBlack.gif");
    
    public HexxagonWindow(String title) {
        super(title);
        this.difficulty = 2;
        this.isChosen = false;      
        playerToPlay = "";
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setPreferredSize(new Dimension(400, 680));
        this.getContentPane().setLayout(new BorderLayout());

        // Radio buttons
        JRadioButton easyButton = new JRadioButton("Easy");
        easyButton.setActionCommand("Easy");
        easyButton.setSelected(true);

        JRadioButton mediumButton = new JRadioButton("Medium");
        mediumButton.setActionCommand("Medium");

        JRadioButton hardButton = new JRadioButton("Hard");
        hardButton.setActionCommand("Hard");

        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        easyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               System.out.println("Easy");
            }
        });

        mediumButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               System.out.println("Medium");
            }
        });

        hardButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               System.out.println("Hard");
            }
        });

        // Menu
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        menuItem = new JMenuItem("New Game");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_MASK));
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
               newGame();
            }
        });

        menuItem = new JMenuItem("Exit");
        menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        menu = new JMenu("Settings");
        menuBar.add(menu);

        menuItem = new JMenuItem("Change Difficulty");
        menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, KeyEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});

        menuItem = new JMenuItem("Who starts");
        menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(b.turn == PLAYER1)
					b.turn = PLAYER2;
				else
					b.turn = PLAYER1;
				newGame();
			}
		});
        
        menu = new JMenu("Help");
        menuBar.add(menu);

        menuItem = new JMenuItem("How To Play...");
        menu.add(menuItem);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, KeyEvent.CTRL_MASK));
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "Press one of your players in order to choose which one you'd like to play with.\n"
                        + "Possible moves will be marked when purple color is for replicate moves and red is for regular moves.\n"
                        + "You need to have more players than your oponnet. In order to do so, either fill the whole board with more players or simply eat all of your oponnet's player.\n"
                        + "Press on the 'Opponent's Move' button in order to finish your turn and let your oponnet to make his move.");
            }
        });

        menuItem = new JMenuItem("About...");
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "?? Omri Naor\n 2014 - 2015");
            }
        });

        computerPlay = new JButton("Opponent's Move");
        computerPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(b.turn == PLAYER2)
				{
					b.doMove(cp.getMove());
					b.turn = PLAYER1;
				    panGameBoard.removeAll();
			        setScoreText();
			        createLabels();		
			        checkEnd();
				}
				else
					JOptionPane.showMessageDialog(null, "It is your turn! Let's not waste it!");
			}
		});
        b = new Board();
        this.cp = new ComputerPlayer(difficulty, b);
        label = new JLabel[SIZE + WALL_SIZE * 2][SIZE];
        scorePlayer1 = new JLabel();
        scorePlayer2 = new JLabel();
        panGameBoard = new JPanel(new GridLayout(SIZE + WALL_SIZE * 2, SIZE));
        panScore = new JPanel(new FlowLayout());
        createLabels();
        setScoreText();
        panScore.add(scorePlayer1);
        panScore.add(computerPlay);
        panScore.add(scorePlayer2);
        scorePlayer1.setFont(new Font("Ariel", Font.BOLD, 18));
        scorePlayer2.setFont(new Font("Ariel", Font.BOLD, 18));
        this.getContentPane().add(panGameBoard, BorderLayout.CENTER);
        this.getContentPane().add(panScore, BorderLayout.SOUTH);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        getRootPane().setDefaultButton(computerPlay);
        this.setJMenuBar(menuBar);
    }

    private void createLabels() {

        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        Border chosenBorder = BorderFactory.createLineBorder(Color.DARK_GRAY);
        Border validMoveRegular = BorderFactory.createLineBorder(Color.RED);
        Border validMoveReplicate = BorderFactory.createLineBorder(Color.magenta);
        Move m;
        int row = -1;
        int col = -1;
        if(playerToPlay.length() >= 3)
        {
         String s []= playerToPlay.split(STRING_TO_SPLIT);
         row = Integer.parseInt(s[0]);
         col = Integer.parseInt(s[1]);
        }
        for (int i = 0; i < SIZE + WALL_SIZE * 2; i++) {
            for (int j = 0; j < SIZE; j++) {
            	
            	m = new Move(i, j + WALL_SIZE, PLAYER1, row, col);
                label[i][j] = new JLabel();
                if(row == i && col == j + WALL_SIZE)
                    label[i][j].setBorder(chosenBorder);
                else
                    label[i][j].setBorder(border);
                
                if(b.isValidMoveRegular(m))
                {
                    label[i][j].setBorder(validMoveRegular);
                    label[i][j].setIcon(IMG_REG);
                }

                if(b.isValidMoveReplicate(m))
                {
                	label[i][j].setIcon(IMG_REG);
                    label[i][j].setBorder(validMoveReplicate);
                }

                label[i][j].setOpaque(true); // Makes the color visible

                label[i][j].setName(i + STRING_TO_SPLIT + (j + WALL_SIZE));
                label[i][j].addMouseListener(this);

                if (b.getPos(i, j+ WALL_SIZE) == EMPTY && !b.isValidMoveRegular(m) && !b.isValidMoveReplicate(m)) {
                    label[i][j].setIcon(IMG_EMPTY); // Background color
                }
                if (b.getPos(i, j+ WALL_SIZE) == PLAYER1) {
                    label[i][j].setIcon(IMG_PLAYER1); // Background color
                }                
                
                if(b.getPos(i, j+ WALL_SIZE) == WALL)
                	label[i][j].setBackground(Color.BLUE);
                if (b.getPos(i, j+ WALL_SIZE) == PLAYER2) {
                    label[i][j].setIcon(IMG_PLAYER2); // Background color
                }
                panGameBoard.add(label[i][j]);
            }
        }

        this.setVisible(true);

    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = HexxagonWindow.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    private void newGame()
    {
    	this.isChosen = false;
        playerToPlay = "";
        b = new Board();
        this.cp = new ComputerPlayer(difficulty, b);
        panGameBoard.removeAll();
        createLabels();
        setScoreText();
    }
    
    private void setScoreText()
    {
        this.scorePlayer1.setText("Black: "+ b.getScore(PLAYER1)+"           ");
        this.scorePlayer2.setText("         White: "+ b.getScore(PLAYER2));
    }
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JLabel && b.turn == PLAYER1) {
        	
            String s[] = ((JLabel) e.getSource()).getName().split(
                    STRING_TO_SPLIT);
            int row = Integer.parseInt(s[0]);
            int col = Integer.parseInt(s[1]);
            Move m;
            if (b.getPos(row, col) == PLAYER1) {
                playerToPlay = row + STRING_TO_SPLIT + col;
                isChosen = true;
                b.isHole(PLAYER1);
            } else {
                if (isChosen && b.getPos(row, col) == EMPTY) {
                    s = playerToPlay.split(STRING_TO_SPLIT);
                    int currentRow = Integer.parseInt(s[0]);
                    int currentCol = Integer.parseInt(s[1]);
                    m = new Move(row, col, PLAYER1, currentRow, currentCol);
                    if (b.isValidMoveRegular(m)) {
                        isChosen = false;
                        b.turn = PLAYER2;
                        playerToPlay = "";
                        b.doMove(m);
                    } else
                        if(b.isValidMoveReplicate(m))
                        {
                            this.b.doMove(m);
                            isChosen = false;
                            b.turn = PLAYER2;
                            playerToPlay = "";
                        }

                }
            }
            panGameBoard.removeAll();
            setScoreText();
            createLabels();  
            checkEnd();
        }
    }

    public void checkEnd()
    {
    	 
    	if(b.checkEnd() == PLAYER1)
    	{
    		 JOptionPane.showMessageDialog(null, "Congratulations! You won!\n Let's start a new game!");
        	 newGame();
    	}
    	
    	if(b.checkEnd() == PLAYER2)
    	{
    		JOptionPane.showMessageDialog(null, "Ha! You lost! Good game at all. Let's have a new one!");
        	newGame();
    	}
           
        
        if(b.getPossibleMoves(PLAYER1).isEmpty())
        {
        	JOptionPane.showMessageDialog(null, "You're out of possible moves! You lost!");
        	newGame();
        }
        
        if(b.getPossibleMoves(PLAYER2).isEmpty())
        {
        	JOptionPane.showMessageDialog(null, "Your oponnet is out of possible moves! You won!");
        	newGame();
        }
    }
    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
}
