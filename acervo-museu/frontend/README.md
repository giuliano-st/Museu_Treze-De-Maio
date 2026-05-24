# Frontend principal

Este e o frontend usado pela aplicacao Spring Boot do projeto.

## Como rodar

```bash
npm install
npm run dev
```

O Vite sobe, por padrao, em `http://localhost:5173`.

## Estrutura

```text
src/
  main.tsx                  # Entrada do React
  vite-env.d.ts             # Tipos do Vite
  app/
    App.tsx                 # Componente raiz
    routes.tsx              # Rotas da aplicacao
    components/             # Telas e componentes visuais
      Layout.tsx            # Estrutura comum das paginas
      Home.tsx              # Pagina inicial
      Login.tsx             # Login
      Register.tsx          # Cadastro de usuario
      Perfil.tsx            # Perfil do usuario
      PesquisaObras.tsx     # Busca de obras
      DetalhesObra.tsx      # Detalhes de uma obra
      CadastroObras.tsx     # Area de cadastro de obras
      CadastroLivro.tsx     # Cadastro de livro
      CadastroJornal.tsx    # Cadastro de jornal
      CadastroRevista.tsx   # Cadastro de revista
      PainelAdmin.tsx       # Painel administrativo
      ProtectedRoute.tsx    # Protecao de rotas privadas
    contexts/
      AuthContext.tsx       # Estado de autenticacao
    services/
      api.ts                # Configuracao das chamadas HTTP
  styles/
    tailwind.css            # Estilos globais do Tailwind
```

## Onde mexer

- Novas paginas: criar em `src/app/components/` e registrar em `src/app/routes.tsx`.
- Chamadas para o backend: centralizar em `src/app/services/`.
- Estado compartilhado: criar ou ajustar arquivos em `src/app/contexts/`.
- Estilos globais: manter em `src/styles/`.

## O que nao versionar

As pastas `node_modules/` e `dist/` nao devem ir para o Git. Elas sao geradas localmente.
