package main;

import lista.Lista;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main2 {
    public static void main(String[] args) {
        //Entero
        var lista = List.of(1, 2, 3, 4);
        //Integer to Double
        Function<Integer, Double> fn = x -> x * 0.2;

        //Agregar
        Function<Integer,Integer> red= x-> x*2;

        System.out.println("Entero "+mapEntero(lista, fn));
        System.out.println("Generico "+mapGenerico(lista, fn));
        //Generico
        //Stream
        System.out.println("Reduce "+ reduce(0,lista));
        System.out.println("Reduce Generico "+ reduceGen(lista,red));
        /*
        var mapeado=lista.stream().map(fn).collect(Collectors.toList());
        mapeado.forEach(System.out::println);
        */
    }

    static <T, U> List<U> mapGenerico(List<T> lista, Function<T,U> fn) {
        var ls=lista;
        var list = new ArrayList<U>();
        for (T l:ls) {
            list.add(fn.apply(l));
        }
        return list;
    }

    //Map List
    static <Integer, Double> List<Double> mapEntero(List<Integer> lista, Function<Integer,Double> fn) {
        var ls=lista;
        var listaDouble = new ArrayList<Double>();
        for (Integer a : lista) {
            listaDouble.add(fn.apply(a));
        }
        return listaDouble;
    }
    //Reduce Enteros
    static Integer reduce(Integer neutro,List<Integer> lista){
        //Suma identidad
        Integer valor= neutro;
        for (Integer value: lista) {
            valor+=value;
        }
        return valor;
    }
    static <T> Integer reduceGen(List<T> lista,Function<T,T> fn){
        //Suma identidad
        //Valor inicial
        Integer ret = 0;
        for (T value: lista) {
            ret=((Integer) fn.apply(value))+ret;
        }
        return ret;
    }
}
