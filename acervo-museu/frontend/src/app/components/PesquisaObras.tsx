import { useState, useEffect } from "react";
import { Search, BookOpen, Calendar, User, SlidersHorizontal } from "lucide-react";
import { useNavigate } from "react-router";
import { buscarObras } from "../services/api";
import { registrarAcesso } from "../services/api";
import { useAuth } from "../contexts/AuthContext";

interface Obra {
  id: number;
  titulo: string;
  autor: string;
  dataPublicacao: string;
  categoria: string;
  tipo: string;
  capa: string;
  descricao: string;
  status: string;
  localizacaoFisica?: string;
  isbn?: string;
  issn?: string;
  notasInternas?: string;
  quantidadeSaidas?: number;
}

export function PesquisaObras() {
  const navigate = useNavigate();
  const { user } = useAuth();

  const [obras, setObras] = useState<Obra[]>([]);
  const [carregando, setCarregando] = useState(false);

  // Filtros — estilo catálogo vertical (como Amazon / Estante Virtual)
  const [termo, setTermo] = useState("");
  const [tipo, setTipo] = useState("");
  const [categoria, setCategoria] = useState("");
  const [dataInicio, setDataInicio] = useState("");
  const [dataFim, setDataFim] = useState("");

  // Registra acesso ao carregar a página
  useEffect(() => {
    registrarAcesso("/pesquisa", user?.email);
    buscar();
  }, []);

  async function buscar() {
    setCarregando(true);
    const params: Record<string, string> = {};
    if (termo) params.termo = termo;
    if (tipo) params.tipo = tipo;
    if (categoria) params.categoria = categoria;
    if (dataInicio) params.dataInicio = dataInicio;
    if (dataFim) params.dataFim = dataFim;

    const data = await buscarObras(params);
    setObras(Array.isArray(data) ? data : []);
    setCarregando(false);
  }

  function getStatusColor(status: string) {
    if (status === "DISPONIVEL") return "bg-green-100 text-green-700";
    if (status === "EMPRESTADO") return "bg-red-100 text-red-700";
    return "bg-gray-100 text-gray-700";
  }

  return (
    <div className="max-w-7xl mx-auto px-4 py-10">
      <div className="text-center mb-10">
        <h1 className="text-4xl font-bold text-[#2E5B37] mb-2">Acervo Digital</h1>
        <p className="text-gray-500">Explore nossa coleção de livros, revistas e jornais</p>
      </div>

      {/* Layout catálogo vertical — filtros na lateral, resultados à direita */}
      <div className="flex gap-8">

        {/* COLUNA DE FILTROS — lateral esquerda */}
        <aside className="w-64 shrink-0">
          <div className="bg-white border-2 border-[#2E5B37] rounded-xl p-5 sticky top-4">
            <div className="flex items-center gap-2 text-[#2E5B37] font-bold mb-5">
              <SlidersHorizontal size={20} />
              Filtros
            </div>

            {/* Busca por texto */}
            <div className="mb-4">
              <label className="block text-sm font-medium text-[#2E5B37] mb-1">Buscar</label>
              <div className="relative">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={16} />
                <input
                  type="text"
                  value={termo}
                  onChange={(e) => setTermo(e.target.value)}
                  onKeyDown={(e) => e.key === "Enter" && buscar()}
                  placeholder="Título, autor..."
                  className="w-full pl-9 pr-3 py-2 border-2 border-gray-200 rounded-lg text-sm focus:border-[#2E5B37] focus:outline-none"
                />
              </div>
            </div>

            {/* Tipo */}
            <div className="mb-4">
              <label className="block text-sm font-medium text-[#2E5B37] mb-1">Tipo</label>
              <select value={tipo} onChange={(e) => setTipo(e.target.value)}
                className="w-full px-3 py-2 border-2 border-gray-200 rounded-lg text-sm focus:border-[#2E5B37] focus:outline-none bg-white">
                <option value="">Todos</option>
                <option value="LIVRO">Livro</option>
                <option value="JORNAL">Jornal</option>
                <option value="REVISTA">Revista</option>
              </select>
            </div>

            {/* Categoria */}
            <div className="mb-4">
              <label className="block text-sm font-medium text-[#2E5B37] mb-1">Categoria</label>
              <select value={categoria} onChange={(e) => setCategoria(e.target.value)}
                className="w-full px-3 py-2 border-2 border-gray-200 rounded-lg text-sm focus:border-[#2E5B37] focus:outline-none bg-white">
                <option value="">Todas</option>
                <option value="História">História</option>
                <option value="Cultura">Cultura</option>
                <option value="Literatura">Literatura</option>
                <option value="Artes">Artes</option>
                <option value="Biografias">Biografias</option>
              </select>
            </div>

            {/* Filtro por data de publicação */}
            <div className="mb-4">
              <label className="block text-sm font-medium text-[#2E5B37] mb-1">
                <Calendar size={14} className="inline mr-1" />
                Data de publicação
              </label>
              <input type="text" value={dataInicio} onChange={(e) => setDataInicio(e.target.value)}
                placeholder="De (ex: 1990)"
                className="w-full px-3 py-2 border-2 border-gray-200 rounded-lg text-sm mb-2 focus:border-[#2E5B37] focus:outline-none" />
              <input type="text" value={dataFim} onChange={(e) => setDataFim(e.target.value)}
                placeholder="Até (ex: 2020)"
                className="w-full px-3 py-2 border-2 border-gray-200 rounded-lg text-sm focus:border-[#2E5B37] focus:outline-none" />
            </div>

            <button onClick={buscar}
              className="w-full bg-[#2E5B37] text-white py-2.5 rounded-lg font-semibold hover:bg-[#1f4026] transition-colors text-sm">
              Pesquisar
            </button>

            {/* Limpar filtros */}
            <button onClick={() => { setTermo(""); setTipo(""); setCategoria(""); setDataInicio(""); setDataFim(""); }}
              className="w-full mt-2 py-2 text-sm text-gray-500 hover:text-[#2E5B37] transition-colors">
              Limpar filtros
            </button>
          </div>
        </aside>

        {/* COLUNA DE RESULTADOS */}
        <div className="flex-1">
          <div className="flex items-center justify-between mb-5">
            <span className="text-gray-500 text-sm">
              {carregando ? "Buscando..." : `${obras.length} obra(s) encontrada(s)`}
            </span>
          </div>

          {carregando && (
            <div className="text-center py-20 text-gray-400">Carregando...</div>
          )}

          {/* Grid de cards */}
          {!carregando && (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
              {obras.map((obra) => (
                <div key={obra.id}
                  className="bg-white border-2 border-gray-200 rounded-xl overflow-hidden hover:border-[#2E5B37] hover:shadow-lg transition-all duration-300 flex flex-col">
                  {/* Capa */}
                  <div className="h-56 bg-gray-100 overflow-hidden">
                    <img src={obra.capa || "https://via.placeholder.com/400x600?text=Sem+Capa"}
                      alt={obra.titulo}
                      className="w-full h-full object-cover hover:scale-105 transition-transform duration-300" />
                  </div>

                  {/* Conteúdo */}
                  <div className="p-4 flex flex-col flex-1">
                    <div className="flex items-start justify-between gap-2 mb-2">
                      <span className="inline-block bg-[#2E5B37] text-white text-xs px-2 py-0.5 rounded-full">
                        {obra.tipo}
                      </span>
                      <span className={`text-xs px-2 py-0.5 rounded-full font-medium ${getStatusColor(obra.status)}`}>
                        {obra.status}
                      </span>
                    </div>

                    <h3 className="font-bold text-[#2E5B37] line-clamp-2 mb-2">{obra.titulo}</h3>

                    <div className="space-y-1 text-sm text-gray-500 mb-3">
                      <div className="flex items-center gap-1.5"><User size={13} />{obra.autor}</div>
                      <div className="flex items-center gap-1.5"><Calendar size={13} />{obra.dataPublicacao}</div>
                      <div className="flex items-center gap-1.5"><BookOpen size={13} />{obra.categoria}</div>
                    </div>

                    <p className="text-gray-500 text-sm line-clamp-3 flex-1 mb-4">{obra.descricao}</p>

                    <button
                      onClick={() => navigate(`/obra/${obra.id}`, { state: { obra } })}
                      className="w-full bg-[#2E5B37] text-white py-2.5 rounded-lg hover:bg-[#1f4026] transition-colors text-sm font-medium">
                      Ver Detalhes
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}

          {!carregando && obras.length === 0 && (
            <div className="text-center py-20">
              <BookOpen size={64} className="mx-auto mb-4 text-gray-200" />
              <p className="text-gray-400">Nenhuma obra encontrada. Tente outros filtros.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
