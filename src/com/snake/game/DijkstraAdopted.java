package com.snake.game;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraAdopted {

    private static class NodeComparator implements Comparator<DijkstraAdopted.Node> {
        public int compare(DijkstraAdopted.Node n1, DijkstraAdopted.Node n2) {
            if (n1.value > n2.value)
                return 1;
            else if (n1.value < n2.value)
                return -1;
            return 0;
        }
    }

    private static class Node {
        public int x;
        public int y;
        public int value;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        public Node setValue(int value) {
            this.value = value;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x &&
                    y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", value=" + value +
                    '}';
        }
    }

    private static class SquareGrid {

        private int width;
        private int height;
        private Map<Node, Integer> weights = new HashMap<>();

        public SquareGrid(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void addWeight(Node node, int weight) {
            weights.put(node, weight);
        }

        private boolean inBounds(int x, int y) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }

        private List<Node> neighbors(int x, int y) {
            List<Node> results = new ArrayList<>();

            results.add(new Node(x+1,y));
            results.add(new Node(x,y-1));
            results.add(new Node(x-1,y));
            results.add(new Node(x,y+1));

            return results.
                    stream().
                    filter(node -> inBounds(node.x, node.y)).
                    collect(Collectors.toList());
        }

        private int cost(Node from, Node to) {
            return weights.getOrDefault(to, 1);
        }
    }

    public boolean search(SquareGrid grid, Node start, Node goal) {
        Queue<Node> frontier = new
                PriorityQueue<>(
                grid.height*grid.width,
                new NodeComparator()
        );
        frontier.add(start.setValue(0));

        Map<Node, Integer> A = new HashMap<>(); // cost so far
        Map<Node, Node> B = new HashMap<>(); // came from

        // initial cost of the source is 0
        A.put(start, 0);

        // We did not come to the source from anywhere
        B.put(start, null);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (current.equals(goal)) {
                break;
            }

            for (Node next: grid.neighbors(current.x, current.y)) {
                int newCost = A.get(current) + grid.cost(current, next);
                if (!A.containsKey(next) || newCost < A.get(next)) {
                    A.put(next, newCost);
                    frontier.add(next.setValue(newCost));
                    B.put(next, current);
                }
            }

        }
        System.out.println(A);

        return true;
    }

    public static void main(String[] args) {
        DijkstraAdopted dijkstra = new DijkstraAdopted();
        SquareGrid grid = new SquareGrid(10, 10);
        grid.addWeight(new Node(3,4), 5);
        grid.addWeight(new Node(3,5), 5);
        grid.addWeight(new Node(4,1), 5);
        grid.addWeight(new Node(4,2), 5);
        grid.addWeight(new Node(4,3), 5);
        grid.addWeight(new Node(4,4), 5);
        grid.addWeight(new Node(4,5), 5);
        grid.addWeight(new Node(4,6), 5);
        grid.addWeight(new Node(4,7), 5);
        grid.addWeight(new Node(4,8), 5);
        grid.addWeight(new Node(5,1), 5);
        grid.addWeight(new Node(5,2), 5);
        grid.addWeight(new Node(5,3), 5);
        grid.addWeight(new Node(5,4), 5);
        grid.addWeight(new Node(5,5), 5);
        grid.addWeight(new Node(5,6), 5);
        grid.addWeight(new Node(5,7), 5);
        grid.addWeight(new Node(5,8), 5);
        grid.addWeight(new Node(6,2), 5);
        grid.addWeight(new Node(6,3), 5);
        grid.addWeight(new Node(6,4), 5);
        grid.addWeight(new Node(6,5), 5);
        grid.addWeight(new Node(6,6), 5);
        grid.addWeight(new Node(6,7), 5);
        grid.addWeight(new Node(7,3), 5);
        grid.addWeight(new Node(7,4), 5);
        grid.addWeight(new Node(7,5), 5);

        boolean res = dijkstra.search(
                grid,
                new Node(1,4),
                new Node(7,8)
        );
    }
}
