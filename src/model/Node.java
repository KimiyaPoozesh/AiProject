package model;

import core.Constants;

import java.util.*;

public class Node implements Comparable<Node> {
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

        if (Board.mode == Constants.MODE_NORMAL) {
            return this.board.goalValue - getSumOfMaximums();
        } else {
            HashMap<Integer, Integer> map = new HashMap<>();

            for (int i = 0; i < this.board.row; i++ ) {
                for (int j = 0; j < this.board.col; j++) {
                    int cell = this.board.cells[i][j];
                    if (cell == 0)
                        continue;
                    Integer mapCell = map.get(cell);
                    if (mapCell == null) {
                        map.put(cell, 1);
                    } else {
                        map.put(cell, mapCell + 1);
                    }
                }
            }

            int sum = getSum(map);
            return sum;
        }
    }

    public int heuristic2() {
        int nonzeros = 0;
        for (int i = 0; i < this.board.row; i++ ) {
            for (int j = 0; j < this.board.col; j++) {
                if (board.cells[i][j] != 0)
                    nonzeros++;
            }
        }
        return nonzeros;
    }

    private int getSum(HashMap<Integer, Integer> map) {
        int sum = 0;
        for (HashMap.Entry<Integer,Integer> entry : map.entrySet()) {
            int value = entry.getValue();

            if (value % 2 == 0) {
                sum += value;
            } else {
                sum += value - 1;
            }
        }
        return sum;
    }

    private int getSumOfMaximums() {
        int currentMax = 0;
        int currentSecondMax = 0;
        for (int i = 0; i < this.board.row; i++ ) {
            for (int j = 0; j < this.board.col; j++) {
                if (this.board.cells[i][j] > currentMax) {
                    currentSecondMax = currentMax;
                    currentMax = this.board.cells[i][j];
                }else if(this.board.cells[i][j] > currentSecondMax) {
                    currentSecondMax = this.board.cells[i][j];
                }
            }
        }
        return currentMax + currentSecondMax;
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

    // normal heuristic
    @Override
    public int compareTo(Node o) {
        return this.heuristic2() + this.pathCost() > o.heuristic2() + o.pathCost() ? 1: -1;
    }

    // advanced heuristic
//    @Override
//    public int compareTo(Node o) {
//        return this.pathCost() + this.heuristic2() > o.pathCost() + o.heuristic2() ? 1: -1;
//    }

    // ucs
//    @Override
//    public int compareTo(Node o) {
//        return this.pathCost() > o.pathCost() ? 1: -1;
//    }

}
