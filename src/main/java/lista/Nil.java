package lista;

//--nodo final
final class Nil<T> implements Lista<T> {
    Nil() {
    }

    @Override
    public T head() {
        return null;
    }

    @Override
    public Lista<T> tail() {
        return null;
    }

    public boolean isEmpty( ) {
        return true;
    }

    @Override
    public String toString() {
        return "NIL";
    }
}
