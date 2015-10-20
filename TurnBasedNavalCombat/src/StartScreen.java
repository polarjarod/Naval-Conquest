import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;


public class StartScreen implements ActionListener{
	private boolean run;
	private JFrame frame;
	private int w,h;
	
	
	/**
	 * Create the application.
	 */
	public StartScreen(int w1,int h1) {
		w = w1;
		h = h1;
		run = true;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	
		StartPhoto p = new StartPhoto(w,h);
		frame.setBounds(w/3, h/3, w/3, h/3);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().add(p, BorderLayout.CENTER);
		JLabel lblNavalConquestTurnbased = new JLabel("Naval Conquest Turn-Based Strategy");
		lblNavalConquestTurnbased.setBackground(Color.BLACK);
		lblNavalConquestTurnbased.setForeground(Color.BLACK);
		lblNavalConquestTurnbased.setFont(new Font("AR DESTINE", Font.BOLD, 20));
		lblNavalConquestTurnbased.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNavalConquestTurnbased, BorderLayout.NORTH);
		
		JButton btnPlayGame = new JButton("Play Game");
		btnPlayGame.setBackground(Color.RED);
		btnPlayGame.setForeground(Color.BLACK);
		btnPlayGame.setFont(new Font("AR DESTINE", Font.BOLD, 20));
		btnPlayGame.addActionListener(this);
		frame.getContentPane().add(btnPlayGame, BorderLayout.SOUTH);
}

	public boolean run(){
		return run;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		run = false;
		try{
			Thread.sleep(1000);
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}
}
