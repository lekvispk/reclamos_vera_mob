package movil.reclamos.com.pe.reclamos.rest.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaRest {

    @JsonProperty("codigo")
    private Integer codigo;
    @JsonProperty("mensaje")
    private String mensaje;

    @JsonProperty("codigo")
    public Integer getCodigo() {
    return codigo;
    }

    @JsonProperty("codigo")
    public void setCodigo(Integer codigo) {
    this.codigo = codigo;
    }

    @JsonProperty("mensaje")
    public String getMensaje() {
    return mensaje;
    }

    @JsonProperty("mensaje")
    public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
    }

}