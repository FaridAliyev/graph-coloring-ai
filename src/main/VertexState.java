package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VertexState {
    private final Map<Integer, List<Integer>> domainMap = new HashMap<>();
    private final Map<Integer, Integer> colorMap = new HashMap<>();

    public void setDomain(int vertexId, List<Integer> domain) {
        domainMap.put(vertexId, domain);
    }

    public List<Integer> getDomain(int vertexId) {
        return domainMap.get(vertexId);
    }

    public void setColor(int vertexId, int color) {
        colorMap.put(vertexId, color);
    }

    public int getColor(int vertexId) {
        return colorMap.getOrDefault(vertexId, 0);
    }
}
