package Chess;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import javax.swing.event.ChangeListener;


class Start{
	//game setup and preferences
	public synchronized static void main(String[] args){
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135,206,250));
		frame.setTitle("New Chess Game");
		frame.setPreferredSize(new Dimension(500,150));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.weighty = 1.0;
		constraints.weightx = 1.0;

		JLabel label  = new JLabel("Game Settings");
		constraints.gridx = 1;
		constraints.gridy = 1;
		panel.add(label, constraints);

		JLabel whiteSide  = new JLabel("White");
		constraints.gridx = 2;
		constraints.gridy = 1;
		panel.add(whiteSide, constraints);
		
		JLabel blackSide = new JLabel("Black");
		constraints.gridx = 3;
		constraints.gridy = 1;
		panel.add(blackSide, constraints);


		JTextField nameWhite = new JTextField("White     ");
		constraints.gridx = 2;
		constraints.gridy = 2;
		panel.add(nameWhite, constraints);
		
		JTextField nameBlack = new JTextField("Black     ");
		constraints.gridx = 3;
		constraints.gridy = 2;
		panel.add(nameBlack, constraints);
		
		JButton start = new JButton("Start Game");
		
		File[] boardFiles = {new File("resources" + File.separator + "boards" + File.separator + "DefaultBoard.brd")};
		try {
			boardFiles = BoardIO.availableBoards();
		} catch (Exception e){}
		String[] boardNames = new String[boardFiles.length];
		for (int i = 0; i<boardFiles.length; i++){
			boardNames[i] = boardFiles[i].getName().replace(".brd", "");
		}
		
		JComboBox<String> boards = new JComboBox<String>(boardNames);
		boards.setSelectedItem("DefaultBoard");
		constraints.gridx = 1;
		constraints.gridy = 3;
		panel.add(boards, constraints);

		JButton custom = new JButton("Custom Board");
		constraints.gridx = 2;
		constraints.gridy = 3;
		panel.add(custom, constraints);
		custom.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				BoardMaker customBoard = new BoardMaker();
				try {
					wait(1000);
				} catch (InterruptedException ie){
					File[] boardFiles = {new File("resources" + File.separator + "boards" + File.separator + "DefaultBoard.brd")};
					try {
						boardFiles = BoardIO.availableBoards();
					} catch (Exception exc){}
					boards.removeAll();
					for (int i = 0; i<boardFiles.length; i++){
						boards.addItem(boardFiles[i].getName().replace(".brd", ""));
					}					
				}
			}
		});
		
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		panel.add(start, constraints);
		
		start.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				Piece[][] startingBoard = null;
				try {
					startingBoard = BoardIO.readBoard(boards.getSelectedItem().toString() + ".brd");
				} catch (Exception ex){
					ex.printStackTrace();
				}
				Window window = new Window(nameWhite.getText(), nameBlack.getText(), startingBoard);
				frame.setVisible(false);
			}
		});

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}	
}	
