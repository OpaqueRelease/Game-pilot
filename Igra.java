import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.util.*;

public class Igra extends JFrame implements MouseMotionListener, MouseListener{
static final long serialVersionUID = 1; 
static int xDimension = 800;
static int yDimension = 500;
static int padding = 100;
static int nFields;
static int mouseX = -1;
static int mouseY = -1;
static int nOfRedFields = 0;
static boolean endGame = false;
static boolean[][] coloring; //keeps color of fields

	public Igra(){
		// let's make the window a bit bigger than the field
		setSize(new Dimension(xDimension + 2 * padding, yDimension + 2 * padding));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		

		JPanel p = new JPanel() {
			
			public void paint(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				
				
				// Color the field
				for (int i = 0; i < nFields; i++) {
					for (int j = 0; j < nFields; j++) {
						if (coloring[i][j]) {
							if(mouseX == i && mouseY == j) { //track the mouse
								g2.setColor(Color.MAGENTA.darker());
							}else {
								g2.setColor(Color.MAGENTA);
							}
						}else {
							if(mouseX == i && mouseY == j) { //track the mouse
								g2.setColor(Color.DARK_GRAY.brighter());
							}else {
								g2.setColor(Color.DARK_GRAY);
							}
						}
						
						g2.fillRect(i * xDimension / nFields + padding, j * yDimension / nFields + padding, xDimension / nFields , yDimension / nFields );
					}
				}
				
				//draw the field
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(3));
				
				//vertical lines
				for (int i = 0; i <= nFields; i++) {
					int measure = xDimension / nFields * i;
					g2.drawLine(measure + padding, 0 + padding, measure + padding, yDimension + padding);
					
				}
				
				//and horizontal lines
				for (int i = 0; i <= nFields; i++) {
					int measure = yDimension / nFields * i;
					g2.drawLine(0 + padding, measure + padding, xDimension + padding, measure + padding);
					
				}
				
				
				if(endGame) {
					//JOptionPane.showMessageDialog(null, "You win! Good Job!","Game Over", JOptionPane.ERROR_MESSAGE);
					g2.setColor(Color.RED);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 100));
					g2.drawString("Victory!", (xDimension + 2*padding)/3, (yDimension + 2*padding)/2);
				}
			}
		};
		
		setTitle("A game");
		Container c = this.getContentPane();
		c.add(p);
		c.addMouseMotionListener(this);
		c.addMouseListener(this);
	}
	
	public void mouseMoved(MouseEvent e){ //track the mouse and repaint
		int x = e.getX() - padding;
		x = x / (xDimension / nFields);
		if (x < 0 || x >= nFields) {
			mouseX = -1;
		}else {
			mouseX = x;
		}
		
		int y = e.getY() - padding;
		y = y / (yDimension / nFields);
		if (y < 0 || y >= nFields) {
			mouseY = -1;
		}else {
			mouseY = y;
		}
		repaint();
	}
	
	public void mouseDragged(MouseEvent e){
		//do nothing
	}
	public void mousePressed(MouseEvent e) {
		//do nothing
	}
	public void mouseReleased(MouseEvent e) {
		if (!endGame && e.getButton() == MouseEvent.BUTTON1) {
			action();
		}
	}
	public void mouseClicked(MouseEvent e) {
		//do nothing
	}
	public void mouseExited(MouseEvent e) {
		//do nothing
	}
	public void mouseEntered(MouseEvent e) {
		//do nothing
	}
	
	public void action() { //makes an action
		if(mouseX > - 1 && mouseY > -1 && mouseX < nFields && mouseY < nFields) { //check if we are inside the field
			boolean Ux = mouseX > 0;
			boolean Ox = mouseX < nFields - 1;
			boolean Uy = mouseY > 0;
			boolean Oy = mouseY < nFields - 1;
			
			//flip the fields
			
			//central
			if(coloring[mouseX][mouseY]) {
				coloring[mouseX][mouseY] = false;
				nOfRedFields--;
			}else {
				coloring[mouseX][mouseY] = true;
				nOfRedFields++;
			}
			
			//under X
			if(Ux) {
				if(coloring[mouseX - 1][mouseY]) {
					coloring[mouseX - 1][mouseY] = false;
					nOfRedFields--;
				}else {
					coloring[mouseX - 1][mouseY] = true;
					nOfRedFields++;
				}
			}
			
			//under Y
			if(Uy) {
				if(coloring[mouseX][mouseY - 1]) {
					coloring[mouseX][mouseY - 1] = false;
					nOfRedFields--;
				}else {
					coloring[mouseX][mouseY - 1] = true;
					nOfRedFields++;
				}
			}
			
			//Over X
			if(Ox) {
				if(coloring[mouseX + 1][mouseY]) {
					coloring[mouseX + 1][mouseY] = false;
					nOfRedFields--;
				}else {
					coloring[mouseX + 1][mouseY] = true;
					nOfRedFields++;
				}
			}
			
			//Over Y
			if(Oy) {
				if(coloring[mouseX][mouseY + 1]) {
					coloring[mouseX][mouseY + 1] = false;
					nOfRedFields--;
				}else {
					coloring[mouseX][mouseY + 1] = true;
					nOfRedFields++;
				}
			}
			
			
			if (nOfRedFields == 0) {
				endGame = true;
			}
			repaint();
		}
	}
	

	
	public static void main (String arg[]) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		Random r = new Random();
		System.out.println("Enter the field size!");
		nFields = sc.nextInt();
		coloring = new boolean[nFields][nFields];
		//create the game array
		for (int i = 0; i < nFields; i++) {
			for (int j = 0; j < nFields; j++) {
				int color = r.nextInt(2);
				if (color < 1) {
					coloring[i][j] = true;
					nOfRedFields++;
				}
			}
		}
		//create and run the game
		new Igra();
		
		
	
	}
	

}