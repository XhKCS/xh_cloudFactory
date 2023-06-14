package com.factory.view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import com.factory.model.User;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ModifyUserForm extends JFrame {
	private UserManageForm backForm;
	private User user;
	
	private JPanel contentPane;
	private JTextField passField;
	private JTextField nameField;
	private JTextField connectField;
	private JTextField facNameField;
	private JTextField factDesField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ModifyUserForm frame = new ModifyUserForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifyUserForm(UserManageForm backForm, User user) {
		this.backForm = backForm;
		this.user = user;
		
		this.setTitle("管理员修改用户信息界面");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 512, 541);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("您可修改以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 20));
		mainLabel.setBounds(98, 10, 306, 36);
		contentPane.add(mainLabel);
		
		JLabel accountLabel = new JLabel("登录账号");
		accountLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		accountLabel.setBounds(26, 68, 73, 27);
		contentPane.add(accountLabel);
		
		JLabel accountLabel2 = new JLabel(user.getAccount());
		accountLabel2.setFont(new Font("SimSun", Font.BOLD, 16));
		accountLabel2.setBounds(133, 63, 294, 36);
		contentPane.add(accountLabel2);
		
		JLabel passLabel = new JLabel("登录密码");
		passLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		passLabel.setBounds(26, 123, 83, 21);
		contentPane.add(passLabel);
		
		passField = new JTextField();
		passField.setFont(new Font("SimSun", Font.PLAIN, 16));
		passField.setBounds(133, 124, 294, 36);
		passField.setText(user.getPassword());
		contentPane.add(passField);
		passField.setColumns(10);
		
		JLabel nameLabel = new JLabel("真实姓名");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		nameLabel.setBounds(26, 189, 73, 21);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(133, 182, 294, 36);
		nameField.setText(user.getName());
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel connetcLabel = new JLabel("联系方式");
		connetcLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		connetcLabel.setBounds(27, 258, 72, 21);
		contentPane.add(connetcLabel);
		
		connectField = new JTextField();
		connectField.setFont(new Font("SimSun", Font.PLAIN, 16));
		connectField.setText(user.getConnectionWay());
		connectField.setBounds(133, 251, 294, 36);
		contentPane.add(connectField);
		connectField.setColumns(10);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 17));
		cancleButton.setBounds(277, 430, 103, 25);
		contentPane.add(cancleButton);
		
		JLabel selectLabel = new JLabel("角色类型");
		selectLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		selectLabel.setBounds(26, 337, 73, 20);
		contentPane.add(selectLabel);
		
		JRadioButton foRadioButton = new JRadioButton("云工厂");
		foRadioButton.setFont(new Font("SimSun", Font.BOLD, 17));
		foRadioButton.setBounds(151, 329, 103, 36);
		contentPane.add(foRadioButton);
		
		JRadioButton sellerRadioButton = new JRadioButton("经销商");
		sellerRadioButton.setFont(new Font("SimSun", Font.BOLD, 17));
		sellerRadioButton.setBounds(296, 333, 131, 28);
		contentPane.add(sellerRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(sellerRadioButton);
		group.add(foRadioButton);
		
		if (user instanceof FactoryOwner) {
			foRadioButton.setSelected(true);
		}
		else {
			sellerRadioButton.setSelected(true);
		}
		foRadioButton.setEnabled(false);
		sellerRadioButton.setEnabled(false);
		
		//修改操作
		JButton modifyButton = new JButton("确认修改");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = passField.getText();
				String name = nameField.getText();
				String connectionWay = connectField.getText();
				if (password.equals("")) {
					JOptionPane.showMessageDialog(null, "密码不能为空！");
				}
				else if (password.length()<3 || password.length()>20) {
					JOptionPane.showMessageDialog(null, "密码长度必须在3位到20位之间！");
				}
				else if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "真实姓名不能为空！");
				}
				else if (connectionWay.equals("")) {
					JOptionPane.showMessageDialog(null, "联系方式不能为空！");
				}
				else {
					user.setPassword(password);
					user.setName(name);
					user.setConnectionWay(connectionWay);
					backForm.updateData();
					backForm.updateView();
					JOptionPane.showMessageDialog(null, "修改成功！");
					dispose();
				}
			}
		});
		modifyButton.setFont(new Font("SimSun", Font.BOLD, 17));
		modifyButton.setBounds(130, 429, 113, 26);
		contentPane.add(modifyButton);
		
		this.setVisible(true);
	}
}
