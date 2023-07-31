package main;

import executable.Executable;
import lista.Lista;

import java.util.*;
import java.util.function.Function;

public class OperacionesListas {
    //GENERICO CORRECURSION
    static <T, U> List<U> map(List<T> ls, Function<T, U> fn) {
        //Vector de arreglo, que ajusta el tamaño de vector
        //Reajustar en cada insercion
        List<U> ret = new ArrayList<>();
        //Lista enlazada, tiene unido el final e inicio unido , para  no recorrer la lista
        //Veloz al insertar ya que reajusta los enlaces, prepend y append son iguales
        //En el medio tiene problemas para insertar
        var ll = new LinkedList<U>();
        for (T t : ls) {
            ret.add(fn.apply(t));
        }
        return ret;
    }


    //TxT en T dos funcioines
    /* Antiguo
    static <T,U> T reduce(List<T> ls,Function<T,Function<T,T>> fn){
        //Chequear los errores como lista vacia o un solo elemento;
        T acum=ls.get(0);
        for (int i=1;i<ls.size();i++) {
            //izquierda derecha acum+elem
            T elem=ls.get(i);
            acum= fn.apply(acum).apply(elem);
        }
        return acum;
    }
*/

    //folding
    static Integer fold(List<Integer> ls, Integer identity, Function<Integer, Function<Integer, Integer>> fn) {
       /*  1,2,3
       izquierda derecha UXT->U
        fold = (((0+1)+2)+3)+4
        ls=1,2,3,4
        vi=0
        f(x,y)= x+y
        1. f(0,1)=0+1=1
        2. f(1,2)=1+2=3
        3. f(3,3)=3+3=6
        4. f(6,4)=6+4=10
        */
        Integer acum = identity;
        //Correcursivo
        for (Integer i : ls) {
            System.out.printf("%d, %d\n", acum, i);
            acum = fn.apply(acum).apply(i);
        }
        return acum;
    }

    static String foldString(List<Integer> ls, String identity, Function<String, Function<Integer, String>> fn) {
       /*  1,2,3
       izquierda derecha
        f:UXT->U, f(s,i) ==> s.append(i.toString())
        concat(s,i.toString())
        ls=1,2,3,4
        fold ="1234"
        1. f("",1)= ""+1="1"
        2. f("1",2)="1"+2="12"
        3. f("12",3)="12"+3="123"
        4. f("123",4) "123"+4="1234"
        identity=""
        */
        String acum = identity;
        //Correcursivo
        for (Integer i : ls) {

            acum = fn.apply(acum).apply(i);
        }
        return acum;
    }
    //Fold i-d generico

    //TXT->T
    static <T> T reduce(T identity, List<T> ls, Function<T, Function<T, T>> fn) {
        T acum = identity;
        for (T elem : ls) {
            //acum + first value of the list
            acum = fn.apply(acum).apply(elem);
        }
        return acum;

    }

    static <T, U> U foldLeft(List<T> ls, U identity, Function<U, Function<T, U>> fn) {
       /*
        f:UXT->U, f(s,i) ==> s.append(i.toString())
        identity=""
        */
        U acum = identity;
        //Correcursivo
        for (T i : ls) {
            //fn.aplly(U).apply(T)
            acum = fn.apply(acum).apply(i);
        }
        return acum;
    }
    //fold d-i
    /*
    static <T,U> U foldLeft(List<T> ls,U identity,Function<U,Function<T,U>> fn){
        U acum=identity;
            //fn.aplly(U).apply(T)
            acum=fn.apply(acum).apply((U)acum);

        return acum;
    }
    */

