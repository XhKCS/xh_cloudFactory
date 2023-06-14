package com.factory.view;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BackgroundPane extends JPanel{
	private ImageIcon imageIcon = new ImageIcon("images/background4.png");
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}



	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//g.drawImage(imageIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
	}



	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(500, 150, 600, 800);
		frame.setContentPane(new BackgroundPane());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);

	}

}
