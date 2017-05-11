package movil.reclamos.com.pe.reclamos.rest.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "razonSocial",
        "representante",
        "fechaReclamo",
        "asuntoReclamo",
        "respuestaReclamo"
})
public class Notificacion {

    @JsonProperty("razonSocial")
    private String razonSocial;
    @JsonProperty("representante")
    private String representante;
    @JsonProperty("fechaReclamo")
    private String fechaReclamo;
    @JsonProperty("asuntoReclamo")
    private String asuntoReclamo;
    @JsonProperty("respuestaReclamo")
    private String respuestaReclamo;

    @JsonProperty("razonSocial")
    public String getRazonSocial() {
        return razonSocial;
    }

    @JsonProperty("razonSocial")
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @JsonProperty("representante")
    public String getRepresentante() {
        return representante;
    }

    @JsonProperty("representante")
    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    @JsonProperty("fechaReclamo")
    public String getFechaReclamo() {
        return fechaReclamo;
    }

    @JsonProperty("fechaReclamo")
    public void setFechaReclamo(String fechaReclamo) {
        this.fechaReclamo = fechaReclamo;
    }

    @JsonProperty("asuntoReclamo")
    public String getAsuntoReclamo() {
        return asuntoReclamo;
    }

    @JsonProperty("asuntoReclamo")
    public void setAsuntoReclamo(String asuntoReclamo) {
        this.asuntoReclamo = asuntoReclamo;
    }

    @JsonProperty("respuestaReclamo")
    public String getRespuestaReclamo() {
        return respuestaReclamo;
    }

    @JsonProperty("respuestaReclamo")
    public void setRespuestaReclamo(String respuestaReclamo) {
        this.respuestaReclamo = respuestaReclamo;
    }

}