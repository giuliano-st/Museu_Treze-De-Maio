import { createContext, useContext, useState, ReactNode } from "react";

interface User {
  nome: string;
  papel: "Administrador" | "Visitante";
  email: string;
  avatar?: string;
}

interface AuthContextType {
  user: User | null;
  login: (email: string, password: string) => boolean;
  logout: () => void;
  isAdmin: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);

  const login = (email: string, password: string): boolean => {
    // Mock authentication - aceita qualquer email/senha
    if (email && password) {
      setUser({
        nome: "Administrador do Museu",
        papel: "Administrador",
        email: email,
        avatar: "https://ui-avatars.com/api/?name=Admin&background=2E5B37&color=fff"
      });
      return true;
    }
    return false;
  };

  const logout = () => {
    setUser(null);
  };

  const isAdmin = user?.papel === "Administrador";

  return (
    <AuthContext.Provider value={{ user, login, logout, isAdmin }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
}
