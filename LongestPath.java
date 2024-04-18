import java.util.*;

class Vertex {
    long id;

    public Vertex(long id) {
        this.id = id;
    }
}

class Edge {
    Vertex from;
    Vertex to;
    int weight;

    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

public class LongestPath {
    private Map<Vertex, List<Edge>> graph;

    public LongestPath(Map<Vertex, List<Edge>> graph) {
        this.graph = graph;
    }

    public void longestPath(Vertex start) {
        int V = graph.size();
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MIN_VALUE);
        
        Map<Vertex, Integer> vertexIndexMap = new HashMap<>();
        List<Vertex> vertices = new ArrayList<>(graph.keySet());
        for (int i = 0; i < V; i++) {
            vertexIndexMap.put(vertices.get(i), i);
        }

        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        int startIndex = vertexIndexMap.get(start);
        dist[startIndex] = 0;

        while (!stack.isEmpty()) {
            int u = stack.pop();

            if (dist[u] != Integer.MIN_VALUE) {
                for (Edge edge : graph.getOrDefault(vertices.get(u), new ArrayList<>())) {
                    int v = vertexIndexMap.get(edge.to);
                    if (dist[v] < dist[u] + edge.weight) {
                        dist[v] = dist[u] + edge.weight;
                    }
                }
            }
        }

        // Print the longest distances
        for (int i = 0; i < V; i++) {
            if (dist[i] == Integer.MIN_VALUE) {
                System.out.print("Infinity ");
            } else {
                System.out.print(dist[i] + " ");
            }
        }
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;
        for (Edge edge : graph.getOrDefault(new Vertex(v), new ArrayList<>())) {
            int toIndex = (int) edge.to.id;
            if (!visited[toIndex]) {
                topologicalSortUtil(toIndex, visited, stack);
            }
        }
        stack.push(v);
    }

    public static void main(String[] args) {
        // Example usage
        Vertex v1 = new Vertex(0);
        Vertex v2 = new Vertex(1);
        Vertex v3 = new Vertex(2);
        Vertex v4 = new Vertex(3);
        Vertex v5 = new Vertex(4);
        Vertex v6 = new Vertex(5);

        Edge e1 = new Edge(v1, v2, 5);
        Edge e2 = new Edge(v1, v3, 3);
        Edge e3 = new Edge(v2, v4, 6);
        Edge e4 = new Edge(v2, v3, 2);
        Edge e5 = new Edge(v3, v5, 4);
        Edge e6 = new Edge(v4, v5, 2);
        Edge e7 = new Edge(v4, v6, 7);
        Edge e8 = new Edge(v5, v6, 1);

        Map<Vertex, List<Edge>> graph = new HashMap<>();
        graph.put(v1, Arrays.asList(e1, e2));
        graph.put(v2, Arrays.asList(e3, e4));
        graph.put(v3, Collections.singletonList(e5));
        graph.put(v4, Arrays.asList(e6, e7));
        graph.put(v5, Collections.singletonList(e8));
        graph.put(v6, new ArrayList<>());

        LongestPath solver = new LongestPath(graph);
        System.out.println("Longest path from vetex 1: ");
        solver.longestPath(v1);
    }
}
