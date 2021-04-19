import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;


/*
 * By Caden R. and Liam B.
 * 
 * 
 * ####TO DO####
 * Set difficulty
 * Highscore
 */

@SuppressWarnings("serial")
public class Minesweeper extends JFrame{

	private JButton[][] buttons;
	private JLabel statsLabel;
	private boolean gameOver;
	private int size;
	private int mines;
	private int[][] values;
	private boolean set;
	private JButton reset;
	private JButton time;
	private static Timer timer;
	private int seconds;
//	private JButton easy;
//	private JButton normal;
//	private JButton hard;
	
	public Minesweeper() {
		super("Minesweeper");
		size = 16;
		mines = 40;
		set = false;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(makeMainPanel());
		pack();
		setVisible(true);
		
		
	}
	//makes the jbuttons and panels
	public JPanel makeMainPanel() {
		

		values = new int[size][size];
		buttons = new JButton[size][size];
		JPanel buttonPanel = new JPanel(new GridLayout(size,size));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		Listener listener = new Listener();

		JPanel resetPanel = new JPanel();
        reset = new JButton();
		reset.setPreferredSize(new Dimension(200,80));
		reset.addActionListener(listener);
		resetPanel.add(reset);
		reset.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		reset.setText("Reset");
		
		JButton filler = new JButton();
		filler.setPreferredSize(new Dimension(80,80));
		filler.setOpaque(false);
		filler.setContentAreaFilled(false);
		filler.setBorderPainted(false);
		filler.setEnabled(false);
		resetPanel.add(filler);
		
        time = new JButton();
        time.setPreferredSize(new Dimension(200,80));
        resetPanel.add(time);
        time.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		time.setText("0");
		//every second updates the time button
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	time.setText(Integer.toString(seconds));
            	seconds++;
            }
          });
        timer.setInitialDelay(0);     
        
