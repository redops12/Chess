import javax.swing.*;
import java.awt.*;


class StartScreen{


	static void main(String[] args){
		JFrame frame;
		JTextField name;
		JButton start;
		
		frame = new JFrame();
		frame.setTitle("New Chess Game");
		frame.setSize(384,64);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		name = new JTextField("Name");
		frame.add(name);
		frame.setVisible(true);
	}	
}	
