package com.factory.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.model.FactoryOwner;
import com.factory.model.Seller;
import com.factory.service.Service;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddingUserForm extends JFrame {

	private UserManageForm backForm; //上一层界面

	private JPanel contentPane;
	private JTextField accountField;
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
					//AddingUserForm frame = new AddingUserForm(new UserManageForm());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddingUserForm(UserManageForm backForm) {
		setResizable(false);
		this.backForm = backForm;
		
		this.setTitle("管理员添加用户界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 507, 600);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("请输入要添加的用户的以下信息");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 20));
		mainLabel.setBounds(98, 10, 306, 36);
		contentPane.add(mainLabel);
		
		JLabel accountLabel = new JLabel("登录账号");
		accountLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		accountLabel.setBounds(26, 75, 73, 27);
		contentPane.add(accountLabel);
		
		accountField = new JTextField();
		accountField.setFont(new Font("SimSun", Font.PLAIN, 16));
		accountField.setBounds(133, 72, 295, 36);
		contentPane.add(accountField);
		accountField.setColumns(10);
		
		JLabel passLabel = new JLabel("登录密码");
		passLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		passLabel.setBounds(26, 130, 83, 21);
		contentPane.add(passLabel);
		
		passField = new JTextField();
		passField.setFont(new Font("SimSun", Font.PLAIN, 16));
		passField.setBounds(133, 124, 295, 36);
		contentPane.add(passField);
		passField.setColumns(10);
		
		JLabel nameLabel = new JLabel("真实姓名");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		nameLabel.setBounds(26, 195, 73, 21);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(133, 189, 295, 36);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel connetcLabel = new JLabel("联系方式");
		connetcLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		connetcLabel.setBounds(27, 255, 72, 21);
		contentPane.add(connetcLabel);
		
		connectField = new JTextField();
		connectField.setFont(new Font("SimSun", Font.PLAIN, 16));
		connectField.setBounds(130, 249, 298, 36);
		contentPane.add(connectField);
		connectField.setColumns(10);
		
		JLabel chooseLabel = new JLabel("注册方式");
		chooseLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		chooseLabel.setBounds(26, 311, 73, 21);
		contentPane.add(chooseLabel);
		
		JRadioButton foRadioButton = new JRadioButton("云工厂");
		foRadioButton.setFont(new Font("SimSun", Font.BOLD, 17));
		foRadioButton.setBounds(155, 303, 105, 36);
		contentPane.add(foRadioButton);
		
		JRadioButton sellerRadioButton = new JRadioButton("经销商");
		sellerRadioButton.setFont(new Font("SimSun", Font.BOLD, 17));
		sellerRadioButton.setBounds(310, 304, 94, 35);
		contentPane.add(sellerRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(sellerRadioButton);
		group.add(foRadioButton);
		
		JLabel facNameLabel = new JLabel("工厂名称");
		facNameLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		facNameLabel.setBounds(26, 372, 73, 21);
		contentPane.add(facNameLabel);
		
		facNameField = new JTextField();
		facNameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		facNameField.setBounds(133, 365, 295, 36);
		contentPane.add(facNameField);
		facNameField.setColumns(10);
		
		JLabel factDesLabel = new JLabel("工厂简介");
		factDesLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		factDesLabel.setBounds(26, 438, 73, 21);
		contentPane.add(factDesLabel);
		
		factDesField = new JTextField();
		factDesField.setFont(new Font("SimSun", Font.PLAIN, 16));
		factDesField.setBounds(133, 431, 295, 36);
		contentPane.add(factDesField);
		factDesField.setColumns(10);
		
		JButton addingButton = new JButton("确定添加");
		addingButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Service service = Service.getSingletonInstance();
				
				String account = accountField.getText();
				String password = passField.getText();
				String name = nameField.getText();
				String connectionWay = connectField.getText();
				if (account.equals("")) {
					JOptionPane.showMessageDialog(null, "账号不能为空！");
				}
				else if (password.equals("")) {
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
				else
				{
					//添加的是经销商
					if (sellerRadioButton.isSelected()) {
						Seller newSeller = service.registerSeller(account, password, name, connectionWay);
						if (newSeller  != null) {
							JOptionPane.showMessageDialog(null, "添加成功！");
							backForm.updateData();
							backForm.updateView();
							dispose();
						}
					}
					else {
						String factoryName = facNameField.getText();
						String factDescrip = factDesField.getText();
						if (factoryName.equals("")) {
							JOptionPane.showMessageDialog(null, "工厂名称不能为空！");
							return;
						}
//						else if (factDescrip.equals("")) {
//							JOptionPane.showMessageDialog(null, "工厂简介不能为空！");
//						}
						FactoryOwner newOwner = service.registerFO(account, password, name, connectionWay, factoryName, factDescrip);
						if (newOwner != null) {
							JOptionPane.showMessageDialog(null, "添加成功！");
							backForm.updateData();
							backForm.updateView();
							dispose();
						}
					}
				}
				
			}
		});
		addingButton.setFont(new Font("SimSun", Font.BOLD, 18));
		addingButton.setBounds(119, 509, 113, 27);
		contentPane.add(addingButton);
		
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "已取消！");
				dispose();
			}
		});
		cancelButton.setFont(new Font("SimSun", Font.BOLD, 18));
		cancelButton.setBounds(257, 509, 113, 27);
		contentPane.add(cancelButton);
		
		this.setVisible(true);
	}
}

