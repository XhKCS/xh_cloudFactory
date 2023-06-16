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
import com.factory.dao.ProductDataBase;
import com.factory.dao.UserDataBase;
import com.factory.model.Product;
import com.factory.model.User;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ProductManaForm extends JFrame {
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
					//ProductManaForm frame = new ProductManaForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public  Object[][] createData(ArrayList<Product> products) {
		int num = products.size();
		Object[][] newData = new Object[num][];
		int i = 0;
		for (Product k : products) {
			newData[i] = new Object[] {new Boolean(false), k.getID(), k.getTypeNum(), k.getName(), 
					k.getCategory().getCategoryName(), k.getSize(), k.getDescription()};
			i++;
		}
		return newData;
	}
	
	public void updateData() {
		ProductDataBase pdb = Dao.getDb().getProductsDB();
		data = createData(pdb.getProducts());	
	}
	
	public void updateView() {
		updateView(data);
	}
	
	public void updateView(Object[][] newData) {
		table.setModel(new DefaultTableModel(
				newData,
				new String[] {
					"  \u9009\u4E2D", "   ID", "\u4EA7\u54C1\u7F16\u53F7", "\u4EA7\u54C1\u540D\u79F0", "\u4EA7\u54C1\u7C7B\u522B", "\u4EA7\u54C1\u89C4\u683C", "\u4EA7\u54C1\u63CF\u8FF0"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, Integer.class, String.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
		});
	}
	
	public JTable getTable() {
		return table;
	}

	/**
	 * Create the frame.
	 */
	public ProductManaForm(ManagerMainForm backForm) {
		this.backForm = backForm;
		
		setResizable(false);
		this.setTitle("管理员——产品管理界面");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				backForm.setVisible(true);
				dispose();
			}
			
		});
		setBounds(500, 150, 752, 613);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		searchField = new JTextField();
		searchField.setFont(new Font("SimSun", Font.PLAIN, 17));
		searchField.setBounds(22, 33, 240, 43);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton cateSearchButton = new JButton("按产品类别查找");
		cateSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cateName = searchField.getText();
				ProductDataBase pdb = Dao.getDb().getProductsDB();
				ArrayList<Product> products = pdb.searchProductsByCateName(cateName);
				int num = products.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该类别名称前缀的产品！");
				}
				else {
					Object[][] newData = createData(products);
					updateView(newData);
				}
				
			}
		});
		cateSearchButton.setFont(new Font("SimSun", Font.BOLD, 16));
		cateSearchButton.setBounds(290, 10, 154, 35);
		contentPane.add(cateSearchButton);
		
		JButton nameSearchButton = new JButton("按产品名称查找");
		nameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pName = searchField.getText();
				ProductDataBase pdb = Dao.getDb().getProductsDB();
				ArrayList<Product> products = pdb.searchProductsByName(pName);
				int num = products.size();
				if (num == 0) {
					JOptionPane.showMessageDialog(null, "不存在该名称前缀的产品！");
				}
				else {
					Object[][] newData = createData(products);
					updateView(newData);
				}
			}
		});
		nameSearchButton.setFont(new Font("SimSun", Font.BOLD, 16));
		nameSearchButton.setBounds(290, 53, 154, 35);
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
		clearButton.setBounds(477, 40, 118, 29);
		contentPane.add(clearButton);
		
		JButton addingButton = new JButton("新建");
		addingButton.addActionListener(new AddingProductListener(this));
		
		addingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		addingButton.setBounds(27, 127, 112, 29);
		contentPane.add(addingButton);
		
		JButton removingButton = new JButton("删除");
		removingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow()>=0) {
					int isDel = JOptionPane.showConfirmDialog(null, "确定要删除吗？"); //选择对话框，包含“是”/“否”/“取消”
					if (isDel == 0) { //说明用户选中“确定”
						ProductDataBase pdb = Dao.getDb().getProductsDB();
						for (int i=0; i<table.getRowCount(); i++)
						{
							Boolean bl = (Boolean) table.getValueAt(i, 0); //判断第i行复选框是否被选中
							if (bl) { //说明第i行被选中
								int productID = (int) (table.getValueAt(i, 1));
								pdb.removeProduct(pdb.getProduct(productID));
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
		removingButton.setBounds(167, 127, 102, 29);
		contentPane.add(removingButton);
		
		JButton editButton = new JButton("修改");
		editButton.addActionListener(new ModifyProductListener(this));
		
		editButton.setFont(new Font("SimSun", Font.BOLD, 17));
		editButton.setBounds(536, 125, 112, 33);
		contentPane.add(editButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 209, 717, 219);
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
		backButton.setBounds(605, 509, 102, 34);
		contentPane.add(backButton);
			
		this.setVisible(true);
	}
	
}

class AddingProductListener implements ActionListener {
	private ProductManaForm backForm;
	
	public AddingProductListener(ProductManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建产品添加界面
		new AddingProductForm(backForm);
	}
	
}

class ModifyProductListener implements ActionListener {
	private ProductManaForm backForm;
	
	public ModifyProductListener(ProductManaForm backForm) {
		this.backForm = backForm;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		//新建产品修改界面
		JTable table = backForm.getTable();
		if (table.getSelectedRow()>=0) {
			int productID = (int) table.getValueAt(table.getSelectedRow(), 1);
			Product p = Dao.getDb().getProductsDB().getProduct(productID);
			
			new ModifyProductForm(backForm, p);
		}
		else {
			JOptionPane.showMessageDialog(null, "请先选择要修改的数据！");
		}
	}
	
}
