import { createContext, useContext, useState, ReactNode } from "react";
import { login as loginApi } from "../services/api";

interface User {
  id: number;
  nome: string;
  papel: string;
  email: string;
  avatar?: string;
}

interface AuthContextType {
  user: User | null;
  login: (email: string, senha: string) => Promise<boolean>;
  logout: () => void;
  isAdmin: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(() => {
    // Mantém sessão ao recarregar a página
    const salvo = localStorage.getItem("usuario");
    return salvo ? JSON.parse(salvo) : null;
  });

  const login = async (email: string, senha: string): Promise<boolean> => {
    const data = await loginApi(email, senha);
    if (data.erro) return false;
    setUser(data);
    localStorage.setItem("usuario", JSON.stringify(data));
    return true;
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("usuario");
  };

  const isAdmin = user?.papel === "ADMINISTRADOR";

  return (
    <AuthContext.Provider value={{ user, login, logout, isAdmin }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth deve estar dentro de AuthProvider");
  return ctx;
}
