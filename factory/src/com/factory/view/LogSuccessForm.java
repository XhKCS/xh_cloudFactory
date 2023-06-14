package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogSuccessForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new LogSuccessForm();
	}

	/**
	 * Create the frame.
	 */
	public LogSuccessForm() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 300, 350, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("登录成功！正在跳转——");
		lblNewLabel.setBounds(69, 32, 227, 123);
		lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 16));
		contentPane.add(lblNewLabel);
		
		this.setVisible(true);
		
		// 设置定时器，延迟4秒后关闭窗体
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭窗体
            	try {
					Thread.sleep(3000);
					dispose();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        timer.setRepeats(false); // 设置为仅触发一次
        timer.start();
		
	}
}
