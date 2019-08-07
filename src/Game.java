import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Game  implements KeyListener{
	
	private final int OTHER_PLAYER_SIZE = 6;
	
	private class OtherPlayer {

		public int yCounter;
		public int xCounter;
		
		public int order;
		public final int baseX = 350;
		public final int baseY = 600;
		
		public JLabel imageLabel;
		
		public OtherPlayer(int x, int y, int order) {
			this.xCounter = x;
			this.yCounter = y;
			
			this.order = order;
		}
	} 
	
	private String  userName;
	private final MainWindow mainWindow;
	private static final long serialVersionUID = 1L;
	final JLabel imagelabel1,imagelabel2,imagelabel3, labelspeed1, labelspeed2, labelTime, labelGameover, labelFinish;
	JLabel labelStep;
	int i = 7;
	int j = 0;
	int flag=0;
	int speed = 0;
	boolean release = true;
	boolean keyPressed = false, turnLeft = false, turnRight = false, gameOver = false;
	int uprelease =0;
	int actorX = -700;
	int	actorY = -200;
	final int actorBgY = -290;
	int yCounter = 0;
	int roadType = 0;
	private  String[] motorImages = {};
	private String[] backgroundImages = {};
	private String[] backgroundImagesCurve1 = {"curve1.png","curve2.png","curve3.png","curve4.png","curve5.png","curve6.png","curve7.png","curve8.png","curve9.png","curve10.png","curve11.png","curve12.png","curve13.png","curve14.png"
			,"curve15.png","curve16.png","curve17.png","curve18.png","curve19.png","curve20.png","curve21.png","curve22.png","curve23.png","curve24.png","curve25.png","curve26.png","curve27.png","curve28.png","curve29.png"
			,"curve30.png","curve31.png","curve32.png","curve33.png","curve34.png","curve35.png","curve36.png","curve37.png","curve38.png","curve39.png","curve40.png","curve41.png","curve42.png"};
	private String[] backgroundImagesCurve2 = {"curve-1.png","curve-2.png","curve-3.png","curve-4.png","curve-5.png","curve-6.png","curve-7.png","curve-8.png","curve-9.png","curve-10.png","curve-11.png","curve-12.png","curve-13.png","curve-14.png"
			,"curve-15.png","curve-16.png","curve-17.png","curve-18.png","curve-19.png","curve-20.png","curve-21.png","curve-22.png","curve-23.png","curve-24.png","curve-25.png","curve-26.png","curve-27.png","curve-28.png","curve-29.png"
			,"curve-30.png","curve-31.png","curve-32.png","curve-33.png","curve-34.png","curve-35.png","curve-36.png","curve-37.png","curve-38.png","curve-39.png","curve-40.png","curve-41.png","curve-42.png"};
	private String[] straightRoad = {"Bg1.png","Bg2.png","Bg3.png","Bg4.png"};
	private String[] start = {"start1.png","start2.png","start3.png"};
	private String[] sky = {"sky.png"};
	private String[] player = {"Left7.png","Left6.png","Left5.png","Left4.png","Left3.png","Left2.png","Left1.png","Motor1.png","Right1.png","Right2.png","Right3.png","Right4.png","Right5.png","Right6.png","Right7.png"};
	private String[] fall = {"fall1.png","fall2.png","fall3.png","fall4.png","fall5.png","fall6.png","fall7.png","fall8.png","fall9.png","fall10.png"};
	private String[] leftfall = {"leftfall1.png","leftfall2.png","leftfall3.png","leftfall4.png","leftfall5.png","leftfall6.png","leftfall7.png","leftfall8.png","leftfall9.png","leftfall10.png"};
	private String[] otherPlayerNormal = {"Other1.png","Other2.png","Other3.png","Other4.png","Other5.png","Other6.png","Other7.png"};
	private String[] otherPlayerLeft = {"OtherLeft1.png","OtherLeft2.png","OtherLeft3.png","OtherLeft4.png","OtherLeft5.png"};
	private String[] otherPlayerRight = {"OtherRight1.png","OtherRight2.png","OtherRight3.png","OtherRight4.png","OtherRight5.png"};
	private String[] otherPlayer = {};
	private Thread thread1;
	private Thread thread2;
	private Thread thread3;
	
	private java.util.Timer timer;
	private boolean isStarted = false;
	private int timeSecond = 60;
	private int step = 0;
	ArrayList<Integer> stepScores = new ArrayList<>();
	
	private ReadAndWrite fileReaderAndWriter;
	
	//Sound
	private AudioInputStream audioInputStream;
	private Clip clip;
	
	//Other Player
	ArrayList<OtherPlayer> otherPlayers;
	ArrayList<Integer> orderList = new ArrayList<Integer>() {{
	    add(6);
	    add(4);
	    add(2);
	    add(5);
	    add(1); 
	    add(3);
	}};
	private void resetValues() {
		this.i = 7;
		this.j = 0;
		this. flag=0;
		this.speed = 0;
		this.release = true;
		this.keyPressed = false;
		this.turnLeft = false;
		this.turnRight = false;
		this.gameOver = false;
		this.uprelease =0;
		this.actorX = -700;
		this.actorY = -200;
		this.yCounter = 0;
		this.roadType = 0;
		this.step = 0;
	}
	
	
	public Game(final MainWindow mainWindow, final String userName, final String scorefilePath)  {
		
		try {
			this.audioInputStream = AudioSystem.getAudioInputStream(new File("D:\\Eclipse-workspaces\\workspaces\\TermProject\\bin\\motorsound.wav").getAbsoluteFile());
			this.clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		this.fileReaderAndWriter = new ReadAndWrite(scorefilePath);
		this.mainWindow = mainWindow;
		
		otherPlayers = new ArrayList<>();
		for (int k = 0; k < OTHER_PLAYER_SIZE; ++k) {
			
			int a  = new Random().nextInt(orderList.size());
			final OtherPlayer o = new OtherPlayer(-180 + (70 * k),  20, orderList.remove(a));
			
			o.imageLabel = new JLabel(new ImageIcon(getClass().getResource(otherPlayerNormal[0])));
			o.imageLabel.setLocation(o.baseX + o.xCounter, o.baseY -  o.yCounter);
			o.imageLabel.setSize(300,200);
			
			otherPlayers.add(o);
			
			this.mainWindow.add(o.imageLabel);
		
		}
			
		this.userName = userName;
		motorImages = player;
		
		labelspeed1 = new JLabel("Speed: ");
		labelspeed1.setBounds(100,75,400,300);
		backgroundImages = start;
		imagelabel1 = new JLabel(new ImageIcon(getClass().getResource(motorImages[7])));
		imagelabel1.setLocation(350,600);
		imagelabel1.setSize(300,200);
		this.mainWindow.add(imagelabel1);
		
		imagelabel2 = new JLabel(new ImageIcon(getClass().getResource(backgroundImages[2])));
		imagelabel2.setBounds(actorX,-actorY,2400,970);
		
		imagelabel3 = new JLabel(new ImageIcon(getClass().getResource(sky[0])));
		imagelabel3.setBounds(-650,actorBgY,2400,1500);
		 
		
		backgroundImages = straightRoad;
		
		createChangeImageRightLeftThread();
		createSpeedThread();
		createChangeBackGroundImageThread();
		this.thread1.start();
		this.thread2.start();
		this.thread3.start();
		
		this.mainWindow.add(labelspeed1);

		labelspeed2 = new JLabel(speed + "km/h");
		labelspeed2.setBounds(150,75,400,300);
		
		
		labelTime = new JLabel(timeSecond + " sec");
		labelTime.setBounds(500,75,400,300);
		
		labelGameover =  new JLabel("GAME OVER!");
		labelGameover.setFont(new Font(null, Font.BOLD, 50));
		labelGameover.setBounds(300,185,3000,500);
		labelGameover.setVisible(false);

		labelFinish =  new JLabel("FINISH!");
		labelFinish.setFont(new Font(null, Font.BOLD, 50));
		labelFinish.setBounds(400,185,3000,500);
		labelFinish.setVisible(false);
		
		labelStep = new JLabel("1. Stage");
		labelStep.setBounds(500,100,400,300);
	
		this.mainWindow.add(labelFinish);
		this.mainWindow.add(labelStep);
		this.mainWindow.add(labelGameover);
		this.mainWindow.add(labelTime);
		this.mainWindow.add(labelspeed2);
		this.mainWindow.add(imagelabel2);
		this.mainWindow.add(imagelabel3);
		this.mainWindow.setLayout(null);
		this.mainWindow.setSize(1000, 850);
		this.mainWindow.addKeyListener(this);
		this.mainWindow.setVisible(true);
	}
	
	
	
	public void restart() {
		resetValues();
		
		for (int k = 0; k < OTHER_PLAYER_SIZE; ++k) {
			
			OtherPlayer o = otherPlayers.get(k);
						
			o.xCounter = -180 + (70 * k);
			o.yCounter = 20;
			o.imageLabel.setIcon(new ImageIcon(getClass().getResource(otherPlayerNormal[0])));
			o.imageLabel.setLocation(o.baseX + o.xCounter, o.baseY -  o.yCounter);
			o.imageLabel.setSize(300,200);
			
			
		}
		
		
		motorImages = player;
		backgroundImages = start;
		imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[7])));
		imagelabel1.setLocation(350,600);
		imagelabel1.setSize(300,200);
			
		imagelabel2.setIcon(new ImageIcon(getClass().getResource(backgroundImages[2])));
		imagelabel2.setBounds(actorX,-actorY,2400,970);
		
		imagelabel3.setIcon(new ImageIcon(getClass().getResource(sky[0])));
		imagelabel3.setBounds(-650,actorBgY,2400,1500);

		backgroundImages = straightRoad;
		isStarted = false;
		timeSecond = 60;
		labelTime.setText(timeSecond + " sec");
		
		if (timer != null)
			timer.cancel();
		
		gameOver = false;
		labelGameover.setVisible(false);
		labelFinish.setVisible(false);
		step = 0;
		
		stepScores.clear();
	}
	
	public void keyPressed(KeyEvent event) {
		String whichKey = KeyEvent.getKeyText(event.getKeyCode());

		if  (isStarted == false) {
			isStarted = true;
			timer = new java.util.Timer();
			
			timer.scheduleAtFixedRate(new TimerTask() {
				  @Override
				  public void run() {
					  --timeSecond;
					  
					  labelTime.setText(timeSecond + " sec");
				  }
				}, 1000, 1000);
		}
		if(whichKey.compareTo("Left") == 0) {
			flag = 1;
			release = true;
			turnLeft = true;
			
		}
		else if(whichKey.compareTo("Right") == 0) {
			flag = 2;
			release = true;
			turnRight = true;
		}
		else if(whichKey.compareTo("Up") == 0 && !keyPressed) {
			uprelease = 1;
			keyPressed = true;
			
		}
		else if(whichKey.compareTo("Down") == 0 && !keyPressed) {
			uprelease = -1;
			keyPressed = true;
		}

	}
	
	public void createChangeBackGroundImageThread() {
		this.thread3 = new Thread() {
			public void run() {
					while(j >= 0) {
						if(turnLeft && release && speed > 0) {
							
							imagelabel2.setLocation(actorX+=30,-actorY);
							imagelabel3.setLocation(actorX+=30,actorBgY);
							turnLeft = false;
							
						}
						if(turnRight && release && speed > 0) {
							
							imagelabel2.setLocation(actorX-=30,-actorY);
							imagelabel3.setLocation(actorX-=30,actorBgY);
							turnRight = false;
							
						}
						if(speed != 0) {
							clip.start();
							clip.loop(1);
							
							imagelabel2.setIcon(new ImageIcon(getClass().getResource(backgroundImages[j])));
							labelspeed2.setText(speed + "km/h");
							j++;
							
							//0: first straight road, 1:first curve road, 2: second straight road, 3: second curve road, 4: last straight road
							if(roadType == 1 ) {
								if(j > 10) {
										if(turnLeft == false) { 
											imagelabel2.setLocation(actorX-=30,-actorY);
										}
										else {
											imagelabel2.setLocation(actorX+=30,-actorY);
										}
								}
							}
							else if(roadType == 3) {
								if(j > 10) {
									if(turnRight == false) {
										imagelabel2.setLocation(actorX+=30,-actorY);
									}
									else {
										imagelabel2.setLocation(actorX-=30,-actorY);
									}
								}
							}
							
						}
					
						if((roadType == 0 || roadType == 2 || roadType ==4) && j > 3) {
							j = 0;
						}
						if(j > 41) {
							j = 41;
						}
						
						try {
							yCounter++;
							Thread.sleep(220 - speed);
							
						}
						catch(InterruptedException e) {
							e.printStackTrace();
						}
					
					}
			}
		};
	}
	public void createSpeedThread() {
		 this.thread2 = new Thread() {
			public void run() {
				while(true) {
					Random rand = new Random();

					// Obtain a number between [0 - 5].
					int random = rand.nextInt(5);
					if(uprelease == 1 && speed < 150) {
							speed+=random;
						
					}
					else if(uprelease == -1 && speed > 0) {
						speed -= random;
						if(speed < 0) {
							speed = 0;
						}
					}
				
					try {
						
						Thread.sleep(200);
					
						//0: first straight road, 1:first curve road, 2: second straight road, 3: second curve road, 4: last straight road
						if(roadType == 0 && yCounter > 350) { 
							backgroundImages = backgroundImagesCurve1;
							j=0;
							roadType = 1;
				
						} else if (roadType == 1 && j == 41) {
							backgroundImages = straightRoad;
							j=0;
							roadType =2;
						} else if (roadType == 2 && yCounter >= 750) {
							j=0;
							backgroundImages = backgroundImagesCurve2;
							roadType = 3;
						} else if(roadType == 3 && j == 41) {
							backgroundImages = straightRoad;
							j=0;
							roadType = 4;
						}
						
						
						if (step == 0 && yCounter >= 250) {// Between CheckPoint1 - CheckPoint2
							stepScores.add(60 - timeSecond);
							timeSecond = 60;
							step = 1;
							labelStep.setText("2. Stage");
						} else if (step == 1 && yCounter >= 500) { // Between CheckPoint2 - CheckPoint3
							stepScores.add(60 - timeSecond);
							timeSecond = 60;
							step = 2;
							labelStep.setText("3. Stage");

						} else if (step == 2 && yCounter >= 750) {// Between CheckPoint3 - CheckPoint4
							stepScores.add(60 - timeSecond);
							timeSecond = 60;
							step = 3;
							labelStep.setText("4. Stage");
						} else if (step == 3 && yCounter >= 1000) {// Between CheckPoint4 - CheckPoint5
							stepScores.add(60 - timeSecond);
							timeSecond = 60;
							step = 4;
							labelStep.setText("5. Stage");
						} else if (step == 4 && yCounter >= 1250)  {// Between CheckPoint5 - CheckPoint6
							stepScores.add(60 - timeSecond);
							timeSecond = 60;
							step = 5;
							labelFinish.setVisible(true);	
							fileReaderAndWriter.write(userName, stepScores);
							clip.stop();
							
						}
						
						if (isStarted == true) {
							int val = actorX - (-700);
							for (int k = 0; k < otherPlayers.size(); ++k) {
								OtherPlayer o = otherPlayers.get(k);
								
								int bound = 0;					
								Random r = new Random();
		
								if (o.yCounter < 50)
									o.yCounter += r.nextInt(15);
								else
									o.yCounter += r.nextInt(2 * (o.order));
										
								int imageNumber = ((o.yCounter - yCounter) / 20);
								
								

								if(backgroundImages == backgroundImagesCurve1) {
									otherPlayer = otherPlayerLeft;
									bound = 5;
								}
								else if(backgroundImages == straightRoad) {
									otherPlayer = otherPlayerNormal;
									bound = 7;
								}
								else if(backgroundImages == backgroundImagesCurve2) {
									otherPlayer = otherPlayerRight;
									bound = 5;
								}
								if (imageNumber >= 0 && imageNumber < bound) {
									o.imageLabel.setIcon(new ImageIcon(getClass().getResource(otherPlayer[imageNumber])));
									o.imageLabel.setVisible(true);
								} else {
									o.imageLabel.setVisible(false);
								}
								
								if (o.xCounter < 0 && o.yCounter > yCounter)
									o.imageLabel.setLocation(o.baseX  + o.xCounter + val-(-10*imageNumber), o.baseY  -  o.yCounter + yCounter);
								else if (o.xCounter > 0 && o.yCounter > yCounter)
									o.imageLabel.setLocation(o.baseX  + o.xCounter + val-(10*imageNumber), o.baseY  -  o.yCounter + yCounter);

								
							}
						}
							
					}
					catch(InterruptedException e) {
						e.printStackTrace();
					}
					
					
				}
				
			}
		};

	}
	
	public void createChangeImageRightLeftThread() {
		 this.thread1 = new Thread() {
			public void run() {
				
				
					while( i < 15 && i >=0) {
					
						if(actorX < -1240) {
							
							speed = 0;
							clip.stop();
										
							if(gameOver == false) {
								gameOver = true;
								i = 0;
							}
							
							actorX = actorX - 20;
							motorImages = fall;
							
							if (i < 10) {
								imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[i])));	
							} else {
								
								if (i == 14)
									labelGameover.setVisible(true);

								imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[9])));	
							}	
							if (i < 14)
								i++;
							
							try {						
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}	
											
						} else if(actorX > -200) {
							
							speed = 0;
							clip.stop();

							if(gameOver = false) {
								gameOver = true;
								i = 0;
							}
							
							actorX = actorX + 20;
							motorImages = leftfall;
							
							if (i < 10) {
								imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[i])));	
							}
							else {
								if (i == 14)
									labelGameover.setVisible(true);
								imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[9])));	
							}	
							if (i < 14)
								i++;
							try {						
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}	
							
						} else if(release == true) {
							imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[i])));			
							if(flag==2) {
								i++;
							}
							if(flag==1) {
							
								i--;
							}
							if(i > 14) {
								i = 13;
							}
							if(i < 0) {
								i = 1;
							}
						} else if(release == false){
							imagelabel1.setIcon(new ImageIcon(getClass().getResource(motorImages[i])));
							if(i < 7){
								i++;
							}
							else if(i > 7){
								i--;
							}
						}

					}
					try {						
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}					
			}
			
		};
	}
	public void keyReleased(KeyEvent e) {
		String whichKey = KeyEvent.getKeyText(e.getKeyCode());
		if(whichKey.compareTo("Left") == 0) {
			release = false;
		
		}
		else if(whichKey.compareTo("Right") == 0) {
			release = false;
		
		}
		else if(whichKey.compareTo("Up") == 0 && keyPressed) {
			uprelease = -1;
			keyPressed = false;
		}
		else if(whichKey.compareTo("Down") == 0 && keyPressed) {
		
			keyPressed = false;
		}
	}
	public void keyTyped(KeyEvent arg0) {
		
	}
}
