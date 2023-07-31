package tree;

final class LeafBinTree<T> implements BinTree<T> {

    @Override
    public T value() {
        return null;
    }

    @Override
    public BinTree<T> left() {
        return null;
    }

    @Override
    public BinTree<T> right() {
        return null;
    }

    @Override
    public String toString() {
        return "Leaf";
    }

    @Override
    public Integer size() {
        return 0;
    }

    public void print(int level) {

        String tab = "-".repeat(level+1);
        //System.out.print(tab);
        //System.out.print("Leaf");
    }
}
