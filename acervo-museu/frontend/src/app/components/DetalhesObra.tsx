import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router";
import { useAuth } from "../contexts/AuthContext";
import { buscarObraPorId, registrarSaida, registrarDevolucao, excluirObra } from "../services/api";
import {
  ArrowLeft, BookOpen, User, Calendar, Tag, MapPin,
  FileText, Hash, Edit, Trash2, Lock, LogOut, LogIn, Gift
} from "lucide-react";

export function DetalhesObra() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user, isAdmin } = useAuth();

  const [obra, setObra] = useState<any>(null);
  const [carregando, setCarregando] = useState(true);

  useEffect(() => {
    buscarObraPorId(Number(id)).then((data) => {
      setObra(data);
      setCarregando(false);
    });
  }, [id]);

  if (carregando) return <div className="text-center py-20 text-gray-400">Carregando...</div>;
  if (!obra) return <div className="text-center py-20 text-gray-400">Obra não encontrada.</div>;

  const handleEditar = () => navigate(`/cadastro/${obra.tipo.toLowerCase()}`, { state: { obraParaEditar: obra } });

  const handleExcluir = async () => {
    if (!window.confirm(`Excluir "${obra.titulo}"?`)) return;
    await excluirObra(obra.id, user!.email);
    navigate("/pesquisa");
  };

  const handleSaida = async () => {
    const atualizada = await registrarSaida(obra.id, user!.email);
    setObra(atualizada);
  };

  const handleDevolucao = async () => {
    const atualizada = await registrarDevolucao(obra.id, user!.email);
    setObra(atualizada);
  };

  const badgeTipo: Record<string, string> = {
    LIVRO: "bg-blue-100 text-blue-700 border-blue-300",
    JORNAL: "bg-green-100 text-green-700 border-green-300",
    REVISTA: "bg-purple-100 text-purple-700 border-purple-300",
  };

  return (
    <div className="max-w-5xl mx-auto px-4 py-8">
      <button onClick={() => navigate(-1)}
        className="mb-6 flex items-center gap-2 text-[#2E5B37] hover:underline text-sm">
        <ArrowLeft size={18} /> Voltar para o Acervo
      </button>

      {/* Barra do administrador */}
      {isAdmin && (
        <div className="bg-[#2E5B37] text-white rounded-xl p-4 mb-6 flex flex-wrap gap-3 justify-between items-center shadow-md">
          <div className="flex items-center gap-2 font-semibold">
            <Lock size={18} /> Painel Administrativo
          </div>
          <div className="flex flex-wrap gap-2">
            <button onClick={handleEditar}
              className="flex items-center gap-1.5 bg-white text-[#2E5B37] px-3 py-2 rounded-lg text-sm font-medium hover:bg-gray-100 transition-colors">
              <Edit size={16} /> Editar
            </button>
            {obra.status === "DISPONIVEL" ? (
              <button onClick={handleSaida}
                className="flex items-center gap-1.5 bg-amber-500 text-white px-3 py-2 rounded-lg text-sm font-medium hover:bg-amber-600 transition-colors">
                <LogOut size={16} /> Registrar Saída
              </button>
            ) : (
              <button onClick={handleDevolucao}
                className="flex items-center gap-1.5 bg-emerald-500 text-white px-3 py-2 rounded-lg text-sm font-medium hover:bg-emerald-600 transition-colors">
                <LogIn size={16} /> Registrar Devolução
              </button>
            )}
            <button onClick={handleExcluir}
              className="flex items-center gap-1.5 bg-red-600 text-white px-3 py-2 rounded-lg text-sm font-medium hover:bg-red-700 transition-colors">
              <Trash2 size={16} /> Excluir
            </button>
          </div>
        </div>
      )}

      <div className="bg-white border-2 border-[#2E5B37] rounded-xl shadow-xl overflow-hidden">
        <div className="grid md:grid-cols-2 gap-8 p-8">
          {/* Capa */}
          <div>
            <div className="border-4 border-[#2E5B37] rounded-xl overflow-hidden shadow-lg">
              <img src={obra.capa || "https://via.placeholder.com/400x600?text=Sem+Capa"}
                alt={obra.titulo} className="w-full h-auto object-cover" />
            </div>
            <div className="flex items-center gap-2 mt-4 flex-wrap">
              <span className={`px-3 py-1 rounded-full border-2 text-sm font-semibold ${badgeTipo[obra.tipo] || "bg-gray-100 text-gray-700"}`}>
                {obra.tipo}
              </span>
              <span className={`px-3 py-1 rounded-full text-sm font-medium ${obra.status === "DISPONIVEL" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"}`}>
                {obra.status}
              </span>
            </div>
          </div>

          {/* Informações */}
          <div className="space-y-5">
            <h1 className="text-[#2E5B37] text-2xl font-bold">{obra.titulo}</h1>

            {/* Campos públicos */}
            <div className="space-y-3">
              <Info icon={<User size={18} />} label="Autor" valor={obra.autor} />
              <Info icon={<Calendar size={18} />} label="Data de Publicação" valor={obra.dataPublicacao} />
              <Info icon={<Tag size={18} />} label="Categoria" valor={obra.categoria} />
              <Info icon={<FileText size={18} />} label="Descrição" valor={obra.descricao} />
              {obra.doador && (
                <Info icon={<Gift size={18} />} label="Doador" valor={obra.doador} />
              )}
            </div>

            {/* Campos administrativos */}
            {isAdmin && (
              <div className="border-t-2 border-[#2E5B37] pt-5 mt-5 space-y-3">
                <div className="bg-amber-50 border border-amber-200 rounded-lg p-3 text-amber-800 text-sm flex items-center gap-2">
                  <Lock size={14} /> Informações Administrativas
                </div>
                <Info icon={<Hash size={18} />} label="ID" valor={`#${obra.id}`} />
                {obra.localizacaoFisica && <Info icon={<MapPin size={18} />} label="Localização" valor={obra.localizacaoFisica} />}
                {obra.isbn && <Info icon={<BookOpen size={18} />} label="ISBN" valor={obra.isbn} />}
                {obra.issn && <Info icon={<BookOpen size={18} />} label="ISSN" valor={obra.issn} />}
                <Info icon={<LogOut size={18} />} label="Total de Saídas" valor={`${obra.quantidadeSaidas || 0} vez(es)`} />
                {obra.notasInternas && <Info icon={<FileText size={18} />} label="Notas Internas" valor={obra.notasInternas} />}
              </div>
            )}

            {!user && (
              <div className="bg-blue-50 border border-blue-200 rounded-lg p-3 text-blue-700 text-sm">
                Faça login como administrador para ver informações adicionais.
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

// Componente auxiliar para exibir um campo com ícone
function Info({ icon, label, valor }: { icon: React.ReactNode; label: string; valor: string }) {
  if (!valor) return null;
  return (
    <div className="flex items-start gap-3">
      <span className="text-[#2E5B37] mt-0.5 shrink-0">{icon}</span>
      <div>
        <p className="text-gray-500 text-xs">{label}</p>
        <p className="font-medium text-gray-800 text-sm">{valor}</p>
      </div>
    </div>
  );
}
