import java.util.Scanner;

public class Graph {

	//a graph has a 2d array called graph
	private int[][] graph;

	//constructor that takes a scanner (the input file) and creates a 2d array from it
	public Graph (Scanner file)
	{
		int numNodes = file.nextInt();

		graph = new int[numNodes][numNodes];

		for ( int i = 0; i < numNodes; i++ )
		{
			int edges = file.nextInt();

			for ( int j = 0; j < edges; j++ )
			{
				int node = file.nextInt();
				int weight = file.nextInt();
				graph[i][node] = weight;		
			}
		}
	}

	//sets the graph equal to a given 2d array
	public Graph (int [][] array) 
	{
		graph = array;
	}

	//prints the graph in the same format as the input txt file
	public void print()
	{
		System.out.println(graph.length); //prints the number of nodes first
		
		
		for ( int i = 0; i < graph.length; i++)
		{
			//then prints the number of nodes the current node is attached to
			int count = 0;
			for ( int j = 0; j < graph.length; j++)
			{
				if ( graph[i][j] != 0 )
					count++;
			}

			System.out.print(count);

			//then prints the a node it is attached to and the weight of its edge
			for ( int j = 0; j < graph.length; j++)
			{
				if ( graph[i][j] != 0 )
				{
					System.out.print(" " + j + " " + graph[i][j]);
				}
			}

			System.out.println();
		}
	}

	//checks if the graph is connected and returns true or false respectively
	public boolean isConnected()
	{
		//first do a depth first traversal of the graph and store it
		int[] array = DFS( 0 ); 

		//if something in the depth first traversal is not filled (= -1) then the graph is not connected
		for ( int i = 0; i < graph.length; i++ )
		{
			if ( array[i] == -1 )
				return false;
		}

		return true;
	}

	//depth first traversal of the graph starting at a given node
	private int[] DFS(int node)
	{
		//create an array one longer than the rows in the graph and we store current number in last location
		int[] array = new int[graph.length + 1]; 
		
		//fill the array with -1 except for the last spot
		for ( int i = 0; i < graph.length; i++ )
			array[i] = -1;

		//depth first search given the starting node and the array of -1s
		DFS( node, array);
		return array;
	}

	private void DFS (int node, int[] number)
	{
		//set the first item in the array equal to the last (0) and add one to the current number we are on
		number[node] = number[number.length - 1];
		number[number.length - 1]++;

		//if the value in the given array at the current location hasnt been changed (= -1) and the graph at
		//the starting node row and the current location column is not equal to zero (they are connected)
		//then recursively DFS starting with that current location
		for ( int i = 0; i < graph.length; i++ )
		{
			if ( number[i] == -1 && graph[node][i] != 0 )
			{
				DFS (i, number);
			}
		}
	}

	//checks if the graph is completely connected to use for isMetric
	//a graph is completely connected if every node is connected to every other node
	public boolean isCompletelyConnected()
	{
		//if any location in the graph besides the diagonal is equal to 0, the graph is not
		//completely connected so return false
		for ( int i = 0; i < graph.length; i++ )
		{
			for ( int j = i + 1; j < graph.length; j++ )
			{
				if ( graph[i][j] == 0 )
				{
					return false;
				}	
			}
		}
		return true;
	}

	//besides being completely connected, a graph must also obey the triangle inequality to be metric
	public boolean isMetric()
	{
		//for any three connected nodes in a triangle, if the distance from i to j is greater than
		//the distance from i to k plus the distance from k to j, the graph does not obey the trianle
		//inequality and is therefore not metric, return false
		for ( int i = 0; i < graph.length; i++ )
		{
			for ( int j = i + 1; j < graph.length; j++ )
			{
				for ( int k = 0; k < graph.length; k++ )
				{
					if ( k != j && k != i && graph[i][j] > graph[i][k] + graph[k][j] )
					{
						return false;
					}	
				}
			}
		}

		return true;

	}

	
	public Graph mst()//will visit every node and add only the edges with the lowest weight that allows the graph to remain connected
	{
		boolean [] s = new boolean [graph.length];//states whether the node has been visited
		s[0] = true; 	//is true because this is the node that we are stating with
		int [][] mst = new int [graph.length][graph.length]; 	//holds the result
		for(int i = 0; i<graph.length - 1; i++) 
		{
			int start = 0;	//node that you are on 
			int end = 0;	//node that you are going to
			int distance = Integer.MAX_VALUE;  
			for(int j = 0; j<graph.length; j++)
			{
				if(s[j]) 
				{
					for(int k = 0; k<graph.length; k++) 
					{                     
						if(!s[k] && graph[j][k] !=0 && graph[j][k]<distance) //Checks if the current edge has the least weight to get to the node
						{
							start = j;
							end = k;
							distance = graph[j][k];
						}
					}
				}

			}
			mst[start][end] = distance; 
			mst[end][start] = distance; //is done both ways to make the matrix symmetrical
			s[end] = true;
		}
		return new Graph(mst);
	}

