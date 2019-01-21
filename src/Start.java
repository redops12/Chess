package Chess;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class StartScreen{
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextField name = new JTextField("Name");
		JButton start = new JButton("Start Game");
		JRadioButton black = new JRadioButton("Black Side");
		JRadioButton white = new JRadioButton("White Side");
		ButtonGroup group = new ButtonGroup();
		group.add(white);
		group.add(black);
		white.setSelected(true);
		start.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				Side side = Side.BLACK_SIDE;
				if (white.isSelected()){
					side = Side.WHITE_SIDE;
				}
				Window window = new Window(side);
			}
		});
		frame.setTitle("New Chess Game");
		frame.setSize(new Dimension(600,400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(name);
		panel.add(white);
		panel.add(black);
		panel.add(start);
		// frame.pack();
		frame.add(panel);
		frame.setVisible(true);
	}	
}	
