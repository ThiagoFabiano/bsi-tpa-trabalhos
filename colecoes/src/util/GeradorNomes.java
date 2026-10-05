package util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

// Sorteia nomes no formato "Nome Sobrenome Sobrenome" sem repetir nenhum.
// A árvore não aceita chaves repetidas, então nomes repetidos ficariam fora da árvore de nomes.
// Listas adaptadas do gerador do professor (sem acentos e sem sobrenomes repetidos).
// São 111 nomes x 95 x 94 sobrenomes = 991.230 combinações, bem mais que os 400.000 contatos.
public class GeradorNomes {

    private static final String[] NOMES = {
        "Ana", "Bruno", "Carlos", "Daniela", "Eduardo", "Fernanda", "Gabriel", "Helena", "Isabela", "Joao",
        "Juliana", "Lucas", "Mariana", "Nathan", "Olivia", "Paulo", "Quesia", "Rafael", "Sofia", "Thiago",
        "Victor", "William", "Xavier", "Yasmin", "Zuleica", "Alfredo", "Beatriz", "Caio", "Denise", "Eliana",
        "Felipe", "Gustavo", "Heitor", "Igor", "Jessica", "Kevin", "Larissa", "Mateus", "Natalia", "Otavio",
        "Patricia", "Renato", "Sandra", "Tadeu", "Ursula", "Vinicius", "Wellington", "Zilda", "Adriana", "Benicio",
        "Cristina", "Davi", "Emanuel", "Flavia", "Geraldo", "Heloisa", "Icaro", "Jaqueline", "Leonardo", "Marta",
        "Nelson", "Orlando", "Priscila", "Raquel", "Saulo", "Tatiane", "Ubirajara", "Vera", "Wesley", "Zenaide",
        "Alice", "Brenda", "Caetano", "Danilo", "Enzo", "Fabiana", "Gilberto", "Henrique", "Isadora", "Jose",
        "Katia", "Lorena", "Mauricio", "Natanael", "Osvaldo", "Pamela", "Regina", "Sandro", "Tania", "Ulisses",
        "Vania", "Wilson", "Yago", "Zelia", "Amelia", "Bernardo", "Celso", "Dulce", "Edson", "Fatima", "Gilmar",
        "Humberto", "Irene", "Jorge", "Kleber", "Luciana", "Marcelo", "Nadir", "Otacilio", "Paula", "Renata"
    };

    private static final String[] SOBRENOMES = {
        "Almeida", "Barbosa", "Campos", "Dias", "Evangelista", "Ferreira", "Gomes", "Henrique", "Iglesias", "Junqueira",
        "Klein", "Lima", "Medeiros", "Nascimento", "Oliveira", "Pereira", "Queiroz", "Rodrigues", "Silva", "Teixeira",
        "Uchoa", "Vasconcelos", "Watanabe", "Ximenes", "Yamamoto", "Zanetti", "Araujo", "Borges", "Coelho", "Dantas",
        "Esteves", "Farias", "Guimaraes", "Holanda", "Ivo", "Jardim", "Krieger", "Lacerda", "Monteiro", "Neves",
        "Porto", "Quintana", "Ramos", "Sanches", "Torrico", "Urbano", "Vieira", "Wanderley", "Xavier",
        "Yunes", "Zampieri", "Abreu", "Barreto", "Coutinho", "Delgado", "Elias", "Franca", "Godoy", "Haddad",
        "Ibrahim", "Jacob", "Lopes", "Moura", "Nogueira", "Ortega", "Pinto", "Quaresma", "Reis", "Souto",
        "Torres", "Ubaldo", "Valente", "Weber", "Yamaguchi", "Zanella", "Alvarenga", "Bittencourt", "Carvalho",
        "Duarte", "Espindola", "Freitas", "Goncalves", "Herrera", "Ishikawa", "Mancini", "Noronha",
        "Orsini", "Paz", "Quevedo", "Rangel", "Souza", "Tavares", "Vilela", "Werneck", "Xisto"
    };

    private final Random sorteio = new Random();
    private final Set<String> usados = new HashSet<>();

    // Sorteia um nome que ainda não foi usado. Se sair um repetido, sorteia de novo.
    public String proximo() {
        String nome;
        do {
            String sobrenome1 = SOBRENOMES[sorteio.nextInt(SOBRENOMES.length)];
            String sobrenome2;
            do {
                sobrenome2 = SOBRENOMES[sorteio.nextInt(SOBRENOMES.length)];
            } while (sobrenome2.equals(sobrenome1));
            nome = NOMES[sorteio.nextInt(NOMES.length)] + " " + sobrenome1 + " " + sobrenome2;
        } while (!usados.add(nome));
        return nome;
    }
}
