package com.factory.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.factory.dao.CategoryDataBase;
import com.factory.dao.Dao;
import com.factory.model.Category;
import com.factory.model.ProductCategory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModifyPCForm extends JFrame {
	private PCManaForm backForm;
	private ProductCategory pc;
	
	private JPanel contentPane;
	private JTextField nameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//ModifyPCForm frame = new ModifyPCForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifyPCForm(PCManaForm backForm, ProductCategory pc) {
		setResizable(false);
		this.backForm = backForm;
		this.pc = pc;
		
		this.setTitle("管理员修改产品类别界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 456, 263);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("您可选择修改以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 19));
		mainLabel.setBounds(94, 21, 234, 34);
		contentPane.add(mainLabel);
		
		JLabel nameLabel = new JLabel("类别名称");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 18));
		nameLabel.setBounds(10, 83, 85, 34);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(105, 82, 287, 39);
		nameField.setText(pc.getCategoryName());
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JButton modifyButton = new JButton("确认修改");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cateName = nameField.getText();
				CategoryDataBase cdb = Dao.getDb().getCategoriesDB();
				Category check = cdb.getCategoryByName(cateName);
				if (check != null) {
					JOptionPane.showMessageDialog(null, "该类别名称已存在！请重新设置名称！");
				}
				else {
					pc.setCategoryName(cateName);
					backForm.updateData();
					backForm.updateView();
					JOptionPane.showMessageDialog(null, "修改成功！");
					dispose();
				}
			}
		});
		modifyButton.setFont(new Font("SimSun", Font.BOLD, 17));
		modifyButton.setBounds(89, 161, 112, 34);
		contentPane.add(modifyButton);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "已取消！");
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 17));
		cancleButton.setBounds(242, 161, 104, 34);
		contentPane.add(cancleButton);
		
		this.setVisible(true);
	}
}
