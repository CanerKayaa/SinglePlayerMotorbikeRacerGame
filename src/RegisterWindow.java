import javax.swing.*;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class RegisterWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JLabel label1,label2;
	private JButton button1;
	private JButton button2;
	private JLabel temp;
	private  JTextField jTextField1,jTextField2;
	private  String str;
	private boolean registerControl = false;
	String userName;
	String password;
	
	ActionListener actionListener;
	RegisterWindow(final String userFilePath){

		 this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String command = actionEvent.getActionCommand();
				 if(command.contentEquals("Register")) {
					 	
					 	userName = jTextField2.getText();
					 	password = jTextField1.getText();
					 	str =  userName + " " + password;
					 	
					    try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFilePath,true))){
							writer.write(str);
							writer.newLine();
							writer.close();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
					    
					 	frame.dispose();
			        }
				 else if(command.contentEquals("Login")) {
					
					 
				        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
				        	
						 	userName = jTextField2.getText();
						 	password = jTextField1.getText();
						 	str =  userName + " " + password;
						 	String line;
						 	boolean found = false;
						 	
					         while ((line = br.readLine()) != null) {
					             System.out.println(line);
					             if (line.equals(str)) {
					            	 found = true;
					             }
					          }    
						 	
				            if(found == false) {
				            	String[] message = {"No Such Register!!"};
			        			JOptionPane.showMessageDialog(new JFrame(), message, "Error",JOptionPane.ERROR_MESSAGE);
				            }
				            else {
				            	registerControl = true;
				            }
				           
				        } catch (IOException e) {
				            e.printStackTrace();
				        }	        
				        frame.dispose();
			        }
				 
				 else if(command.contentEquals("Cancel")) {
					 frame.dispose();
				 }

			}
		};
		
	}
	
	
	public void createScreen(String sItem) {
	
		frame = new JFrame(sItem);
		frame.setBounds(700, 300, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		label1 = new JLabel("Name :");
		label1.setBounds(5,85,350,50);
		jTextField2 = new JTextField();
		jTextField2.setBounds(105,100,200,25);
		
		
		label2 = new JLabel("Password :");
		label2.setBounds(5,120,350,50);
	    jTextField1 = new JTextField();
		jTextField1.setBounds(105,140,200,25);
		
		frame.add(label1);
		frame.add(label2);
		
		frame.add(jTextField1);
		frame.add(jTextField2);
		
		button1 = new JButton("Cancel");
		button1.setBounds(400,250,75,15);
		button1.addActionListener(actionListener);
		button1.setActionCommand("Cancel");
		frame.add(button1);
		
		button2 = new JButton(sItem);
		button2.setBounds(250,250,100,15);
		button2.addActionListener(actionListener);
		button2.setActionCommand(sItem);
		frame.add(button2);
		
		temp = new JLabel();
		frame.add(temp);
		frame.setVisible(true);
	}
	
	
	
	public String getUsername() {
		return userName;
	}
	
	public boolean isRegistered() {
		return registerControl;
	}
}
