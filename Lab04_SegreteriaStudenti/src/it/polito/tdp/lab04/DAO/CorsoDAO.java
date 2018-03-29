package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				
				Corso nuovoCorso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(nuovoCorso);
				
				
			}

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(Corso corso) {
		final String sql = "SELECT nome FROM corso WHERE codins = ?";


		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				//System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);
				
				Corso corsoFound = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				return corsoFound;
			
			}

			return null;


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "SELECT studente.matricola, studente.nome, studente.cognome, studente.CDS FROM iscrizione JOIN studente ON iscrizione.matricola = studente.matricola WHERE codins=?";
		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String nome = rs.getString("nome");
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("cds");
				
				//System.out.println(matricola + " " + nome + " " + cognome + " " + cds);
				
				Studente studenteTrovato = new Studente(matricola, nome, cognome, cds);
				studenti.add(studenteTrovato);
				
			
			}

			return studenti;


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		
	}
	
	
	public List<Corso> getCorsiStudente(int matricola) {
		
		final String sql = "SELECT corso.codins, corso.nome, corso.crediti, corso.pd FROM iscrizione JOIN corso ON iscrizione.codins = corso.codins WHERE iscrizione.matricola=?";
		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				
				Corso nuovoCorso = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(nuovoCorso);
				
			
			}

			return corsi;


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean iscriviStudenteACorso(Studente studente, Corso corso) {
		
		for(Corso c : this.getCorsiStudente(studente.getMatricola())) {
			if(corso.getCodins().compareTo(c.getCodins()) == 0) 
				return false; }
		
		final String sql = "INSERT INTO iscrizione (matricola, codins) VALUES (?,?)";
		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			int matricola = studente.getMatricola();
			String codIns = corso.getCodins();

			st.setInt(1, matricola);
			st.setString(2, codIns);
			
			st.executeUpdate();
			

				return true;
				
			
			}
		

		catch (SQLException e) {
			throw new RuntimeException("Errore Db");
	}			
						

		
	}
	
	
}
