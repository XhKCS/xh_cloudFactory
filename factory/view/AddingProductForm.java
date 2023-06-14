package com.factory.view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.ProductCategory;
import com.factory.service.Service;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddingProductForm extends JFrame {
	private ProductManaForm backForm;
	
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
					//AddingProductForm frame = new AddingProductForm(new ProductManaForm());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddingProductForm(ProductManaForm backForm) {
		setResizable(false);
		this.backForm = backForm;
		
		this.setTitle("管理员添加产品界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 463, 539);
		getContentPane().setLayout(null);
		
		JLabel mainLabel = new JLabel("请输入要添加的产品的以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 18));
		mainLabel.setBounds(76, 22, 297, 44);
		getContentPane().add(mainLabel);
		
		JLabel nameLabel = new JLabel("产品名称");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		nameLabel.setBounds(39, 95, 80, 24);
		getContentPane().add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(140, 96, 246, 35);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JLabel cateLabel = new JLabel("产品类别");
		cateLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		cateLabel.setBounds(39, 165, 80, 24);
		getContentPane().add(cateLabel);
		
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
		cateComboBox.setBounds(140, 160, 152, 35);
		getContentPane().add(cateComboBox);
		
		JLabel sizeLabel = new JLabel("产品规格");
		sizeLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		sizeLabel.setBounds(39, 239, 80, 24);
		getContentPane().add(sizeLabel);
		
		sizeField = new JTextField();
		sizeField.setFont(new Font("SimSun", Font.PLAIN, 16));
		sizeField.setBounds(140, 235, 246, 35);
		getContentPane().add(sizeField);
		sizeField.setColumns(10);
		
		JLabel descripLabel = new JLabel("产品描述");
		descripLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		descripLabel.setBounds(39, 316, 80, 24);
		getContentPane().add(descripLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 16));
		descripField.setBounds(140, 312, 246, 35);
		getContentPane().add(descripField);
		descripField.setColumns(10);
		
		JButton addingButton = new JButton("确认添加");
		addingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String cateName = (String)cateComboBox.getSelectedItem();
				String size = sizeField.getText();
				String des = descripField.getText();
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "产品名称不能为空！");
				}
				else if (cateName.equals("")) {
					JOptionPane.showMessageDialog(null, "请选择产品类型！");
				}
				else if (size.equals("")) {
					JOptionPane.showMessageDialog(null, "产品规格不能为空！");
				}
				else {
					Service service = Service.getSingletonInstance();
					int flag = service.addProduct(name, cateName, size, des); //其余操作在service层完成
					if (flag == 0) {
						backForm.updateData();
						backForm.updateView();
						dispose();
					}
				}
			}
		});
		addingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		addingButton.setBounds(102, 398, 109, 35);
		getContentPane().add(addingButton);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "已取消！");
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 17));
		cancleButton.setBounds(264, 398, 99, 35);
		getContentPane().add(cancleButton);
		
		this.setVisible(true);
	}
}
