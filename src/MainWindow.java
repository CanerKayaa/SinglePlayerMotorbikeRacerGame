import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu gameMenu,userMenu,quitMenu;
	JMenuItem gameMenu_Item1, gameMenu_Item2, gameMenu_Item3, gameMenu_Item4, userMenu_Item1, userMenu_Item2, userMenu_Item3;
	JMenuItem menuItem;
	JLabel label;
	
	private final String scoreFilePath = "D:\\Eclipse-workspaces\\workspaces\\TermProject\\bin\\HighScore.txt";
	private final String userFilePath = "D:\\Eclipse-workspaces\\workspaces\\TermProject\\bin\\Users.txt";

	
	private Game game = null;
	private RegisterWindow registerWindow;
	private ReadAndWrite scoreFileReaderAndWriter;
	
	public MainWindow() {
		super("CSE212 - Hang on");
		label = new JLabel(new ImageIcon(getClass().getResource("MainWindowbg.gif")));
		
		scoreFileReaderAndWriter = new ReadAndWrite(scoreFilePath);
		
		
		add(label);

		registerWindow = new RegisterWindow(userFilePath);
		
		ActionListener actionListener = new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				String command = actionEvent.getActionCommand();
				 if(command.contentEquals("Register")) {
					 	registerWindow.createScreen(command);
			        }
				 else if(command.contentEquals("Login")) {
					 	registerWindow.createScreen(command);
			        }
				 
				 else if(command.contentEquals("Start")) {
					 if(registerWindow.isRegistered() == true ) {
					 		
					 		label.setVisible(false);
					 		game = new Game(MainWindow.this, registerWindow.getUsername(), scoreFilePath);
					 }
					 else {
						 String[] message = {"You must be login to play game!!"};
						 JOptionPane.showMessageDialog(new JFrame(), message, "Error",JOptionPane.ERROR_MESSAGE);
					 }
					 	
				 } else if(command.contentEquals("Restart") && game != null) {
					 
					 game.restart();
				 }  else if(command.contentEquals("High Score")) {
					 String highScores = scoreFileReaderAndWriter.getHighScores();
					 JOptionPane.showMessageDialog(new JFrame(), highScores, "TOP 3",JOptionPane.INFORMATION_MESSAGE);
					 
				 }
				 else if(command.contentEquals("Quit")) {
					 System.exit(0);
				 }
				
				
			}
			
		};
		
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the first menu.
		gameMenu = new JMenu("Game");
		userMenu = new JMenu("User");
		quitMenu = new JMenu("Quit");
		

		//gameMenu items
		gameMenu_Item1 = new JMenuItem("Start");
		gameMenu_Item1.addActionListener(actionListener);
		
		gameMenu_Item2 = new JMenuItem("Pause");
		gameMenu_Item2.addActionListener(actionListener);
		gameMenu_Item3 = new JMenuItem("Restart");
		gameMenu_Item3.addActionListener(actionListener);

		gameMenu_Item4 = new JMenuItem("Quit");
		gameMenu_Item4.addActionListener(actionListener);
		
		gameMenu.add(gameMenu_Item1);
		gameMenu.add(gameMenu_Item2);
		gameMenu.add(gameMenu_Item3);
		
		quitMenu.add(gameMenu_Item4);

	
		userMenu_Item1 = new JMenuItem("Register");
		userMenu_Item1.addActionListener(actionListener);
		//userMenu_Item1.setActionCommand("");
		
		userMenu_Item2 = new JMenuItem("Login");
		userMenu_Item2.addActionListener(actionListener);
		userMenu_Item3 = new JMenuItem("High Score");
		userMenu_Item3.addActionListener(actionListener);

		
		
		userMenu.add(userMenu_Item1);
		userMenu.add(userMenu_Item2);
		userMenu.add(userMenu_Item3);
		
		menuBar.add(gameMenu);
		menuBar.add(userMenu);
		menuBar.add(quitMenu);
		

		//frame show
		
		setJMenuBar(menuBar);
		setBounds(400,100,500, 400);
		setVisible(true);
	}

}
