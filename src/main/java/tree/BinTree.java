package tree;

import lista.Lista;

import java.util.function.Consumer;

public sealed interface BinTree<T> permits ConsBinTree, LeafBinTree {

    BinTree Leaf = new LeafBinTree();

    T value();
    BinTree<T> left();
    BinTree<T> right();

    static <T> BinTree<T> of(T value, BinTree<T> left, BinTree<T> right) {
        return new ConsBinTree<>(value,left,right);
    }

    static <T> BinTree<T> of(T value) {
        return new ConsBinTree<>(value);
    }

    Integer size();

    void print(int level);

    static <T> BinTree<T> buildTree(Lista<T> ls) {
        if( ls.isEmpty() ) {
            return BinTree.Leaf;
        }
        else {
            var h = ls.head();
            var t = ls.tail();

            int k = t.size() / 2;

            var leftList = t.take(k);
            var rightList = t.drop(k);

            return BinTree.of(h, buildTree(leftList), buildTree(rightList));
        }
    }
    default void forEachPost(Consumer<T> fn ){
        if (this!=BinTree.Leaf){
            left().forEachPost(fn);
            right().forEachPost(fn);
            fn.accept(this.value());
        }
    }
    default void forEachPre(Consumer<T> fn ){
        if (this!=BinTree.Leaf){
            fn.accept(this.value());
            left().forEachPre(fn);
            right().forEachPre(fn);

        }
    }
    default void forEachIn(Consumer<T> fn ){
        if (this!=BinTree.Leaf){
            left().forEachIn(fn);
            fn.accept(this.value());
            right().forEachIn(fn);
        }
    }
}
