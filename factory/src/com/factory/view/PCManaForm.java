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

import com.factory.dao.CategoryDataBase;
import com.factory.dao.Dao;
import com.factory.dao.ProductDataBase;
import com.factory.model.Equipment;
import com.factory.model.EquipmentCategory;
import com.factory.model.Product;
import com.factory.model.ProductCategory;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class PCManaForm extends JFrame {
	private ManagerMainForm backForm;
	
	private JPanel contentPane;
	private JTextField searchField;
	private JTable table;
	private Object[][] data;
	
	public JTable getTable() {
		return table;
	}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//PCManaForm frame = new PCManaForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Object[][] createData(ArrayList<ProductCategory> pcs) {
		int num = pcs.size();
		Object[][] newData = new Object[num][3];
		int i = 0;
		for (ProductCategory k : pcs) {
			newData[i] = new Object[] {new Boolean(false), k.getID(), k.getCategoryName()};
			i++;
		}
		return newData;
	}
	
	public void updateData() {
		ArrayList<ProductCategory> pcs = Dao.getDb().getCategoriesDB().getProductCategories();
		data = createData(pcs);
	}
	
	public void updateView() {
		updateView(data);
	}
	
	public void updateView(Object[][] newData) {
		table.setModel(new DefaultTableModel(
				newData,
				new String[] {
					"     \u9009\u4E2D", "      ID", "     \u7C7B\u522B\u540D\u79F0"
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
	 * Create the frame.
	 */
	public PCManaForm(ManagerMainForm backForm) {
		this.backForm = backForm;
		
		this.setTitle("管理员——产品类别管理界面");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 598, 530);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 17));
		searchField.setBounds(26, 28, 236, 29);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton nameSearchButton = new JButton("按类别名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cateName = searchField.getText();
				CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
				ArrayList<ProductCategory> pcs = cdb.searchPCsByName(cateName);
				int num = pcs.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的产品类别！");
				}
				else {
					Object[][] newData = createData(pcs);
					updateView(newData);
				}
			}
		});
		nameSearchButton.setFont(new Font("SimSun", Font.BOLD, 15));
		nameSearchButton.setBounds(285, 29, 155, 28);
		contentPane.add(nameSearchButton);
		
		JButton clearButton = new JButton("重置");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				updateData();
				updateView();
			}
		});
		clearButton.setFont(new Font("SimSun", Font.BOLD, 16));
		clearButton.setBounds(450, 29, 93, 26);
		contentPane.add(clearButton);
		
		JButton addingButton = new JButton("新建");
		addingButton.addActionListener(new AddingPCListener(this));
		addingButton.setFont(new Font("SimSun", Font.BOLD, 16));
		addingButton.setBounds(23, 93, 98, 29);
		contentPane.add(addingButton);
		
		JButton removingButton = new JButton("删除");
		removingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() >= 0) {
					int isDel = JOptionPane.showConfirmDialog(null, "确定要删除吗？"); //选择对话框，包含“是”/“否”/“取消”
					if (isDel == 0) { //说明用户选中“确定”
						CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
						ProductDataBase pdb = Dao.getDb().getProductsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); //判断第i行复选框是否被选中
							if (bl) { //说明第i行被选中
								int cateID = (int) (table.getValueAt(i, 1));
								ProductCategory pc = cdb.getPCByID(cateID);
								ArrayList<Product> check = pdb.getProductsByCategory(pc);
								if (check.size() > 0) {
									JOptionPane.showMessageDialog(null, "该类别正在被产品引用，不能删除！");
									break;
								}
								else {
									cdb.removeCategory(pc);
								}							}
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
		removingButton.setFont(new Font("SimSun", Font.BOLD, 16));
		removingButton.setBounds(139, 93, 93, 29);
		contentPane.add(removingButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 156, 566, 189);
		contentPane.add(scrollPane);	
		table = new JTable();
		updateData();
		updateView();
		scrollPane.setViewportView(table);
		
		
		JButton editButton = new JButton("修改");
		editButton.addActionListener(new ModifyPCListener(this));
		editButton.setFont(new Font("SimSun", Font.BOLD, 16));
		editButton.setBounds(450, 93, 93, 29);
		contentPane.add(editButton);
		
		JButton backButton = new JButton("返回");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backForm.setVisible(true);
				dispose();
			}
		});
		backButton.setFont(new Font("SimSun", Font.BOLD, 16));
		backButton.setBounds(462, 429, 93, 33);
		contentPane.add(backButton);
		this.setVisible(true);
	}
}

class AddingPCListener implements ActionListener {
	private PCManaForm backForm;
	
	public AddingPCListener(PCManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建产品类别添加界面
		new AddingPCForm(backForm);
	}
	
}

class ModifyPCListener implements ActionListener {
	private PCManaForm backForm;
	
	public ModifyPCListener(PCManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建产品修改界面
		JTable table = backForm.getTable();
		if (table.getSelectedRow()>=0) {
			int cateID = (int)table.getValueAt(table.getSelectedRow(), 1);
			CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
			ProductCategory pc = cdb.getPCByID(cateID);
			
			new ModifyPCForm(backForm, pc);
		}
		else {
			JOptionPane.showMessageDialog(null, "请先选择要修改的数据！");
		}
	}
	
}

