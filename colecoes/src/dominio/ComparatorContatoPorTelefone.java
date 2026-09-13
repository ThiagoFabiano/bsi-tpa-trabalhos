package dominio;
import java.util.Comparator;

public class ComparatorContatoPorTelefone implements Comparator<Contato> {
    @Override
    public int compare(Contato c1, Contato c2) {
        return c1.getTelefone().compareTo(c2.getTelefone());
    }
}