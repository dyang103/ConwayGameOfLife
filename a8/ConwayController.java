package a8;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class ConwayController {

	private ConwayModel model;
	private ConwayView view;
	
	public ConwayController(ConwayModel model, ConwayView view) {
		this.model = model;
		this.view = view;
		
	}
		
}