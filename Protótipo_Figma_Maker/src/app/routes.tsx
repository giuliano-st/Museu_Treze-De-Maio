import { createBrowserRouter } from "react-router";
import { Layout } from "./components/Layout";
import { Home } from "./components/Home";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { CadastroObras } from "./components/CadastroObras";
import { PesquisaObras } from "./components/PesquisaObras";
import { CadastroLivro } from "./components/CadastroLivro";
import { CadastroJornal } from "./components/CadastroJornal";
import { CadastroRevista } from "./components/CadastroRevista";
import { ProtectedRoute } from "./components/ProtectedRoute";
import { Perfil } from "./components/Perfil";
import { DetalhesObra } from "./components/DetalhesObra";

export const router = createBrowserRouter([
  {
    path: "/",
    Component: Layout,
    children: [
      { index: true, Component: Home },
      { path: "login", Component: Login },
      { path: "register", Component: Register },
      { path: "pesquisa", Component: PesquisaObras },
      { path: "obra/:id", Component: DetalhesObra },
      {
        path: "perfil",
        element: <ProtectedRoute><Perfil /></ProtectedRoute>
      },

      // Rotas protegidas - requerem autenticação
      { path: "obra/:id", Component: DetalhesObra },
      {
        path: "cadastro",
        element: <ProtectedRoute><CadastroObras /></ProtectedRoute>
      },
      {
        path: "cadastro/livro",
        element: <ProtectedRoute><CadastroLivro /></ProtectedRoute>
      },
      {
        path: "cadastro/jornal",
        element: <ProtectedRoute><CadastroJornal /></ProtectedRoute>
      },
      {
        path: "cadastro/revista",
        element: <ProtectedRoute><CadastroRevista /></ProtectedRoute>
      },
    ],
  },
]);