package main;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public class Program {

    public static void main(String[] args) throws FileNotFoundException {
        ColorGraph graph = ColorGraph.initGraph("graph.txt");
        boolean solved = graph.colorDfs(graph.getVertices().get(0));

        // print the results
        System.out.println(solved ? "Solution found" : "Couldn't find a solution");
        for (Map.Entry<Vertex, List<Vertex>> entry : graph.getAdjacencyList().entrySet()) {
            Vertex vertex = entry.getKey();
            int color = vertex.currentColor;
            System.out.println("Vertex: " + vertex + ", Color: " + color);
        }
    }
}
