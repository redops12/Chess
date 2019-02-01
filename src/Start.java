package Chess;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


class Start{
	//game setup and preferences
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135,206,250));
		frame.setTitle("New Chess Game");
		frame.setPreferredSize(new Dimension(350,150));
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


		JTextField name_white = new JTextField("White     ");
		constraints.gridx = 2;
		constraints.gridy = 2;
		panel.add(name_white, constraints);
		
		JTextField name_black = new JTextField("Black     ");
		constraints.gridx = 3;
		constraints.gridy = 2;
		panel.add(name_black, constraints);
		
		JButton start = new JButton("Start Game");
		
		File[] boardFiles = null;
		try {
			boardFiles = BoardIO.availableBoards();
		} catch (Exception e){}
		String[] boardNames = new String[boardFiles.length];
		for (int i = 0; i<boardFiles.length; i++){
			boardNames[i] = boardFiles[i].getName();
		}
		
		JComboBox boards = new JComboBox(boardNames);
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
					System.out.println(boards.getSelectedItem().toString());
					startingBoard = BoardIO.readBoard(boards.getSelectedItem().toString());
				} catch (Exception ex){
					ex.printStackTrace();
				}
				Window window = new Window(name_white.getText(), name_black.getText(), startingBoard);
				frame.setVisible(false);
			}
		});

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}	
}	
