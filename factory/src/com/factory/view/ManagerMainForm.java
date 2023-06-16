package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.SystemManager;
import com.factory.service.Service;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

//系统管理员主界面
public class ManagerMainForm extends JFrame {
	private BeginningForm backForm;
	private SystemManager manager;
		
	private JPanel contentPane;
	
	public void createUserManaForm() {
		new UserManageForm(this);
		this.setVisible(false);
	}
	
	public void createFactoryManaForm() {
		new FactoryManaForm(this);
		this.setVisible(false);
	}
	
	public void createProductManaForm() {
		new ProductManaForm(this);
		this.setVisible(false);
	}
	
	public void createPCManaForm() {
		new PCManaForm(this);
		this.setVisible(false);
	}
	
	public void createEquipmentManaForm() {
		new EquipmentManaForm(this);
		this.setVisible(false);
	}
	
	public void createECManaForm() {
		new ECManaForm(this);
		this.setVisible(false);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new ManagerMainForm(new BeginningForm(), Dao.getDb().getManager()); //for test
	}

	/**
	 * Create the frame.
	 */
	public ManagerMainForm(BeginningForm backForm, SystemManager manager) {
		this.backForm = backForm;
		this.manager = manager;
		
		setResizable(false);
		this.setTitle("管理员主界面——欢迎您，"+manager.getName()+"!");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 566, 683);
		//contentPane = new JPanel();
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton userManageButton = new JButton("用户管理");
		userManageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "跳转成功！");
				//新建用户管理界面
				createUserManaForm();
			}
		});
		userManageButton.setFont(new Font("SimSun", Font.BOLD, 20));
		userManageButton.setBounds(130, 38, 261, 41);
		contentPane.add(userManageButton);
		
		JButton factoryManageButton = new JButton("云工厂管理");
		factoryManageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "跳转成功！");
				//新建云工厂管理界面
				createFactoryManaForm();
			}
		});
		factoryManageButton.setFont(new Font("SimSun", Font.BOLD, 20));
		factoryManageButton.setBounds(130, 134, 261, 41);
		contentPane.add(factoryManageButton);
		
		JButton productCategoryButton = new JButton("产品类别管理");
		productCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "跳转成功！");
				//新建产品类别管理界面
				createPCManaForm();
			}
		});
		productCategoryButton.setFont(new Font("SimSun", Font.BOLD, 20));
		productCategoryButton.setBounds(130, 219, 261, 41);
		contentPane.add(productCategoryButton);
		
		JButton productManaButton = new JButton("产品信息管理");
		productManaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "跳转成功！");
				//新建产品管理界面
				createProductManaForm();
			}
		});
		productManaButton.setFont(new Font("SimSun", Font.BOLD, 20));
		productManaButton.setBounds(130, 310, 261, 41);
		contentPane.add(productManaButton);
		
		JButton equipCategoryButton = new JButton("设备类型管理");
		equipCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//新建设备类别管理界面
				JOptionPane.showMessageDialog(null, "跳转成功！");
				createECManaForm();
			}
		});
		equipCategoryButton.setFont(new Font("SimSun", Font.BOLD, 20));
		equipCategoryButton.setBounds(130, 396, 261, 41);
		contentPane.add(equipCategoryButton);
		
		JButton equipButton = new JButton("设备管理");
		equipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "跳转成功！");
				//新建设备管理界面
				createEquipmentManaForm();
			}
		});
		equipButton.setFont(new Font("SimSun", Font.BOLD, 20));
		equipButton.setBounds(130, 482, 261, 46);
		contentPane.add(equipButton);
		
		JButton backButton = new JButton("退出账号");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int isClose = JOptionPane.showConfirmDialog(null, "确认要退出该账号吗？");
				if (isClose == 0) {
					backForm.setVisible(true);
					dispose();
				}
			}
		});
		backButton.setFont(new Font("SimSun", Font.BOLD, 17));
		backButton.setBounds(211, 579, 108, 40);
		contentPane.add(backButton);
		setVisible(true);
	}
}
