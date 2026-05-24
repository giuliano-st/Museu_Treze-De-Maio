# Museu Treze de Maio - Sistema de Acervo

Repositorio do sistema web de gerenciamento de acervo do Museu Treze de Maio.

## Estrutura do repositorio

```text
acervo-museu/
  pom.xml                         # Projeto Spring Boot
  src/main/java/                  # Backend Java
  src/main/resources/             # Configuracoes e arquivos estaticos
  frontend/                       # Frontend principal em React + Vite

Protótipo_Figma_Maker/            # Prototipo visual exportado do Figma Maker
Diagramas/                        # Diagramas do projeto
```

## Frontend principal

O frontend que deve ser alterado para evoluir o sistema fica em:

```text
acervo-museu/frontend/
```

Dentro dele, os principais pontos sao:

- `src/main.tsx`: entrada do React.
- `src/app/App.tsx`: componente principal.
- `src/app/routes.tsx`: rotas da aplicacao.
- `src/app/components/`: telas e componentes.
- `src/app/contexts/`: estados compartilhados, como autenticacao.
- `src/app/services/`: comunicacao com o backend.
- `src/styles/`: estilos globais.

Mais detalhes estao em `acervo-museu/frontend/README.md`.

## Prototipo

O prototipo visual fica em:

```text
Protótipo_Figma_Maker/
```

Ele serve como referencia visual e nao deve ser confundido com o frontend principal.

## Configuracao do backend

Copie o arquivo de exemplo:

```text
acervo-museu/src/main/resources/application.example.properties
```

para:

```text
acervo-museu/src/main/resources/application.properties
```

Depois ajuste usuario, senha e banco de dados local.

## Como rodar o frontend principal

```bash
cd acervo-museu/frontend
npm install
npm run dev
```

## Como rodar o backend

```bash
cd acervo-museu
mvn spring-boot:run
```
