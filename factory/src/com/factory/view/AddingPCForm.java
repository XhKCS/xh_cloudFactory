package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.service.Service;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddingPCForm extends JFrame {
	private PCManaForm backForm;
	
	private JPanel contentPane;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//AddingPCForm frame = new AddingPCForm(new PCManaForm());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddingPCForm(PCManaForm backForm) {
		setResizable(false);
		this.backForm = backForm;
		
		this.setTitle("管理员添加产品类别界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 490, 262);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("请输入您要添加的产品类别的以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 18));
		mainLabel.setBounds(57, 10, 353, 49);
		contentPane.add(mainLabel);
		
		JLabel nameLabel = new JLabel("类别名称");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 18));
		nameLabel.setBounds(23, 85, 85, 28);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(121, 78, 267, 43);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton addingButton = new JButton("确认添加");
		addingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pcName = nameField.getText();
				if (pcName.equals("")) {
					JOptionPane.showMessageDialog(null, "类别名称不能为空！");
				}
				else {
					Service service = Service.getSingletonInstance();
					int flag = service.addProductCategory(pcName); //剩余操作在service层
					if (flag == 0) { //添加成功
						backForm.updateData();
						backForm.updateView();
						dispose();
					}
				}
			}
		});
		addingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		addingButton.setBounds(132, 162, 124, 28);
		contentPane.add(addingButton);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 17));
		cancleButton.setBounds(285, 162, 103, 28);
		contentPane.add(cancleButton);
		
		this.setVisible(true);
	}

}
