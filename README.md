# Museu Treze de Maio — Sistema de Acervo

Repositório do sistema web de gerenciamento de acervo do Museu Treze de Maio, desenvolvido como projeto da disciplina de Projeto de Software.

## Sobre o projeto

O sistema permite a catalogação, pesquisa e visualização das obras do acervo do museu, incluindo livros, jornais e revistas. Conta com autenticação de usuários e área restrita para cadastro de novos itens.

## Funcionalidades

- Página inicial com apresentação do acervo
- Pesquisa de obras por título, autor e tipo
- Visualização de detalhes de cada obra
- Cadastro de livros, jornais e revistas (requer autenticação)
- Autenticação com login, cadastro e perfil de usuário

## Tecnologias

- [React 18](https://react.dev/) + [TypeScript](https://www.typescriptlang.org/)
- [Vite](https://vitejs.dev/) — build e servidor de desenvolvimento
- [Tailwind CSS v4](https://tailwindcss.com/) — estilização
- [shadcn/ui](https://ui.shadcn.com/) + [Radix UI](https://www.radix-ui.com/) — componentes de interface
- [React Router v7](https://reactrouter.com/) — roteamento
- [pnpm](https://pnpm.io/) — gerenciamento de pacotes

## Como executar

**Pré-requisitos:** Node.js 18+ e pnpm instalados.

```bash
# Instalar dependências
pnpm install

# Iniciar servidor de desenvolvimento
pnpm dev
```

A aplicação estará disponível em `http://localhost:5173`.

## Estrutura do projeto

```
src/
├── app/
│   ├── components/
│   │   ├── ui/          # Componentes base (shadcn/ui)
│   │   ├── figma/       # Utilitários de imagem
│   │   ├── Layout.tsx
│   │   ├── Home.tsx
│   │   ├── Login.tsx
│   │   ├── Register.tsx
│   │   ├── Perfil.tsx
│   │   ├── PesquisaObras.tsx
│   │   ├── DetalhesObra.tsx
│   │   ├── CadastroObras.tsx
│   │   ├── CadastroLivro.tsx
│   │   ├── CadastroJornal.tsx
│   │   └── CadastroRevista.tsx
│   ├── contexts/
│   │   └── AuthContext.tsx
│   ├── App.tsx
│   └── routes.tsx
├── styles/
└── main.tsx
```

## Protótipo

Protótipo do projeto disponível no Figma Maker:
- Link: https://www.figma.com/make/FglaasT5AO66AHufDd2Rgd/Site-Acervo?p=f&t=ir8UYNKt8mIriPJ5-0

## Diagramas

Os diagramas de caso de uso e modelo de domínio estão disponíveis na pasta [`Diagramas/`](./Diagramas/).