	public int[][] shortestPath(int node) //find the way to get to each node with the lowest weight/distance
	{
		boolean [] s = new boolean [graph.length]; //records if the node has been visited
		int [] distances = new int [graph.length]; //records the weight it takes to get to the node from the preceding node
		int [] pred = new int [graph.length]; //records the preceding node
		for(int i = 0; i<graph.length; i++) 
		{
			distances[i] = Integer.MAX_VALUE; 
		}
		distances[node] = 0; //it takes the starting node 0 distance to get to itself from itself
		for(int i = 0; i<graph.length; i++) 
		{
			int u = 0;
			int distance = Integer.MAX_VALUE;
			for(int j = 0; j<distances.length; j++) 
			{
				if(!s[j] && distances[j]<=distance) 
				{
					u = j;
					distance = distances[j];
				}
			}
			for(int j = 0; j<graph.length; j++)//if node u and j are connected and the distance to get to j plus the distance of u is less than 
				//the currently recorded distance of j, j's distance will be updated with the smaller amount and the predecessor becomes u
			{
				if(graph[u][j]!=0 && distances[j]>distances[u]+ graph[u][j]) 
				{ 
					distances[j] = distances[u]+ graph[u][j];
					pred[j] = u;
				}
			}
			s[u] = true;
		}
		int[][] shortest = {distances, pred};
		return shortest; 
	}

	//makes the graph metric by finding the shortest path to each node from each node and setting its 
	//edge weight equal to the total weight of the shortest path
	public void makeMetric()
	{
		if ( !isMetric() )
		{
			for ( int i = 0; i < graph.length; i++ )
			{
				int[][] result = shortestPath(i);
				int[] distances = result[0];
				for ( int j = 0; j < distances.length; j++ )
				{
					graph[i][j] = distances[j];
				}
			}
		}
	}

	public int[] travelingSalesman() //goes through every possible tour until it finds the shortest one
	{
		int [] tour = new int [graph.length+1];//records the current tour
		int [] best = new int [graph.length+1];//records the best tour
		best[graph.length] = Integer.MAX_VALUE;
		boolean [] visited = new boolean [graph.length];//records if the node has been visited
		travelingSalesman(tour, best, 0, 0, visited); //goes through every tour to find the best one
		return best;
	}
	private void travelingSalesman(int [] tour, int [] best, int step, int node, boolean [] visited) 
	{ 
		if(step == graph.length) 
		{
			if(node == 0 && tour[graph.length]< best[graph.length]) //if the tour is better than the current best, it will become the new best
			{
				for(int i = 0; i<best.length; i++) 
				{
					best[i] = tour[i];
				}
			}
		}
		else if(!visited[node]) //looks at the nodes connected to the one that have not been visited.
		{ 
			visited[node] = true;
			tour[step] = node;
			for(int i = 0; i<graph.length; i++) 
			{ //looks at each node i that is connected to the current node. Adds the weight of node i to the
				//tour and recursively looks at the next node. If the tour isn't the best then the weight will be removed. 
				if(graph[node][i] != 0) 
				{
					tour[graph.length] += graph[node][i];
					travelingSalesman(tour, best, step +1, i, visited);
					tour[graph.length] -= graph[node][i]; 
				}
			}
			visited[node] = false;
		}
	}

	//this method finds the approximate tsp through different shortcut approaches
	public int[] approxTSP()
	{
		//we first get the mst for the graph and store it
		Graph mst = mst();

		//then we find and store the dfs of the mst
		int[] array = mst.DFS( 0 );

		//we create an array of length graph + 1 to store the tour and the total distance ( the + 1 )
		int[] tour = new int[graph.length + 1];

		//start with a distance of 0
		int distance = 0;

		//we switch the value and index in the dfs and store the result in tour
		for ( int i = 0; i < graph.length; i++ )
		{
			tour[array[i]] = i; //index value switch
		}
		
		//we cycle through the tour and get the distance from each node to the next and add it to distance
		//if it is the last node in the tour, we get the distance to go back to the front
		for ( int i = 0; i < graph.length; i++ )
		{
			if ( i == graph.length - 1 )
				distance += graph[tour[i]][tour[0]];
			else
				distance += graph[tour[i]][tour[i+1]];
		}
		tour[tour.length-1] = distance;
		return tour;
	}

	public int getNodes() //returns the number of nodes
	{
		return graph.length;
	}
}


