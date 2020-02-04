/*
*	In the purpose of project "Communication Network Analysis"
*	of the "Advanced OOP with Java" course - WS1920 
*	Computer Science Department – Frankfurt University of Applied Sciences
*	
*	Authors: 
*		Ngo Minh Thong, 
*		Luu Nguyen Phat, 
*		Tran Huu Le Huy,
*		Nguyen Quynh Huong.
*/

package com.github.yadsendew;

import com.github.yadsendew.GraphParser;
import com.github.yadsendew.GraphWriter;
import com.github.yadsendew.ShortestPath;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
		
		long startPoint = System.currentTimeMillis();

		Arguments myArgs = new Arguments();
        myArgs.analyse(args);
		
		// // Specify path of file name
		String path = "resources/" + myArgs.getFileName();
		System.out.println(myArgs.getFileName());

    	// Parse graphml to graph object
		UndirectedWeightedGraph graph = GraphParser.parse(path);
		
		
		if (myArgs.getOutputFileList().size() == 0) {	// output in command line
			// 1. Print all attributes of the graph
			System.out.println("### Graph attributes ###");
			// get number of node
			System.out.println("\t" + "Number of nodes: " + graph.getTotalNodes());
			
			// get number of edge
			System.out.println("\t" + "Number of edges: " + graph.getTotalEdges());
			
			// print all vertices's ID
			System.out.println("\t" + "Vertex IDs: " + graph.getNodeId());
			
			// 3. print all edges's ID
			System.out.println("\t" + "Edge IDs: " + graph.getEdgeId());
			
			// check connectivity
			System.out.println("\t" + "Graph " + ( Connectivity.isConnected(graph) == true ? "is" : "is not") + "connected" );
			
			// get diameter
			System.out.println("\t" + "Gragh diameter: " + Diameter.calculate(graph));
			System.out.println(System.currentTimeMillis() - startPoint);
			
			
			// 4. other task
			for (ArrayList<String> task : myArgs.getTaskAnalysed()) {
				// 4.1 find shortest path between 2 nodes
				if ( task.get(0).equals("-s") ) {
					System.out.println("### Shortest path ###");
					
					// get the ArrayList of nodeList
					//ArrayList<String> nodeIdList = new ArrayList<String>( graph.getNodeList().keySet() );
					
					
					String startId = task.get(1);
					String endId = task.get(2);
					// check if id are valid
					if ( !graph.containsNode(startId) ) {
						System.out.println("There is no node ID " + startId);
						System.exit(0);
					}
					if ( !graph.containsNode(endId) ) {
						System.out.println("There is no node ID " + endId);
						System.exit(0);
					}
					
					// get the list of shortest path then choose the 1st as the default path
					ShortestPath shortestPathInfo = new ShortestPath(graph, startId, endId);
					ArrayList< ArrayList<String>> shortestPathList = shortestPathInfo.getPathList();
					ArrayList<String> shortestPath = shortestPathList.get(0);	// get the 1st path to print to the cmd
					double length = (Double) shortestPathInfo.getLength();
					
					System.out.print("Shortest path " + startId + " to " + endId);
					System.out.print(": path -> " + shortestPath);
					System.out.println("; length -> " + length);
					
					System.out.println();
					
				}	// end shortest path
				
				// 4.2 betweenness centrality measure
				else if ( task.get(0).equals("-b") ) {
					System.out.println("### Betweenness centrality ###");
					
					String nodeId = task.get(1);
					// check if id is valid
					if ( !graph.containsNode(nodeId) ) {
						System.out.println("There is no node ID " + nodeId);
						System.exit(0);
					}
					
					BetweennessCentrality bCentrality = new BetweennessCentrality(graph, nodeId);
					System.out.println("Node " + nodeId + ": " + bCentrality.getBCM());
					System.out.println(System.currentTimeMillis() - startPoint);
					
				}	// end BCM
			}	// end for loop 
			
		}	// end CMD
		
		else {	// Export to file
			// Output to XML format
			GraphWriter.exportToXML(graph, myArgs.getOutputFileList().get(0));
			System.out.println(System.currentTimeMillis() - startPoint);
			// Output to normal text
			//GraphWriter.exportToText(graph, myArgs.getOutputFileList().get(1));
		}

		// End of running time
		System.out.println("----------------------------------------");
		System.out.print("Executed time: ");
        System.out.println(System.currentTimeMillis() - startPoint);
        
    }
}