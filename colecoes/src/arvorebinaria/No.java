package arvorebinaria;

public class No<T> {
    private T valor;
    private No<T> esq;
    private No<T> dir;

    public No(T valor) {
        this.valor = valor;
        this.esq = null;
        this.dir = null;
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public No<T> getEsq() {
        return esq;
    }

    public void setEsq(No<T> esq) {
        this.esq = esq;
    }

    public No<T> getDir() {
        return dir;
    }

    public void setDir(No<T> dir) {
        this.dir = dir;
    }
}
