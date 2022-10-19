package ai;

import model.Movement;
import model.Node;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class UCS {

    public void search(Node startNode) {
        ArrayList<Node> frontier = new ArrayList<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        if (startNode.isGoal()) {
            System.out.println("you win!");
            printResult(startNode, 0);
            return;
        }
        frontier.add(startNode);
        inFrontier.put(startNode.hash(), true);
        while (!frontier.isEmpty()) {
            Node temp = frontier.remove(0);
            if (temp.isGoal()) {
                printResult(temp, 0);
                System.out.println("you win !!!");
                return;
            }
            inFrontier.remove(temp.hash());
            ArrayList<Node> children = temp.successor();
            for (Node child : children) {
                if (!(inFrontier.containsKey(child.hash()))) {
                    frontier.add(child);
                    inFrontier.put(child.hash(), true);
                }
            }
            //sort frontier
            for (int i = 0; i < frontier.size() - 1; i++) {
                for (int j = 0; j < frontier.size() - 1 - i; j++) {
                    if (frontier.get(j).pathCost() > frontier.get(j+1).pathCost()) {
                        Node tempN = frontier.get(j);
                        frontier.set(j, frontier.get(j+1));
                        frontier.set(j+1, tempN);
                    }
                }
            }
        }
        System.out.println("no solution");
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
