package seng300.software.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.lsmr.selfcheckout.devices.SupervisionStation;

public class Program
{

	public static void main(String args[])
	{
		SupervisionStation attendant = new SupervisionStation();
		attendant.screen.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		attendant.screen.getFrame().setBounds(100, 100, 450, 450);
//		JPanel contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		attendant.screen.getFrame().setContentPane(contentPane);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					attendant.screen.getFrame().setContentPane(new AttendantLogin());
					attendant.screen.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
