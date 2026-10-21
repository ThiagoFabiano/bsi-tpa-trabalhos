package arvorebinaria;

import java.util.Comparator;


public class ArvoreBinariaAVL<T> extends ArvoreBinaria<T> {

    public ArvoreBinariaAVL(Comparator<T> comparador) {
        super(comparador);
    }

    @Override
    public boolean adicionar(T novoValor) {
        return false;
    }

    @Override
    public boolean remover(T valor) {
      
        return false;
    }

    private void atualizarAlturaNo(No<T> no){

        int novaAltura = 1 + Math.max(alturaNo(no.getEsq()) , alturaNo(no.getDir()));

        no.setAltura(novaAltura);
    }

    private int alturaNo(No<T> no){

        if (no == null){
            return -1;
        }

        return no.getAltura();
    }

    private int fatorBalanceamento(No<T> no){

        int fb = alturaNo(no.getEsq()) - alturaNo(no.getDir());

        return fb;
    }

    private No<T> rotacaoDireita(No<T> noDesbalanceado){

        No<T> novoNoPai = noDesbalanceado.getEsq();

        noDesbalanceado.setEsq(novoNoPai.getDir());

        novoNoPai.setDir(noDesbalanceado);

        atualizarAlturaNo(noDesbalanceado);
        atualizarAlturaNo(novoNoPai);

        return novoNoPai;
    }

    private No<T> rotacaoEsquerda(No<T> noDesbalanceado){

         No<T> novoNoPai = noDesbalanceado.getDir();

        noDesbalanceado.setDir(novoNoPai.getEsq());

        novoNoPai.setEsq(noDesbalanceado);

        atualizarAlturaNo(noDesbalanceado);
        atualizarAlturaNo(novoNoPai);

        return novoNoPai;
    }

}

  
