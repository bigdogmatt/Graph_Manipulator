import java.util.Scanner;
import java.io.*;


public class Graphs 
{	
	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in); //new scanner object for user input
		Scanner file = null; //new scanner object for the graph file;

		//loop to ask the user for a file until a valid file is given
		while(file == null) 
		{
			try //gets user input file and creates a scanner from the file
			{
				System.out.print("Enter an undirected graph file: ");
				String graphFileName = in.nextLine();
				file = new Scanner(new File(graphFileName));
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("Invalid file name.");
			}
		}
		
		//create a new graph from the given file
		Graph graph = new Graph(file);
		file.close();
		

		int input = 0;

		//asks the user what they want to do with the graph until they choose the quit option
		while ( input != 8)
		{
			System.out.println("1. Is Connected");
			System.out.println("2. Minimum Spanning Tree");
			System.out.println("3. Shortest Path");
			System.out.println("4. Is Metric");
			System.out.println("5. Make Metric");
			System.out.println("6. Traveling Salesman Problem");
			System.out.println("7. Approximate TSP");
			System.out.println("8. Quit");
			System.out.println();
			System.out.print("Make your choice ( 1 - 8 ): ");

			input = in.nextInt();

			System.out.println();

			switch ( input )
			{
			case 1: 
				//checks if the graph is connected and prints the respective statement
				if (graph.isConnected())
					System.out.println("Graph is connected.");
				else
					System.out.println("Graph is not connected.");
				System.out.println();
				break;
			case 2:
				if (graph.isConnected()) 
				{
					Graph mst = graph.mst();//makes the mst array for graph into a graph
					mst.print();
				}
				else
					System.out.println("Error: Graph is not connected.");
				System.out.println();
				break;
			case 3:
				System.out.print("From which node would you like to find the shortest paths (0 - " + (graph.getNodes()-1) + "): ");
				int start = in.nextInt();
				int [][] result = graph.shortestPath(start);
				int [] distances = result[0];
				int [] pred = result[1];
				for(int i = 0; i < distances.length; i++) 
				{
					System.out.print(i + ": "); //prints current node
					if(distances[i] == Integer.MAX_VALUE) //if the node is unreachable, it will print infinity
					{ 
						System.out.println("(Infinity)");
					}
					else 
					{
						System.out.print("(" + distances[i] + ")\t");
						printPath(pred, i, start); //will print the predecessor before node i
						System.out.println();
					}

				}
				System.out.println();
				break;
			case 4:
				//checks if the graph is metric by first checking if it is completely connected 
				//then checking if its metric and then prints the respective statement
				if (!graph.isCompletelyConnected())
					System.out.println("Graph is not metric: Graph is not completely connected.");
				else if (!graph.isMetric())
					System.out.println("Graph is not metric: Edges do not obey the triangle inequality.");
				else
					System.out.println("Graph is metric.");
				System.out.println();
				break;
			case 5:
				//makes the graph metric and prints it out as long as it is connected
				if (!graph.isConnected())
					System.out.println("Error: Graph is not connected.");
				else
					graph.makeMetric();
				graph.print();
				System.out.println();
				break;
			case 6:
				if(!graph.isConnected())
					System.out.println("Error: Graph is not connected.");
	
				else 
				{
					int [] tour = graph.travelingSalesman();
					if(tour[graph.getNodes()]==Integer.MAX_VALUE) //if the last node in the tour is not the starting node, there isn't a tour
					{ 
						System.out.println("Error: Graph has no tour.");
					}
					else 
					{ 
						System.out.print(tour[graph.getNodes()] + ": "); //prints the length
						for(int i = 0; i<graph.getNodes(); i++) //prints the next node in the tour followed by an arrow
						{ 
							System.out.print(tour[i] + " -> ");
						}
						System.out.println("0");
					}
				}
				System.out.println();
				break;
			case 7:
				//performs the approx tsp on the graph as long as it is metric
				if ( !graph.isMetric())
					System.out.println("Error: Graph is not metric");
				else
				{
					int [] tour = graph.approxTSP();

					System.out.print(tour[graph.getNodes()] + ": "); //prints the length
					
					//prints the next node in the tour followed by an arrow
					for(int i = 0; i<graph.getNodes(); i++) 
					{
						System.out.print(tour[i] + " -> ");
					}
					System.out.println("0");

				}
				System.out.println();
				break;
			}

		}
		in.close();



	}
	private static void printPath(int [] pred, int node, int start) //prints the predecessor before the node
	{
		if(node == start) 
			System.out.print(node);
		else 
		{
			printPath(pred, pred[node], start);
			System.out.print(" -> " + node);
		}
	}

}
