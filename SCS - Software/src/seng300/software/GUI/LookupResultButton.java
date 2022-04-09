package seng300.software.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

import org.lsmr.selfcheckout.products.PLUCodedProduct;

public class LookupResultButton extends JButton
{
	private final PLUCodedProduct product;
	
	public LookupResultButton(PLUCodedProduct product)
	{
		super(product.getDescription());
		this.product = product;
		setPreferredSize(new Dimension(75, 75));
		setMinimumSize(new Dimension(75, 75));
		setMaximumSize(new Dimension(75, 75));
		setBackground(new Color(25, 25, 112));
		setFont(new Font("Tahoma", Font.BOLD, 16));
		setForeground(new Color(255, 255, 255));
	}
	
	public PLUCodedProduct getProduct()
	{
		return product;
	}
}