import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MayinTarlasi implements MouseListener{
	
	JFrame frame;
	Buttons[][] board = new Buttons[10][10];
	int openedBtn;
	
	public MayinTarlasi() {
		openedBtn=0;
		frame = new JFrame();
		frame.setSize(800,800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(10,10));
		
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[0].length;col++) {
				Buttons b = new Buttons(row, col);
				frame.add(b);
				b.addMouseListener(this);
				board[row][col]=b;
			}
		}
		
		mayinUret();
		countBelirleme();
		//print();
		//printMine();
		
		frame.setVisible(true);
	}
		
	public void mayinUret() {
		int i=0;
		while(i<10) {
			int randRow = (int)(Math.random() * board.length);
			int randCol = (int)(Math.random() * board[0].length);
			
			while(board[randRow][randCol].isMine()) {
				randRow = (int)(Math.random() * board.length);
				randCol = (int)(Math.random() * board[0].length);
			}
			
			board[randRow][randCol].setMine(true); 
			i++;
		}
	}
	
	public void print() {
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[0].length;col++) {
				if(board[row][col].isMine()) {
					System.out.println(row+"-"+col);
					board[row][col].setIcon(new ImageIcon("mine.png"));
				}else {
					board[row][col].setText(board[row][col].getCount()+"");
					board[row][col].setEnabled(false);
				}
			}
		}
	}
	
	public void countBelirleme() {
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[0].length;col++) {
				if(board[row][col].isMine()) {
					counting(row,col);
				}
			}
		}
	}
	
	public void counting(int row,int col) {
		for(int i=row-1; i <= row+1; i++) {
			for(int j=col-1; j <= col+1;j++) {
				try{
					int value = board[i][j].getCount();
					board[i][j].setCount(++value);
				}catch(Exception e) {
					
				}
			}
		}
	}
	
	public void open(int r,int c) {
		if(r < 0 || r >= board.length || c<0 || c >= board[0].length || board[r][c].getText().length() > 0 || board[r][c].isEnabled()==false) {
			return;
		}else if(board[r][c].getCount() != 0) {
			board[r][c].setText(board[r][c].getCount()+"");
			board[r][c].setEnabled(false);
			openedBtn++;
		}else {
			openedBtn++;
			board[r][c].setEnabled(false);
			open(r-1, c);
			open(r+1, c);
			open(r-1, c-1);
			open(r-1, c+1);
		}
	}
	
	public void printMine() {
		for(int row=0;row<board.length;row++) {
			for(int col=0;col<board[0].length;col++) {
				if(board[row][col].isMine()) {
					board[row][col].setIcon(new ImageIcon("mine.png"));
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Buttons b = (Buttons)e.getComponent();
		if(e.getButton()==1) {
			//System.out.println("Sol Týklandý");
			if(b.isMine()) {
				JOptionPane.showMessageDialog(frame, "Mayýna Bastýnýz Oyun Bitti !");
				print();
			}else {
				open(b.getRow(), b.getCol());
				if(openedBtn == (board.length*board[0].length-10)) {
					JOptionPane.showMessageDialog(frame, "Tebrikler Oyunu Kazandýnýz !");
					print();
				}
			}
		}else if(e.getButton()==3) {
			//System.out.println("Sað Týklandý");
			if(!b.isFlag()) {
				b.setIcon(new ImageIcon("flag.png"));
				b.setFlag(true);
			}else {
				b.setIcon(null);
				b.setFlag(false);
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
