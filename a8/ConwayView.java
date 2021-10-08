package a8;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
 
public class ConwayView extends JFrame implements ActionListener{
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(5020, 5070);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(120, 170);
    private static final int BLOCK_SIZE = 10;
 
    private JMenuBar menu;
    private JMenu file, gameMenu;
    private JMenuItem fileOptions, fileExit, fileRules;
    private JMenuItem gameAutofill, gamePlay, gameStop, gameReset, gameTorus;
    private int movesPerSecond = 3;
    private GameBoard gameBoard;
    private Thread game;
    private int lowBirth = 2;
    private int highBirth = 3;
    private int lowSurvive = 3;
    private int highSurvive = 3;
    private boolean torus = false;
 
    public static void main(String[] args) {
        JFrame game = new ConwayView();
        
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setTitle("Conway's Game of Life");
        game.setMaximumSize(DEFAULT_WINDOW_SIZE);
        game.setMinimumSize(MINIMUM_WINDOW_SIZE);
        
        game.addComponentListener(new ComponentAdapter() {
        	public void componentResized(ComponentEvent evt) {
        		Dimension size = game.getSize();
        		Dimension min = game.getMinimumSize();
        		if (size.getWidth() < min.getWidth()) {
        			game.setSize((int)min.getWidth(), (int)size.getHeight());
        		}
        		if (size.getHeight() < min.getHeight()) {
        	          game.setSize((int) size.getWidth(), (int) min.getHeight());
        	        }
        	}
        });
        
        game.setSize(500,500);
       
        game.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - game.getWidth()), 
                (Toolkit.getDefaultToolkit().getScreenSize().height - game.getHeight()));
        game.setVisible(true);
        
    }
    public ConwayView() {
        menu = new JMenuBar();
        setJMenuBar(menu);
        file = new JMenu("File");
        menu.add(file);
        
        gameMenu = new JMenu("Game");
        menu.add(gameMenu);
       
        fileOptions = new JMenuItem("Options");
        fileOptions.addActionListener(this);
        fileRules = new JMenuItem("Rules");
        fileRules.addActionListener(this);
        fileExit = new JMenuItem("Exit");
        fileExit.addActionListener(this);
        
        file.add(fileOptions);
        file.add(fileOptions);
        file.add(new JSeparator());
        file.add(fileExit);
        
        gameTorus = new JMenuItem("Torus");
        gameTorus.addActionListener(this);
        
        gameAutofill = new JMenuItem("Autofill");
        gameAutofill.addActionListener(this);
        
        gamePlay = new JMenuItem("Play");
        gamePlay.addActionListener(this);
        
        gameStop = new JMenuItem("Stop");
        gameStop.setEnabled(false);
        gameStop.addActionListener(this);
        
        gameReset = new JMenuItem("Reset");
        gameReset.addActionListener(this);
        
        gameMenu.add(gameAutofill);
        gameMenu.add(gameTorus);
        gameMenu.add(new JSeparator());
        gameMenu.add(gamePlay);
        gameMenu.add(gameStop);
        gameMenu.add(gameReset);
        
        gameBoard = new GameBoard();
        add(gameBoard);
    }
 
    public void setGameBeingPlayed(boolean isBeingPlayed) {
        if (isBeingPlayed) {
            gamePlay.setEnabled(false);
            gameStop.setEnabled(true);
            game = new Thread(gameBoard);
            game.start();
        } else {
            gamePlay.setEnabled(true);
            gameStop.setEnabled(false);
            game.interrupt();
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(fileExit)) {
            System.exit(0);
        } else if (ae.getSource().equals(fileOptions)) {
            final JFrame optionsFrame = new JFrame();
            
            optionsFrame.setTitle("Options");
            optionsFrame.setSize(300,60);
            optionsFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - optionsFrame.getWidth())/2, 
                (Toolkit.getDefaultToolkit().getScreenSize().height - optionsFrame.getHeight())/2);
            optionsFrame.setResizable(false);
            
            JPanel optionsPanel = new JPanel();
            optionsPanel.setOpaque(false);
            optionsFrame.add(optionsPanel);
            optionsPanel.add(new JLabel("Number of moves per second:"));
            Integer[] secondOptions = {1,2,3,4,5,6,7,8,9,10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
            		20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            		30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
            		40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
            		50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
            		60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
            		70, 71, 72, 73, 74, 75, 76, 77, 78, 79,
            		80, 81, 82, 83, 84, 85, 86, 87, 88, 89,
            		90, 91, 92, 93, 94, 95, 96, 97, 98, 99,
            		100};
            final JComboBox cb_seconds = new JComboBox(secondOptions);
            
            optionsPanel.add(cb_seconds);
            
            cb_seconds.setSelectedItem(movesPerSecond);
            cb_seconds.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae) {
                    movesPerSecond = (Integer)cb_seconds.getSelectedItem();
                    optionsFrame.dispose();
                }
            });
            optionsFrame.setVisible(true);
        } else if (ae.getSource().equals(fileRules)) {
        	JFrame rulesFrame = new JFrame();
        	rulesFrame.setTitle("Rules");
        	rulesFrame.setSize(300,60);
        	rulesFrame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - rulesFrame.getWidth())/2, 
                    (Toolkit.getDefaultToolkit().getScreenSize().height - rulesFrame.getHeight())/2);
        	rulesFrame.setResizable(false);
        	
        	JPanel rulesPanel = new JPanel();
        	rulesPanel.setOpaque(false);
        	rulesFrame.add(rulesPanel);
        	rulesPanel.add(new JLabel("Low Birth Threshold:"));
        	
        	JTextField textField1= new JTextField(10);
        	rulesPanel.add(textField1);
        	rulesPanel.add(new JLabel("High Birth Threshold:"));
        	
        	JTextField textField2= new JTextField(10);
        	rulesPanel.add(textField2);
        	rulesPanel.add(new JLabel("Low Survive Threshold:"));
        	
        	JTextField textField3= new JTextField(10);
        	rulesPanel.add(textField3);
        	rulesPanel.add(new JLabel("High Survive Threshold:"));
        	
        	JButton apply = new JButton(String.valueOf("Apply"));
        	rulesPanel.add(apply);
        	
        	apply.addActionListener(new ActionListener() {
        		@Override
        		public void actionPerformed(ActionEvent ae) {
        			lowBirth = Integer.parseInt(textField1.getText());
        			highBirth = Integer.parseInt(textField2.getText());
        			lowSurvive = Integer.parseInt(textField3.getText());
        			lowSurvive = Integer.parseInt(textField3.getText());
        			
        			rulesFrame.dispose();
        		}
        	});
        	
        	Object[] options = {apply};
        	JTextField textField= new JTextField(10);
        	rulesPanel.add(textField);
        	
        	int result = JOptionPane.showOptionDialog(null, rulesPanel, "Thresholds:",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, null);
        	
            if (result == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, textField.getText());
        	}
            rulesFrame.dispose();
        	
        } else if (ae.getSource().equals(gameAutofill)) {
            int max = 100;
            int min = 1;
            
            gameBoard.resetBoard();
            gameBoard.randomlyFillBoard((int)(Math.random() * ((max - min)) + min));
        } else if (ae.getSource().equals(gameReset)) {
            gameBoard.resetBoard();
            gameBoard.repaint();
        } else if (ae.getSource().equals(gamePlay)) {
            setGameBeingPlayed(true);
        } else if (ae.getSource().equals(gameStop)) {
            setGameBeingPlayed(false);
        } else if (ae.getSource().equals(gameTorus)) {
        	if (gameTorus.getBackground().equals(Color.WHITE)) {
        		gameTorus.setBackground(Color.BLACK);
        		gameTorus.setForeground(Color.WHITE);
        		torus = true;
        	} else {
        		gameTorus.setBackground(Color.WHITE);
        		gameTorus.setForeground(Color.BLACK);
        		torus = false;
        	}
        }
        
    }

    private class GameBoard extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, Runnable {
        private Dimension gameBoardSize = null;
        private ArrayList<Point> point = new ArrayList<Point>(0);
 
        public GameBoard() {
            addComponentListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
        }
 
        private void updateArraySize() {
            ArrayList<Point> removeList = new ArrayList<Point>(0);
           
            for (Point currentPoint : point) {
                if ((currentPoint.x > gameBoardSize.width-1) || (currentPoint.y > gameBoardSize.height-1)) {
                    removeList.add(currentPoint);
                }
            }
            
            point.removeAll(removeList);
            repaint();
        }
 
        public void addPoint(int x, int y) {
            if (!point.contains(new Point(x,y))) {
                point.add(new Point(x,y));
            } 
            repaint();
        }
 
        public void addPoint(MouseEvent m) {
            int x = m.getPoint().x/BLOCK_SIZE-1;
            int y = m.getPoint().y/BLOCK_SIZE-1;
            if ((x >= 0) && (x < gameBoardSize.width) && (y >= 0) && (y < gameBoardSize.height)) {
                addPoint(x,y);
            }
        }
 
        public void resetBoard() {
            point.clear();
        }
 
        public void randomlyFillBoard(int percent) {
            for (int i=0; i<gameBoardSize.width; i++) {
                for (int j=0; j<gameBoardSize.height; j++) {
                    if (Math.random()*100 < percent) {
                        addPoint(i,j);
                    }
                }
            }
        }
 
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            try {
                for (Point newPoint : point) {
                    g.setColor(Color.BLUE);
                    g.fillRect(BLOCK_SIZE + (BLOCK_SIZE*newPoint.x), BLOCK_SIZE + (BLOCK_SIZE*newPoint.y), BLOCK_SIZE, BLOCK_SIZE);
                }
            } catch (ConcurrentModificationException cme) {}
            g.setColor(Color.BLACK);
            for (int i=0; i<=gameBoardSize.width; i++) {
                g.drawLine(((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE, (i*BLOCK_SIZE)+BLOCK_SIZE, BLOCK_SIZE + (BLOCK_SIZE*gameBoardSize.height));
            }
            for (int i=0; i<=gameBoardSize.height; i++) {
                g.drawLine(BLOCK_SIZE, ((i*BLOCK_SIZE)+BLOCK_SIZE), BLOCK_SIZE*(gameBoardSize.width+1), ((i*BLOCK_SIZE)+BLOCK_SIZE));
            }
        }
 
        @Override
        public void componentResized(ComponentEvent e) {
            gameBoardSize = new Dimension(getWidth()/BLOCK_SIZE-2, getHeight()/BLOCK_SIZE-2);
            updateArraySize();
        }
        @Override
        public void componentMoved(ComponentEvent e) {}
        
        @Override
        public void componentShown(ComponentEvent e) {}
        
        @Override
        public void componentHidden(ComponentEvent e) {}
        
        @Override
        public void mouseClicked(MouseEvent e) {}
        
        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {
            addPoint(e);
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {}
 
        @Override
        public void mouseExited(MouseEvent e) {}
 
        @Override
        public void mouseDragged(MouseEvent e) {
            addPoint(e);
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {}
 
        @Override
        public void run() {
            boolean[][] gameBoard = new boolean[gameBoardSize.width+2][gameBoardSize.height+2];
            for (Point current : point) {
            	if(torus && current.x == gameBoard.length - 3)
            	{
            		current.x = 0;
            	}
            	else if(torus && current.y == gameBoard[0].length - 3)
            	{
            		current.y = 0;
            	}
            	else if (torus && current.x == 0)
            	{
            		current.x = gameBoard.length - 3;
            	}
            	else if(torus && current.y == 0)
            	{
            		current.y = gameBoard[0].length - 3;
            	}
                gameBoard[current.x+1][current.y+1] = true;
            }
            ArrayList<Point> survivingCells = new ArrayList<Point>(0);
            for (int i=1; i<(gameBoard.length-1); i++) {
                for (int j=1; j<(gameBoard[0].length-1); j++) {
                    int surroundingCells = 0;
                    
                    if (gameBoard[i-1][j-1]) { surroundingCells++; }
                    if (gameBoard[i-1][j])   { surroundingCells++; }
                    if (gameBoard[i-1][j+1]) { surroundingCells++; }
                    if (gameBoard[i][j-1])   { surroundingCells++; }
                    if (gameBoard[i][j+1])   { surroundingCells++; }
                    if (gameBoard[i+1][j-1]) { surroundingCells++; }
                    if (gameBoard[i+1][j])   { surroundingCells++; }
                    if (gameBoard[i+1][j+1]) { surroundingCells++; }
                    if (gameBoard[i][j]) {
                        if (surroundingCells >= lowBirth && surroundingCells <= highBirth){
                            survivingCells.add(new Point(i-1,j-1));
                        } 
                    } else {
                        if (surroundingCells >= lowSurvive && surroundingCells <= highSurvive) {
                            survivingCells.add(new Point(i-1,j-1));
                        }
                    }
                    
                }
            }
            resetBoard();
            point.addAll(survivingCells);
            repaint();
            try {
                Thread.sleep(1000/movesPerSecond);
                run();
            } catch (InterruptedException ex) {}
        }
    }
}