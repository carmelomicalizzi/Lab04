package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	
	public Studente getStudenteDataMatricola(int matricolaData) {

		final String sql = "SELECT nome, cognome, matricola, cds FROM studente WHERE matricola = ?";


		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, matricolaData);


			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				int matricola = rs.getInt("matricola");
				String cds = rs.getString("cds");

				
				Studente foundStudente = new Studente(matricola, nome, cognome, cds);
				
				return foundStudente;

				
			}

			return null;


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}


}
