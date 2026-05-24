// Em dev o Vite roda na 5173 e o Spring na 8080.
// Em produção (após build) tudo está no Spring, então /obra/... funciona direto.
const BASE = import.meta.env.DEV ? "http://localhost:8080" : "";

function headers(emailAdmin?: string) {
  const h: Record<string, string> = { "Content-Type": "application/json" };
  if (emailAdmin) h["email-admin"] = emailAdmin;
  return h;
}

export async function login(email: string, senha: string) {
  const res = await fetch(`${BASE}/usuario/login`, { method: "POST", headers: headers(), body: JSON.stringify({ email, senha }) });
  return res.json();
}
export async function cadastrarUsuario(dados: object) {
  const res = await fetch(`${BASE}/usuario/cadastrar`, { method: "POST", headers: headers(), body: JSON.stringify(dados) });
  return res.json();
}
export async function buscarObras(params: Record<string, string>) {
  const query = new URLSearchParams(params).toString();
  const res = await fetch(`${BASE}/obra/buscar?${query}`);
  return res.json();
}
export async function listarObras() {
  const res = await fetch(`${BASE}/obra/listar`);
  return res.json();
}
export async function buscarObraPorId(id: number) {
  const res = await fetch(`${BASE}/obra/${id}`);
  return res.json();
}
export async function salvarObra(obra: object, emailAdmin: string) {
  const res = await fetch(`${BASE}/obra/salvar`, { method: "POST", headers: headers(emailAdmin), body: JSON.stringify(obra) });
  return res.json();
}
export async function excluirObra(id: number, emailAdmin: string) {
  await fetch(`${BASE}/obra/excluir/${id}`, { method: "DELETE", headers: headers(emailAdmin) });
}
export async function registrarSaida(id: number, emailAdmin: string) {
  const res = await fetch(`${BASE}/obra/saida/${id}`, { method: "POST", headers: headers(emailAdmin) });
  return res.json();
}
export async function registrarDevolucao(id: number, emailAdmin: string) {
  const res = await fetch(`${BASE}/obra/devolucao/${id}`, { method: "POST", headers: headers(emailAdmin) });
  return res.json();
}
export async function maisAcessadas() {
  const res = await fetch(`${BASE}/obra/mais-acessadas`);
  return res.json();
}
export async function registrarAcesso(pagina: string, emailUsuario?: string) {
  await fetch(`${BASE}/acesso/registrar`, { method: "POST", headers: headers(), body: JSON.stringify({ pagina, emailUsuario: emailUsuario ?? null }) });
}
export async function historicoAcesso() {
  const res = await fetch(`${BASE}/acesso/historico`);
  return res.json();
}
export async function logAcoes() {
  const res = await fetch(`${BASE}/log/acoes`);
  return res.json();
}
