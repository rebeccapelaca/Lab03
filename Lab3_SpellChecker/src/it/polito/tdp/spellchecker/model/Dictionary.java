package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario = new ArrayList<String>();
	
	public void loadDictionary(String language){
		
		if(language.compareTo("English")==0){
			try {
				FileReader fr = new FileReader("rsc/English.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
					dizionario.add(word.toLowerCase().replaceAll("[ \\p{Punct}]", ""));
				}
				br.close();
				} catch (IOException e){
				System.out.println("Errore nella lettura del file");
				}
		}
		else {
			try {
				FileReader fr = new FileReader("rsc/Italian.txt");
				BufferedReader br = new BufferedReader(fr);
				String word;
				while ((word = br.readLine()) != null) {
					dizionario.add(word.toLowerCase().replaceAll("[ \\p{Punct}]", ""));
				}
				br.close();
				} catch (IOException e1){
				System.out.println("Errore nella lettura del file");
				}
		}
	}
	
	public List<String> spellCheckText(List<String> inputTextList) {
		
		/*
		List<String> parole_errate = new ArrayList<String>();
		
		for(String parola_input : inputTextList) {
			if(!dizionario.contains(parola_input)
				 parole_errate.add(parola_input);

		return parole_errate;
		*/
		
		Collections.sort(dizionario);
		
		int p1;
		int p2;
		int c;
		boolean trovata = false;
		
		List<String> paroleErrate = new LinkedList<String>();
		
		for(int i=0; i<inputTextList.size(); i++) {
			
			p1 = 0;
			p2 = dizionario.size()-1;
			c = (p1+p2)/2;
			trovata = false;

			while ((!trovata) && p1<=p2) {
				
			   if(inputTextList.get(i).compareToIgnoreCase(dizionario.get(c))==0)
				   trovata=true;
			   
				   
			   if(inputTextList.get(i).compareToIgnoreCase(dizionario.get(c))>0) {
				   p1 = c+1;
				   c = (p1+p2)/2;  
			   }
			   
			   if(inputTextList.get(i).compareToIgnoreCase(dizionario.get(c))<0) {  
				   p2 = c-1;
				   c = (p1+p2)/2;
			   }
			}
			
			if(trovata==false) 
				paroleErrate.add(inputTextList.get(i));
		}	
		
		return paroleErrate;
	}
}

