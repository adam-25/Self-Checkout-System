package seng300.software.GUI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class BaggingAreaPanel extends JPanel implements ActionListener
{
    public final JButton returnButton;
    public final JButton deleteButton;
    
    private JButton upButton;
    private JButton downButton;

    private JLabel windowText;
    private JLabel highText;
    private JLabel middleText;
    private JLabel lowText;

    private int n = 0;
    private int arraySize;

    ArrayList<String> cars;

    void displayOfItems(ArrayList<String> stuff){
        if(arraySize == 0){
            highText.setText("");
            highText.setOpaque(false);
            middleText.setText("Bagging Area is Empty");
            middleText.setOpaque(false);
            lowText.setText("");
            lowText.setOpaque(false);
        }else{
            if(n == 0){
                highText.setText("");
                highText.setOpaque(false);
            }else{
                highText.setText(stuff.get(n-1));
                highText.setOpaque(true);
            }
            middleText.setText(stuff.get(n));
            middleText.setOpaque(true);
            if(n == (arraySize-1)){
                lowText.setText("");
                lowText.setOpaque(false);
            }else{
                lowText.setText(stuff.get(n+1));
                lowText.setOpaque(true);
            }
        }
    }

    public BaggingAreaPanel(ArrayList<String> carsInput)
    {
    	setBackground(Color.WHITE);
    	setBorder(new EmptyBorder(15, 15, 15, 15));
        cars = carsInput;
        arraySize = cars.size();

        //----Setting up Text----//
        windowText = new JLabel();
        windowText.setBackground(Color.WHITE);
        windowText.setHorizontalAlignment(SwingConstants.CENTER);
        windowText.setFont(new Font("Tahoma", Font.BOLD, 32));
        windowText.setText("Bagging Area");
        windowText.setBounds(0, 0, 100, 100);

        highText = new JLabel();
        highText.setOpaque(true);
        highText.setHorizontalAlignment(SwingConstants.CENTER);
        highText.setFont(new Font("Tahoma", Font.BOLD, 20));
        highText.setForeground(new Color(130, 130, 130));
        highText.setBackground(new Color(248, 248, 255));
        highText.setBounds(0, 100, 100, 100);

        middleText = new JLabel();
        middleText.setOpaque(true);
        middleText.setHorizontalAlignment(SwingConstants.CENTER);
        middleText.setFont(new Font("Tahoma", Font.BOLD, 20));
        middleText.setForeground(new Color(0, 0, 128));
        middleText.setBackground(new Color(248, 248, 255));
        middleText.setText("");
        middleText.setBounds(0, 50, 100, 100);

        lowText = new JLabel();
        lowText.setOpaque(true);
        lowText.setHorizontalAlignment(SwingConstants.CENTER);
        lowText.setFont(new Font("Tahoma", Font.BOLD, 20));
        lowText.setForeground(new Color(0, 0, 128));
        lowText.setBackground(new Color(248, 248, 255));
        lowText.setText("");
        lowText.setBounds(0, 0, 100, 100);
        //----Setting up Text----//

        //----Setting up Buttons----//
        returnButton = new JButton();
        returnButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        returnButton.setBackground(new Color(240, 255, 240));
        returnButton.setForeground(new Color(0, 100, 0));
        returnButton.setMargin(new Insets(2, 14, 2, 0));
        returnButton.setBounds(0, 0, 100, 100);
        returnButton.addActionListener(this);
        returnButton.setText("Return to Checkout");
        returnButton.setFocusable(false);
        returnButton.setBorder(BorderFactory.createEtchedBorder());

        deleteButton = new JButton();
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        deleteButton.setBackground(new Color(255, 228, 225));
        deleteButton.setForeground(new Color(139, 0, 0));
        deleteButton.setBounds(100, 0, 100, 100);
        deleteButton.addActionListener(this);
        deleteButton.setText("Remove Item");
        deleteButton.setFocusable(false);
        deleteButton.setBorder(BorderFactory.createEtchedBorder());

        upButton = new JButton();
        upButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        upButton.setBackground(SystemColor.menu);
        upButton.setForeground(Color.BLACK);
        upButton.setBounds(0, 0, 100, 100);
        upButton.addActionListener(this);
        upButton.setText("UP");
        upButton.setFocusable(false);
        upButton.setBorder(BorderFactory.createEtchedBorder());

        downButton = new JButton();
        downButton.setFont(new Font("Tahoma", Font.BOLD, 16));
        downButton.setBackground(SystemColor.menu);
        downButton.setForeground(Color.BLACK);
        downButton.setBounds(100, 0, 100, 100);
        downButton.addActionListener(this);
        downButton.setText("DOWN");
        downButton.setFocusable(false);
        downButton.setBorder(BorderFactory.createEtchedBorder());
        //----Setting up Buttons----//

        //----Setting up Panels----//
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel2.setBorder(new EmptyBorder(15, 5, 15, 5));
        JPanel panel3 = new JPanel();
        panel3.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel panel4 = new JPanel();
        panel4.setBorder(new EmptyBorder(15, 15, 15, 15));

        panel1.setBackground(Color.white);
        panel2.setBackground(Color.white);
        panel3.setBackground(Color.white);
        panel4.setBackground(Color.white);

        panel1.setPreferredSize(new Dimension(100,75));
        panel2.setPreferredSize(new Dimension(75,100));
        panel3.setPreferredSize(new Dimension(100,75));
        panel4.setPreferredSize(new Dimension(100,100));
        setLayout(new BorderLayout(0, 0));
        
        
        // -- Content Pane (JPanel)
        //     -- content
        
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.EAST);
        add(panel3, BorderLayout.SOUTH);
        add(panel4, BorderLayout.CENTER);
        
        panel1.setLayout(new GridLayout(0, 1, 0, 0));
        //----Setting up Panels----//

        panel1.add(windowText);
        panel4.setLayout(new GridLayout(3, 1, 0, 15));
        panel4.add(highText);
        panel4.add(middleText);
        panel4.add(lowText);

        displayOfItems(cars);
        panel3.setLayout(new GridLayout(1, 2, 5, 0));

        panel3.add(returnButton);
        panel3.add(deleteButton);
        panel2.setLayout(new GridLayout(0, 1, 0, 5));

        panel2.add(upButton);
        panel2.add(downButton);
    }
    
    public int getCurrentSelectedIndex()
    {
    	return n;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==returnButton){
            System.out.println("Return Button clicked");
            displayOfItems(cars);
        }else if(e.getSource()==deleteButton){
            System.out.println("Delete Button clicked");
            displayOfItems(cars);
        }else if(e.getSource()==upButton){
        	if(n > 0) n -= 1;
            System.out.println("Up Button clicked " + n);
            displayOfItems(cars);
        }else if(e.getSource()==downButton){
        	if(n < arraySize -1) n += 1;
            System.out.println("Down Button clicked " + n);
            displayOfItems(cars);
        }

    }
    
    /**
	 * Launch the application. TO BE USED FOR TESTING ONLY!
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArrayList<String> test = new ArrayList<>();
					test.add("hi");
					test.add("bye");
					test.add("nope");
					test.add("goat");
					JFrame frame = new JFrame();
					frame.getContentPane().add(new BaggingAreaPanel(test));
					frame.pack();
					frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
