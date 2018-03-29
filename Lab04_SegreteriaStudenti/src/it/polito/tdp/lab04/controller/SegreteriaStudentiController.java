package it.polito.tdp.lab04.controller;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

	
	CorsoDAO cdaotemp = new CorsoDAO();
	StudenteDAO sdaotemp = new StudenteDAO();

	
    @FXML
    private ComboBox<Corso> menuCorsi;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtAreaStudenti;

    @FXML
    private Button btnReset; 

    @FXML
    private CheckBox checkButton;

    @FXML
    void cercaCorsi(ActionEvent event) {
    	
    	int matricolaCercata = Integer.parseInt(txtMatricola.getText());
    	List<Corso> corsiStudente = new ArrayList<Corso>(cdaotemp.getCorsiStudente(matricolaCercata));
    	
    	
    	String elenco = "";
    	for(Corso c : corsiStudente)
    		elenco += c.completeToString() +"\n";
    	
    	this.txtAreaStudenti.setText(elenco);

    }

    @FXML
    void cercaIscritti(ActionEvent event) {
    	
    	String elenco = "";
    	for(Studente s : cdaotemp.getStudentiIscrittiAlCorso(menuCorsi.getValue()))
    		elenco += s.toString() +"\n";
    	
    	this.txtAreaStudenti.setText(elenco);

    }

    @FXML
    void iscrivi(ActionEvent event) {
    	
    	Corso aCorso = menuCorsi.getValue();
    	Studente studenteDaIscrivere = sdaotemp.getStudenteDataMatricola(Integer.parseInt(this.txtMatricola.getText()));
    	
    	if(cdaotemp.iscriviStudenteACorso(studenteDaIscrivere, aCorso) == true)
    		this.txtAreaStudenti.setText("Studente iscritto correttamente!");
    	
    	else if(cdaotemp.iscriviStudenteACorso(studenteDaIscrivere, aCorso) == false)
    		this.txtAreaStudenti.setText("Studente già iscritto a questo corso");

    	
    	
    }

    @FXML
    void reset(ActionEvent event) {
    	
    	this.txtAreaStudenti.clear();
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    	this.menuCorsi.setValue(null);
    }

  
    
    @FXML
    void completaNomeCognome(ActionEvent event) {
    	
    	String nome = sdaotemp.getStudenteDataMatricola(Integer.parseInt(txtMatricola.getText())).getNome();
    	String cognome = sdaotemp.getStudenteDataMatricola(Integer.parseInt(txtMatricola.getText())).getCognome();

    	this.txtNome.setText(nome);
    	this.txtCognome.setText(cognome);
    	
    }
    
    @FXML
    void initialize() {
    	
    	menuCorsi.getItems().addAll(cdaotemp.getTuttiICorsi());
    	menuCorsi.getItems().add(cdaotemp.getTuttiICorsi().size(), null);
    	
    }

}
