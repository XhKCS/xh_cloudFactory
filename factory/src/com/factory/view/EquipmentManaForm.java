package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.factory.dao.Dao;
import com.factory.dao.EquipmentDataBase;
import com.factory.dao.FactoryDataBase;
import com.factory.dao.ProductDataBase;
import com.factory.model.Equipment;
import com.factory.model.Factory;
import com.factory.model.Product;
import com.factory.model.RentEquipment;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

/*这是系统管理员操作的产能中心的设备管理界面，与云工厂管理员的界面不同，这里添加的只能是产能中心的供租用设备，
 * 即RentEquipment；而云工厂管理员新建的也只能是个人工厂的工厂设备，或从产能中心租用设备
 */
public class EquipmentManaForm extends JFrame {
	private ManagerMainForm backForm;
	
	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	private Object[][] data;
	
	public JTable getTable() {
		return table;
	}
	
	public Object[][] createData(ArrayList<Equipment> equipments) {
		int num = equipments.size();
		Object[][] newData = new Object[num][10];
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
		EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
		data = createData(edb.getEquipments());
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
					//EquipmentManaForm frame = new EquipmentManaForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EquipmentManaForm(ManagerMainForm backForm) {
		this.backForm = backForm;
		
		setResizable(false);
		this.setTitle("产能中心——设备管理");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 794, 693);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 15));
		searchField.setBounds(10, 36, 209, 29);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton nameSearchButton = new JButton("按设备名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String eName = searchField.getText();
				EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
				ArrayList<Equipment> equipments = edb.searchEquipmentsByName(eName);
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
		nameSearchButton.setBounds(250, 10, 153, 35);
		contentPane.add(nameSearchButton);
		
		JButton clearButton = new JButton("重置");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				updateData();
				updateView();
			}
		});
		clearButton.setFont(new Font("SimSun", Font.BOLD, 17));
		clearButton.setBounds(444, 34, 123, 30);
		contentPane.add(clearButton);
		
		JButton cateSearchButton = new JButton("按类别名称查找");
		cateSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cateName = searchField.getText();
				EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
				ArrayList<Equipment> equipments = edb.searchEquipmentsByCateName(cateName);
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
		cateSearchButton.setBounds(250, 55, 153, 30);
		contentPane.add(cateSearchButton);
		
		JButton addingButton = new JButton("新增");
		addingButton.addActionListener(new AddingREListener(this));
		addingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		addingButton.setBounds(23, 139, 107, 29);
		contentPane.add(addingButton);
		
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
							Boolean bl = (Boolean) table.getValueAt(i, 0); //判断第i行复选框是否被选中
							if (bl) { //说明第i行被选中
								int equipmentID = (int) (table.getValueAt(i, 1));
								Equipment equipment = edb.getEquipment(equipmentID);
								if (equipment instanceof RentEquipment) {
									if (equipment.getRentState().equals("未被租用")) {
										edb.removeEquipment(equipment);
									}
									else {
										JOptionPane.showMessageDialog(null, "该设备正在被工厂租用，不能删除！");
										break;
									}
								}
								else {
									JOptionPane.showMessageDialog(null, "该设备是工厂设备，不能删除！");
									break;
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
		removingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		removingButton.setBounds(154, 139, 100, 29);
		contentPane.add(removingButton);
		
		JButton editButton = new JButton("修改");
		editButton.addActionListener(new ModifyREListener(this));
		editButton.setFont(new Font("SimSun", Font.BOLD, 17));
		editButton.setBounds(607, 139, 107, 29);
		contentPane.add(editButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 209, 761, 308);
		contentPane.add(scrollPane);
		
		table = new JTable();
		
		updateData();
		updateView();
		
		scrollPane.setViewportView(table);
		
		JButton stateButton = new JButton("设备状态");
		stateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount() > 0) 
				{
					int isChange = JOptionPane.showConfirmDialog(null, "确定要改变设备工作状态吗？");
					if (isChange == 0) { //说明用户选中“确定”
						EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							
							Boolean bl = (Boolean) table.getValueAt(i, 0);
							if (bl) { //说明第i行被选中
								int equipmentID = (int) (table.getValueAt(i, 1));
								Equipment equipment = edb.getEquipment(equipmentID);
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
		stateButton.setFont(new Font("SimSun", Font.BOLD, 16));
		stateButton.setBounds(608, 87, 107, 35);
		contentPane.add(stateButton);
		
		JButton assureButton = new JButton("确定");
		assureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		assureButton.setFont(new Font("SimSun", Font.BOLD, 17));
		assureButton.setBounds(88, 588, 115, 29);
		contentPane.add(assureButton);
		
		JButton backButton = new JButton("返回");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backForm.setVisible(true);
				dispose();
			}
		});
		backButton.setFont(new Font("SimSun", Font.BOLD, 17));
		backButton.setBounds(539, 588, 107, 29);
		contentPane.add(backButton);
		
		this.setVisible(true);
	}

}

class AddingREListener implements ActionListener {
	private EquipmentManaForm backForm;
	
	public AddingREListener(EquipmentManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建可租用设备添加界面
		new AddingREForm(backForm);
	}
	
}

class ModifyREListener implements ActionListener {
	private EquipmentManaForm backForm;
	
	public ModifyREListener(EquipmentManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable table = backForm.getTable();
		if (table.getSelectedRow()>=0) {
			int equipmentID = (int)(table.getValueAt(table.getSelectedRow(), 1));
			Equipment equipment = Dao.getDb().getEquipmentsDB().getEquipment(equipmentID);
			if (equipment instanceof RentEquipment) {
				if (equipment.getRentState().equals("未被租用")) {
					//新建设备修改界面
					new ModifyREForm(backForm, (RentEquipment)equipment);
				}
				else {
					JOptionPane.showMessageDialog(null, "该设备正在被工厂租用，不能修改！");
					return;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "该设备是工厂设备，不能修改！");
				return;
			}	
		}
		else {
			JOptionPane.showMessageDialog(null, "请先选择要修改的数据！");
		}
	}
	
}
