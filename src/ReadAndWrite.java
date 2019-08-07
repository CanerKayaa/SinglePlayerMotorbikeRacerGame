import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JTextField;
public class ReadAndWrite {
	
	 private String filePath;

	public ReadAndWrite(String filePath) {
		this.filePath = filePath;
		
	}
	
	public void write(String userName, ArrayList<Integer>stepScores) {
		int totalScore = 0;
		for(int i = 0; i < stepScores.size(); i ++) {
			totalScore += stepScores.get(i);
		}
		String str = userName + " " + totalScore;
		 try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))){
				writer.write(str);
				writer.newLine();
				writer.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
	}
	
	
	public String getHighScores() {
        String result = "";

        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
        	
        	ArrayList<String> scores = new ArrayList<String>();
        	
		 	String line;
		 	
	         while ((line = br.readLine()) != null) {
	        	 scores.add(line);	          
	         }
	          
	         for (int s = 0; s < 3; ++s) {
		         int compare = 300;
		         int highScoreIndex = -1;
		         
		         for (int i = 0; i < scores.size(); ++i) {
		        	 String userAndScore = scores.get(i);
		        	 
		        	 String[] r = userAndScore.split(" ");
		        	 
		        	 if (r.length > 1) {       		
		        		 int score = Integer.parseInt(r[1]);     
		        		 
		        		 if (score < compare) {
		        			 compare = score;
		        			 highScoreIndex = i;
		        		 }
		        	 }
		         }
		         
		         if (highScoreIndex != -1) {
		        	 
		        	 result += scores.remove(highScoreIndex)  + "\n";
		         } 
	         }
	                          
        } catch (IOException e) {
            e.printStackTrace();
        }	
        
        
        return result;

	}
}
