package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class TestFrame extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestFrame() {
		setLayout(new CardLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "name_103548832002900");
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(0, 0, 146, 96);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(151, 0, 146, 96);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(302, 0, 146, 96);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(0, 101, 146, 96);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setBounds(151, 101, 146, 96);
		panel_1.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		btnNewButton_5.setBounds(302, 101, 146, 96);
		panel_1.add(btnNewButton_5);
		
		JButton btnNewButton_6 = new JButton("New button");
		btnNewButton_6.setBounds(0, 202, 146, 96);
		panel_1.add(btnNewButton_6);
		
		JButton btnNewButton_7 = new JButton("New button");
		btnNewButton_7.setBounds(151, 202, 146, 96);
		panel_1.add(btnNewButton_7);
		
		JButton btnNewButton_8 = new JButton("New button");
		btnNewButton_8.setBounds(302, 202, 146, 96);
		panel_1.add(btnNewButton_8);
		
		JPanel panel = new JPanel();
		add(panel, "name_103555703304600");
		panel.setLayout(new GridLayout(0, 3, 5, 5));

	}

}
