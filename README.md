# Graph_Manipulator
Created November 2018:
<br />A program that takes a txt file of a graph and preforms different tasks specified by the user.  
The user can:  
 - Print the graph
 - See if the graph is connected
 - Create an MST of the graph
 - See if the graph is metric
 - Make the graph metric
 - Find the shortest path from one node to all other nodes
 - Find a shortest TSP tour
 - Find the approx TSP using shortcuts 
 
<br />An example of a graph.txt file can be seen below:
<br />6
<br />2 1 5 5 6
<br />2 0 5 2 3
<br />3 1 3 3 4 5 2
<br />2 2 4 4 5
<br />2 3 5 5 7
<br />3 0 6 2 2 4 7 

Each line represents a node except the top number. The first number (6) represents the total number of nodes in the graph. The first number in each line represents the number of nodes that the current node is connected to. The following numbers are in pairs and represent the connected node and its weight. For example: the third node, node 2, is connected to three nodes, node 1, which has an edge weight of 3, node 3, which has an edge weight of 4, and node 5, which has an edge weight of 2. 

