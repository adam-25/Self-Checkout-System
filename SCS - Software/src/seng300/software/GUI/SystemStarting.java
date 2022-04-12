package seng300.software.GUI;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import seng300.software.AttendantLogic;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class SystemStarting extends JPanel implements ActionListener{
	private JLabel systemLoad;
	private AttendantGUI aGUI;
	private Timer timer;
	private int systemNum;
	/**
	 * Create the panel.
	 */
	public SystemStarting(int systemNum, AttendantGUI gui) {
		this.systemNum = systemNum;
		this.aGUI = gui;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		timer = new Timer(2000, this);
		timer.setRepeats(false);
		
		systemLoad = new JLabel("System " + systemNum + " is starting");
		systemLoad.setFont(new Font("Tahoma", Font.PLAIN, 30));
		GridBagConstraints gbc_systemLoad = new GridBagConstraints();
		gbc_systemLoad.insets = new Insets(0, 0, 5, 5);
		gbc_systemLoad.fill = GridBagConstraints.BOTH;
		gbc_systemLoad.gridx = 1;
		gbc_systemLoad.gridy = 3;
		add(systemLoad, gbc_systemLoad);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.stop();
		aGUI.openAttendantMain();
	}
	
	public void currentSystem(int systemNum) {
		this.systemNum = systemNum;
		systemLoad.setText("System " + systemNum + " is starting");
	}
	
	public void shoutOff() {
		if (!timer.isRunning()) {
			timer.start();
		}
	}
	
}
