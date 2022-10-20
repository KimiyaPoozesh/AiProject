package ai;
import model.Node;
import java.util.ArrayList;

public class IDS {

    public void IDFS(Node startNode){
        for (int depth =0 ; depth<50 ; depth++){
            Node found = DFS(startNode,depth);
            if (found!=null){
                printResult(found,0);
                break;
            }
        }
    }

    public Node DFS(Node startNode, int depth){
        if (depth==0 && startNode.isGoal()){
            return startNode;
        }
        if(depth>0){
            ArrayList<Node> children = startNode.successor();
            for (Node child : children) {
                Node found = DFS(child,depth-1);
                if (found!=null){
                    return found;
                }
            }
        }
        return null;
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



