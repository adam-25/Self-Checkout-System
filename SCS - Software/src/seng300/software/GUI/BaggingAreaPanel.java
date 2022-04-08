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
            middleText.setText("Bagging Area is empty");
            lowText.setText("");
        }else{
            if(n == 0){
                highText.setText("");
            }else{
                highText.setText(stuff.get(n-1));
            }
            middleText.setText(stuff.get(n));
            if(n == (arraySize-1)){
                lowText.setText("");
            }else{
                lowText.setText(stuff.get(n+1));
            }
        }
    }

    public BaggingAreaPanel(ArrayList<String> carsInput)
    {
    	setBorder(new EmptyBorder(15, 15, 15, 15));
        cars = carsInput;
        arraySize = cars.size();

        //----Setting up Text----//
        windowText = new JLabel();
        windowText.setHorizontalAlignment(SwingConstants.CENTER);
        windowText.setFont(new Font("Tahoma", Font.BOLD, 32));
        windowText.setText("Bagging Area");
        windowText.setBounds(0, 0, 100, 100);

        highText = new JLabel();
        highText.setOpaque(true);
        highText.setHorizontalAlignment(SwingConstants.CENTER);
        highText.setFont(new Font("Tahoma", Font.BOLD, 28));
        highText.setForeground(new Color(230, 230, 250));
        highText.setBackground(new Color(0, 0, 128));
        highText.setBounds(0, 100, 100, 100);

        middleText = new JLabel();
        middleText.setOpaque(true);
        middleText.setHorizontalAlignment(SwingConstants.CENTER);
        middleText.setFont(new Font("Tahoma", Font.BOLD, 42));
        middleText.setForeground(new Color(230, 230, 250));
        middleText.setBackground(new Color(0, 0, 128));
        middleText.setText("");
        middleText.setBounds(0, 50, 100, 100);

        lowText = new JLabel();
        lowText.setOpaque(true);
        lowText.setHorizontalAlignment(SwingConstants.CENTER);
        lowText.setFont(new Font("Tahoma", Font.BOLD, 28));
        lowText.setForeground(new Color(230, 230, 250));
        lowText.setBackground(new Color(0, 0, 128));
        lowText.setText("");
        lowText.setBounds(0, 0, 100, 100);
        //----Setting up Text----//

        //----Setting up Buttons----//
        returnButton = new JButton();
        returnButton.setBackground(new Color(240, 248, 255));
        returnButton.setMargin(new Insets(2, 14, 2, 0));
        returnButton.setBounds(0, 0, 100, 100);
        returnButton.addActionListener(this);
        returnButton.setText("RETURN");
        returnButton.setFocusable(false);
        returnButton.setBorder(BorderFactory.createEtchedBorder());

        deleteButton = new JButton();
        deleteButton.setBackground(new Color(255, 228, 225));
        deleteButton.setBounds(100, 0, 100, 100);
        deleteButton.addActionListener(this);
        deleteButton.setText("DELETE");
        deleteButton.setFocusable(false);
        deleteButton.setBorder(BorderFactory.createEtchedBorder());

        upButton = new JButton();
        upButton.setBackground(new Color(240, 248, 255));
        upButton.setBounds(0, 0, 100, 100);
        upButton.addActionListener(this);
        upButton.setText("UP");
        upButton.setFocusable(false);
        upButton.setBorder(BorderFactory.createEtchedBorder());

        downButton = new JButton();
        downButton.setBackground(new Color(240, 248, 255));
        downButton.setBounds(100, 0, 100, 100);
        downButton.addActionListener(this);
        downButton.setText("DOWN");
        downButton.setFocusable(false);
        downButton.setBorder(BorderFactory.createEtchedBorder());
        //----Setting up Buttons----//

        //----Setting up Frame----//
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        getContentPane().setLayout(null);
//        this.setSize(952, 707);
//        getContentPane().setLayout(new BorderLayout(10,10));
//        this.setVisible(true);
        //----Setting up Frame----//

        //----Setting up Panels----//
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        panel2.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel panel3 = new JPanel();
        panel3.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel panel4 = new JPanel();
        panel4.setBorder(new EmptyBorder(5, 50, 5, 50));

        panel1.setBackground(Color.red);
        panel2.setBackground(Color.green);
        panel3.setBackground(Color.yellow);
        panel4.setBackground(Color.blue);

        panel1.setPreferredSize(new Dimension(100,75));
        panel2.setPreferredSize(new Dimension(75,100));
        panel3.setPreferredSize(new Dimension(100,75));
        panel4.setPreferredSize(new Dimension(100,100));
        setLayout(new BorderLayout(0, 0));
        
//        getContentPane().add(panel1,BorderLayout.NORTH);
//        getContentPane().add(panel2,BorderLayout.EAST);
//        getContentPane().add(panel3,BorderLayout.SOUTH);
//        getContentPane().add(panel4,BorderLayout.CENTER);
        
        // -- Content Pane (JPanel)
        //     -- content
        
        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.EAST);
        add(panel3, BorderLayout.SOUTH);
        add(panel4, BorderLayout.CENTER);
        
        panel1.setLayout(new GridLayout(0, 1, 0, 0));
        //----Setting up Panels----//

        panel1.add(windowText);
        panel4.setLayout(new GridLayout(3, 1, 0, 10));
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