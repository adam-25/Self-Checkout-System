package seng300.software.GUI;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.EventQueue;
import java.awt.Font;

public class StationUnavailablePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public StationUnavailablePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 36, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel stationUnavailableLabel = new JLabel("Station is Unavailable");
		stationUnavailableLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_stationUnavailableLabel = new GridBagConstraints();
		gbc_stationUnavailableLabel.insets = new Insets(0, 0, 5, 5);
		gbc_stationUnavailableLabel.gridx = 1;
		gbc_stationUnavailableLabel.gridy = 1;
		add(stationUnavailableLabel, gbc_stationUnavailableLabel);

	}
	
	/**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new StationUnavailablePanel());
					frame.setBounds(100, 100, 450, 450);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
