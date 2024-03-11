package main;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ColorGraphTest {

    @Test
    void shouldBeValid() throws FileNotFoundException {
        ColorGraph graph = ColorGraph.initGraph("graph.txt");
        assertTrue(graph.colorDfs(graph.getVertices().get(0)));
    }

    @Test
    void shouldBeValid2() throws FileNotFoundException {
        ColorGraph graph = ColorGraph.initGraph("graph2.txt");
        assertTrue(graph.colorDfs(graph.getVertices().get(0)));
    }

    @Test
    void shouldBeInvalid() throws FileNotFoundException {
        ColorGraph graph = ColorGraph.initGraph("graph3.txt");
        assertFalse(graph.colorDfs(graph.getVertices().get(0)));
    }

    @Test
    void shouldBeInvalid2() throws FileNotFoundException {
        ColorGraph graph = ColorGraph.initGraph("graph4.txt");
        assertFalse(graph.colorDfs(graph.getVertices().get(0)));
    }
}