    public static void test(String[] args) {
        List<Integer> ls = List.of(1, 2, 3, 4);
        //La funcion es implicita en el lambda
        //La ventaja es la reutilidad del codigo
        //Funciona con cualquier lista
        var ret = map(ls, x -> x * 1.2);
        System.out.println(ret);

        //------------------
        var list = Lista.of(1, 2, 3, 4);
        var ret2 = list.map(x -> x * 1.2);
        System.out.println("Lista h,t: " + ret2);

        //-------------------------------
        var ret3 = list.mapIterativo(x -> x * 1.2);
        System.out.println("Map Iterativo: " + ret3);

        //--------------------------------
        //Integer suma=reduce(ls,x->y-> x+y);
        Integer suma = reduce(0, ls, x -> y -> x + y);
        System.out.println("Reduce suma:" + suma);
        //--------------------------------
        Integer suma2 = fold(ls, 0, x -> y -> x + y);
        System.out.println("Suma fold:" + suma2);
        //--------------------------------
        String concat = foldString(ls, "", str -> x -> str.concat(x.toString()));
        System.out.println("Concat fold:" + concat);

        Integer suma3 = foldLeft(ls, 0, x -> y -> x + y);
        System.out.println("Suma foldLeft:" + suma3);

        String concat2 = foldLeft(ls, "", str -> x -> str.concat(x.toString()));
        System.out.println("Concat foldLeft:" + concat);

        var ret1 = foldLeft(ls, "0", str -> x -> String.format("(%s + %d)", str, x));
        System.out.println("Fold l-r format:" + ret1);


        //Entrada: 1,2,3,4 salida: {"1","2","3"}
        //fn lista string xinteger string
        //
        List<String> in = new ArrayList<>();
        //Funcion uxt->u
        Function<List<String>, Function<Integer, List<String>>> fn = lis -> t -> {
            String tmp = "s" + t.toString();
            lis.add(tmp);
            return lis;
        };
        Function<List<String>, Function<Integer, List<String>>> fn2 = lis -> t -> {
            lis.add(t.toString());
            return lis;
        };

        var salida = foldLeft(ls, in, fn);
        System.out.println("Fold l-r format:" + salida);

        List<Integer> values = List.of(1, 2, 3, 4, 5, 5, 4, 3, 2, 1);
        Map<Integer, Integer> v1 = new HashMap<>();
        var counts = foldLeft(values, v1, map -> t -> {
            if (map.containsKey(t)) {
                var cc = map.get(t);
                map.put(t, cc + 1);
            } else {
                map.put(t, 1);
            }
            return map;
        });
        /*
        Folding
        UXT-> U
        U:map i,i
        T: elem
        vi=count map
         */
        Function<Map<Integer, Integer>, Function<Integer, Map<Integer, Integer>>> fn3 = map -> t -> {
            if (map.containsKey(t)) {
                var cc = map.get(t);
                map.put(t, cc + 1);
            } else {
                map.put(t, 1);
            }
            return map;
        };
        System.out.println(v1);
    }


    public static void mapLeftyRight(String[] args) {
        List<Integer> lista = List.of(1, 2, 3, 4);
        Lista ls = Lista.of(1, 2, 3, 4);
        Lista ls2 = Lista.of(1, 2, 3, 4);
        //System.out.println(ls.invertir());
        //System.out.println(ls.invertirL());
        //Map left to right
        //fn: U X T -> U // Lista x Map(fn)-Lista

        //System.out.println("Lista original: " + ls);

//        Function<Integer,Double> fn2= x->x*0.2;
//        System.out.println(map(lista,fn2));
//        System.out.println(ls.map(fn2));
        Function<Lista<Double>, Function<Integer, Lista<Double>>> fn = list -> t -> {
            Function<Integer, Double> mapeo = x -> x * 0.2;
            return list.append(mapeo.apply(t));
        };
        //
        var mapl = ls.foldLeft(Lista.NIL, fn);
        System.out.println("Mapeo: " + mapl);

        Function<Lista<Double>, Function<Integer, Lista<Double>>> fn2 = list -> t -> {
            Function<Integer, Double> map = x -> x * 0.2;
            return list.prepend(map.apply(t));
        };
        Function<Lista<Double>, Function<Integer, Lista<Double>>> fn3 = list -> t -> list.prepend(t * 0.2);

        var mapr = ls2.foldLeft(Lista.NIL, fn3);
        System.out.println("Mapeo: " + mapr);
    }

    public static void main(String[] args) {
        //Construccion
        Lista ls = Lista.of(1, 2, 3, 4, 5);

        Executable neutro = () -> {
        };
        Executable e1 = () -> {
            neutro.exec();
            System.out.println(1);
        };
        Executable ex2 = () -> {
            e1.exec();
            System.out.println(2);
        };
        Executable ex3 = () -> {
            ex2.exec();
            System.out.println(3);
        };
        Executable ex4 = () -> {
            ex3.exec();
            System.out.println(4);
        };
        Executable ex5 = () -> {
            ex4.exec();
            System.out.println(5);
        };
        System.out.println("Execucion en cadena");
        ex5.exec();
        System.out.println("Chain reaction");
        Function<Executable, Function<Integer, Executable>> fn = ex -> elem ->()-> {
        //Function<Executable, Function<Integer, Executable>> fn = ex -> elem -> {
          //  return ()-> System.out.println(elem);
                ex.exec();
                printelem(elem);
            };
        Executable exe = (Executable) ls.foldLeft(neutro, fn);
        exe.exec();
    }

    static <T> void printelem(T elem) {
        System.out.println(elem);
    }
}



