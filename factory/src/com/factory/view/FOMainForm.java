package com.factory.view;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.factory.dao.Dao;
import com.factory.dao.EquipmentDataBase;
import com.factory.dao.FactoryDataBase;
import com.factory.model.Equipment;
import com.factory.model.EquipmentCategory;
import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import com.factory.model.RentEquipment;
import com.factory.service.Service;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

//云工产管理员主界面
//增删改只能针对工厂自有的工厂设备；可租用设备只能查找、租借、归还
public class FOMainForm extends JFrame {
	private BeginningForm backForm;
	private FactoryOwner owner;
	
	private Factory myFactory;
	
	private JPanel contentPane;
	private JTable table;
	private Object[][] data;
	private JTextField searchField;
	
	
	public FactoryOwner getFO() {
		return owner;
	}
	public Factory getMyFactory() {
		return myFactory;
	}
	
	//以下是一些方法
	public void createAddingFEForm() {
		new AddingFEForm(this);
	}
	
	public void createModifyFEForm(Equipment fe) {
		new ModifyFEForm(this, fe);
	}
	
	public void createRentingForm() {
		new RentingForm(this);
	}
	
	public Object[][] createData(ArrayList<Equipment> equipments) {
		int num = equipments.size();
		Object[][] newData = new Object[num][];
		FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
		int i = 0;
		//注意这里需要判断所属工厂是否为空，因为未被租用的RE并没有所属工厂！！！
		for (Equipment k : equipments) {
			Factory factory = fdb.getFactory(k.getBelongFactoryID());
			if (factory != null) {
				newData[i] = new Object[] {new Boolean(false), k.getID(), k.getName(), k.getTypeNum(),
					k.getCategory().getCategoryName(), k.getSize(), k.getWorkingState(),
					k.getDescription(), k.getRentState(), factory.getName()};
			}
			else {
				newData[i] = new Object[] {new Boolean(false), k.getID(), k.getName(), k.getTypeNum(),
						k.getCategory().getCategoryName(), k.getSize(), k.getWorkingState(),
						k.getDescription(), k.getRentState(), ""};
			}
			
			i++;
		}
		return newData;
	}
	
	public void updateData() {
		Factory myFactory = Dao.getDb().getFactoriesDB().getFactoryByOwnerID(owner.getID());
	    data = createData(myFactory.getEquipments());
	}
	
	public void updateView() {
		updateView(data);
	}
	
	public void updateView(Object[][] newData) {
		table.setModel( new DefaultTableModel(
	        	newData,
	        	new String[] {
	        		"   \u9009\u4E2D", "    ID", "\u8BBE\u5907\u540D\u79F0", "\u8BBE\u5907\u7F16\u53F7", "\u8BBE\u5907\u7C7B\u522B", "\u8BBE\u5907\u89C4\u683C", "\u8BBE\u5907\u72B6\u6001", "\u8BBE\u5907\u63CF\u8FF0", "\u79DF\u7528\u72B6\u6001", "\u6240\u5C5E\u5DE5\u5382"
	        	}
	        ) {
	        	Class[] columnTypes = new Class[] {
	        		Boolean.class, Integer.class, String.class, String.class, String.class, String.class, Object.class, String.class, Object.class, String.class
	        	};
	        	public Class getColumnClass(int columnIndex) {
	        		return columnTypes[columnIndex];
	        	}
	    });
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		FactoryOwner owner = Dao.getDb().getUsersDB().getFactoryOwners().get(0);
		
		new FOMainForm(new BeginningForm(), owner);
	}

	/**
	 * Create the frame.
	 */
	
	public FOMainForm(BeginningForm backForm, FactoryOwner owner) {
		setResizable(false);
		this.backForm = backForm;
		this.owner = owner;
		FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
		myFactory = fdb.getFactory(owner.getMyFactoryID());
		
		setTitle("云工厂管理员主界面——欢迎您，"+owner.getName()+"!");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 792, 709);
		contentPane = new BackgroundPane();
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		//table = new JTable(new EquipmentsTM(owner.getMyFactory().getEquipments()));
	    
		// 创建表格数据模型
//		DefaultTableModel model = new DefaultTableModel();
//	    model.addColumn("");
//	    model.addColumn("ID");
//	    model.addColumn("设备名称");
//	    model.addColumn("设备编号");
//	    model.addColumn("设备类别");
//	    model.addColumn("设备规格");
//	    model.addColumn("设备状态");
//	    model.addColumn("设备描述");
//	    model.addColumn("租用状态");
//	    model.addColumn("所属工厂"); //10列的表头名称
	   
	    
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(714, 218);
		scrollPane.setLocation(30, 281);
		contentPane.add(scrollPane);
		
		table = new JTable();
		
		updateData();
		updateView();
		scrollPane.setViewportView(table);
		
