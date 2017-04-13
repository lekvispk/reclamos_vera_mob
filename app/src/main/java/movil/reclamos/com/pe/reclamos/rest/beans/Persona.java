package movil.reclamos.com.pe.reclamos.rest.beans;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Persona {

    @JsonProperty("idPersona")
    private Integer idPersona;
    @JsonProperty("apePaterno")
    private String apePaterno;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("direccion")
    private String direccion;
    @JsonProperty("email")
    private String email;
    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("nombres")
    private String nombres;
    @JsonProperty("numeroDocumento")
    private String numeroDocumento;
    @JsonProperty("telefono1")
    private String telefono1;
    @JsonProperty("ubigeo")
    private String ubigeo;
    @JsonProperty("cliente")
    private Cliente cliente;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idPersona")
    public Integer getIdPersona() {
    return idPersona;
    }

    @JsonProperty("idPersona")
    public void setIdPersona(Integer idPersona) {
    this.idPersona = idPersona;
    }

    @JsonProperty("apePaterno")
    public String getApePaterno() {
    return apePaterno;
    }

    @JsonProperty("apePaterno")
    public void setApePaterno(String apePaterno) {
    this.apePaterno = apePaterno;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
    return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    @JsonProperty("direccion")
    public String getDireccion() {
    return direccion;
    }

    @JsonProperty("direccion")
    public void setDireccion(String direccion) {
    this.direccion = direccion;
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

    @JsonProperty("nombres")
    public String getNombres() {
    return nombres;
    }

    @JsonProperty("nombres")
    public void setNombres(String nombres) {
    this.nombres = nombres;
    }

    @JsonProperty("numeroDocumento")
    public String getNumeroDocumento() {
    return numeroDocumento;
    }

    @JsonProperty("numeroDocumento")
    public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
    }

    @JsonProperty("telefono1")
    public String getTelefono1() {
    return telefono1;
    }

    @JsonProperty("telefono1")
    public void setTelefono1(String telefono1) {
    this.telefono1 = telefono1;
    }

    @JsonProperty("ubigeo")
    public String getUbigeo() {
    return ubigeo;
    }

    @JsonProperty("ubigeo")
    public void setUbigeo(String ubigeo) {
    this.ubigeo = ubigeo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}