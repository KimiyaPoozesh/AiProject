package ai;

import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;

public class IDAS {

    private static int cutoff, min;
    private static Stack<Node> frontier;
    private static Hashtable<String, Boolean> flagdude;
    private static Node goal;

    public void ida_star(Node startNode) {
        cutoff = startNode.pathCost() + startNode.heuristic2();
        int t = 0;
        while (t != Integer.MAX_VALUE) {
            frontier = new Stack<>();
            frontier.add(startNode);
            flagdude = new Hashtable<>();
            flagdude.put(startNode.hash(), true);
            min = Integer.MAX_VALUE;
            t = search(cutoff);
            if (t == 0){
                printResult(goal,0);
                break;
            }
            cutoff = t;
        }
    }

    public int search(int cutoff) {
        while (!frontier.isEmpty()) {
            Node current = frontier.pop();
            int f = current.pathCost() + current.heuristic2();
            if (f > cutoff) {
                if (f < min) {
                    min = f;
                    if (current.isGoal()) {
                        goal = current;
                        return 0;
                    }

                }
                continue;
            }
            if (current.isGoal()) {
                goal = current;
                return 0;
            }
            ArrayList<Node> children = current.successor();
            for (Node child : children) {
                if (!flagdude.containsKey(child.hash())) {
                    frontier.push(child);
                    flagdude.put(child.hash(), true);
                }
            }
        }
        return min;
    }

    public void printResult(Node node, int depthCounter) {
        if (node.getParent() == null) {
            System.out.println("problem solved at a depth of  : " + depthCounter);
            return;
        }
        System.out.println(node.toString());
        node.drawState();
        printResult(node.getParent(), depthCounter + 1);
    }
}
