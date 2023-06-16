package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.factory.dao.Dao;
import com.factory.dao.EquipmentDataBase;
import com.factory.dao.FactoryDataBase;
import com.factory.dao.UserDataBase;
import com.factory.model.Equipment;
import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import com.factory.model.RentEquipment;
import com.factory.model.Seller;
import com.factory.model.User;
import com.factory.util.DataBase;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionEvent;

public class UserManageForm extends JFrame {
	private ManagerMainForm backForm;

	private JPanel contentPane;
	
	private JTextField inputField;
	private JTable table;
	private Object[][] data; //更新写法后，这个就可以不用了
	//private DefaultTableModel tableModel;
	
	/**
	 * Launch the application.
	 */
	
	public JTable getTable() {
		return table;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//UserManageForm frame = new UserManageForm();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Object[][] createData(ArrayList<User> users) {
		int num = users.size();
		Object[][] newData = new Object[num][6];
		int i = 0;
		for (User k : users) {
			newData[i] = new Object[] {new Boolean(false), k.getID(), k.getAccount(),
					k.getName(), k.getConnectionWay(), k.getIdentity()};
			i++;
		}
		return newData;
	}
	
	public void updateData() {
		UserDataBase udb = Dao.getDb().getUsersDB();
		int num = udb.getAllUsers().size();
		data = createData(udb.getAllUsers());
	}
			
	public void updateView() {
		updateView(data);		
	}
	
	public void updateView(Object[][] newData) {
		table.setModel(new DefaultTableModel(
				newData,
				new String[] { //表头
					"\u9009\u4E2D", "ID", "\u767B\u5F55\u8D26\u53F7", "\u59D3\u540D", "\u8054\u7CFB\u65B9\u5F0F", "\u89D2\u8272\u540D\u79F0"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, Integer.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public UserManageForm(ManagerMainForm backForm) {
		this.backForm = backForm;
		
		setResizable(false);
		this.setTitle("管理员——用户管理界面");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 663, 521);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		inputField = new JTextField();
		inputField.setFont(new Font("SimSun", Font.PLAIN, 20));
		inputField.setBounds(10, 27, 242, 31);
		contentPane.add(inputField);
		inputField.setColumns(10);
		
		JButton userNameButton = new JButton("按用户姓名查找");
		userNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = inputField.getText();
				UserDataBase udb = Dao.getDb().getUsersDB();
				ArrayList<User> users = udb.searchUsersByName(userName);
				int num = users.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该姓名前缀的用户！");
				}
				else {
					Object[][] newData = createData(users);			
					updateView(newData);		
				}
				
			}
		});
		userNameButton.setFont(new Font("SimSun", Font.BOLD, 16));
		userNameButton.setBounds(274, 27, 166, 33);
		contentPane.add(userNameButton);
		
		JButton restartButton = new JButton("重置");
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputField.setText("");
				updateData();
				updateView();
			}
		});
		restartButton.setFont(new Font("SimSun", Font.BOLD, 18));
		restartButton.setBounds(461, 27, 108, 31);
		contentPane.add(restartButton);
		
		JButton addingButton = new JButton("新建");
		addingButton.addActionListener(new AddingUserListener(this));
		
		addingButton.setFont(new Font("SimSun", Font.BOLD, 18));
		addingButton.setBounds(25, 99, 107, 31);
		contentPane.add(addingButton);
		
		JButton removingButton = new JButton("删除");
		removingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) {
					int isDel = JOptionPane.showConfirmDialog(null, "确定要删除吗？"); //选择对话框，包含“是”/“否”/“取消”
					if (isDel == 0) { //说明用户选中“确定”
					// 要注意：如果删除的用户是云工厂管理员，则要先把其管理的云工厂也在相应的Database中删除！！
					// 还要记得在删除工厂之前，先把该工厂中的所有工厂设备删除，租用的设备归还！！！
						boolean flag = true;
						UserDataBase udb = Dao.getDb().getUsersDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); //判断第i行复选框是否被选中
							if (bl) { //说明第i行被选中
								int userID = (int) (table.getValueAt(i, 1));
								User user = udb.getUserByID(userID);
								if (user instanceof FactoryOwner) 
								{
									flag = false;
									FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
									FactoryOwner fo = (FactoryOwner)user;
									Factory fac = fdb.getFactory(fo.getMyFactoryID());
									int isDel2 = JOptionPane.showConfirmDialog
											(null, user.getName()+"是云工厂管理员，确定要删除该用户、删除其工厂并归还其租借的设备吗？");
									if (isDel2==0) {
										flag = true;
										EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
										ArrayList<Equipment> equipments = edb.getEquipmentsByFactoryID(fac.getID());
										for (Equipment k : equipments) {
											if (k instanceof RentEquipment) {
												((RentEquipment)k).beReturned();
												k.setBelongFactoryID(-1);
												fac.removeEquipment(k);
											}
											else {
												edb.removeEquipment(k); //该工厂自有的工厂设备
											}
										}
										fdb.removeFactory(fac);
										udb.removeUser(user);
									}
								}
								else {
									udb.removeUser(user);
								}
							}
						}
						updateData(); //更新数据
						updateView(); //还要更新视图
						if (flag)
							JOptionPane.showMessageDialog(null, "删除成功！");
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要删除的数据！");
				}
			}
		});
		removingButton.setFont(new Font("SimSun", Font.BOLD, 18));
		removingButton.setBounds(155, 99, 97, 31);
		contentPane.add(removingButton);
		
		JButton editButton = new JButton("修改");
		editButton.addActionListener(new ModifyUserListener(this));
		
		editButton.setFont(new Font("SimSun", Font.BOLD, 18));
		editButton.setBounds(476, 100, 97, 30);
		contentPane.add(editButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 629, 197);
		contentPane.add(scrollPane);
		
		table = new JTable();
		//之后考虑如何更新数据并更新显示
		//更新写法
//		String[] titles = { "选中", "ID", "登录账号", "姓名", "联系方式", "角色名称", };
//		tableModel = new DefaultTableModel(titles, 0); // (columnNames, rowCount)
//		table.setModel(tableModel);
		
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
		backButton.setFont(new Font("SimSun", Font.BOLD, 18));
		backButton.setBounds(518, 429, 101, 33);
		contentPane.add(backButton);
		
		this.setVisible(true);
		
	}
	
}

class AddingUserListener implements ActionListener {
	private UserManageForm backForm;
	
	public AddingUserListener(UserManageForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建用户增加界面
		new AddingUserForm(backForm);	
	}
	
}

class ModifyUserListener implements ActionListener {
	private UserManageForm backForm;	
	
	public ModifyUserListener(UserManageForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建用户修改界面
		JTable table = backForm.getTable();
		if (table.getSelectedRow()>=0) {
			int userID = (int)table.getValueAt(table.getSelectedRow(), 1);
			User user = Dao.getDb().getUsersDB().getUserByID(userID);
			
			new ModifyUserForm(backForm, user);
		}
		else {
			JOptionPane.showMessageDialog(null, "请先选择要修改的数据！");
		}
	}
	
}
