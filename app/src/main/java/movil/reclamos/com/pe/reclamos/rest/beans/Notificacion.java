package movil.reclamos.com.pe.reclamos.rest.beans;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"idNotificacion",
"cliente",
"asunto",
"notificacion",
"created_at",
"estado"
})
public class Notificacion {

    @JsonProperty("idNotificacion")
    private Integer idNotificacion;
    @JsonProperty("cliente")
    private Cliente cliente;
    @JsonProperty("asunto")
    private String asunto;
    @JsonProperty("notificacion")
    private String notificacion;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("estado")
    private Integer estado;

    @JsonProperty("idNotificacion")
    public Integer getIdNotificacion() {
    return idNotificacion;
    }

    @JsonProperty("idNotificacion")
    public void setIdNotificacion(Integer idNotificacion) {
    this.idNotificacion = idNotificacion;
    }

    @JsonProperty("cliente")
    public Cliente getCliente() {
    return cliente;
    }

    @JsonProperty("cliente")
    public void setCliente(Cliente cliente) {
    this.cliente = cliente;
    }

    @JsonProperty("asunto")
    public String getAsunto() {
    return asunto;
    }

    @JsonProperty("asunto")
    public void setAsunto(String asunto) {
    this.asunto = asunto;
    }

    @JsonProperty("notificacion")
    public String getNotificacion() {
    return notificacion;
    }

    @JsonProperty("notificacion")
    public void setNotificacion(String notificacion) {
    this.notificacion = notificacion;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
    return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    @JsonProperty("estado")
    public Integer getEstado() {
    return estado;
    }

    @JsonProperty("estado")
    public void setEstado(Integer estado) {
    this.estado = estado;
    }

}