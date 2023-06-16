package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.factory.dao.CategoryDataBase;
import com.factory.dao.Dao;
import com.factory.dao.EquipmentDataBase;
import com.factory.model.Equipment;
import com.factory.model.EquipmentCategory;
import com.factory.model.ProductCategory;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ECManaForm extends JFrame {
	private ManagerMainForm backForm;
	
	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	private Object[][] data;
	
	public JTable getTable() {
		return table;
	}
	
	public Object[][] createData(ArrayList<EquipmentCategory> ecs) {
		int num = ecs.size();
		Object[][] newData = new Object[num][3];
		int i = 0;
		for (EquipmentCategory k : ecs) {
			newData[i] = new Object[] {new Boolean(false), k.getID(), k.getCategoryName()};
			i++;
		}
		return newData;
	}
	
	public void updateData() {
		ArrayList<EquipmentCategory> ecs = Dao.getDb().getCategoriesDB().getEquipmentCategories();
		data = createData(ecs);
	}
	
	public void updateView() {
		updateView(data);
	}
	
	public void updateView(Object[][] newData) {
		table.setModel(new DefaultTableModel(
				newData,
				new String[] {
					"   \u9009\u4E2D", "    ID", "\u7C7B\u522B\u540D\u79F0"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, Integer.class, String.class
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
					//ECManaForm frame = new ECManaForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ECManaForm(ManagerMainForm backForm) {
		this.backForm = backForm;
		
		setResizable(false);
		this.setTitle("产能中心——设备类别管理");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 651, 564);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton nameSearchButton = new JButton("按类别名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cateName = searchField.getText();
				CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
				ArrayList<EquipmentCategory> ecs = cdb.searchECsByName(cateName);
				int num = ecs.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的产品类别！");
				}
				else {
					Object[][] newData = createData(ecs);
					updateView(newData);
				}
			}
		});
		nameSearchButton.setFont(new Font("SimSun", Font.BOLD, 16));
		nameSearchButton.setBounds(265, 46, 154, 39);
		contentPane.add(nameSearchButton);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 15));
		searchField.setBounds(34, 50, 210, 33);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton clearButton = new JButton("重置");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				updateData();
				updateView();
			}
		});
		clearButton.setFont(new Font("SimSun", Font.BOLD, 17));
		clearButton.setBounds(452, 49, 107, 33);
		contentPane.add(clearButton);
		
		JButton addingButton = new JButton("新建");
		addingButton.addActionListener(new AddingECListener(this));
		addingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		addingButton.setBounds(34, 126, 107, 33);
		contentPane.add(addingButton);
		
		JButton removingButton = new JButton("删除");
		removingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int isDel = JOptionPane.showConfirmDialog(null, "确定要删除吗？"); //选择对话框，包含“是”/“否”/“取消”
					if (isDel == 0) { //说明用户选中“确定”
						CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
						EquipmentDataBase edb = Dao.getDb().getEquipmentsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); //判断第i行复选框是否被选中
							if (bl) { //说明第i行被选中
								int cateID = (int) (table.getValueAt(i, 1));
								EquipmentCategory ec = cdb.getECByID(cateID);
								ArrayList<Equipment> check = edb.getEquipmentsByCategory(ec);
								if (check.size() > 0) {
									JOptionPane.showMessageDialog(null, "该类别正在被设备引用，不能删除！");
									break;
								}
								else {
									cdb.removeCategory(ec);
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
		removingButton.setBounds(175, 126, 101, 33);
		contentPane.add(removingButton);
		
		JButton editButton = new JButton("修改");
		editButton.addActionListener(new ModifyECListener(this));
		editButton.setFont(new Font("SimSun", Font.BOLD, 17));
		editButton.setBounds(465, 126, 107, 33);
		contentPane.add(editButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 202, 592, 188);
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
		backButton.setBounds(480, 453, 107, 35);
		contentPane.add(backButton);
		
		this.setVisible(true);
	}

}

class AddingECListener implements ActionListener {
	private ECManaForm backForm;
	
	public AddingECListener(ECManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建设备类别添加界面
		new AddingECForm(backForm);
		
	}
}

class ModifyECListener implements ActionListener {
	private ECManaForm backForm;
	
	public ModifyECListener(ECManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建产品修改界面
		JTable table = backForm.getTable();
		if (table.getSelectedRow()>=0) {
			int cateID = (int)table.getValueAt(table.getSelectedRow(), 1);
			CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
			EquipmentCategory ec = cdb.getECByID(cateID);
			new ModifyECForm(backForm, ec);
		}
		else {
			JOptionPane.showMessageDialog(null, "请先选择要修改的数据！");
		}
	}
	
}
