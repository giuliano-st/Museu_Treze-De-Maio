import { Link } from "react-router";
import { BookOpen, Activity } from "lucide-react";
import { useAuth } from "../contexts/AuthContext";
import { useEffect } from "react";
import { registrarAcesso } from "../services/api";

export function Home() {
  const { user, isAdmin } = useAuth();

  // Registra acesso à home
  useEffect(() => {
    registrarAcesso("/", user?.email);
  }, []);

  const btnClass =
    "inline-block bg-[#2E5B37] text-white px-6 py-2.5 rounded-lg font-medium transition-colors hover:bg-[#1f4026]";

  return (
    <div>
      {/* Hero */}
      <div className="bg-[#2E5B37] py-16">
        <div className="max-w-7xl mx-auto px-4 text-center">
          <img
            src="https://clubessociaisnegros.com/wp-content/uploads/2023/02/LOGO.webp"
            alt="Logo Museu Treze de Maio"
            className="mx-auto mb-8 h-32 md:h-48 w-auto object-contain drop-shadow-lg"
          />
          <h1 className="mb-4 text-white text-3xl md:text-4xl font-bold">
            Bem-vindo ao Museu Treze de Maio
          </h1>
          <p className="max-w-2xl mx-auto text-white/90 text-lg leading-relaxed">
            Espaço dedicado à preservação e difusão da história e cultura
            afro-brasileira, mantido pela Sociedade Cultural Ferroviária Treze de Maio.
          </p>
        </div>
      </div>

      <div className="max-w-7xl mx-auto px-4 py-12">

        {/* Cards principais */}
        <div className={`grid gap-8 mb-16 ${isAdmin ? "md:grid-cols-3" : "md:grid-cols-2"}`}>

          {/* Acervo — visível para todos */}
          <div className="bg-white border-2 border-[#2E5B37] rounded-xl p-6 text-center hover:shadow-xl transition-shadow">
            <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-md">
              <BookOpen size={32} />
            </div>
            <h3 className="mb-2 text-[#2E5B37] font-bold text-lg">Acervo Digital</h3>
            <p className="text-gray-500 mb-5 text-sm">
              Explore nossa coleção de obras, livros e documentos históricos.
            </p>
            <Link to="/pesquisa" className={btnClass}>Explorar Acervo</Link>
          </div>

          {/* Cadastro — APENAS para admin */}
          {isAdmin && (
            <div className="bg-white border-2 border-[#2E5B37] rounded-xl p-6 text-center hover:shadow-xl transition-shadow">
              <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-md">
                <BookOpen size={32} />
              </div>
              <h3 className="mb-2 text-[#2E5B37] font-bold text-lg">Cadastrar Obra</h3>
              <p className="text-gray-500 mb-5 text-sm">
                Adicione novas obras ao acervo digital do museu.
              </p>
              <Link to="/cadastro" className={btnClass}>Cadastrar</Link>
            </div>
          )}

          {/* Painel admin */}
          {isAdmin && (
            <div className="bg-white border-2 border-[#2E5B37] rounded-xl p-6 text-center hover:shadow-xl transition-shadow">
              <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-md">
                <Activity size={32} />
              </div>
              <h3 className="mb-2 text-[#2E5B37] font-bold text-lg">Painel Admin</h3>
              <p className="text-gray-500 mb-5 text-sm">
                Histórico de acessos, log de ações e obras mais buscadas.
              </p>
              <Link to="/admin" className={btnClass}>Acessar Painel</Link>
            </div>
          )}

        </div>

        {/* Sobre o Museu */}
        <div className="bg-gray-50 rounded-xl p-8 border border-gray-200">
          <h2 className="mb-6 text-[#2E5B37] text-center text-2xl font-bold">Sobre o Museu</h2>
          <div className="grid md:grid-cols-2 gap-8">
            <div>
              <h3 className="mb-3 text-[#2E5B37] font-semibold">Nossa História</h3>
              <p className="text-gray-600 leading-relaxed text-sm">
                A Sociedade Cultural Ferroviária Treze de Maio foi fundada com o objetivo de valorizar
                e preservar a memória cultural afro-brasileira, especialmente ligada aos trabalhadores
                ferroviários e suas contribuições para a sociedade.
              </p>
            </div>
            <div>
              <h3 className="mb-3 text-[#2E5B37] font-semibold">Nossa Missão</h3>
              <p className="text-gray-600 leading-relaxed text-sm">
                Promover o conhecimento, a pesquisa e a valorização da cultura afro-brasileira através
                da preservação de documentos, livros e objetos históricos, tornando-os acessíveis a
                pesquisadores, estudantes e ao público em geral.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
