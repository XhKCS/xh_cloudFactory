package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.FactoryOwner;
import com.factory.model.SystemManager;
import com.factory.model.User;
import com.factory.service.Service;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

//  整个程序启动的入口
public class BeginningForm extends JFrame {
	
	private RegisterForm registerForm;
	
	private JPanel contentPane;
	private JTextField accountField;
	private JPasswordField passField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BeginningForm frame = new BeginningForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void createRegisterForm() {
		new RegisterForm(this);
		this.setVisible(false);
	}
	
	public void createFOMainForm(FactoryOwner fo) {
		new FOMainForm(this, fo);
		this.setVisible(false);
	}
	
	public void createManagerMainForm(SystemManager manager) {
		new ManagerMainForm(this, manager);
		this.setVisible(false);
	}

	/**
	 * Create the frame.
	 */
	public BeginningForm() {
		setResizable(false);
		this.setTitle("云工厂系统登录页面");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Dao.save(); //在退出前将数据保存到文件
				System.exit(0);
				
			}
		});
		setBounds(500, 150, 560, 495);
		//contentPane = new JPanel();
		contentPane = new BackgroundPane();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		passField = new JPasswordField();
		passField.setFont(new Font("SimSun", Font.PLAIN, 18));
		passField.setBounds(158, 277, 315, 42);
		contentPane.add(passField);
		
		JLabel mainLabel = new JLabel("云平台制造-1.0.1");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 35));
		mainLabel.setBounds(125, 30, 335, 75);
		contentPane.add(mainLabel);
		
		JLabel accountLabel = new JLabel("账号");
		accountLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		accountLabel.setBounds(77, 183, 60, 33);
		contentPane.add(accountLabel);
		
		accountField = new JTextField();
		accountField.setFont(new Font("SimSun", Font.PLAIN, 18));
		accountField.setBounds(158, 180, 315, 42);
		contentPane.add(accountField);
		accountField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("密码");
		lblNewLabel.setFont(new Font("SimSun", Font.BOLD, 20));
		lblNewLabel.setBounds(77, 286, 60, 33);
		contentPane.add(lblNewLabel);
		
		JButton logInButton = new JButton("登录");
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account = accountField.getText();
				String password = new String( passField.getPassword() );
				if (account.equals("")) {
					JOptionPane.showMessageDialog(null, "账号不能为空！");
				}
				else if (password.equals("")) {
					JOptionPane.showMessageDialog(null, "密码不能为空！");
				}
				else if (password.length()<3 || password.length()>20) {
					JOptionPane.showMessageDialog(null, "密码长度必须在3位到20位之间！");
				}
				else
				{
					// 调用service层相应方法
					Service service = Service.getSingletonInstance();
					User user = service.logIn(account, password);
					if (user == null) {
						JOptionPane.showMessageDialog(null, "帐号或密码错误！");
					}
					else {
						//new LogSuccessForm();
						JOptionPane.showMessageDialog(null, "登录成功！");
						passField.setText(""); //清空文本框内容
						
						if (user instanceof SystemManager) {
							//下面新建系统管理员主界面
							createManagerMainForm((SystemManager)user);
						}
						else if(user instanceof FactoryOwner) {
							//新建云工厂管理员主界面
							createFOMainForm((FactoryOwner)user);
						}
					}
				}
			}
		});
		logInButton.setFont(new Font("SimSun", Font.BOLD, 20));
		logInButton.setBounds(102, 400, 100, 33);
		contentPane.add(logInButton);
		
		JButton regeisterButton = new JButton("注册");
		regeisterButton.setFont(new Font("SimSun", Font.BOLD, 20));
		regeisterButton.setBounds(360, 400, 100, 33);
		regeisterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createRegisterForm();
			}
			
		});
		contentPane.add(regeisterButton);
		
		
		this.setVisible(true);
		
		
	}
}
