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
import com.factory.model.EquipmentCategory;
import com.factory.model.ProductCategory;
import com.factory.model.RentEquipment;

public class ModifyREForm extends JFrame {
	private EquipmentManaForm backForm;
	private RentEquipment re;
	
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
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModifyREForm(EquipmentManaForm backForm, RentEquipment re) {
		this.backForm = backForm;
		this.re = re;
		
		this.setTitle("产能中心——修改可租用设备界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 485, 517);
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
		nameLabel.setBounds(35, 92, 95, 30);
		contentPane.add(nameLabel);
	
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(140, 96, 255, 35);
		nameField.setText(re.getName());
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
		cateComboBox.setBounds(140, 160, 149, 35);
		
		cateComboBox.setSelectedItem(re.getCategory().getCategoryName()); //指定被选中项
		contentPane.add(cateComboBox);
		
		JLabel sizeLabel = new JLabel("设备规格");
		sizeLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		sizeLabel.setBounds(35, 239, 80, 24);
		contentPane.add(sizeLabel);
		
		sizeField = new JTextField();
		sizeField.setFont(new Font("SimSun", Font.PLAIN, 16));
		sizeField.setBounds(140, 235, 255, 35);
		sizeField.setText(re.getSize());
		getContentPane().add(sizeField);
		sizeField.setColumns(10);
		
		JLabel descripLabel = new JLabel("设备描述");
		descripLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		descripLabel.setBounds(35, 316, 80, 24);
		contentPane.add(descripLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 16));
		descripField.setBounds(140, 312, 255, 35);
		descripField.setText(re.getDescription());
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
				
				re.setName(name);
				re.setCategory(ec);
				re.setSize(size);
				re.setDescription(des);
				
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
