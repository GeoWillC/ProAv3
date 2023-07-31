package main;

import lista.Lista;
import tree.BinTree;

public class Main {
    public static void main(String[] args) {
        BinTree n3 = BinTree.of(3.0);
        BinTree n5 = BinTree.of(5.0);
        BinTree n2 = BinTree.of(2.0);
        BinTree n4 = BinTree.of(4.0);
        BinTree n8 = BinTree.of(8.0);
        BinTree oMul = BinTree.of("*", n3, n5);
        BinTree oSum1 = BinTree.of("+", oMul, n2);
        BinTree oDiv = BinTree.of("/", n8, n4);
        BinTree oSum2 = BinTree.of("+", oSum1, oDiv);
        var tree = oSum2;
        //Operar
        System.out.println(tree);
        System.out.println("Resultado: " + simple(tree));
        System.out.println("POST-ORDER " + postorder(tree));
        System.out.println("PRE-ORDER " + preorder(tree));
        System.out.println("IN-ORDER " + inorder(tree));
        tree.print(0);
    }


    //Metodo que opere
    static <T> Double simple(BinTree<T> tree) {
        if (tree == BinTree.Leaf) {
            return 0.0;
        }else{
            var i = tree.left();
            var d = tree.right();
            if (tree.value() == "+") {
                return simple(i) + simple(d);
            }
            if (tree.value() == "-") {
                return simple(i) - simple(d);
            }
            if (tree.value() == "*") {
                return simple(i) * simple(d);
            }
            if (tree.value() == "/") {
                return simple(i) / simple(d);
            } else {
                return (Double) tree.value();
            }
        }
    }
    static <T> Lista<T> inorder(BinTree<T> tree) {
        if (tree == BinTree.Leaf) {
            return Lista.NIL;
        } else {
            //2
            var l = inorder(tree.left());
            //5,9
            var r = inorder(tree.right());
            var tmp = l.append(tree.value()).concat(r);
            return tmp;
        }
    }

    //preorder 1,2,5,9
    static <T> Lista<T> preorder(BinTree<T> tree) {
        if (tree == BinTree.Leaf) {
            return Lista.NIL;
        } else {
            //5,9
            var r = preorder(tree.right());
            //2
            var l = preorder(tree.left());
            var tmp = Lista.of(tree.value(), l).concat(r);
            return tmp;
        }
    }

    //2,9,5,1
    static <T> Lista<T> postorder(BinTree<T> tree) {
        if (tree == BinTree.Leaf) {
            return Lista.NIL;
        } else {
            //5,9
            var r = postorder(tree.right());
            //2
            var l = postorder(tree.left());
            var tmp = l.concat(r).append(tree.value());
            return tmp;
        }
    }
}