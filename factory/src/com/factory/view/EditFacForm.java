package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.Factory;
import com.factory.model.Product;
import com.factory.model.ProductCategory;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditFacForm extends JFrame {
	private FOMainForm backForm;
	private Factory myFactory;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField descripField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//EditFacForm frame = new EditFacForm();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditFacForm(FOMainForm backForm) {
		this.backForm = backForm;
		this.myFactory = backForm.getMyFactory();
		
		this.setTitle("修改工厂信息界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 451, 351);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("您可选择修改以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 17));
		mainLabel.setBounds(113, 10, 242, 44);
		contentPane.add(mainLabel);
		
		JLabel facNameLabel = new JLabel("工厂名称");
		facNameLabel.setFont(new Font("SimSun", Font.BOLD, 16));
		facNameLabel.setBounds(32, 96, 87, 35);
		contentPane.add(facNameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(122, 92, 266, 44);
		contentPane.add(nameField);
		nameField.setColumns(10);
		nameField.setText(myFactory.getName());
		
		JLabel lblNewLabel = new JLabel("工厂简介");
		lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 16));
		lblNewLabel.setBounds(32, 166, 97, 35);
		contentPane.add(lblNewLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 16));
		descripField.setBounds(122, 163, 266, 44);
		contentPane.add(descripField);
		descripField.setColumns(10);
		descripField.setText(myFactory.getDescription());
		
		JButton editButton = new JButton("确认修改");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String facName = nameField.getText();
				String facDes = descripField.getText(); 
				if (facName.equals("")) {
					JOptionPane.showMessageDialog(null, "工厂名称不能为空！");
				}
//				else if (facDes.equals("")) {
//					JOptionPane.showMessageDialog(null, "工厂简介不能为空！");
//				}
				else {
					myFactory.setName(facName);
					myFactory.setDescription(facDes);
					backForm.updateData();
					backForm.updateView();
					JOptionPane.showMessageDialog(null, "修改成功！");
					dispose();
				}
			}
		});
		editButton.setFont(new Font("SimSun", Font.BOLD, 16));
		editButton.setToolTipText("");
		editButton.setBounds(98, 251, 106, 35);
		contentPane.add(editButton);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 16));
		cancleButton.setBounds(258, 254, 97, 32);
		contentPane.add(cancleButton);
		
		this.setVisible(true);
	}
}
