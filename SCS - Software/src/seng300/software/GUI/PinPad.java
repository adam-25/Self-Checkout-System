package seng300.software.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;

public class PinPad extends JFrame {

	private static final int ONE 	= 1;
	private static final int TWO 	= 2;
	private static final int THREE 	= 3;
	private static final int FOUR 	= 4;
	private static final int FIVE 	= 5;
	private static final int SIX 	= 6;
	private static final int SEVEN 	= 7;
	private static final int EIGHT 	= 8;
	private static final int NINE 	= 9;
	private static final int ZERO 	= 0;
	private static final int CLEAR 	= -1;
	private static final int ENTER 	= -2;
	
	private JPanel contentPane;
	private JTextField displayInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PinPad frame = new PinPad();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PinPad() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[33.33%,grow][33.333%,grow][33.33%,grow]", "[][grow][grow][grow][grow][grow][grow][]"));
		
		displayInput = new JTextField();
		displayInput.setHorizontalAlignment(SwingConstants.CENTER);
		displayInput.setFont(new Font("Tahoma", Font.PLAIN, 28));
		contentPane.add(displayInput, "cell 0 1 3 1,grow");
		displayInput.setColumns(10);
		
		JButton oneBtn = new JButton("1");
		oneBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		oneBtn.addActionListener(e -> push(ONE));
		contentPane.add(oneBtn, "cell 0 3,grow");
		
		JButton twoBtn = new JButton("2");
		twoBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		twoBtn.addActionListener(e -> push(TWO));
		contentPane.add(twoBtn, "cell 1 3,grow");
		
		JButton threeBtn = new JButton("3");
		threeBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		threeBtn.addActionListener(e -> push(THREE));
		contentPane.add(threeBtn, "cell 2 3,grow");
		
		JButton fourBtn = new JButton("4");
		fourBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		fourBtn.addActionListener(e -> push(FOUR));
		contentPane.add(fourBtn, "cell 0 4,grow");
		
		JButton fiveBtn = new JButton("5");
		fiveBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		fiveBtn.addActionListener(e -> push(FIVE));
		contentPane.add(fiveBtn, "cell 1 4,grow");
		
		JButton sixBtn = new JButton("6");
		sixBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		sixBtn.addActionListener(e -> push(SIX));
		contentPane.add(sixBtn, "cell 2 4,grow");
		
		JButton sevenBtn = new JButton("7");
		sevenBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		sevenBtn.addActionListener(e -> push(SEVEN));
		contentPane.add(sevenBtn, "cell 0 5,grow");
		
		JButton eightBtn = new JButton("8");
		eightBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		eightBtn.addActionListener(e -> push(EIGHT));
		contentPane.add(eightBtn, "cell 1 5,grow");
		
		JButton nineBtn = new JButton("9");
		nineBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		nineBtn.addActionListener(e -> push(NINE));
		contentPane.add(nineBtn, "cell 2 5,grow");
		
		JButton clearPadBtn = new JButton("Clear");
		clearPadBtn.setForeground(Color.RED);
		clearPadBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		clearPadBtn.addActionListener(e -> push(CLEAR));
		contentPane.add(clearPadBtn, "cell 0 6,grow");
		
		JButton zeroBtn = new JButton("0");
		zeroBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		zeroBtn.addActionListener(e -> push(ZERO));
		contentPane.add(zeroBtn, "cell 1 6,grow");
		
		JButton padEnterBtn = new JButton("Enter");
		padEnterBtn.setForeground(new Color(0, 153, 51));
		padEnterBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		padEnterBtn.addActionListener(e -> push(ENTER));
		contentPane.add(padEnterBtn, "cell 2 6,grow");
	}
	
	void push(int opt)
	{
		if (opt == CLEAR)
		{	// remove last char from current input
			String oldText = displayInput.getText();
			String newText = oldText.substring(0, oldText.length()-1);
			displayInput.setText(newText);
		}
		else if(opt == ENTER)
		{
			//todo
		}
		else
		{
			String oldText = displayInput.getText();
			String newText = oldText + opt;
			displayInput.setText(newText);
		}
	}

}
