# Rode os comandos a partir da raiz do repositório.
# A biblioteca (estruturas de dados) compila sozinha, sem conhecer nenhum app.
# Cada app compila com a biblioteca no classpath e roda de dentro da própria pasta,
# onde ficam os seus arquivos de entrada.

JAVAC := javac -encoding UTF-8

BIBLIOTECA_SRC := $(shell find biblioteca/src -name '*.java')
BIBLIOTECA_BIN := biblioteca/bin

CONTATOS_DIR := apps/contatos
CONTATOS_SRC := $(shell find $(CONTATOS_DIR)/src -name '*.java')

TRABALHO3_DIR := apps/trabalho-3
TRABALHO3_SRC := $(shell find $(TRABALHO3_DIR)/src -name '*.java')

# Classpath usado de dentro da pasta de um app (apps/<nome>).
APP_CP := bin:../../$(BIBLIOTECA_BIN)

.PHONY: all biblioteca contatos trabalho-3 executar-contatos executar-trabalho-3 \
        gerar gerar-ordenados gerar-balanceados limpar

all: biblioteca contatos trabalho-3

biblioteca:
	$(JAVAC) -d $(BIBLIOTECA_BIN) $(BIBLIOTECA_SRC)

# ---------- Trabalhos 1 e 2: app de contatos ----------

contatos: biblioteca
	$(JAVAC) -cp $(BIBLIOTECA_BIN) -d $(CONTATOS_DIR)/bin $(CONTATOS_SRC)

executar-contatos: contatos
	cd $(CONTATOS_DIR) && java -cp $(APP_CP) app.Main

# Geradores dos arquivos de entrada (gravam em apps/contatos/entradas).
gerar: contatos
	cd $(CONTATOS_DIR) && java -cp $(APP_CP) util.GeradorArquivos $(TAMANHOS)

gerar-ordenados: contatos
	cd $(CONTATOS_DIR) && java -cp $(APP_CP) util.GeradorArquivosOrdenados

gerar-balanceados: contatos
	cd $(CONTATOS_DIR) && java -cp $(APP_CP) util.GeradorArquivosBalanceados

# ---------- Trabalho 3: app de grafos ----------

trabalho-3: biblioteca
ifneq ($(strip $(TRABALHO3_SRC)),)
	$(JAVAC) -cp $(BIBLIOTECA_BIN) -d $(TRABALHO3_DIR)/bin $(TRABALHO3_SRC)
else
	@echo "trabalho-3: ainda não há código em $(TRABALHO3_DIR)/src"
endif

executar-trabalho-3: trabalho-3
	cd $(TRABALHO3_DIR) && java -cp $(APP_CP) app.Main

limpar:
	rm -rf $(BIBLIOTECA_BIN) $(CONTATOS_DIR)/bin $(TRABALHO3_DIR)/bin
