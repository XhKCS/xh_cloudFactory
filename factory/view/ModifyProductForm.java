package com.factory.view;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.Product;
import com.factory.model.ProductCategory;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyProductForm extends JFrame {
	private ProductManaForm backForm;
	private Product product;
	
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField sizeField;
	private JTextField descripField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ModifyProductForm frame = new ModifyProductForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifyProductForm(ProductManaForm backForm, Product product) {
		setResizable(false);
		this.backForm = backForm;
		this.product = product;
		
		this.setTitle("管理员修改产品信息界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 461, 506);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("您可选择修改以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 20));
		mainLabel.setBounds(106, 10, 248, 54);
		contentPane.add(mainLabel);
		
		JLabel nameLabel = new JLabel("产品名称");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 18));
		nameLabel.setBounds(35, 92, 95, 30);
		contentPane.add(nameLabel);
	
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(140, 96, 248, 35);
		nameField.setText(product.getName());
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel cateLabel = new JLabel("产品类别");
		cateLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		cateLabel.setBounds(35, 165, 80, 24);
		contentPane.add(cateLabel);
		
		JComboBox<String> cateComboBox = new JComboBox<String>();
		cateComboBox.setFont(new Font("SimSun", Font.BOLD, 17));
		ArrayList<ProductCategory> pcs = Dao.getDb().getCategoriesDB().getProductCategories();
		String[] choices = new String[pcs.size()];
		int i = 0;
		for (ProductCategory k : pcs) {
			choices[i] = k.getCategoryName();
			i++;
		}
		cateComboBox.setModel(new DefaultComboBoxModel<String>(choices));
		cateComboBox.setBounds(140, 160, 145, 35);
		
		cateComboBox.setSelectedItem(product.getCategory().getCategoryName()); //指定被选中项
		contentPane.add(cateComboBox);
		
		JLabel sizeLabel = new JLabel("产品规格");
		sizeLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		sizeLabel.setBounds(35, 239, 80, 24);
		contentPane.add(sizeLabel);
		
		sizeField = new JTextField();
		sizeField.setFont(new Font("SimSun", Font.PLAIN, 16));
		sizeField.setBounds(140, 235, 248, 35);
		sizeField.setText(product.getSize());
		getContentPane().add(sizeField);
		sizeField.setColumns(10);
		
		JLabel descripLabel = new JLabel("产品描述");
		descripLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		descripLabel.setBounds(35, 316, 80, 24);
		contentPane.add(descripLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 16));
		descripField.setBounds(140, 312, 248, 35);
		descripField.setText(product.getDescription());
		contentPane.add(descripField);
		descripField.setColumns(10);
		
		JButton modifyButton = new JButton("确认修改");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String pcName = (String)cateComboBox.getSelectedItem();
				ProductCategory pc = Dao.getDb().getCategoriesDB().getPCByName(pcName);
				String size = sizeField.getText();
				String des = descripField.getText();
				
				product.setName(name);
				product.setCategory(pc);
				product.setSize(size);
				product.setDescription(des);
				
				backForm.updateData();
				backForm.updateView();
				JOptionPane.showMessageDialog(null, "修改成功！");
				dispose();
			}
		});
		modifyButton.setFont(new Font("SimSun", Font.BOLD, 17));
		modifyButton.setBounds(96, 389, 108, 35);
		contentPane.add(modifyButton);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 17));
		cancleButton.setBounds(253, 390, 101, 33);
		contentPane.add(cancleButton);
		
		this.setVisible(true);
	}
}
