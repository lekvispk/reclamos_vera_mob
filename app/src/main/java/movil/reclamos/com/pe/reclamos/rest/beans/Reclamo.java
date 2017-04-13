package movil.reclamos.com.pe.reclamos.rest.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reclamo implements Serializable,Parcelable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("idReclamo")
    private Integer idReclamo;
    @JsonProperty("idCliente")
    private Integer idCliente;
    @JsonProperty("asunto")
    private String asunto;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("estados")
    private List<Object> estados = null;
    @JsonProperty("fecReclamo")
    private String fecReclamo;
    @JsonProperty("mensaje")
    private String mensaje;
    @JsonProperty("prioridad")
    private Integer prioridad;
    @JsonProperty("vencimiento")
    private String vencimiento;
    @JsonProperty("factura")
    private Factura factura;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Reclamo(){}

    public Reclamo(Parcel in ){
        this.idCliente = in.readInt();
        this.idReclamo = in.readInt();
        this.asunto = in.readString();
        this.createdAt = in.readString();
        this.estado = in.readInt();
        this.fecReclamo = in.readString();
        this.mensaje = in.readString();
        this.prioridad = in.readInt();
        this.vencimiento = in.readString();
        //this.factura = in.readParcelable(Factura.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.idCliente );
        dest.writeInt( this.idReclamo );
        dest.writeString( this.asunto );
        dest.writeString( this.createdAt );
        dest.writeInt( this.estado );
        dest.writeString( this.fecReclamo );
        dest.writeString( this.mensaje );
        dest.writeInt( this.prioridad );
        dest.writeString( this.vencimiento );
        //dest.writeTypedObject( factura, 1);
    }

    public static final Parcelable.Creator<Reclamo> CREATOR = new Parcelable.Creator<Reclamo>()
    {
        public Reclamo createFromParcel(Parcel in)
        {
            return new Reclamo(in);
        }
        public Reclamo[] newArray(int size)
        {
            return new Reclamo[size];
        }
    };

    @JsonProperty("idReclamo")
    public Integer getIdReclamo() {
    return idReclamo;
    }

    @JsonProperty("idReclamo")
    public void setIdReclamo(Integer idReclamo) {
    this.idReclamo = idReclamo;
    }

    @JsonProperty("idCliente")
    public Integer getIdCliente() {
    return idCliente;
    }

    @JsonProperty("idCliente")
    public void setIdCliente(Integer idCliente) {
    this.idCliente = idCliente;
    }

    @JsonProperty("asunto")
    public String getAsunto() {
    return asunto;
    }

    @JsonProperty("asunto")
    public void setAsunto(String asunto) {
    this.asunto = asunto;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
    return createdAt;
    }

    @JsonProperty("createdAt")
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

    @JsonProperty("estados")
    public List<Object> getEstados() {
    return estados;
    }

    @JsonProperty("estados")
    public void setEstados(List<Object> estados) {
    this.estados = estados;
    }

    @JsonProperty("fecReclamo")
    public String getFecReclamo() {
    return fecReclamo;
    }

    @JsonProperty("fecReclamo")
    public void setFecReclamo(String fecReclamo) {
    this.fecReclamo = fecReclamo;
    }

    @JsonProperty("mensaje")
    public String getMensaje() {
    return mensaje;
    }

    @JsonProperty("mensaje")
    public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
    }

    @JsonProperty("prioridad")
    public Integer getPrioridad() {
    return prioridad;
    }

    @JsonProperty("prioridad")
    public void setPrioridad(Integer prioridad) {
    this.prioridad = prioridad;
    }

    @JsonProperty("vencimiento")
    public String getVencimiento() {
        return vencimiento;
    }

    @JsonProperty("vencimiento")
    public void setVencimiento(String vencimiento) {
    this.vencimiento = vencimiento;
    }

    @JsonProperty("factura")
    public Factura getFactura() {
    return factura;
    }

    @JsonProperty("factura")
    public void setFactura(Factura factura) {
    this.factura = factura;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "idReclamo=" + idReclamo +
                ", idCliente=" + idCliente +
                ", asunto='" + asunto + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", estado=" + estado +
                ", fecReclamo='" + fecReclamo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", prioridad=" + prioridad +
                ", vencimiento='" + vencimiento + '\'' +
                '}';
    }
}