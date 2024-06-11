package br.com.plutusmanager.PlutusManager.dto;

public class LoginRequestDto {

    private String loginUsuario;
    private String senha;

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
