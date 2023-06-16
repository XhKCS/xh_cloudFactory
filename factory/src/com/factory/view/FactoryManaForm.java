package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.factory.dao.Dao;
import com.factory.dao.FactoryDataBase;
import com.factory.dao.UserDataBase;
import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class FactoryManaForm extends JFrame {
	private ManagerMainForm backForm;
	
	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	private Object[][] data;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//FactoryManaForm frame = new FactoryManaForm();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//更新表格中的数据
	
	public Object[][] createData(ArrayList<Factory> factories) {
		int num = factories.size();
		Object[][] newData = new Object[num][8];
		int i = 0;
		UserDataBase udb = Dao.getDb().getUsersDB();
		for (Factory k : factories) {
			FactoryOwner owner = udb.getOwnerByFactID(k.getID());
			newData[i] = new Object[] {new Boolean(false), k.getID(), k.getName(),
					k.getDescription(), owner.getName(), owner.getConnectionWay(), 
					owner.getAccount(), k.getState()};
			i++;
		}
		return newData;
	}
	
	public void updateData() {
		FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
		data = createData(fdb.getFactories());
	}
	
	public void updateView() {
		updateView(data);
	}
	
	public void updateView(Object[][] newData) {
		table.setModel(new DefaultTableModel(
				newData,
				new String[] {
					"   \u9009\u4E2D", "    ID", " \u5DE5\u5382\u540D\u79F0", " \u5DE5\u5382\u7B80\u4ECB", "  \u8D1F\u8D23\u4EBA", "\u8054\u7CFB\u65B9\u5F0F", " \u767B\u5F55\u8D26\u53F7", " \u5DE5\u5382\u72B6\u6001"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
		});
	}

	/**
	 * Create the frame.
	 */
	public FactoryManaForm(ManagerMainForm backForm) {
		this.backForm = backForm;
		
		setResizable(false);
		this.setTitle("管理员——云工厂管理页面");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 782, 656);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 17));
		searchField.setBounds(10, 30, 250, 40);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton nameSearchButton = new JButton("按工厂名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchName = searchField.getText();
				FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
				//模糊查找方法
				ArrayList<Factory> factories = fdb.searchFactoriesByName(searchName);
				int num = factories.size();
				if (num==0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的工厂！");
				}
				else {
					Object[][] newData = createData(factories);	
					updateView(newData);
				}	
			}
		});
		nameSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		nameSearchButton.setBounds(302, 10, 164, 40);
		contentPane.add(nameSearchButton);
		
		JButton ownerSearchButton = new JButton("按负责人姓名查找");
		ownerSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchName = searchField.getText();
				FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
				//模糊查找方法
				ArrayList<Factory> factories = fdb.searchFactoriesByOwnerName(searchName);
				int num = factories.size();
				if (num==0) {
					JOptionPane.showMessageDialog(null, "不存在该负责人姓名前缀的工厂！");
				}
				else {
					Object[][] newData = createData(factories);
					updateView(newData);
				}
			}
		});
		ownerSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		ownerSearchButton.setBounds(302, 60, 164, 40);
		contentPane.add(ownerSearchButton);
		
		JButton advertButton = new JButton("切换工厂状态");
		advertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int isDel = JOptionPane.showConfirmDialog(null, "确定要切换工厂状态吗？"); //选择对话框，包含“是”/“否”/“取消”
					if (isDel == 0) { //说明用户选中“确定”
						FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); //判断第i行复选框是否被选中
							if (bl) { //说明第i行被选中
								int factoryID = (int)table.getValueAt(i, 1);
								fdb.getFactory(factoryID).changeState();
							}
						}
						
						updateData();
						updateView();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要改变状态的工厂！");
				}
			}
		});
		advertButton.setFont(new Font("SimSun", Font.BOLD, 15));
		advertButton.setBounds(26, 116, 137, 35);
		contentPane.add(advertButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 182, 748, 294);
		contentPane.add(scrollPane);
		
		table = new JTable();	
		updateData();
		updateView();
		scrollPane.setViewportView(table);
		
		JButton backButton = new JButton("返回");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backForm.setVisible(true);
				dispose();
			}
		});
		backButton.setFont(new Font("SimSun", Font.BOLD, 17));
		backButton.setBounds(600, 547, 102, 35);
		contentPane.add(backButton);
		
		JButton clearButton = new JButton("重置");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				updateData();
				updateView();
			}
		});
		clearButton.setFont(new Font("SimSun", Font.BOLD, 17));
		clearButton.setBounds(521, 40, 110, 35);
		contentPane.add(clearButton);
		
		this.setVisible(true);
	}
}
