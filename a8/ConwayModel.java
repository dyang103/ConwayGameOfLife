package a8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import javax.swing.JPanel;

public class ConwayModel {
    private static final int BLOCK_SIZE = 10;

	
    private int low_birth = 2;
    private int high_birth = 3;
    private int low_survive = 3;
    private int high_survive = 3;
    
    public int getLowBirth() {
    	return low_birth;
    }
    public int getHighBirth() {
    	return high_birth;
    }
    public int getLowSurvive() {
    	return low_survive;
    }
    public int getHighSurvive() {
    	return high_survive;
    }
    
    public void setLowBirth(int lowBirth) {
    	low_birth = lowBirth;
    }
    public void setHighBirth(int highBirth) {
    	
    }
	
}
