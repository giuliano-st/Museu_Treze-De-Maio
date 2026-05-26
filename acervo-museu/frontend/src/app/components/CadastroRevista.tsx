import { useState } from "react";
import { useNavigate } from "react-router";
import { Library, ArrowLeft } from "lucide-react";
import { salvarObra } from "../services/api";
import { useAuth } from "../contexts/AuthContext";

export function CadastroRevista() {
  const navigate = useNavigate();
  const { user } = useAuth();

  const [formData, setFormData] = useState({
    titulo: "", numeroEdicao: "", mes: "", ano: "", issn: "",
    editora: "", localizacaoFisica: "", urlCapa: "", assuntos: "",
    periodicidade: "", descricaoFisica: "", categoria: "", doador: "",
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await salvarObra({
      tipo: "REVISTA",
      titulo: formData.titulo,
      numeroEdicao: formData.numeroEdicao,
      mes: formData.mes,
      dataPublicacao: formData.ano,
      issn: formData.issn,
      editora: formData.editora,
      localizacaoFisica: formData.localizacaoFisica,
      capa: formData.urlCapa,
      assuntos: formData.assuntos,
      periodicidade: formData.periodicidade,
      descricaoFisica: formData.descricaoFisica,
      categoria: formData.categoria,
      doador: formData.doador,
    }, user!.email);
    navigate("/cadastro");
  };

  const f = (id: keyof typeof formData) => ({
    value: formData[id],
    onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) =>
      setFormData({ ...formData, [id]: e.target.value }),
  });

  const cls = "w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none text-sm transition-colors";

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <button onClick={() => navigate("/cadastro")}
        className="mb-6 flex items-center gap-2 text-[#2E5B37] hover:underline text-sm">
        <ArrowLeft size={18} /> Voltar
      </button>

      <div className="bg-white border-2 border-[#2E5B37] rounded-xl shadow-xl p-8">
        <div className="text-center mb-8">
          <div className="bg-[#2E5B37] text-white w-14 h-14 rounded-full flex items-center justify-center mx-auto mb-3 shadow-md">
            <Library size={28} />
          </div>
          <h1 className="text-[#2E5B37] text-2xl font-bold">Cadastrar Revista</h1>
          <p className="text-gray-500 text-sm mt-1">Adicione uma nova revista ao acervo</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div className="grid md:grid-cols-2 gap-5">
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Título *</label>
              <input type="text" required {...f("titulo")} className={cls} placeholder="Nome da revista" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Número da Edição</label>
              <input type="text" {...f("numeroEdicao")} className={cls} placeholder="Vol. 5, Nº 12" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Mês</label>
              <select {...f("mes")} className={cls + " bg-white"}>
                <option value="">Selecione</option>
                {["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"].map(m => (
                  <option key={m} value={m}>{m}</option>
                ))}
              </select>
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Ano</label>
              <input type="text" {...f("ano")} className={cls} placeholder="2024" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">ISSN</label>
              <input type="text" {...f("issn")} className={cls} placeholder="XXXX-XXXX" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Editora</label>
              <input type="text" {...f("editora")} className={cls} placeholder="Nome da editora" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Localização Física</label>
              <input type="text" {...f("localizacaoFisica")} className={cls} placeholder="Estante B, Prateleira 2" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Periodicidade</label>
              <input type="text" {...f("periodicidade")} className={cls} placeholder="Mensal, Bimestral..." />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Categoria</label>
              <input type="text" {...f("categoria")} className={cls} placeholder="Ex: Cultura, Artes" />
            </div>
            <div>
              <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Doador</label>
              <input type="text" {...f("doador")} className={cls} placeholder="Nome de quem doou (se houver)" />
            </div>
          </div>

          <div>
            <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Assuntos</label>
            <input type="text" {...f("assuntos")} className={cls} placeholder="Separe por vírgula" />
          </div>
          <div>
            <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">URL da Capa</label>
            <input type="url" {...f("urlCapa")} className={cls} placeholder="https://exemplo.com/capa.jpg" />
          </div>
          <div>
            <label className="block mb-1.5 text-[#2E5B37] font-medium text-sm">Descrição Física</label>
            <textarea rows={3} {...f("descricaoFisica")} className={cls} placeholder="Páginas e outros detalhes" />
          </div>

          <div className="flex gap-4 pt-2">
            <button type="submit"
              className="flex-1 bg-[#2E5B37] text-white py-3 rounded-lg font-bold hover:bg-[#1f4026] transition-colors">
              Cadastrar Revista
            </button>
            <button type="button" onClick={() => navigate("/cadastro")}
              className="px-8 py-3 border-2 border-[#2E5B37] text-[#2E5B37] rounded-lg hover:bg-gray-50 transition-colors">
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
