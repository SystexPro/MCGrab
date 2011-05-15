package com.bukkit.systexpro.mcgrab;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Configuration {
	static String filename = "plugins/MCGrab/logs/logs.txt";	
	static String filenameChat = "plugins/MCGrab/logs/chat.txt";
	static String filenameKbs = "plugins/MCGrab/logs/kbs.txt";
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	static DateFormat shortDate = new SimpleDateFormat("yyyy-MM-dd");
	static DateFormat year = new SimpleDateFormat("yyyy");
	static DateFormat month = new SimpleDateFormat("MM");
	static DateFormat day = new SimpleDateFormat("dd");
	public void openFile(String args) {
		try{
			// Create file 
			FileWriter fstream = new FileWriter(args);
			out = new BufferedWriter(fstream);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToLogin(String playername, String line) {

		Date date = new Date();
        String datetime = dateFormat.format(date);
        String sShortDate = shortDate.format(date);
        String sYear = year.format(date);
        String sMonth = month.format(date);
        
        String message;
    
        
		message = "[" + datetime + "] " + playername + ": "+ line;

		BufferedWriter out = null;
        
        try {
            
        	out = new BufferedWriter(new FileWriter(filename, true));
            out.write(message);
            out.newLine();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (out != null) {
                	out.flush();
                	out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

	public static void writeToChat(String playername, String line) {

		Date date = new Date();
        String datetime = dateFormat.format(date);
        String sShortDate = shortDate.format(date);
        String sYear = year.format(date);
        String sMonth = month.format(date);
        
        String message;
    
        
		message = "[" + datetime + "] " + playername + ": "+ line;

		BufferedWriter out = null;
        
        try {
            
        	out = new BufferedWriter(new FileWriter(filenameChat, true));
            out.write(message);
            out.newLine();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (out != null) {
                	out.flush();
                	out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	public static void writeToKick(String playername, String line) {

		Date date = new Date();
        String datetime = dateFormat.format(date);
        String sShortDate = shortDate.format(date);
        String sYear = year.format(date);
        String sMonth = month.format(date);
        
        String message;
    
        
		message = "[" + datetime + "] " + playername + ": "+ line;

		BufferedWriter out = null;
        
        try {
            
        	out = new BufferedWriter(new FileWriter(filenameKbs, true));
            out.write(message);
            out.newLine();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (out != null) {
                	out.flush();
                	out.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	
	public  BufferedWriter out;
}
