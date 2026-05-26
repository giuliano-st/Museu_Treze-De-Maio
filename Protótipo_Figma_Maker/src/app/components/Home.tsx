import { Link } from "react-router";
import { BookOpen, Search, PlusCircle } from "lucide-react";

export function Home() {
  const buttonClass =
    "inline-block bg-[#2E5B37] text-white px-6 py-2 rounded border-2 border-transparent transition-colors hover:bg-white hover:text-black hover:border-[#2E5B37]";

  return (
    <div>
      {/* Seção Hero com fundo verde */}
      <div className="bg-[#2E5B37] py-16">
        <div className="max-w-7xl mx-auto px-4 text-center">
          <img
            src="https://clubessociaisnegros.com/wp-content/uploads/2023/02/LOGO.webp"
            alt="Logo Museu Treze de Maio"
            className="mx-auto mb-8 h-32 md:h-48 w-auto object-contain"
          />
          <h1 className="mb-4 text-white text-3xl md:text-4xl font-bold">
            Bem-vindo ao Museu Treze de Maio
          </h1>
          <p className="max-w-2xl mx-auto text-white text-lg">
            O Museu Treze de Maio é um espaço dedicado à
            preservação e difusão da história e cultura
            afro-brasileira, mantido pela Sociedade Cultural
            Ferroviária Treze de Maio.
          </p>
        </div>
      </div>

      {/* Conteúdo abaixo do hero */}
      <div className="max-w-7xl mx-auto px-4 py-12">

     {/* Grid de Cards */}
<div className="grid md:grid-cols-2 gap-8 mb-16">

  <div className="bg-white border-2 border-[#2E5B37] rounded-lg p-6 text-center hover:shadow-xl transition-shadow">
    <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
      <BookOpen size={32} />
    </div>
    <h3 className="mb-3 text-[#2E5B37] font-semibold">Acervo Digital</h3>
    <p className="text-gray-600 mb-4">
      Explore nossa coleção de obras, livros e documentos históricos.
    </p>
    <Link to="/pesquisa" className={buttonClass}>
      Explorar Acervo
    </Link>
  </div>

  <div className="bg-white border-2 border-[#2E5B37] rounded-lg p-6 text-center hover:shadow-xl transition-shadow">
    <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
      <PlusCircle size={32} />
    </div>
    <h3 className="mb-3 text-[#2E5B37] font-semibold">Cadastrar Obra</h3>
    <p className="text-gray-600 mb-4">
      Adicione novas obras ao nosso acervo digital.
    </p>
    <Link to="/cadastro" className={buttonClass}>
      Cadastrar
    </Link>
  </div>

</div>
      {/* Seção Sobre */}
      <div className="bg-gray-50 rounded-lg p-8 border border-gray-200">
        <h2 className="mb-6 text-[#2E5B37] text-center text-2xl font-bold">
          Sobre o Museu
        </h2>
        <div className="grid md:grid-cols-2 gap-8">
          <div>
            <h3 className="mb-3 text-[#2E5B37] font-semibold">
              Nossa História
            </h3>
            <p className="text-gray-700 leading-relaxed">
              A Sociedade Cultural Ferroviária Treze de Maio foi
              fundada com o objetivo de valorizar e preservar a
              memória cultural afro-brasileira, especialmente
              ligada aos trabalhadores ferroviários e suas
              contribuições para a sociedade.
            </p>
          </div>
          <div>
            <h3 className="mb-3 text-[#2E5B37] font-semibold">
              Nossa Missão
            </h3>
            <p className="text-gray-700 leading-relaxed">
              Promover o conhecimento, a pesquisa e a
              valorização da cultura afro-brasileira através da
              preservação de documentos, livros e objetos
              históricos, tornando-os acessíveis a
              pesquisadores, estudantes e ao público em geral.
            </p>
          </div>
        </div>
      </div>
      </div>
    </div>
  );
}
