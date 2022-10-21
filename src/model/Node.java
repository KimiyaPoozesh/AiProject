package model;

import core.Constants;

import java.util.ArrayList;
import java.util.Comparator;

public class Node  implements Comparable<Node> {
    Board board;
    Node parent;
    Movement previousMovement;

    public Node (Board board, Node parent, Movement previousMovement) {
        this.parent = parent;
        this.board = board;
        this.previousMovement = previousMovement;
    }

    public ArrayList<Node> successor() {
        ArrayList<Node> result = new ArrayList<Node>();
        result.add(new Node(board.moveLeft(), this, Movement.LEFT));
        result.add(new Node(board.moveRight(), this, Movement.RIGHT));
        result.add(new Node(board.moveDown(), this, Movement.DOWN));
        result.add(new Node(board.moveUp(), this, Movement.UP));
        return result;
    }

    public void drawState() {
        System.out.println("moved to : " + this.previousMovement);
        for (int i = 0; i < board.row; i++) {
            for (int j = 0; j < board.col; j++) {
                System.out.print(Constants.ANSI_BRIGHT_GREEN + board.cells[i][j] + spaceRequired(board.cells[i][j]));
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }

    public boolean isGoal() {
        return board.isGoal();
    }

    public int pathCost() {
        int cost = 0;
        switch (previousMovement) {
            case LEFT:
                cost = 1;
                break;
            case RIGHT:
                cost = 3;
                break;
            case DOWN:
                cost = 5;
                break;
            case UP:
                cost = 7;
                break;
        }
        if (parent == null)
            return cost;
        return cost + parent.pathCost();
    }

    public int heuristic() {
        // TODO: 2/16/2022 implement heuristic function
        return 0;
    }

    public String hash() {
        StringBuilder hash = new StringBuilder();
        hash.append(board.hash()).append("/PM=").append(previousMovement.toString());
        return hash.toString();
    }

    private String spaceRequired(int cell) {
        int length = String.valueOf(cell).length();
        String result = " ".repeat(5 - length);
        result += " ";
        return result;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public int compareTo(Node o) {
        return this.pathCost()>o.pathCost() ?1:-1;
    }
}
