import { useEffect, useState } from "react";
import { historicoAcesso, logAcoes, maisAcessadas } from "../services/api";
import { Activity, TrendingUp, FileText, Users } from "lucide-react";

export function PainelAdmin() {
  const [acessos, setAcessos] = useState<any[]>([]);
  const [logs, setLogs] = useState<any[]>([]);
  const [top, setTop] = useState<any[]>([]);
  const [aba, setAba] = useState<"acessos" | "logs" | "top">("acessos");

  useEffect(() => {
    historicoAcesso().then(setAcessos);
    logAcoes().then(setLogs);
    maisAcessadas().then(setTop);
  }, []);

  const abas = [
    { id: "acessos", label: "Histórico de Acessos", icon: <Users size={16} /> },
    { id: "logs", label: "Log de Ações", icon: <FileText size={16} /> },
    { id: "top", label: "Mais Buscadas", icon: <TrendingUp size={16} /> },
  ] as const;

  return (
    <div className="max-w-6xl mx-auto px-4 py-10">
      <div className="flex items-center gap-3 mb-8">
        <div className="bg-[#2E5B37] text-white w-10 h-10 rounded-full flex items-center justify-center">
          <Activity size={20} />
        </div>
        <div>
          <h1 className="text-2xl font-bold text-[#2E5B37]">Painel Administrativo</h1>
          <p className="text-gray-500 text-sm">Visível apenas para administradores</p>
        </div>
      </div>

      {/* Abas */}
      <div className="flex gap-2 mb-6 border-b-2 border-gray-200">
        {abas.map((a) => (
          <button key={a.id} onClick={() => setAba(a.id)}
            className={`flex items-center gap-2 px-4 py-2.5 text-sm font-medium rounded-t-lg transition-colors ${
              aba === a.id
                ? "bg-[#2E5B37] text-white"
                : "text-gray-600 hover:text-[#2E5B37]"
            }`}>
            {a.icon} {a.label}
          </button>
        ))}
      </div>

      {/* Histórico de acessos */}
      {aba === "acessos" && (
        <div className="bg-white border-2 border-gray-200 rounded-xl overflow-hidden">
          <table className="w-full text-sm">
            <thead className="bg-[#2E5B37] text-white">
              <tr>
                <th className="px-4 py-3 text-left">Data/Hora</th>
                <th className="px-4 py-3 text-left">IP</th>
                <th className="px-4 py-3 text-left">Página</th>
                <th className="px-4 py-3 text-left">Usuário</th>
              </tr>
            </thead>
            <tbody>
              {acessos.length === 0 && (
                <tr><td colSpan={4} className="px-4 py-6 text-center text-gray-400">Nenhum acesso registrado</td></tr>
              )}
              {acessos.map((a) => (
                <tr key={a.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-600">{new Date(a.dataHora).toLocaleString("pt-BR")}</td>
                  <td className="px-4 py-3 font-mono text-xs text-gray-500">{a.ip}</td>
                  <td className="px-4 py-3 text-[#2E5B37] font-medium">{a.pagina}</td>
                  <td className="px-4 py-3 text-gray-600">{a.emailUsuario || <span className="text-gray-400 italic">Não logado</span>}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Log de ações */}
      {aba === "logs" && (
        <div className="bg-white border-2 border-gray-200 rounded-xl overflow-hidden">
          <table className="w-full text-sm">
            <thead className="bg-[#2E5B37] text-white">
              <tr>
                <th className="px-4 py-3 text-left">Data/Hora</th>
                <th className="px-4 py-3 text-left">Usuário</th>
                <th className="px-4 py-3 text-left">Ação</th>
                <th className="px-4 py-3 text-left">Detalhes</th>
              </tr>
            </thead>
            <tbody>
              {logs.length === 0 && (
                <tr><td colSpan={4} className="px-4 py-6 text-center text-gray-400">Nenhuma ação registrada</td></tr>
              )}
              {logs.map((l) => (
                <tr key={l.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-600">{new Date(l.dataHora).toLocaleString("pt-BR")}</td>
                  <td className="px-4 py-3 text-[#2E5B37]">{l.emailUsuario}</td>
                  <td className="px-4 py-3">
                    <span className="bg-[#2E5B37]/10 text-[#2E5B37] px-2 py-0.5 rounded text-xs font-mono font-bold">
                      {l.acao}
                    </span>
                  </td>
                  <td className="px-4 py-3 text-gray-600">{l.detalhes}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Top 5 mais buscadas */}
      {aba === "top" && (
        <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-5">
          {top.length === 0 && (
            <p className="text-gray-400 col-span-3 text-center py-10">Nenhuma busca registrada ainda</p>
          )}
          {top.map((obra, i) => (
            <div key={obra.id} className="bg-white border-2 border-[#2E5B37] rounded-xl p-5 flex gap-4 items-center shadow-sm">
              <div className="bg-[#2E5B37] text-white w-10 h-10 rounded-full flex items-center justify-center font-bold text-lg shrink-0">
                {i + 1}
              </div>
              <div>
                <p className="font-bold text-[#2E5B37] line-clamp-1">{obra.titulo}</p>
                <p className="text-sm text-gray-500">{obra.autor}</p>
                <p className="text-xs text-gray-400 mt-1">{obra.contadorBuscas} busca(s)</p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