		//新增工厂设备功能
		JButton addingButton = new JButton("新建");
		addingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createAddingFEForm();
			}
		});
		addingButton.setFont(new Font("SimSun", Font.BOLD, 18));
		addingButton.setBounds(60, 196, 89, 31);
		contentPane.add(addingButton);
		
		//到产能中心租用设备功能
		JButton rentButton = new JButton("租用设备");
		rentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createRentingForm();
			}
		});
		rentButton.setFont(new Font("SimSun", Font.BOLD, 17));
		rentButton.setBounds(181, 196, 110, 31);
		contentPane.add(rentButton);
		
		//删除工厂设备功能
		JButton removingButton = new JButton("删除");
		removingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) 
				{
					int isDel = JOptionPane.showConfirmDialog(null, "确定要删除吗？"); //选择对话框，包含“是”/“否”/“取消”
					if (isDel == 0) { //说明用户选中“确定”
						EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); 
							if (bl) { //说明第i行被选中
								//注意要判断是否为工厂设备；删除不仅要从工厂中删除，还要从EquipmentDataBase中删除
								int equipmentID = (int) (table.getValueAt(i, 1));
								Equipment equipment = myFactory.getEquipment(equipmentID);
								if (equipment instanceof RentEquipment) {
									JOptionPane.showMessageDialog(null, "该设备不是工厂设备，不能删除！");
									break;
								}
								else {
									myFactory.removeEquipment(equipment);
									edb.removeEquipment(equipment);
								}
							}
						}
						updateData(); //更新数据
						updateView(); //还要更新视图
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要删除的数据！");
				}
			}
		});
		removingButton.setFont(new Font("SimSun", Font.BOLD, 18));
		removingButton.setBounds(401, 196, 95, 31);
		contentPane.add(removingButton);
		
		//改变设备状态功能
		JButton stateButton = new JButton("设备状态");
		stateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount() > 0) 
				{
					int isChange = JOptionPane.showConfirmDialog(null, "确定要改变设备工作状态吗？");
					if (isChange == 0) { //说明用户选中“确定”
						//EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							
							Boolean bl = (Boolean) table.getValueAt(i, 0);
							if (bl) { //说明第i行被选中
								int equipmentID = (int) (table.getValueAt(i, 1));
								Equipment equipment = myFactory.getEquipment(equipmentID);
								equipment.changeWorkingState();
							}
						}
						updateData(); //更新数据
						updateView(); //还要更新视图
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要改变状态的设备！");
				}
			}
		});
		stateButton.setFont(new Font("SimSun", Font.BOLD, 18));
		stateButton.setBounds(616, 196, 110, 31);
		contentPane.add(stateButton);
		
		JLabel mainLabel = new JLabel(fdb.getFactoryByOwnerID(owner.getID()).getName()+"  工厂设备管理");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 21));
		mainLabel.setBounds(197, 10, 360, 48);
		contentPane.add(mainLabel);
		
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
		backButton.setFont(new Font("SimSun", Font.BOLD, 18));
		backButton.setBounds(576, 595, 118, 39);
		contentPane.add(backButton);
		
		//修改工厂设备信息功能
		JButton editButton = new JButton("修改");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) {
					int equipmentID = (int)(table.getValueAt(table.getSelectedRow(), 1));
					Equipment equipment = myFactory.getEquipment(equipmentID);
					if (equipment instanceof RentEquipment) {
						JOptionPane.showMessageDialog(null, "该设备不是工厂设备，不能修改！");
					}
					else {
						createModifyFEForm(equipment);
					}	
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要修改的数据！");
				}
			}
		});
		editButton.setFont(new Font("SimSun", Font.BOLD, 18));
		editButton.setBounds(511, 196, 95, 31);
		contentPane.add(editButton);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 17));
		searchField.setBounds(47, 102, 294, 35);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		//查找功能（两种模糊查找）
		JButton nameSearchButton = new JButton("按设备名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eName = searchField.getText();
				ArrayList<Equipment> equipments = myFactory.searchEquipmentsByName(eName);
				int num = equipments.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的设备！");
				}
				else {
					Object[][] newData = createData(equipments);
					updateView(newData);
				}
			}
		});
		nameSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		nameSearchButton.setBounds(360, 83, 149, 31);
		contentPane.add(nameSearchButton);
		
		JButton cateSearchButton = new JButton("按类别名称查找");
		cateSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cateName = searchField.getText();
				ArrayList<Equipment> equipments = myFactory.searchEquipmentsByCateName(cateName);
				int num = equipments.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该类别名称前缀的设备！");
				}
				else {
					Object[][] newData = createData(equipments);
					updateView(newData);
				}
			}
		});
		cateSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		cateSearchButton.setBounds(360, 129, 149, 31);
		contentPane.add(cateSearchButton);
		
		//重置功能
		JButton clearButton = new JButton("重置");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				updateData();
				updateView();
			}
		});
		clearButton.setFont(new Font("SimSun", Font.BOLD, 17));
		clearButton.setBounds(557, 106, 103, 31);
		contentPane.add(clearButton);
		
		//归还设备功能
		JButton returnButton = new JButton("归还设备");
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) 
				{
					int isTurn = JOptionPane.showConfirmDialog(null, "确定要归还吗？"); 
					if (isTurn == 0) { //说明用户选中“确定”
						EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
						boolean flag = true;
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); 
							if (bl) { //说明第i行被选中
								//注意要判断是否为工厂设备；归还仅从工厂中删除，不要从EquipmentDataBase中删除；
								//还要记得改变设备租用状态
								int equipmentID = (int) (table.getValueAt(i, 1));
								Equipment equipment = myFactory.getEquipment(equipmentID);
								if (equipment instanceof RentEquipment) {
									((RentEquipment)equipment).beReturned();
									equipment.setBelongFactoryID(-1);
									myFactory.removeEquipment(equipment);
								}
								else {
									JOptionPane.showMessageDialog(null, "该设备是工厂设备，不用归还！");
									flag = false;
									break;
								}
							}
						}
						updateData(); //更新数据
						updateView(); //还要更新视图
						if (flag)
							JOptionPane.showMessageDialog(null, "归还成功！");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要归还的设备！");
				}
			}
		});
		returnButton.setFont(new Font("SimSun", Font.BOLD, 17));
		returnButton.setBounds(70, 597, 118, 35);
		contentPane.add(returnButton);
		
        
		this.setVisible(true);
	}
}

