package ff132;

/**
 *
 * @author Simone
 * @param <T>
 */
public class CNode<T> {
    public CNode(T iT) {
        //m_T = iT;
        this(iT, TNodeType.UNDEFINED);
    }
    public CNode(T iT, TNodeType iNodeType) {
        m_T = iT;
        NodeType = iNodeType;
    }
    //  *** MEMBERS ***
    public CNode Ancestor;
    public int Level = 0;
    public TNodeType NodeType;
    private final T m_T;
    //  *** METHODS ***
    public T GetUnit() {
        return m_T;
    }
}
