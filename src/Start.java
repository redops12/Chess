package Chess;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class StartScreen{
	//game setup and preferences
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135,206,250));
		frame.setTitle("New Chess Game");
		frame.setPreferredSize(new Dimension(300,150));
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


		JTextField name_white = new JTextField("White Name");
		constraints.gridx = 2;
		constraints.gridy = 2;
		panel.add(name_white, constraints);
		
		JTextField name_black = new JTextField("Black Name");
		constraints.gridx = 3;
		constraints.gridy = 2;
		panel.add(name_black, constraints);
		
		JButton start = new JButton("Start Game");
		start.addActionListener(new ActionListener(){
		
			@Override
			public void actionPerformed(ActionEvent e) {
				Window window = new Window(name_white.getText(), name_black.getText());
				frame.setVisible(false);
			}
		});
		constraints.gridx = 3;
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		panel.add(start, constraints);

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}	
}	
