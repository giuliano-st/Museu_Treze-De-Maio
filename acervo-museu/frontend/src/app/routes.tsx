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
import { PainelAdmin } from "./components/PainelAdmin";

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

      // Perfil — só para logados
      {
        path: "perfil",
        element: <ProtectedRoute><Perfil /></ProtectedRoute>,
      },

      // Cadastros — só para admin
      {
        path: "cadastro",
        element: <ProtectedRoute adminOnly><CadastroObras /></ProtectedRoute>,
      },
      {
        path: "cadastro/livro",
        element: <ProtectedRoute adminOnly><CadastroLivro /></ProtectedRoute>,
      },
      {
        path: "cadastro/jornal",
        element: <ProtectedRoute adminOnly><CadastroJornal /></ProtectedRoute>,
      },
      {
        path: "cadastro/revista",
        element: <ProtectedRoute adminOnly><CadastroRevista /></ProtectedRoute>,
      },

      // Painel administrativo — histórico, logs, obras mais buscadas
      {
        path: "admin",
        element: <ProtectedRoute adminOnly><PainelAdmin /></ProtectedRoute>,
      },
    ],
  },
]);
