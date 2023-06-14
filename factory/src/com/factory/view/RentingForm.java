package com.factory.view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.factory.dao.Dao;
import com.factory.dao.EquipmentDataBase;
import com.factory.dao.FactoryDataBase;
import com.factory.dao.UserDataBase;
import com.factory.model.Equipment;
import com.factory.model.Factory;
import com.factory.model.FactoryOwner;
import com.factory.model.RentEquipment;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RentingForm extends JFrame {
	private FOMainForm backForm;
	private FactoryOwner fo;
	private Factory myFactory;
	
	private JPanel contentPane;
	private JTable table;
	private Object[][] data;
	private JTextField searchField;
	
	public JTable getTable() {
		return table;
	}
	
	public Object[][] createData(ArrayList<RentEquipment> equipments) {
		int num = equipments.size();
		Object[][] newData = new Object[num][10];
		FactoryDataBase fdb = Dao.getDb().getFactoriesDB();
		int i = 0;
		//注意这里需要判断所属工厂是否为空，因为未被租用的RE并没有所属工厂！！！
		for (RentEquipment k : equipments) {
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
		EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
		data = createData(edb.getSpareREs()); //当前未被租用的设备的集合
	}
	
	public void updateView() {
		updateView(data);
	}
	
	public void updateView(Object[][] newData) {
		table.setModel(new DefaultTableModel(
				newData,
				new String[] {
					"  \u9009\u4E2D", "  ID", "\u8BBE\u5907\u540D\u79F0", "\u8BBE\u5907\u7F16\u53F7", "\u8BBE\u5907\u7C7B\u522B", "\u8BBE\u5907\u89C4\u683C", "\u8BBE\u5907\u72B6\u6001", "\u8BBE\u5907\u63CF\u8FF0", "\u79DF\u7528\u72B6\u6001", "\u6240\u5C5E\u5DE5\u5382"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RentingForm(FOMainForm backForm) {
		this.backForm = backForm;
		this.fo = backForm.getFO();
		this.myFactory = backForm.getMyFactory();
		
		this.setTitle("产能中心——云工厂管理员租用设备界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 697, 790);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 258, 644, 376);
		contentPane.add(scrollPane);
		
		table = new JTable();	
		updateData();
		updateView();
		scrollPane.setViewportView(table);
		
		JLabel mainLabel = new JLabel(fo.getName()+"，欢迎光临产能中心！");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 20));
		mainLabel.setBounds(156, 13, 345, 43);
		contentPane.add(mainLabel);
		
		JLabel mainLabel2 = new JLabel("以下是当前可租用设备：");
		mainLabel2.setFont(new Font("SimHei", Font.BOLD, 20));
		mainLabel2.setBounds(166, 66, 258, 34);
		contentPane.add(mainLabel2);
		
		JButton rentButton = new JButton("确认租借");
		rentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount() > 0) 
				{
					int isChange = JOptionPane.showConfirmDialog(null, "确定要租用选中的设备吗？");
					if (isChange == 0) { //说明用户选中“确定”
						EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0);
							if (bl) { //说明第i行被选中
								int equipmentID = (int) (table.getValueAt(i, 1));
								Equipment equipment = edb.getEquipment(equipmentID);
								if (equipment instanceof RentEquipment && equipment.getRentState().equals("未被租用")) {
									RentEquipment re = (RentEquipment)equipment;
									//注意有设置设备所属工厂ID的操作
									re.setBelongFactoryID(myFactory.getID());
									myFactory.addEquiment(re);
									re.beRented();
								}
							}
						}
						updateData(); 
						updateView(); 
						//注意还要给上一个界面更新视图
						backForm.updateData(); 
						backForm.updateView();
						JOptionPane.showMessageDialog(null, "租借成功！");
					}
				}
				else if (table.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null, "产能中心当前已没有可租用设备了哦~");
				}
				else {
					JOptionPane.showMessageDialog(null, "请先选择要租用的设备！");
				}
			}
		});
		rentButton.setFont(new Font("SimSun", Font.BOLD, 17));
		rentButton.setBounds(156, 678, 113, 40);
		contentPane.add(rentButton);
		
		JButton backButton = new JButton("返回");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backForm.updateData();
				backForm.updateView();
				dispose();
			}
		});
		backButton.setFont(new Font("SimSun", Font.BOLD, 17));
		backButton.setBounds(426, 680, 97, 37);
		contentPane.add(backButton);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 16));
		searchField.setBounds(39, 157, 247, 34);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton nameSearchButton = new JButton("按设备名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eName = searchField.getText();
				EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
				ArrayList<RentEquipment> res = edb.searchSpareREByName(eName);
				int num = res.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的可租设备！");
				}
				else {
					Object[][] newData = createData(res);
					updateView(newData);
				}
			}
		});
		nameSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		nameSearchButton.setBounds(308, 129, 149, 43);
		contentPane.add(nameSearchButton);
		
		JButton cateSearchButton = new JButton("按类型名称查找");
		cateSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ecName = searchField.getText();
				EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
				ArrayList<RentEquipment> res = edb.searchSpareREByCateName(ecName);
				int num = res.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的可租设备！");
				}
				else {
					Object[][] newData = createData(res);
					updateView(newData);
				}
			}
		});
		cateSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		cateSearchButton.setBounds(308, 182, 149, 43);
		contentPane.add(cateSearchButton);
		
		JButton clearButton = new JButton("重置");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				updateData();
				updateView();
			}
		});
		clearButton.setFont(new Font("SimSun", Font.BOLD, 17));
		clearButton.setBounds(491, 154, 105, 39);
		contentPane.add(clearButton);
		
		
		this.setVisible(true);
		
	}
}
