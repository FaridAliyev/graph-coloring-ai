# Constraint Satisfaction Problem (CSP) - Graph Coloring

The program reads a graph and the number of colors in a text file and finds whether the graph is colorable with the given number of colors. The proper vertex coloring is such that each vertex is assigned a color and no two adjacent vertices are assigned the same color.

## Prerequisites

Before you run the code, ensure you have met the following requirements:

- Have Java installed on your system.
- The Java code is written using `jdk version 17`. Use the same version to prevent potential errors due to version mismatch.

## How to Run the Code

To run the code on your local machine, follow these steps:

1. **Compile the java code**:
   In the terminal, navigate to the root folder (the folder that contains the `src` folder) and run this command:
   ```sh
   javac -sourcepath ./src/ -d ./out/ ./src/main/Program.java
   ```
2. **Run the program**:
   In the root folder, run the below command:
   ```sh
   java -cp ./out/ main/Program
   ```
  
  After running this command you will get one of these two results:
  - **`Solution found`** if the graph is colorable with the given input, you will also see the assigned color of each vertex
  - **`Couldn't find a solution`** otherwise

Feel free to change the input graph and the number of colors in the `graph.txt` file under the `resources` folder. The graph is undirected and provided in terms of its edges, e.g. *`1,2`* means there is a connection between vertex number 1 and 2. Find an example input below:

``` sh
Colors = 3
1,3 
2,18 
3,19 
2,19
```
The graph presented above has 5 vertices: “1”, “2”, “3”, “18” and “19”, and 4 edges.

***HAVE FUN TESTING THE CODE :)***
