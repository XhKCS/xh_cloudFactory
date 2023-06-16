package com.factory.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.factory.dao.Dao;
import com.factory.model.Equipment;
import com.factory.model.EquipmentCategory;

public class ModifyFEForm extends JFrame {
	private FOMainForm backForm;
	private Equipment fe;
	
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
					//MOdifyFEForm frame = new MOdifyFEForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifyFEForm(FOMainForm backForm, Equipment fe) {
		setResizable(false);
		this.backForm = backForm;
		this.fe = fe;
		
		this.setTitle(backForm.getMyFactory().getName()+" 修改设备界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 491, 514);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("您可选择修改以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 20));
		mainLabel.setBounds(106, 10, 248, 54);
		contentPane.add(mainLabel);
		
		JLabel nameLabel = new JLabel("设备名称");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 18));
		nameLabel.setBounds(35, 97, 95, 30);
		contentPane.add(nameLabel);
	
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(140, 96, 248, 35);
		nameField.setText(fe.getName());
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel cateLabel = new JLabel("设备类别");
		cateLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		cateLabel.setBounds(35, 165, 80, 24);
		contentPane.add(cateLabel);
		
		JComboBox<String> cateComboBox = new JComboBox<String>();
		cateComboBox.setFont(new Font("SimSun", Font.BOLD, 17));
		ArrayList<EquipmentCategory> ecs = Dao.getDb().getCategoriesDB().getEquipmentCategories();
		String[] choices = new String[ecs.size()];
		int i = 0;
		for (EquipmentCategory k : ecs) {
			choices[i] = k.getCategoryName();
			i++;
		}
		cateComboBox.setModel(new DefaultComboBoxModel<String>(choices));
		cateComboBox.setBounds(140, 160, 248, 35);
		
		cateComboBox.setSelectedItem(fe.getCategory().getCategoryName()); //指定被选中项
		contentPane.add(cateComboBox);
		
		JLabel sizeLabel = new JLabel("设备规格");
		sizeLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		sizeLabel.setBounds(35, 240, 80, 24);
		contentPane.add(sizeLabel);
		
		sizeField = new JTextField();
		sizeField.setFont(new Font("SimSun", Font.PLAIN, 16));
		sizeField.setBounds(140, 235, 248, 35);
		sizeField.setText(fe.getSize());
		getContentPane().add(sizeField);
		sizeField.setColumns(10);
		
		JLabel descripLabel = new JLabel("设备描述");
		descripLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		descripLabel.setBounds(35, 317, 80, 24);
		contentPane.add(descripLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 16));
		descripField.setBounds(140, 312, 248, 35);
		descripField.setText(fe.getDescription());
		contentPane.add(descripField);
		descripField.setColumns(10);
		
		JButton modifyButton = new JButton("确认修改");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String ecName = (String)cateComboBox.getSelectedItem();
				EquipmentCategory ec = Dao.getDb().getCategoriesDB().getECByName(ecName);
				String size = sizeField.getText();
				String des = descripField.getText();
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "设备名称不能为空！");
				}
				else if (ecName.equals("")) {
					JOptionPane.showMessageDialog(null, "请选择设备类型！");
				}
				else if (size.equals("")) {
					JOptionPane.showMessageDialog(null, "设备规格不能为空！");
				}
				else {
					fe.setName(name);
					fe.setCategory(ec);
					fe.setSize(size);
					fe.setDescription(des);
					
					backForm.updateData();
					backForm.updateView();
					JOptionPane.showMessageDialog(null, "修改成功！");
					dispose();
				}
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
