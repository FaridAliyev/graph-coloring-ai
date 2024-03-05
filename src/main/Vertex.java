package main;

import java.util.List;
import java.util.Objects;

public class Vertex {
    int id;
    List<Integer> domain;
    int currentColor;

    public Vertex(int id, List<Integer> domain, int currentColor) {
        this.id = id;
        this.domain = domain;
        this.currentColor = currentColor;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", domain=" + domain +
                ", currentColor=" + currentColor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return id == vertex.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
