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

import java.util.ArrayList;

public class BaggingAreaPanel extends JFrame implements ActionListener {
    JButton returnButton;
    JButton deleteButton;
    JButton upButton;
    JButton downButton;

    JLabel windowText;
    JLabel highText;
    JLabel middleText;
    JLabel lowText;

    int n = 0;
    int arraySize;

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
                highText.setText(stuff.get(n+1));
            }
        }
    }

    BaggingAreaPanel(ArrayList<String> carsInput) {
        cars = carsInput;

        arraySize = cars.size();

        //----Setting up Text----//
        windowText = new JLabel();
        windowText.setText("Bagging Area");
        windowText.setBounds(0, 0, 100, 100);

        highText = new JLabel();
        highText.setText("");
        highText.setBounds(0, 100, 100, 100);

        middleText = new JLabel();
        middleText.setText("");
        middleText.setBounds(0, 50, 100, 100);

        lowText = new JLabel();
        lowText.setText("");
        lowText.setBounds(0, 0, 100, 100);
        //----Setting up Text----//

        //----Setting up Buttons----//
        returnButton = new JButton();
        returnButton.setBounds(0, 0, 100, 100);
        returnButton.addActionListener(this);
        returnButton.setText("RETURN");
        returnButton.setFocusable(false);
        returnButton.setBorder(BorderFactory.createEtchedBorder());

        deleteButton = new JButton();
        deleteButton.setBounds(100, 0, 100, 100);
        deleteButton.addActionListener(this);
        deleteButton.setText("DELETE");
        deleteButton.setFocusable(false);
        deleteButton.setBorder(BorderFactory.createEtchedBorder());

        upButton = new JButton();
        upButton.setBounds(0, 0, 100, 100);
        upButton.addActionListener(this);
        upButton.setText("UP");
        upButton.setFocusable(false);
        upButton.setBorder(BorderFactory.createEtchedBorder());

        downButton = new JButton();
        downButton.setBounds(100, 0, 100, 100);
        downButton.addActionListener(this);
        downButton.setText("DOWN");
        downButton.setFocusable(false);
        downButton.setBorder(BorderFactory.createEtchedBorder());
        //----Setting up Buttons----//

        //----Setting up Frame----//
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout(10,10));
        this.setVisible(true);
        //----Setting up Frame----//

        //----Setting up Panels----//
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        panel1.setBackground(Color.red);
        panel2.setBackground(Color.green);
        panel3.setBackground(Color.yellow);
        panel4.setBackground(Color.blue);

        panel1.setPreferredSize(new Dimension(100,75));
        panel2.setPreferredSize(new Dimension(75,100));
        panel3.setPreferredSize(new Dimension(100,75));
        panel4.setPreferredSize(new Dimension(100,100));
        
        this.add(panel1,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.EAST);
        this.add(panel3,BorderLayout.SOUTH);
        this.add(panel4,BorderLayout.CENTER);
        //----Setting up Panels----//

        panel1.add(windowText);
        panel4.add(highText);
        panel4.add(middleText);
        panel4.add(lowText);

        displayOfItems(cars);

        panel3.add(returnButton);
        panel3.add(deleteButton);

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
            if(n < arraySize -1) n += 1;
            System.out.println("Up Button clicked " + n);
            displayOfItems(cars);
        }else if(e.getSource()==downButton){
            if(n > 0) n -= 1;
            System.out.println("Down Button clicked " + n);
            displayOfItems(cars);
        }

    }
}