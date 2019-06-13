package it.polito.tdp.formulaone.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	private FormulaOneDAO dao;
	private Graph<Driver,DefaultWeightedEdge> grafo;

	public Model() {
		this.dao = new FormulaOneDAO();
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
	}
	public void creaGrafo(Season anno) {
		
	}
	
	public List<Season> getSeason(){
		return dao.getAllSeasons();
	}
	
	
}
