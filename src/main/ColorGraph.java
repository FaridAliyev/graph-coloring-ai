package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ColorGraph {
    private final Map<Vertex, List<Vertex>> adjacencyList = new HashMap<>();
    private final Map<Vertex, Boolean> visited = new HashMap<>();
    private final List<Vertex> vertices = new ArrayList<>();
    private int colorCount;
    private boolean solved = false;

    // read the graph from the file
    public static ColorGraph initGraph(String fileName) throws FileNotFoundException {
        ColorGraph graph = new ColorGraph();
        File file = new File(Paths.get("src", "resources", fileName).toString());
        Scanner scanner = new Scanner(file);

        graph.colorCount = Integer.parseInt(scanner.nextLine().split("=")[1].trim());

        while (scanner.hasNextLine()) {
            String[] edge = scanner.nextLine().split(",");
            int vertex1 = Integer.parseInt(edge[0]);
            int vertex2 = Integer.parseInt(edge[1]);
            graph.addEdge(vertex1, vertex2);
        }

        scanner.close();
        return graph;
    }


    // connect the vertices
    private void addEdge(int vertex1, int vertex2) {
        Vertex v1 = vertices.stream()
                .filter(vertex -> vertex.id == vertex1)
                .findFirst()
                .orElse(new Vertex(vertex1, IntStream.rangeClosed(1, colorCount).boxed().collect(Collectors.toCollection(ArrayList::new)), 0));
        Vertex v2 = vertices.stream()
                .filter(vertex -> vertex.id == vertex2)
                .findFirst()
                .orElse(new Vertex(vertex2, IntStream.rangeClosed(1, colorCount).boxed().collect(Collectors.toCollection(ArrayList::new)), 0));


        if (!adjacencyList.containsKey(v1)) {
            adjacencyList.put(v1, new ArrayList<>());
            visited.put(v1, false);
            vertices.add(v1);
        }
        if (!adjacencyList.containsKey(v2)) {
            adjacencyList.put(v2, new ArrayList<>());
            visited.put(v2, false);
            vertices.add(v2);
        }
        if (!adjacencyList.get(v1).contains(v2)) adjacencyList.get(v1).add(v2);
        if (!adjacencyList.get(v2).contains(v1)) adjacencyList.get(v2).add(v1);
    }


    // goal state
    private boolean isGoalReached() {
        for (Vertex vertex : adjacencyList.keySet()) {
            if (vertex.currentColor == 0) return false;
            List<Vertex> neighbors = adjacencyList.get(vertex);
            for (Vertex neighbor : neighbors) {
                if (vertex.currentColor == neighbor.currentColor) return false;
            }
        }

        return true;
    }

    // dfs search
    public boolean colorDfs(Vertex vertex) {
        // check goal condition
        if (isGoalReached() || solved) {
            solved = true;
            return true;
        }

        // Store the previous state
        VertexState prevVertexState = new VertexState();
        for (Vertex v : vertices) {
            prevVertexState.setDomain(v.id, new ArrayList<>(v.domain));
            prevVertexState.setColor(v.id, v.currentColor);
        }

        visited.put(vertex, true);

        // LCV sorted domain
        List<Integer> domain = LCV(vertex);

        // loop through the domain
        for (Integer i : domain) {
            // check goal condition
            if (isGoalReached() || solved) {
                solved = true;
                return true;
            }
            // color the vertex
            vertex.currentColor = i;

            // constraint propagation
            propagate(vertex, vertex.currentColor);

            // forward checking
            for (Vertex v : vertices) {
                if (v != vertex && v.domain.isEmpty()) {
                    visited.put(vertex, false);
                    // Restore the previous state
                    for (Vertex prevVertex : vertices) {
                        prevVertex.domain = prevVertexState.getDomain(prevVertex.id);
                        prevVertex.currentColor = prevVertexState.getColor(prevVertex.id);
                    }
                    return false;
                }
            }

            // MRV sorted vertices
            List<Vertex> mrvSortedVertices = MRVSort();
            if (!mrvSortedVertices.isEmpty() && mrvSortedVertices.get(0) != vertex) {
                colorDfs(mrvSortedVertices.get(0));
            } else {
                if (mrvSortedVertices.size() > 1) {
                    colorDfs(mrvSortedVertices.get(1));
                }
            }
        }

        // return if already solved
        if (solved) return true;

        // couldn't find solution from here, returning back
        visited.put(vertex, false);

        // Restore the previous state
        for (Vertex prevVertex : vertices) {
            prevVertex.domain = prevVertexState.getDomain(prevVertex.id);
            prevVertex.currentColor = prevVertexState.getColor(prevVertex.id);
        }
        return solved; // solved is always false here
    }


    // Minimum Remaining Values (MRV) heuristic
    private List<Vertex> MRVSort() {
        return vertices.stream()
                .filter(v -> !visited.get(v))
                .sorted(Comparator.comparingInt(v -> v.domain.size()))
                .collect(Collectors.toList());
    }

    // Constraint propagation using AC3 algorithm
    private void propagate(Vertex vertex, int color) {
        List<Vertex> neighbors = adjacencyList.get(vertex);
        for (Vertex neighbor : neighbors) {
            if (neighbor.currentColor == 0) {
                neighbor.domain.remove((Integer) color);
            }
        }
        for (Vertex neighbor : neighbors) {
            if (neighbor.domain.size() == 1 && neighbor.currentColor == 0) {
                neighbor.currentColor = neighbor.domain.get(0);
                propagate(neighbor, neighbor.currentColor);
            }
        }
    }

    // Least Constraining Value (LCV) heuristic
    private List<Integer> LCV(Vertex vertex) {
        List<Integer> sortedLCVDomain = new ArrayList<>();
        List<Integer> domain = new ArrayList<>(vertex.domain);
        List<Vertex> neighbors = adjacencyList.get(vertex);
        while (!domain.isEmpty()) {
            int max = Integer.MIN_VALUE;
            int lcvColor = 0;
            for (int color : domain) {
                int nonConflictingColorCount = 0;
                for (Vertex neighbor : neighbors) {
                    List<Integer> neighborsDomain = neighbor.domain;
                    for (Integer k : neighborsDomain) {
                        if (color != k) nonConflictingColorCount++;
                    }
                }
                if (nonConflictingColorCount > max) {
                    max = nonConflictingColorCount;
                    lcvColor = color;
                }
            }
            sortedLCVDomain.add(lcvColor);
            domain.remove((Integer) lcvColor);
        }

        return sortedLCVDomain;
    }

    // getters below
    public List<Vertex> getVertices() {
        return vertices;
    }

    public Map<Vertex, List<Vertex>> getAdjacencyList() {
        return adjacencyList;
    }
}