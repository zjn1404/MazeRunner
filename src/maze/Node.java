package maze;

public class Node<Type1, Type2> {
    Pair<Type1, Type2> coordinate;
    Pair<Type1, Type2> parent;

    public Node() {
        coordinate = new Pair<>();
        parent = new Pair<>();
    }

    public Node(Type1 first, Type2 second, Type1 parentFirst, Type2 parentSecond) {
        coordinate = new Pair<>(first, second);
        parent = new Pair<>(parentFirst, parentSecond);
    }

    public void setCoordinate(Type1 first, Type2 second) {
        coordinate.setFirst(first);
        coordinate.setSecond(second);
    }

    public void setParent(Type1 first, Type2 second) {
        parent.setFirst(first);
        parent.setSecond(second);
    }

    public final Pair<Type1, Type2> getCoordinate() {
        return this.coordinate;
    }

    public final Pair<Type1, Type2> getParent() {
        return this.parent;
    }
}
