package lista;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public sealed interface Lista<T> permits Nil, Cons {
    Lista NIL = new Nil();

    T head();
    Lista<T> tail( );
    boolean isEmpty( );

    static <T> Lista<T> of(T h, Lista<T> t) {
        return new Cons<>(h,t);
    }

    static <T> Lista<T> of(T... elems) {
        Lista<T> ret = Lista.NIL;
        for(int i=elems.length-1; i>=0; i--) {
            T h = elems[i];
            ret = Lista.of(h,ret);
        }
        return ret;
    }

    default Lista<T> append(T elem) {
        if(isEmpty()) {
            return Lista.of(elem);
        }
        else {
            return Lista.of( head(), tail().append(elem) );
        }
    }

    default Lista<T> prepend(T elem) {
        return Lista.of( elem, this);
    }

    default Lista<T> remove(T elem) {
        if( isEmpty() ) {
            return NIL;
        }

        if( head().equals(elem) ) {
            return tail();
        }
        else {
            return Lista.of(head(), tail().remove(elem));
        }
    }

    default Lista<T> drop(int n) {
        if(n<=0 || isEmpty()) {
            return this;
        }
        else {
            return tail().drop(n-1);
        }
    }

    default Lista<T> dropWhile(Predicate<T> p) {
        if( isEmpty() ) {
            return NIL;
        }

        if( p.test(head())  ) {
            return tail().dropWhile(p);
        }
        else {
            return this;
        }
    }

    default Lista<T> take(int n) {
        if(n<=0 || isEmpty() ) {
            return NIL;
        }
        else {
            return Lista.of(head(), tail().take(n-1));
        }
    }

    default Lista<T> takeWhile(Predicate<T> p) {
        if( isEmpty() || !p.test(head())) {
            return NIL;
        }

        return Lista.of(head(), tail().takeWhile(p));
    }

    default Lista<T> concat(Lista<T> ls) {
        if(isEmpty()) {
            return ls;
        }

        return Lista.of(head(), tail().concat(ls));
    }

    default Lista<T> replace( T elem, T newElem ) {
        if( isEmpty() ) {
            return Lista.NIL;
        }

        return head().equals(elem)
                ? Lista.of( newElem, tail() )
                : Lista.of( head(), tail().replace(elem, newElem) );
    }

    default Optional<T> contains(T elem) {
        if (isEmpty()) {
            return Optional.empty();
        }
        return head().equals(elem)
                ? Optional.of(head())
                : tail().contains(elem);
    }

    default void forEach(Consumer<T> fn) {
        if( !isEmpty() ) {
            fn.accept( head() );
            tail().forEach(fn);
        }
    }

    default Integer size() {
        if( isEmpty() ) {
            return 0;
        }
        else {
            return 1 + tail().size();
        }
    }
    //--------------------------------
    //Mapeo recursiva
    //this corresponde a T
    default <U> Lista<U> map(Function<T,U> fn){
        return this.isEmpty() ?
                Lista.NIL
                :Lista.of(fn.apply(head()),tail().map(fn));
                //tail().map(fn).prepend(fn.apply(this.head()))
        /*
        if(this.isEmpty()){
            return Lista.NIL;
        }else{
            var tmp=tail().map(fn);
            var newCab=fn.apply(this.head());
            //return Lista.of(newCab,tmp);
            return Lista.of(fn.apply(this.head()),tail().map(fn));
        }*/
    }

    //
    default <U> Lista<U> mapIterativo(Function<T,U> fn){
        var tmp=this;

        Lista<U> retTmp=Lista.NIL;
                while(tmp!=NIL){
                   T elem=tmp.head();
                   U newElem=fn.apply(elem);
                   tmp=tmp.tail();
                   retTmp=retTmp.prepend(newElem);
                }
        return retTmp.invertir();
    }

    default Lista<T> invertir(){
        var tmp=this;
        Lista<T> retTmp=Lista.NIL;
        while(tmp!=NIL){

            retTmp=retTmp.prepend(tmp.head());
            //retTmp=Lista.of(tmp.head(),retTmp);
            tmp=tmp.tail();
        }
        return retTmp;
    }
    //-----------------------------------

    default T reduce(T identity, Function<T, Function<T, T>> fn) {
        T acum = identity;
        var tmp = this;
        while (!tmp.isEmpty()) {
            acum = fn.apply(tmp.head()).apply(acum);
            tmp = tmp.tail();
        }
        return acum;
    }
    //UxT->U
    default <U> U foldLeft(U indentity,Function<U,Function<T,U>> fn){
        // -> 1,2,3,4
        U acc=indentity;
        var tmp=this;
        while (!this.isEmpty()){
            acc=fn.apply(acc).apply(tmp.head());
            tmp=this.tail();
        }
        return acc;
    }
    //TxU->U
    default <U> U foldRight(U identity,Function<T,Function<U,U>> fn){
        //1,2,3,4 <- recursividad
           if(this.isEmpty()){
               return identity;
        }else{
               return fn.apply(this.head()).apply(this.tail().foldRight(identity,fn));
           }
    }

    default Lista<T> invertirL(){
       return this.foldLeft(Lista.NIL,ls->elem->ls.append(elem));
//       if(isEmpty()){
//        return Lista.NIL;
//       }else{
//           //a la
//           return tail().invertirL().append(head());
//       }
    }
    default <U> Lista<U> mapF(Function<T, U> fn) {
        if (isEmpty()) {
            return Lista.NIL;
        } else {
            return Lista.of(fn.apply(head()), tail().map(fn));
        }
    }
}

