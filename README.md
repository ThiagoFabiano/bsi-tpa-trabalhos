# Trabalho 2 - Árvores Binárias e Análise de Complexidade

## 📌 Sobre o Projeto
Este projeto foi desenvolvido para a disciplina de Técnicas de Programação Avançadas. No Trabalho 1 implementamos uma biblioteca própria de Lista Encadeada Genérica em Java (ordenada e não ordenada). No Trabalho 2 acrescentamos uma biblioteca de Árvore Binária de Busca genérica e analisamos, de forma matemática e empírica, a complexidade dos seus métodos em árvores perfeitamente balanceadas e em árvores degeneradas.

Para testar as bibliotecas, há um programa interativo que gerencia contatos telefônicos carregados a partir de um arquivo texto, permitindo inserções, buscas e remoções. Ao iniciar, o usuário escolhe se os contatos serão guardados em listas (ordenadas ou não) ou em árvores binárias.

## 👥 Componentes do Grupo
* Daniel Pinheiro
* Kaio Henrique da Silva Nezio
* Thiago Fabiano

## 📂 Organização do Código
O código-fonte está em `colecoes/src`, organizado no padrão MVC, com as bibliotecas de estruturas de dados em pacotes próprios:

* **`colecao`**: interface `IColecao.java`, que define os métodos obrigatórios das estruturas.
* **`listaencadeada`**: biblioteca de lista encadeada genérica (`ListaEncadeada.java` e `No.java`), do Trabalho 1.
* **`arvorebinaria`**: biblioteca de árvore binária de busca genérica. `ArvoreBinaria.java` especializa a `ArvoreBinariaBase.java` disponibilizada pelo professor; `No.java` é o nó da árvore.
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

Os arquivos de entrada ficam em `colecoes/entradas` (a pasta não vai para o git; os arquivos são criados pelos geradores).

## 📄 Formato do arquivo de entrada
Um contato por linha, no formato `Nome;Telefone`:

```
Ana Silva;27900000123
Bruno Lima;27900004567
```

Linhas fora desse formato são ignoradas. Não pode haver dois contatos com o mesmo telefone.

## ⚙️ Como Rodar o Projeto

### Pré-requisitos
* Java Development Kit (JDK) instalado.

### Passo a passo (terminal)

1. Clone o repositório e entre na branch do Trabalho 2:
   ```bash
   git clone https://github.com/ThiagoFabiano/bsi-tpa-trabalhos.git
   cd bsi-tpa-trabalhos
   git checkout trabalho2
   ```

2. Entre na pasta `colecoes`. **É importante rodar os comandos a partir dela**, porque os geradores gravam os arquivos em `entradas/` e o programa procura os arquivos nessa pasta:
   ```bash
   cd colecoes
   ```

3. Compile e execute com o `make` (precisa do `make` instalado; no Windows, use o Git Bash ou o WSL):
   ```bash
   make executar
   ```

   Outros comandos do Makefile:
   ```bash
   make                          # só compila
   make gerar-ordenados          # gera ordenado_<tamanho>.txt (50000, 100000, 200000 e 400000)
   make gerar-balanceados        # gera balanceado_<tamanho>.txt (50000, 100000, 200000 e 400000)
   make gerar                    # gera os arquivos embaralhados do Trabalho 1 (entrada_<tamanho>.txt)
   make gerar TAMANHOS="1000"    # gera um arquivo embaralhado com o tamanho que quiser
   make limpar                   # apaga a pasta bin
   ```

   Sem o `make`, compile manualmente. O comando precisa pegar os arquivos de todos os pacotes, por isso muda de um sistema para o outro:

   Linux ou macOS:
   ```bash
   javac -encoding UTF-8 -d bin $(find src -name "*.java")
   ```

   Windows (PowerShell):
   ```powershell
   javac -encoding UTF-8 -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
   ```

   Windows (Prompt de Comando):
   ```cmd
   dir /s /B src\*.java > fontes.txt
   javac -encoding UTF-8 -d bin @fontes.txt
   ```

   O `-encoding UTF-8` evita erro de compilação por causa dos acentos que existem nos comentários e nas mensagens do programa.

4. Execute o programa (igual nos três casos):
   ```bash
   java -cp bin app.Main
   ```

   No Windows, se os acentos aparecerem trocados na tela, rode `chcp 65001` antes de executar.

5. O programa pergunta qual estrutura usar (1 - lista não ordenada, 2 - lista ordenada, 3 - árvore binária) e mostra o menu com as opções de carregar arquivo, adicionar, pesquisar por nome, pesquisar por telefone, remover, alterar e sair. Na opção 1, informe o nome do arquivo (por exemplo `balanceado_50000.txt`); ele é procurado na pasta atual e em `entradas/`.

### Rodando pelo VS Code
Abra a pasta `colecoes` (e não a pasta raiz do repositório), tenha o **Extension Pack for Java** instalado e clique em **Run** acima do `main` da classe `app/Main.java`.

## 📊 Como refazer os testes de desempenho
A partir da pasta `colecoes`, gere os arquivos:

```bash
make gerar-ordenados
make gerar-balanceados
```

Sem o `make`: `java -cp bin util.GeradorArquivosOrdenados` e `java -cp bin util.GeradorArquivosBalanceados`.

Cada gerador mostra, para cada arquivo, o **telefone de pior caso**, isto é, a folha mais distante da raiz na árvore indexada por telefone. No arquivo ordenado é o último telefone; no balanceado é uma folha do último nível.

Para cada arquivo:
1. rode o programa e escolha a opção 3 (árvore binária);
2. na opção 1, informe o nome do arquivo e anote o tempo de montagem;
3. na opção 4, pesquise o telefone de pior caso e anote o tempo e o nome exibido;
4. na opção 3, pesquise esse nome e anote o tempo;
5. na opção 5, remova o telefone de pior caso e anote o tempo.

> Atenção: a montagem da árvore a partir dos arquivos **ordenados** demora, porque a árvore fica
> degenerada e cada inserção percorre todos os nós já inseridos (O(n²)). Com 200.000 contatos leva
> cerca de 20 minutos e com 400.000 cerca de 50 minutos. Com os arquivos **balanceados** leva menos de um segundo.
