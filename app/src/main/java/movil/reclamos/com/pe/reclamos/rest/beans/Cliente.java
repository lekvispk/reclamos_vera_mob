package movil.reclamos.com.pe.reclamos.rest.beans;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class Cliente {

    @JsonProperty("idCliente")
    private Integer idCliente;
    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("fecCliente")
    private String fecCliente;
    @JsonProperty("nomCliente")
    private String nomCliente;
    @JsonProperty("representante")
    private String representante;
    @JsonProperty("rucCliente")
    private String rucCliente;
    @JsonProperty("monto")
    private Double monto;
    @JsonProperty("persona")
    private Persona persona;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idCliente")
    public Integer getIdCliente() {
    return idCliente;
    }

    @JsonProperty("idCliente")
    public void setIdCliente(Integer idCliente) {
    this.idCliente = idCliente;
    }

    @JsonProperty("estado")
    public Integer getEstado() {
    return estado;
    }

    @JsonProperty("estado")
    public void setEstado(Integer estado) {
    this.estado = estado;
    }

    @JsonProperty("fecCliente")
    public String getFecCliente() {
    return fecCliente;
    }

    @JsonProperty("fecCliente")
    public void setFecCliente(String fecCliente) {
    this.fecCliente = fecCliente;
    }

    @JsonProperty("nomCliente")
    public String getNomCliente() {
    return nomCliente;
    }

    @JsonProperty("nomCliente")
    public void setNomCliente(String nomCliente) {
    this.nomCliente = nomCliente;
    }

    @JsonProperty("representante")
    public String getRepresentante() {
    return representante;
    }

    @JsonProperty("representante")
    public void setRepresentante(String representante) {
    this.representante = representante;
    }

    @JsonProperty("rucCliente")
    public String getRucCliente() {
    return rucCliente;
    }

    @JsonProperty("rucCliente")
    public void setRucCliente(String rucCliente) {
    this.rucCliente = rucCliente;
    }

    @JsonProperty("monto")
    public Double getMonto() {
    return monto;
    }

    @JsonProperty("monto")
    public void setMonto(Double monto) {
    this.monto = monto;
    }

    @JsonProperty("persona")
    public Persona getPersona() {
    return persona;
    }

    @JsonProperty("persona")
    public void setPersona(Persona persona) {
    this.persona = persona;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    }

}