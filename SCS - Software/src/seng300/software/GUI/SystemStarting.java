package seng300.software.GUI;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;

import java.time.Duration;
import java.time.Instant;

public class SystemStarting extends JPanel {
	private JLabel systemLoad;
	private int systemNum;
	/**
	 * Create the panel.
	 */
	public SystemStarting(int systemNum) {
		this.systemNum = systemNum;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(Box.createRigidArea(new Dimension(75, 50)));
		systemLoad = new JLabel("System " + systemNum + " is starting");
		systemLoad.setFont(new Font("Tahoma", Font.PLAIN, 30));
		add(systemLoad);
	}

}