//		JButton filler2 = new JButton();
//		filler2.setPreferredSize(new Dimension(80,80));
//		filler2.setOpaque(false);
//		filler2.setContentAreaFilled(false);
//		filler2.setBorderPainted(false);
//		filler2.setEnabled(false);
//		resetPanel.add(filler2);
//		
//        easy = new JButton();
//        easy.setPreferredSize(new Dimension(200,80));
//		easy.addActionListener(listener);
//		easy.setFont(new Font("Lucida Grande", Font.BOLD, 40));
//		easy.setText("Easy");
//		resetPanel.add(easy);
//		
//        normal = new JButton();
//        normal.setPreferredSize(new Dimension(200,80));
//		normal.addActionListener(listener);
//		normal.setFont(new Font("Lucida Grande", Font.BOLD, 40));
//		normal.setText("Normal");
//		resetPanel.add(normal);
//		
//        hard = new JButton();
//        hard.setPreferredSize(new Dimension(200,80));
//		hard.addActionListener(listener);
//		hard.setFont(new Font("Lucida Grande", Font.BOLD, 40));
//		hard.setText("Hard");
//		resetPanel.add(hard);
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				buttons[i][j] = new JButton("");
				buttons[i][j].setPreferredSize(new Dimension(80,80));
				buttons[i][j].addActionListener(listener);
				//Checks for a left click to place and remove a flag
	            buttons[i][j].addMouseListener(new MouseAdapter() {
	            	public void mouseClicked(MouseEvent event){
	            		JButton button = (JButton) event.getSource();
	            	
	            		ImageIcon flag = new ImageIcon("flag.png");
	            		Image image = flag.getImage();
	            		Image newimg = image.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH);
	            		flag = new ImageIcon(newimg);
	            		
	            	    if (event.getButton() == MouseEvent.BUTTON3 && gameOver == false) {
	            	    	button.setFont(new Font("Lucida Grande", Font.BOLD, 40));
            	    	
	            	    	if(button.getText().equals("") && button.getIcon() == null && set == true) {
	    	            		button.setIcon(flag);

	            	    		//button.setText("F");
	            	    	}
	            	    	else if(set == true) {
	    	            		button.setIcon(null);
	    	            		
	            	    		//button.setText("");
	            	    	}
	            	    	
	            	    	}
	            	  }
	            });
				buttonPanel.add(buttons[i][j]);
			}
		}
		
		statsLabel = new JLabel(" ");
		JPanel statsPanel = new JPanel();
		statsPanel.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
		statsPanel.add(statsLabel);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buttonPanel, BorderLayout.CENTER);
		mainPanel.add(statsPanel, BorderLayout.SOUTH);
		mainPanel.add(resetPanel, BorderLayout.NORTH);
		return mainPanel;
	}
	//sets up the mines and values for each space
	public void setup(int fRow, int fCol) {
		
		set = true;
		int mineC = 0;
		int row = 0;
		int col = 0;
		seconds = 0;
		
		//randomly places mines in any spot but around where your first click is
		while(mineC < mines) {
			row = (int)(Math.random() * size);
			col = (int)(Math.random() * size);
			if(values[row][col] != 9 && 
					(row != fRow || col != fCol) &&
					(row != fRow - 1 || col != fCol - 1) &&
					(row != fRow || col != fCol - 1) &&
					(row != fRow + 1 || col != fCol - 1) &&
					(row != fRow - 1 || col != fCol) && 
					(row != fRow + 1 || col != fCol) &&
					(row != fRow - 1 || col != fCol + 1) &&
					(row != fRow || col != fCol + 1) &&
					(row != fRow + 1 || col != fCol + 1))
			{
				values[row][col] = 9;
				mineC++;
			}
			
		////neighbor counts(how many mines are touching this square.)
			//Got this on stack overflow from Baileyr12
			for (row = 0; row < size; row++) {
			    for (col = 0; col < size; col++) {
			        int neighborCount = 0;
			        if (values[row][col] != 9) {
			            if(row > 0 && col > 0 && values[row - 1][col - 1] == 9) { //a 9 is up & left
			                neighborCount++;
			            }
			            if(col > 0 && values[row][col - 1] == 9) { //a 9 is up
			                neighborCount++;
			            }
			            if(row < size - 1 && col > 0 && values[row + 1][col - 1] == 9) { // a 9 is left
			                neighborCount++;
			            }
			            if(row > 0 && values[row - 1][col] == 9) { //left
			                neighborCount++;
			            }
			            if(row < size - 1 && values[row + 1][col] == 9) { //9 is right
			                neighborCount++;
			            }
			            if(row > 0 && col < size - 1 && values[row - 1][col + 1] == 9) { //9 is down
			                neighborCount++;
			            }
			            if(col < size - 1 && values[row][col + 1]== 9) {//9 is up right
			                neighborCount++;
			            }
			            if(row < size - 1 && col < size - 1 && values[row + 1][col + 1]== 9) {//9 is down left
			                neighborCount++;
			            }
			            values[row][col] = neighborCount;
			        }
			    }
			}
		}
	}
	
	private class Listener implements ActionListener {
        @Override
       
        public void actionPerformed(ActionEvent event) {
        	
        	JButton button = (JButton) event.getSource();
        
        	if(!button.getText().equals("Reset")) {
        		
	            if(!gameOver) {
	            	time.setText(Integer.toString(seconds));
	            	int row = 0;
	            	int col = 0;
	            	//finds the location of the clicked button
	            	for(int i = 0; i < size; i++) {
	            		for(int q = 0; q < size; q++) {
	            			if(buttons[i][q] == button) {
	            				row = i;
	            				col = q;
	            			}
	            		}
	            	}
	            	if(set == false){
	            		timer.start();
	            		setup(row,col);
	            	}
	            	if(values[row][col] == 9 && button.getIcon() == null){
	            		gameOver(row,col);
	            	}
	            	if(values[row][col] == 0 && (button.getIcon() == null)) {
	            		onZero(row,col);
	            	}
	            	else if(button.getText().equals("") && button.getIcon() == null) {
	            		button.setFont(new Font("Lucida Grande", Font.BOLD, 40));
	            		button.setBackground(Color.white);
	            		buttons[row][col].setForeground(getColor(values[row][col]));
	            		button.setText(Integer.toString(values[row][col]));
	            		checkWin();
	            	}
	            }
        	}else {			
	            for (int i = 0; i < size; i++) {
	            	for (int q = 0; q < size; q++) {
	            		//Resets all of the buttons 
	            		values[i][q] = 0;
	            		buttons[i][q].setText("");
	            		buttons[i][q].setBackground(null);
	            		buttons[i][q].setIcon(null);
	            		set = false;
	            		timer.stop();
	            		gameOver = false;
	            		statsLabel.setText("");
	            		time.setText("0");
	            		    }
	            		}
        			}  	
        }
	}	

    //Checks if all of the non bomb spaces have been revealed    
	public void checkWin() {
		
		int count = 0;
		for(int row = 0; row < size; row++) {
			for(int col = 0; col < size; col++) {
				if(buttons[row][col].getBackground().equals(Color.white)) {
					count++;
				}
			}
		}
		if(count == ((size * size) - mines)) {
			statsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 40));
			statsLabel.setText("You Win!");
			gameOver = true;
			timer.stop();
			for(int row = 0; row < size; row++) {
				for(int col = 0; col < size; col++) {
					if(!buttons[row][col].getBackground().equals(Color.white)) {
						setIcon("flag.png", row, col);
					}
				}
			}
		}
	}
	//Checks if game is over and reveals all bombs if true
	public void gameOver(int row, int col) {
		gameOver = true;
		for(int i = 0; i < size; i++) {
			for(int q = 0; q < size; q++) {
				if(values[i][q] == 9 && buttons[i][q].getIcon() == null) {
            		buttons[i][q].setFont(new Font("Lucida Grande", Font.BOLD, 40));
            		buttons[i][q].setForeground(getColor(values[i][q]));
            		if(i == row && q == col) {
            			//buttons[i][q].setBackground(Color.red);
            			setIcon("ExplodeBomb.jpg", i, q);
            		}else{
            			setIcon("DodgedBomb.jpg",i,q);
            			//buttons[i][q].setBackground(Color.white);
            		}
            		//buttons[i][q].setText("9");
				}
				if(values[i][q] != 9 &&  buttons[i][q].getIcon() != null) {
					setIcon("WrongBomb.jpg", i, q);
				}
			}
		}
		timer.stop();
		statsLabel.setFont(new Font("Lucida Grande", Font.BOLD, 40));
		statsLabel.setText("Game Over!");
	}
	//Changes Color of the number depending on what number it is
	public Color getColor(int number) {
		Color navy = Color.decode("#000080");
		Color dark_red = Color.decode("#8b0000");
		Color green = Color.decode("#008000");
		if(number == 0) {
			return Color.white;
		}
		if(number == 1) {
			return Color.blue;
		}
		if(number == 2) {
			return green;
		}
		if(number == 3) {
			return Color.red;
		}
		if(number == 4) {
			return navy;
		}
		if(number == 5) {
			return dark_red;
		}
		if(number == 6) {
			return Color.cyan;
		}
		if(number == 7) {
			return Color.magenta;
		}
		if(number == 8) {
			return Color.black;
		}
		return null;
	}
	

	//Changes the Icon of button at given cords to the image with the given name
	public void setIcon(String name, int row, int col) {
		ImageIcon bomb = new ImageIcon(name);
		Image image = bomb.getImage();
		Image newimg = image.getScaledInstance(80,80, java.awt.Image.SCALE_SMOOTH);
		bomb = new ImageIcon(newimg);
		buttons[row][col].setIcon(bomb);
		buttons[row][col].setText("");
	}
	//If you click on a zero you reveal all the spaces around it and if another zero is revealed it repeats for that zero
	public void onZero(int row, int col) {
		
		buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
		buttons[row][col].setForeground(Color.white);
		buttons[row][col].setBackground(Color.white);
		buttons[row][col].setText(Integer.toString(values[row][col]));
		checkWin();
		
            if(row > 0 && col > 0 && values[row - 1][col - 1] == 0) {
        		if(buttons[row-1][col-1].getText().equals("")) {
        			onZero(row-1,col-1);
        		}
            }else if(row > 0 && col > 0 ){
            	buttons[row-1][col-1].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row-1][col-1].setBackground(Color.white);
        		buttons[row-1][col-1].setForeground(getColor(values[row-1][col-1]));
        		buttons[row-1][col-1].setText(Integer.toString(values[row-1][col-1]));
        		checkWin();
            }
            if(col > 0 && values[row][col - 1] == 0) {
        		if(buttons[row][col-1].getText().equals("")) {
        			onZero(row,col-1);
        		}
            }else if(col > 0){
            	buttons[row][col-1].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row][col-1].setBackground(Color.white);
        		buttons[row][col-1].setForeground(getColor(values[row][col-1]));
        		buttons[row][col-1].setText(Integer.toString(values[row][col-1]));
        		checkWin();
        		
            }
            if(row < size - 1 && col > 0 && values[row + 1][col - 1] == 0) {
        		if(buttons[row+1][col-1].getText().equals("")) {
        			onZero(row+1,col-1);
        		}
            }else if(row < size - 1 && col > 0){
            	buttons[row+1][col-1].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row+1][col-1].setBackground(Color.white);
        		buttons[row+1][col-1].setForeground(getColor(values[row+1][col-1]));
        		buttons[row+1][col-1].setText(Integer.toString(values[row+1][col-1]));
        		checkWin();
            }
            if(row > 0 && values[row - 1][col] == 0) {
        		if(buttons[row-1][col].getText().equals("")) {
        			onZero(row-1,col);
        		}
            }else if(row > 0){
            	buttons[row-1][col].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row-1][col].setBackground(Color.white);
        		buttons[row-1][col].setForeground(getColor(values[row-1][col]));
        		buttons[row-1][col].setText(Integer.toString(values[row-1][col]));
        		checkWin();
            }
            if(row < size - 1 && values[row + 1][col] == 0) {
        		if(buttons[row+1][col].getText().equals("")) {
        			onZero(row+1,col);
        		}
            }else if(row < size - 1){
            	buttons[row+1][col].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row+1][col].setBackground(Color.white);
        		buttons[row+1][col].setForeground(getColor(values[row+1][col]));
        		buttons[row+1][col].setText(Integer.toString(values[row+1][col]));
        		checkWin();
            }
            if(row > 0 && col < size - 1 && values[row - 1][col + 1] == 0) {
        		if(buttons[row-1][col+1].getText().equals("")) {
        			onZero(row-1,col+1);
        		}
            }else if(row > 0 && col < size - 1 ){
            	buttons[row-1][col+1].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row-1][col+1].setBackground(Color.white);
        		buttons[row-1][col+1].setForeground(getColor(values[row-1][col+1]));
        		buttons[row-1][col+1].setText(Integer.toString(values[row-1][col+1]));
        		checkWin();
            }
            if(col < size - 1 && values[row][col + 1] == 0) {
        		if(buttons[row][col+1].getText().equals("")) {
        			onZero(row,col+1);
        		}
            }else if(col < size - 1){
            	buttons[row][col+1].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row][col+1].setBackground(Color.white);
        		buttons[row][col+1].setForeground(getColor(values[row][col+1]));
        		buttons[row][col+1].setText(Integer.toString(values[row][col+1]));
        		checkWin();
            }
            if(row < size - 1 && col < size - 1 && values[row + 1][col + 1] == 0) {
        		if(buttons[row+1][col+1].getText().equals("")) {
        			onZero(row+1,col+1);
        		}
            }else if(row < size - 1 && col < size - 1){
            	buttons[row+1][col+1].setFont(new Font("Lucida Grande", Font.BOLD, 40));
        		buttons[row+1][col+1].setBackground(Color.white);
        		buttons[row+1][col+1].setForeground(getColor(values[row+1][col+1]));
        		buttons[row+1][col+1].setText(Integer.toString(values[row+1][col+1]));
        		checkWin();
            }
        }
	
	
	public static void main(String[] args) {
		new Minesweeper();
	}
	
}




