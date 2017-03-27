package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	Dictionary model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLingua;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnSpellCheck;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label lblNumeroErrori;

    @FXML
    private Button btnClearText;

    @FXML
    private Label lblTempoCompletamento;
    
    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	boolean valida = true;
    	
    	Long t1 = System.nanoTime();
    	
    	List<String> input = new LinkedList<String>();
    	
    	model.loadDictionary(cmbLingua.getValue());
    	
    	for(int i=0; i<txtInput.getText().length(); i++){
    		if(Character.isDigit(txtInput.getText().charAt(i))==true){
    			valida = false;
    			txtOutput.setText("Not valid");
    			break;
    		}
    	}
    	
    	if(valida==true) {	
    		
	    	if(txtInput.getText().contains(" ")) {
	    		
	    		String array[] = txtInput.getText().split(" ");
	    		for(int j=0; j<array.length; j++) {
	    			array[j].replaceAll("[ \\p{Punct}]", "");	
	    			if(array[j].compareTo(" ")!=0) 
	    				input.add(array[j]);
	    		}
	    	}
	    	else 
	    		input.add(txtInput.getText());
	    	
	    	List<String> output = new LinkedList<String>(model.spellCheckText(input));
	    	
	    	for(String s : output)
	    		txtOutput.appendText(s + "\n");
	    	
	    	Long t2 = System.nanoTime();
	    	
	    	int numeroErrori = model.spellCheckText(input).size();	
	    	if(numeroErrori==1)
	    		lblNumeroErrori.setText("The text contains " + numeroErrori + " error");
	    	else
	    		lblNumeroErrori.setText("The text contains " + numeroErrori + " errors");
	    	
	    	lblTempoCompletamento.setText("Spell check completed in " + (t2-t1)/1e9 + " seconds");
	    	
	    	}
    	}

    @FXML
    void doClearText(ActionEvent event) {
    	
    	txtInput.clear();
    	txtOutput.clear();
    }


    @FXML
    void initialize() {
        assert cmbLingua != null : "fx:id=\"cmbLingua\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblNumeroErrori != null : "fx:id=\"lblNumeroErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClearText != null : "fx:id=\"btnClearText\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblTempoCompletamento != null : "fx:id=\"lblTempoCompletamento\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        cmbLingua.getItems().addAll("English", "Italian");
        if(cmbLingua.getItems().size()>0)
        	cmbLingua.setValue("English");
    }

	public void setModel(Dictionary model) {
		
		this.model = model;	
	}
}

