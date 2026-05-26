import { useState } from "react";
import { useNavigate, Link } from "react-router";
import { LogIn, AlertCircle } from "lucide-react";
import { useAuth } from "../contexts/AuthContext";
import { registrarAcesso } from "../services/api";

export function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const [carregando, setCarregando] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErro("");
    setCarregando(true);

    const ok = await login(email, senha);
    setCarregando(false);

    if (ok) {
      registrarAcesso("/login", email);
      navigate("/");
    } else {
      setErro("E-mail ou senha inválidos.");
    }
  };

  return (
    <div className="min-h-[calc(100vh-20rem)] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        <div className="bg-white border-2 border-[#2E5B37] rounded-xl shadow-xl p-8">
          <div className="text-center mb-8">
            <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4 shadow-md">
              <LogIn size={32} />
            </div>
            <h2 className="text-[#2E5B37] text-2xl font-bold">Entrar</h2>
            <p className="text-gray-500 mt-1 text-sm">Acesse o sistema do Museu Treze de Maio</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            {erro && (
              <div className="bg-red-50 border border-red-300 rounded-lg p-3 flex items-center gap-2 text-red-700 text-sm">
                <AlertCircle size={18} />
                {erro}
              </div>
            )}

            <div>
              <label className="block mb-1 text-[#2E5B37] font-medium text-sm">E-mail</label>
              <input
                type="email"
                required
                className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="seu@email.com"
              />
            </div>

            <div>
              <label className="block mb-1 text-[#2E5B37] font-medium text-sm">Senha</label>
              <input
                type="password"
                required
                className="w-full px-4 py-2.5 border-2 border-gray-200 rounded-lg focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                placeholder="••••••••"
              />
            </div>

            <button
              type="submit"
              disabled={carregando}
              className="w-full bg-[#2E5B37] text-white py-3 rounded-lg font-bold transition-colors hover:bg-[#1f4026] disabled:opacity-60"
            >
              {carregando ? "Entrando..." : "Entrar"}
            </button>

            <p className="text-center text-gray-500 text-sm">
              Não tem conta?{" "}
              <Link to="/register" className="text-[#2E5B37] font-semibold hover:underline">
                Cadastre-se
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}
