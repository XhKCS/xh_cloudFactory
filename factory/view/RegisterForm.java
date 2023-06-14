package com.factory.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.model.FactoryOwner;
import com.factory.model.Seller;
import com.factory.model.User;
import com.factory.service.Service;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JPasswordField;

//用户注册界面
public class RegisterForm extends JFrame {
	private BeginningForm backForm;
	
	private JPanel contentPane;
	private JTextField accountTextField;
	private JTextField nameField;
	private JTextField contactField;
	private JTextField facNameField;
	private JTextField descripField;

	
	private Service service = Service.getSingletonInstance();
	private JPasswordField passField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		RegisterForm regeisterForm = new RegisterForm(new BeginningForm());

	}

	/**
	 * Create the frame.
	 */
	public RegisterForm(BeginningForm backForm) {
		this.backForm = backForm;
		
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 680, 811);
		this.setTitle("云工厂系统注册界面");
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel accountLabel = new JLabel("登录账号");
		accountLabel.setBounds(37, 49, 101, 47);
		accountLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		contentPane.add(accountLabel);
		
		accountTextField = new JTextField();
		accountTextField.setFont(new Font("SimSun", Font.PLAIN, 20));
		accountTextField.setBounds(161, 49, 477, 47);
		contentPane.add(accountTextField);
		accountTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("登录密码");
		passwordLabel.setBounds(37, 128, 101, 47);
		passwordLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		contentPane.add(passwordLabel);
		
		passField = new JPasswordField();
		passField.setFont(new Font("SimSun", Font.PLAIN, 20));
		passField.setBounds(161, 130, 477, 47);
		contentPane.add(passField);
		
		JLabel nameLable = new JLabel("真实姓名");
		nameLable.setFont(new Font("SimSun", Font.BOLD, 20));
		nameLable.setBounds(37, 214, 89, 40);
		contentPane.add(nameLable);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 20));
		nameField.setBounds(161, 211, 475, 47);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel contactLable = new JLabel("联系方式");
		contactLable.setFont(new Font("SimSun", Font.BOLD, 20));
		contactLable.setBounds(37, 309, 83, 40);
		contentPane.add(contactLable);
		
		contactField = new JTextField();
		contactField.setFont(new Font("SimSun", Font.PLAIN, 20));
		contactField.setBounds(161, 306, 477, 47);
		contentPane.add(contactField);
		contactField.setColumns(10);
		
		JLabel chooseLabel = new JLabel("注册方式");
		chooseLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		chooseLabel.setBounds(37, 402, 86, 30);
		contentPane.add(chooseLabel);
		
		JRadioButton factoryRB = new JRadioButton("云工厂");
		factoryRB.setFont(new Font("SimSun", Font.BOLD, 20));
		factoryRB.setBounds(222, 406, 121, 30);
		factoryRB.setFocusable(rootPaneCheckingEnabled);
		contentPane.add(factoryRB);
		
		JRadioButton sellingRB = new JRadioButton("经销商");
		sellingRB.setFont(new Font("SimSun", Font.BOLD, 20));
		sellingRB.setBounds(455, 406, 111, 30);
		contentPane.add(sellingRB);
		
		ButtonGroup group = new ButtonGroup();
		group.add(factoryRB);
		group.add(sellingRB);
		
		JLabel facNameLabel = new JLabel("工厂名称");
		facNameLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		facNameLabel.setBounds(37, 491, 89, 30);
		contentPane.add(facNameLabel);
		
		facNameField = new JTextField();
		facNameField.setFont(new Font("SimSun", Font.PLAIN, 20));
		facNameField.setBounds(161, 483, 477, 47);
		contentPane.add(facNameField);
		facNameField.setColumns(10);
		
		JLabel descripLabel = new JLabel("工厂简介");
		descripLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		descripLabel.setBounds(37, 591, 101, 30);
		contentPane.add(descripLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 20));
		descripField.setBounds(161, 583, 477, 47);
		contentPane.add(descripField);
		descripField.setColumns(10);
		
		JButton okButton = new JButton("确定");
		okButton.setFont(new Font("SimSun", Font.BOLD, 20));
		okButton.setBounds(345, 696, 111, 45);
		okButton.addActionListener(new OKRegisterListener());
		contentPane.add(okButton);
		
		
		JButton backButton = new JButton("返回");
		backButton.setFont(new Font("SimSun", Font.BOLD, 20));
		backButton.setBounds(492, 695, 106, 47);
		backButton.addActionListener(new BackRegisterListener());
		contentPane.add(backButton);
		
		//this.pack();
		this.setVisible(true);
	}
	
	private class OKRegisterListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String account = accountTextField.getText();
			String password = new String(passField.getPassword());
			String name = nameField.getText();
			String connectionWay = contactField.getText();
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
				String selectedState = "";
				for (Component k : contentPane.getComponents()) {
					if (k instanceof JRadioButton) {
						if (((JRadioButton) k).isSelected()) {
							selectedState = ((JRadioButton) k).getText();
							break;
						}
					}
				}
				if (selectedState.equals("经销商")) {
					Seller newSeller = service.registerSeller(account, password, name, connectionWay);
					if (newSeller  != null) {
						JOptionPane.showMessageDialog(null, "注册成功！");
						dispose();
					}
				}
				else {
					String factoryName = facNameField.getText();
					String factDescrip = descripField.getText();
					if (factoryName.equals("")) {
						JOptionPane.showMessageDialog(null, "工厂名称不能为空！");
						return;
					}
//					else if (factDescrip.equals("")) {
//						JOptionPane.showMessageDialog(null, "工厂简介不能为空！");
//					}
					FactoryOwner newOwner = service.registerFO(account, password, name, connectionWay, factoryName, factDescrip);
					if (newOwner != null) {
						JOptionPane.showMessageDialog(null, "注册成功！");
						new FOMainForm(backForm, newOwner);
						dispose();
					}
				}
			}
		}
		
	}
	
	private class BackRegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			backForm.setVisible(true);
			dispose();
			
		}
		
	}
	
}
