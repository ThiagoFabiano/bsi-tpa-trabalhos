# Técnicas de Programação Avançadas - Trabalhos

## 📌 Sobre o Projeto
Este repositório reúne os trabalhos da disciplina de Técnicas de Programação Avançadas. No Trabalho 1 implementamos uma biblioteca própria de Lista Encadeada Genérica em Java (ordenada e não ordenada). No Trabalho 2 acrescentamos uma biblioteca de Árvore Binária de Busca genérica e analisamos, de forma matemática e empírica, a complexidade dos seus métodos em árvores perfeitamente balanceadas e em árvores degeneradas. Depois acrescentamos a Árvore AVL, que se rebalanceia a cada inserção e remoção, e comparamos o seu desempenho com o da árvore binária comum. No Trabalho 3 (em andamento) vamos acrescentar uma biblioteca de grafos e um aplicativo que a use para resolver um problema real.

As estruturas de dados ficam numa **biblioteca** separada, que não depende de nenhum aplicativo. Cada trabalho tem o **seu aplicativo**, que usa a biblioteca:

* o app de **contatos** (Trabalhos 1 e 2) gerencia contatos telefônicos carregados a partir de um arquivo texto, permitindo inserções, buscas e remoções. Ao iniciar, o usuário escolhe se os contatos serão guardados em listas (ordenadas ou não), em árvores binárias ou em árvores AVL;
* o app do **Trabalho 3** ainda não foi implementado (o tema ainda está sendo definido).

## 👥 Componentes do Grupo
* Daniel Pinheiro
* Kaio Henrique da Silva Nezio
* Thiago Fabiano

## 📂 Organização do Repositório

```
biblioteca/src/        estruturas de dados reaproveitáveis (sem nada de domínio)
apps/contatos/         app de contatos (Trabalhos 1 e 2)
apps/trabalho-3/       app do Trabalho 3
docs/trabalho-1/       especificação e relatório do Trabalho 1
docs/trabalho-2/       especificação e relatórios do Trabalho 2
docs/trabalho-3/       especificação do Trabalho 3
Makefile               compila e executa tudo a partir da raiz
```

A biblioteca não importa nenhuma classe dos apps; os apps é que importam a biblioteca.

### Biblioteca (`biblioteca/src`)
* **`colecao`**: interface `IColecao.java`, que define os métodos obrigatórios das estruturas.
* **`listaencadeada`**: biblioteca de lista encadeada genérica (`ListaEncadeada.java` e `No.java`), do Trabalho 1.
* **`arvorebinaria`**: biblioteca de árvore binária de busca genérica. `ArvoreBinaria.java` especializa a `ArvoreBinariaBase.java` disponibilizada pelo professor; `ArvoreAVL.java` herda de `ArvoreBinaria`, sobrescreve `adicionar` e `remover` e rebalanceia a árvore com rotações; `No.java` é o nó da árvore (guarda também a altura, usada pela AVL).

### App de contatos (`apps/contatos/src`), organizado no padrão MVC
* **`model`**: classe `Contato.java` e os comparadores `ComparatorContatoPorNome.java` e `ComparatorContatoPorTelefone.java`.
* **`view`**: `ContatoView.java`, responsável por toda a interação com o usuário no terminal (menus, leitura e mensagens).
* **`controller`**: `ContatoController.java`, que recebe a opção escolhida na view, chama o service e manda a view exibir o resultado.
* **`service`**: `ContatoService.java`, com as regras do programa (leitura do arquivo, telefone sem repetição, manter as duas coleções iguais) e a medição dos tempos; `Medicao.java` guarda o resultado de uma operação junto com o tempo gasto.
* **`app`**: `Main.java`, que pergunta qual estrutura usar e instancia as coleções, o service e o controller.
* **`util`**: geradores dos arquivos de entrada dos testes de desempenho:
  * `GeradorArquivos.java`: contatos com telefones embaralhados (usado no Trabalho 1);
  * `GeradorArquivosOrdenados.java`: telefones em ordem crescente, que geram uma árvore **degenerada**;
  * `GeradorArquivosBalanceados.java`: telefones numa ordem que gera uma árvore **perfeitamente balanceada**;
  * `GeradorNomes.java`: usado pelos dois geradores acima; sorteia nomes no formato "Nome Sobrenome Sobrenome" **sem repetir**, para que a árvore de nomes guarde todos os contatos (a árvore não aceita chaves repetidas).

Os arquivos de entrada ficam em `apps/contatos/entradas` (a pasta não vai para o git; os arquivos são criados pelos geradores).

## 📄 Formato do arquivo de entrada (app de contatos)
Um contato por linha, no formato `Nome;Telefone`:

```
Ana Silva;27900000123
Bruno Lima;27900004567
```

