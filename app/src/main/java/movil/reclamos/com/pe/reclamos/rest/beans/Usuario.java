package movil.reclamos.com.pe.reclamos.rest.beans;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Usuario {

    @JsonProperty("idUsuario")
    private Integer idUsuario;
    @JsonProperty("password")
    private String password;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("email")
    private String email;
    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("resetPassword")
    private Integer resetPassword;
    @JsonProperty("username")
    private String username;
    @JsonProperty("persona")
    private Persona persona;
    @JsonProperty("deletedAt")
    private String deletedAt;
    @JsonProperty("passwordCaduca")
    private String passwordCaduca;
    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("idUsuario")
    public Integer getIdUsuario() {
    return idUsuario;
    }

    @JsonProperty("idUsuario")
    public void setIdUsuario(Integer idUsuario) {
    this.idUsuario = idUsuario;
    }

    @JsonProperty("password")
    public String getPassword() {
    return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
    this.password = password;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
    return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    @JsonProperty("email")
    public String getEmail() {
    return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
    this.email = email;
    }

    @JsonProperty("estado")
    public Integer getEstado() {
    return estado;
    }

    @JsonProperty("estado")
    public void setEstado(Integer estado) {
    this.estado = estado;
    }

    @JsonProperty("resetPassword")
    public Integer getResetPassword() {
    return resetPassword;
    }

    @JsonProperty("resetPassword")
    public void setResetPassword(Integer resetPassword) {
    this.resetPassword = resetPassword;
    }

    @JsonProperty("username")
    public String getUsername() {
    return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
    this.username = username;
    }

    @JsonProperty("persona")
    public Persona getPersona() {
    return persona;
    }

    @JsonProperty("persona")
    public void setPersona(Persona persona) {
    this.persona = persona;
    }

    @JsonProperty("deletedAt")
    public String getDeletedAt() { return deletedAt; }

    @JsonProperty("deletedAt")
    public void setDeletedAt(String deletedAt) { this.deletedAt = deletedAt; }

    @JsonProperty("passwordCaduca")
    public String getPasswordCaduca() { return passwordCaduca; }

    @JsonProperty("passwordCaduca")
    public void setPasswordCaduca(String passwordCaduca) { this.passwordCaduca = passwordCaduca; }

    @JsonProperty("updatedAt")
    public String getUpdatedAt() { return updatedAt; }

    @JsonProperty("updatedAt")
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}