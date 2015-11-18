package ff132;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author simone
 */
public class CClock {
    public CClock() {
        m_List = new ArrayList<>();
    }
    //  *** MEMBERS ***
    private final ArrayList<CUnit> m_List;
    private int m_MaxValue =  0;
    //  *** METHODS ***
    public void AddUnit(int iValue) {
        CUnit unit = new CUnit();
        unit.Value = iValue;
        m_List.add(unit);
    }
    public void Print() {
        //  Print to screen this clock
        Iterator<CUnit> myEnum = m_List.iterator();
        while(myEnum.hasNext()) {
            System.out.print(myEnum.next().Value + " - ");
        }
        System.out.println();
    }
    public void SearchSolution() {
        m_MaxValue = GetMaxValue();
        ArrayList<Integer> startingPoints = GetStartingPoints();
        if(startingPoints.isEmpty()) {
            //  If I have no starting point, I use all of the clock values as a starting point
            for(int i = 0; i < m_List.size(); i ++) {
                startingPoints.add(i);
            }
        }
        //  Now for every starting point, i have to search a solution
        Iterator<Integer> myEnum = startingPoints.iterator();
        
        while(myEnum.hasNext()) {
            int sp = myEnum.next();
            BinaryTree bt = new BinaryTree();
            // and now take any path is possible :-)
            BuildPath(bt, sp, null);
            //  I have a binary tree, let's search for a real solution
            int maxLevel = bt.GetNodesMaxLevel();
            System.out.println("Starting from position: " + sp);
            if(maxLevel == m_List.size() -1) {
                ArrayList<CNode> terminalNodes = bt.GetNodesByLevel(maxLevel);
                System.out.println(terminalNodes.size() + " solutions found.");
                Iterator<CNode> myEnumNode = terminalNodes.iterator();
                while(myEnumNode.hasNext()) {
                    ArrayList<CNode> solution = bt.GetSolution(myEnumNode.next());
                    for(CNode n : solution) {
                        System.out.print(GetPosition((CUnit) n.GetUnit()) + " -> ");
                    }
                    System.out.println("END");
                }
            } else {
                System.out.println("No solutions found.");
            }
        }
    }
    private int GetMaxValue() {
        int maxValue = 0;
        Iterator<CUnit> myEnum = m_List.iterator();
        while(myEnum.hasNext()) {
            int currentValue = myEnum.next().Value;
            if(currentValue > maxValue) {
                maxValue = currentValue;
            }
        }
        return maxValue;
    }
    private ArrayList<Integer> GetStartingPoints() {
        //  This function catches all starting points
        ArrayList<Integer> retValue = new ArrayList<>();
        //  For each number in clock...
        int counter = 0;
        while(counter < m_List.size()) {
            int unitValue = m_List.get(counter).Value;
            //  Check for clockwise and counterclockwise
            boolean c_sp = true;
            for(int i = 1; i <= m_MaxValue; i ++) {
                int v_c = GetUnit(counter, i).Value;
                int v_cc = GetUnit(counter, - i).Value;
                if(i == v_c || i == v_cc) {
                    //  This is not a starting point
                    c_sp = false;
                }
            }
            if(c_sp) {
                System.out.println("Position: " + counter + ", Value: " + unitValue + " is a starting point.");
                retValue.add(counter);
            }
            counter ++;
        }
        return retValue;
    }
    private CUnit GetUnit(int iStart, int iValue) {
        //  iValue is the direction from which you have to move
        //  Positive: clockwise
        //  Negative: counterclockwise
        return m_List.get(CalculateMovement(iStart, iValue));
    }
    private int CalculateMovement(int iStart, int iValue) {
        int resultIndex = iStart + iValue;
        while(resultIndex < 0 || resultIndex >= m_List.size()) {
            if(resultIndex < 0)
                resultIndex += m_List.size();
            else
                resultIndex -= m_List.size();
        }
        return resultIndex;
    }
    private int GetPosition(CUnit iUnit) {
        return m_List.indexOf(iUnit);
    }
    private void BuildPath(BinaryTree iBT, int iCurrentPosition, CNode iAncestor) {
        //  Starting from current unit, I add clockwise and counterclockwise node to binary tree
        CUnit currentUnit = m_List.get(iCurrentPosition);
        CNode<CUnit> node = new CNode(currentUnit);
        node.Ancestor = iAncestor;
        if(iAncestor != null)
            node.Level = iAncestor.Level + 1;
        if(! iBT.IsAlreadyPresent(node, currentUnit))
            iBT.AddNode(node);

        //  Check if I can add his children
        CUnit unitCW = GetUnit(iCurrentPosition, currentUnit.Value);
        CUnit unitCCW = GetUnit(iCurrentPosition, - currentUnit.Value);
        //  Check if CW and CCW are the same unit
        boolean destinationDuplicated = false;
        if(unitCW == unitCCW) {
            destinationDuplicated = true;
        }
        
        if(! iBT.IsAlreadyPresent(node, unitCW)) {
            BuildPath(iBT, CalculateMovement(iCurrentPosition, currentUnit.Value), node);
        }
        if(! iBT.IsAlreadyPresent(node, unitCCW) && ! destinationDuplicated) {
            BuildPath(iBT, CalculateMovement(iCurrentPosition, - currentUnit.Value), node);
        }
    }
}
