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
import com.factory.model.Factory;
import com.factory.service.Service;

public class AddingFEForm extends JFrame {
	private FOMainForm backForm;
	private Factory myFactory;
	
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
					//AddingFEForm frame = new AddingFEForm();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddingFEForm(FOMainForm backForm) {
		setResizable(false);
		this.backForm = backForm;
		this.myFactory = backForm.getMyFactory();
		
		this.setTitle(myFactory.getName()+" 添加工厂设备界面");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 150, 486, 529);
		contentPane = new BackgroundPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel mainLabel = new JLabel("请输入要添加的工厂设备的以下信息：");
		mainLabel.setFont(new Font("SimHei", Font.BOLD, 18));
		mainLabel.setBounds(64, 44, 340, 37);
		contentPane.add(mainLabel);
		
		JLabel nameLabel = new JLabel("设备名称");
		nameLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		nameLabel.setBounds(25, 123, 71, 28);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("SimSun", Font.PLAIN, 16));
		nameField.setBounds(122, 120, 296, 37);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel cateLabel = new JLabel("设备类别");
		cateLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		cateLabel.setBounds(25, 201, 71, 28);
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
		cateComboBox.setBounds(123, 197, 207, 37);
		getContentPane().add(cateComboBox);
		
		JLabel sizeLabel = new JLabel("设备规格");
		sizeLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		sizeLabel.setBounds(25, 276, 71, 28);
		contentPane.add(sizeLabel);
		
		sizeField = new JTextField();
		sizeField.setFont(new Font("SimSun", Font.PLAIN, 16));
		sizeField.setBounds(122, 268, 296, 37);
		contentPane.add(sizeField);
		sizeField.setColumns(10);
		
		JLabel descripLabel = new JLabel("设备描述");
		descripLabel.setFont(new Font("SimSun", Font.BOLD, 17));
		descripLabel.setBounds(25, 350, 71, 28);
		contentPane.add(descripLabel);
		
		descripField = new JTextField();
		descripField.setFont(new Font("SimSun", Font.PLAIN, 16));
		descripField.setBounds(122, 343, 296, 37);
		contentPane.add(descripField);
		descripField.setColumns(10);
		
		JButton addingButton = new JButton("确认添加");
		addingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String cateName = (String)cateComboBox.getSelectedItem();
				String size = sizeField.getText();
				String des = descripField.getText();
				if (name.equals("")) {
					JOptionPane.showMessageDialog(null, "设备名称不能为空！");
				}
				else if (cateName.equals("")) {
					JOptionPane.showMessageDialog(null, "请选择设备类型！");
				}
				else if (size.equals("")) {
					JOptionPane.showMessageDialog(null, "设备规格不能为空！");
				}
				else {
					Service service = Service.getSingletonInstance();
					int flag = service.addEquipmentToFactory(name, cateName, size, des, myFactory); //其余操作在service层完成
					if (flag == 0) {
						backForm.updateData();
						backForm.updateView();
						dispose();
					}
				}
			}
		});
		addingButton.setFont(new Font("SimSun", Font.BOLD, 17));
		addingButton.setBounds(108, 421, 122, 37);
		contentPane.add(addingButton);
		
		JButton cancleButton = new JButton("取消");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "已取消！");
				dispose();
			}
		});
		cancleButton.setFont(new Font("SimSun", Font.BOLD, 17));
		cancleButton.setBounds(291, 421, 101, 37);
		contentPane.add(cancleButton);
		
		
		this.setVisible(true);
	}

}
