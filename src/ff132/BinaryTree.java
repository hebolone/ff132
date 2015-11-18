package ff132;

import java.util.ArrayList;

public class BinaryTree {
    public BinaryTree() {
        m_Nodes = new ArrayList<>();
    }
    //  *** MEMBERS ***
    private final ArrayList<CNode> m_Nodes;
    //  *** METHODS ***
    public void AddNode(CNode iNode) {
        m_Nodes.add(iNode);
    }
    public boolean IsAlreadyPresent(CNode iNode, CUnit iUnit) {
        //  Go up through ancestors and see if this node is already present
        boolean retValue = false;
        //  Build ancestors line
        ArrayList<CNode> ancestors = new ArrayList<>();
        GetAncestor(ancestors, iNode);
        //  Search inside ancestors if this position is already present
        for(CNode n : ancestors) {
            if(iUnit == n.GetUnit())
                retValue = true;
        }
        return retValue;
    }
    private void GetAncestor(ArrayList<CNode> iAncestors, CNode iCurrentNode) {
        if(iCurrentNode.Ancestor != null) {
            iAncestors.add(iCurrentNode.Ancestor);
            GetAncestor(iAncestors, iCurrentNode.Ancestor);
        }
    }
    public int GetNodesMaxLevel() {
        int retValue = 0;
        for(CNode n : m_Nodes) {
            if(n.Level > retValue)
                retValue = n.Level;
        }
        return retValue;
    }
    public ArrayList<CNode> GetSolution(CNode iNode) {
        ArrayList<CNode> solution = new ArrayList<>();
        ArrayList<CNode> ancestors = new ArrayList<>();
        GetAncestor(ancestors, iNode);
        for(int i = ancestors.size() - 1; i >= 0; i --) {
            solution.add(ancestors.get(i));
        }
        solution.add(iNode);
        return solution;
    }
    public ArrayList<CNode> GetNodesByLevel(int iLevel) {
        ArrayList<CNode> retValue = new ArrayList<>();
        for(CNode n : m_Nodes) {
            if(n.Level == iLevel)
                retValue.add(n);
        }
        return retValue;
    }
}
