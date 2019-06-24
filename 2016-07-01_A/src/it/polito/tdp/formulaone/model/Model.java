package it.polito.tdp.formulaone.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {

	private FormulaOneDAO dao;
	private Graph<Driver,DefaultWeightedEdge> grafo;
	private DriverIdMap idMap;
	public Model() {
		this.dao = new FormulaOneDAO();
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new DriverIdMap();
	}
	public void creaGrafo(Season anno) {
		Graphs.addAllVertices(grafo, idMap.values());
		for(DriverSeasonResult dsr: dao.getDriverSeasonResults(anno, idMap)) {
			Graphs.addEdgeWithVertices(grafo, dsr.getD1(), dsr.getD2(), dsr.getCounter());
		}
		System.out.format("Grafo creato: %d archi, %d nodi\n", grafo.edgeSet().size(), grafo.vertexSet().size());
	}
	
	public List<Season> getSeason(){
		return dao.getAllSeasons() ;
	}
	public Driver getBestDriver() {
		Driver bestDriver = null;
		int best = Integer.MIN_VALUE;
		
		for (Driver d : grafo.vertexSet()) {
			int sum = 0;
			
			// Itero sugli archi uscenti
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(d)) {
				sum += grafo.getEdgeWeight(e);
			}
			
			// Itero sugli archi entranti
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(d)) {
				sum -= grafo.getEdgeWeight(e);
			}
			
			if (sum > best || bestDriver == null) {
				bestDriver = d;
				best = sum;
			}
		}
		
		if (bestDriver == null) {
			new RuntimeException("BestDriver not found!");
		}
		
		return bestDriver;
	
	}
	
	
}
