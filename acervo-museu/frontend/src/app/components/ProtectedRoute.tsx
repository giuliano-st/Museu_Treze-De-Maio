import { Navigate } from "react-router";
import { useAuth } from "../contexts/AuthContext";

interface Props {
  children: React.ReactNode;
  adminOnly?: boolean;
}

export function ProtectedRoute({ children, adminOnly = false }: Props) {
  const { user, isAdmin } = useAuth();

  if (!user) return <Navigate to="/login" />;
  if (adminOnly && !isAdmin) return <Navigate to="/" />;

  return <>{children}</>;
}