Linhas fora desse formato são ignoradas. Não pode haver dois contatos com o mesmo telefone.

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* Java Development Kit (JDK) 16 ou mais novo.

### Passo a passo (terminal)

1. Clone o repositório:
   ```bash
   git clone https://github.com/ThiagoFabiano/TPA.git
   cd TPA
   ```

   O código como foi entregue em cada trabalho está na branch correspondente (`trabalho2`, `trabalho3`).

2. **A partir da raiz do repositório**, compile e execute com o `make` (precisa do `make` instalado; no Windows, use o Git Bash ou o WSL):
   ```bash
   make executar-contatos
   ```

   Outros comandos do Makefile:
   ```bash
   make                          # compila a biblioteca e todos os apps
   make biblioteca               # compila só a biblioteca
   make gerar-ordenados          # gera ordenado_<tamanho>.txt (50000, 100000, 200000 e 400000)
   make gerar-balanceados        # gera balanceado_<tamanho>.txt (50000, 100000, 200000 e 400000)
   make gerar                    # gera os arquivos embaralhados do Trabalho 1 (entrada_<tamanho>.txt)
   make gerar TAMANHOS="1000"    # gera um arquivo embaralhado com o tamanho que quiser
   make executar-trabalho-3      # executa o app do Trabalho 3 (quando existir)
   make limpar                   # apaga as pastas bin
   ```

   Sem o `make`, compile primeiro a biblioteca e depois o app, com a biblioteca no classpath. Linux ou macOS:
   ```bash
   javac -encoding UTF-8 -d biblioteca/bin $(find biblioteca/src -name "*.java")
   javac -encoding UTF-8 -cp biblioteca/bin -d apps/contatos/bin $(find apps/contatos/src -name "*.java")
   ```

   Windows (PowerShell):
   ```powershell
   javac -encoding UTF-8 -d biblioteca/bin (Get-ChildItem -Path biblioteca/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
   javac -encoding UTF-8 -cp biblioteca/bin -d apps/contatos/bin (Get-ChildItem -Path apps/contatos/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
   ```

   O `-encoding UTF-8` evita erro de compilação por causa dos acentos que existem nos comentários e nas mensagens do programa.

3. Execute de dentro da pasta do app, onde ficam os arquivos de entrada (no Windows, troque o `:` do classpath por `;`):
   ```bash
   cd apps/contatos
   java -cp bin:../../biblioteca/bin app.Main
   ```

   No Windows, se os acentos aparecerem trocados na tela, rode `chcp 65001` antes de executar.

4. O programa pergunta qual estrutura usar (1 - lista não ordenada, 2 - lista ordenada, 3 - árvore binária, 4 - árvore AVL) e mostra o menu com as opções de carregar arquivo, adicionar, pesquisar por nome, pesquisar por telefone, remover, alterar e sair. Na opção 1, informe o nome do arquivo (por exemplo `balanceado_50000.txt`); ele é procurado na pasta atual e em `entradas/`.

### Rodando pelo VS Code
Abra a pasta raiz do repositório, tenha o **Extension Pack for Java** instalado e informe as pastas de código-fonte em `.vscode/settings.json`:

```json
{
  "java.project.sourcePaths": ["biblioteca/src", "apps/contatos/src"]
}
```

Depois clique em **Run** acima do `main` da classe `apps/contatos/src/app/Main.java`. Como o VS Code executa a partir da raiz, na opção 1 informe o caminho completo do arquivo (por exemplo `apps/contatos/entradas/balanceado_50000.txt`).

## 📊 Como refazer os testes de desempenho (Trabalho 2)
A partir da raiz do repositório, gere os arquivos:

```bash
make gerar-ordenados
make gerar-balanceados
```

Cada gerador mostra, para cada arquivo, o **telefone de pior caso**, isto é, a folha mais distante da raiz na árvore indexada por telefone. No arquivo ordenado é o último telefone; no balanceado é uma folha do último nível.

Para cada arquivo:
1. rode `make executar-contatos` e escolha a opção 3 (árvore binária) ou a opção 4 (árvore AVL; nos testes do relatório, só com os arquivos ordenados);
2. na opção 1, informe o nome do arquivo e anote o tempo de montagem;
3. na opção 4, pesquise o telefone de pior caso e anote o tempo e o nome exibido;
4. na opção 3, pesquise esse nome e anote o tempo;
5. na opção 5, remova o telefone de pior caso e anote o tempo.

> Atenção: a montagem da árvore a partir dos arquivos **ordenados** demora, porque a árvore fica
> degenerada e cada inserção percorre todos os nós já inseridos (O(n²)). Com 200.000 contatos leva
> cerca de 10 minutos e com 400.000 cerca de 50 minutos. Com os arquivos **balanceados** leva menos de um segundo. Na árvore AVL, mesmo os arquivos ordenados levam menos de um segundo, porque ela nunca fica degenerada.
