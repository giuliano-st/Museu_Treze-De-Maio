package inf.laboratorio.museutreze.dto.web;

public class UsuarioWebDTO {
    public String nomeUsuario;
    public String role;
    public String senha;
    public String email;

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}