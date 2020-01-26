package com.Plugdisk1.tests;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.Plugdisk1.tests.BaseTestLogic.TestReportLisenter;
import com.Plugdisk1.tests.TestWorking;

public class MainViewer extends JFrame{
	private final int WindowSizeHeight = 900;
	private final int WindowSizeWidth = 600;

	JLabel tTitleConf, tTitleReport;
	JTextArea tPathConf, tPathReport;
	JTextArea tTestReport;
	
	JPanel panelTop, panelBody;
	JPanel panelConf, panelReport;
	
	JButton btConfPath, btReportPath;
	JButton btStartTest;
	
	BorderLayout layout, layoutTop;
	BorderLayout layoutConf, layoutReport;
	
	JFileChooser fcConf, fcReport;
	FileFilter filter;
	
	File fConf, fReport;
	
	TestWorking working;
	StringBuilder strBld;
	
	
	
	public MainViewer() {
		// TODO Auto-generated constructor stub
		this.setTitle("PlugDISK 2nd part test");
		
		tTitleConf = new JLabel("Config file : ");
		tTitleReport = new JLabel("Report file : ");
		
		tPathConf = new JTextArea("Select test config file");
		tPathReport = new JTextArea("Select test report file");
		tPathConf.setEditable(false);
		tPathReport.setEditable(false);
		
		btConfPath = new JButton("Browse");
		btReportPath = new JButton("Browse");
		
		layout = new BorderLayout(10, 10);
		setLayout(layout);
		
		panelTop = new JPanel();
		panelBody = new JPanel();
		panelConf = new JPanel();
		panelReport = new JPanel();
		
		layoutConf = new BorderLayout();
		layoutReport = new BorderLayout();
		panelConf.setLayout(layoutConf);
		panelReport.setLayout(layoutReport);
		
		
		panelConf.add(tTitleConf, BorderLayout.WEST);
		panelConf.add(tPathConf, BorderLayout.CENTER);
		panelConf.add(btConfPath, BorderLayout.EAST);
		
		panelReport.add(tTitleReport, BorderLayout.WEST);
		panelReport.add(tPathReport, BorderLayout.CENTER);
		panelReport.add(btReportPath, BorderLayout.EAST);
		
		layoutTop = new BorderLayout();
		layoutTop.setVgap(5);
		
		panelTop.setLayout(layoutTop);
		panelTop.add(panelConf, BorderLayout.NORTH);
		panelTop.add(panelReport, BorderLayout.SOUTH);
		
		add(panelTop, BorderLayout.NORTH);
		
		setSize(new Dimension(WindowSizeWidth, WindowSizeHeight));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		filter = new FileNameExtensionFilter("TEXT", "txt", "TXT");
		fcReport = new JFileChooser();
		fcReport.addChoosableFileFilter(filter);
		fcConf = new JFileChooser();
		fcConf.addChoosableFileFilter(filter);
		
		btStartTest = new JButton("Start Test");
		btStartTest.setEnabled(false);
		
		
		tTestReport = new JTextArea("Log Messages....");
		tTestReport.setEditable(false);
		JScrollPane scroll = new JScrollPane (tTestReport, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//add(tTestReport, BorderLayout.CENTER);
		
		add(scroll, BorderLayout.CENTER);
		add(btStartTest, BorderLayout.SOUTH);
		
		btConfPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fcConf.showOpenDialog(null);
				fConf = fcConf.getSelectedFile();
				
				if(fConf != null){
					tPathConf.setText(fConf.getAbsolutePath());
				}
				
				if(isTestStartAvail(fConf, fReport)){
					btStartTest.setEnabled(true);
				}
				else{
					btStartTest.setEnabled(false);
				}
			}
		});
		
		btReportPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fcReport.showSaveDialog(null);
				fReport = fcReport.getSelectedFile();
				
				if(fReport != null){
					tPathReport.setText(fReport.getAbsolutePath());
				}
				
				if(isTestStartAvail(fConf, fReport)){
					btStartTest.setEnabled(true);
				}
				else{
					btStartTest.setEnabled(false);
				}
			}
		});
		
		btStartTest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				working = new TestWorking(fConf, fReport);
				strBld = new StringBuilder();
				working.setLisenter(new TestReportLisenter() {
					
					@Override
					public void onReportLisenter(String report) {
						// TODO Auto-generated method stub
						strBld.append(report);
						tTestReport.setText(strBld.toString());
					}
				});
				
				try {
					working.startAutoTest();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					strBld.append("\n\n======================\n\n").append(e.toString());
					tTestReport.setText(e.toString());
					
					
					JOptionPane.showMessageDialog(null, e.toString());
				}
			}
		});
	}
	
	boolean isFileSelected(File file1, File file2){
		if(file1 == null || file2 == null){
			return false;
		}
		else {
			return true;
		}
	}
	
	boolean isSameFile(File file1, File file2){
		if(file1 != null || file2 != null){
			if(file1.getAbsolutePath().equals(file2.getAbsolutePath())){
				return true;
			}
		}
		return false;
	}
	
	boolean isTestStartAvail(File file1, File file2){
		if(isFileSelected(file1, file2)){
			if(!isSameFile(file1, file2)){
				return true;
			}
			else {
				JOptionPane.showMessageDialog(this, "File select error");
			}
		}
		return false;
	}
}
