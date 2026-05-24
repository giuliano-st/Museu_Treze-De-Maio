import { useState } from "react";
import { useNavigate, Link } from "react-router";
import { LogIn, AlertCircle } from "lucide-react";
import { useAuth } from "../contexts/AuthContext";

export function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [error, setError] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    const success = login(formData.email, formData.password);

    if (success) {
      navigate("/");
    } else {
      setError("E-mail ou senha inválidos");
    }
  };

  return (
    <div className="min-h-[calc(100vh-20rem)] flex items-center justify-center px-4 py-12">
      <div className="w-full max-w-md">
        <div className="bg-white border-2 border-[#2E5B37] rounded-lg shadow-lg p-8">
          <div className="text-center mb-8">
            <div className="bg-[#2E5B37] text-white w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
              <LogIn size={32} />
            </div>
            <h2 className="text-[#2E5B37] text-2xl font-bold">
              Login
            </h2>
            <p className="text-gray-600 mt-2">
              Acesse sua conta do Museu Treze de Maio
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            {error && (
              <div className="bg-red-50 border-2 border-red-200 rounded p-3 flex items-center gap-2 text-red-700">
                <AlertCircle size={20} />
                <span>{error}</span>
              </div>
            )}

            <div className="bg-blue-50 border-2 border-blue-200 rounded p-3 text-sm text-blue-700">
              <strong>Dica:</strong> Use qualquer e-mail e senha para fazer login como Administrador
            </div>

            <div>
              <label
                htmlFor="email"
                className="block mb-2 text-[#2E5B37] font-medium"
              >
                E-mail
              </label>
              <input
                type="email"
                id="email"
                required
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.email}
                onChange={(e) => {
                  setFormData({
                    ...formData,
                    email: e.target.value,
                  });
                  setError("");
                }}
                placeholder="seu@email.com"
              />
            </div>

            <div>
              <label
                htmlFor="password"
                className="block mb-2 text-[#2E5B37] font-medium"
              >
                Senha
              </label>
              <input
                type="password"
                id="password"
                required
                className="w-full px-4 py-2 border-2 border-gray-300 rounded focus:border-[#2E5B37] focus:outline-none transition-colors"
                value={formData.password}
                onChange={(e) => {
                  setFormData({
                    ...formData,
                    password: e.target.value,
                  });
                  setError("");
                }}
                placeholder="••••••••"
              />
            </div>

            <div className="flex items-center justify-between">
              <label className="flex items-center cursor-pointer">
                <input
                  type="checkbox"
                  className="mr-2 accent-[#2E5B37]"
                />
                <span className="text-gray-700">
                  Lembrar-me
                </span>
              </label>
              <button
                type="button"
                className="text-[#2E5B37] hover:underline text-sm font-medium"
              >
                Esqueceu a senha?
              </button>
            </div>

            <button
              type="submit"
              className="w-full bg-[#2E5B37] text-white py-3 rounded border-2 border-transparent transition-colors hover:bg-white hover:text-black hover:border-[#2E5B37] font-bold"
            >
              Entrar
            </button>

            <p className="text-center text-gray-400 text-sm italic mt-2">
              Use qualquer email e senha para entrar
            </p>

            <p className="text-center text-gray-600">
              Não tem uma conta?{" "}
              <Link
                to="/register"
                className="text-[#2E5B37] font-bold hover:underline"
              >
                Cadastre-se
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
}
