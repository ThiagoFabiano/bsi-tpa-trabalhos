package dominio;
import java.util.Comparator;

public class ComparatorContatoPorNome implements Comparator<Contato> {
    @Override
    public int compare(Contato c1, Contato c2) {
        return c1.getNome().compareToIgnoreCase(c2.getNome());
    }
}