package graph.practice;

import java.util.*;

public class DijkstraSparseGraph {

    static class Pair {
        int vertex;
        int weight;
        Pair(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
    }

    static class Node {
        int vertex;
        int dist;
        Node(int vertex, int dist) {
            this.vertex = vertex;
            this.dist = dist;
        }
    }

    static class DistanceComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.dist == o2.dist) {
                return 0;
            }
            return o1.dist < o2.dist ? -1 : 1;
        }
    }

    /**
     * TC = O(ELogE + V), SC = O(V+E)
     * @param A
     * @param B
     * @param src
     */
    private static void solve(int A, int[][] B, int src) {
        Map<Integer, Set<Pair>> graph = new HashMap<>();
        for (int i=1;i<=A;i++) {
            graph.put(i, new HashSet<>());
        }
        for (int i=0;i<B.length;i++) {
            int u = B[i][0];
            int v = B[i][1];
            int w = B[i][2];
            graph.get(u).add(new Pair(v, w));
            graph.get(v).add(new Pair(u, w));
        }
        int[] dist = new int[A+1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        boolean[] spt = new boolean[A+1];
        PriorityQueue<Node> heap = new PriorityQueue<>(new DistanceComparator());
        heap.offer(new Node(src, 0));
        while (!heap.isEmpty()) {
            Node node = heap.poll();
            int u = node.vertex;
            if (!spt[u]) {
                spt[u] = true;
                Set<Pair> neighbors = graph.get(u);
                for (Pair pair : neighbors) {
                    int v = pair.vertex;
                    int w = pair.weight;
                    if (!spt[v] && dist[u] + w < dist[v]) {
                        dist[v] = dist[u] + w;
                        heap.offer(new Node(v, dist[v]));
                    }
                }
            }
        }
        for (int i=1;i<=A;i++) {
            System.out.println(src + " - " + i + ": " + dist[i]);
        }
    }

    public static void main(String[] args) {
        int A = 8;
        int[][] B = {
                {1,2,6},
                {2,3,2},
                {3,6,3},
                {5,6,5},
                {4,5,3},
                {1,4,3},
                {2,5,2},
                {6,7,5},
                {7,8,3},
        };
        solve(A,B,5);
    }
}
