package movil.reclamos.com.pe.reclamos.rest.beans;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Factura implements Serializable,Parcelable {

    @JsonProperty("idFactura")
    private Integer idFactura;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("emision")
    private String emision;
    @JsonProperty("estado")
    private Integer estado;
    @JsonProperty("monto")
    private Integer monto;
    @JsonProperty("numero")
    private String numero;
    @JsonProperty("cliente")
    private Cliente cliente;

    public Factura(){}

    public Factura(Parcel in ){
        this.idFactura = in.readInt();
        this.createdAt= in.readString();
        this.emision= in.readString();
        this.estado= in.readInt();
        this.monto= in.readInt();
        this.numero= in.readString();
        //ciente
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( this.idFactura );
        dest.writeString( this.createdAt );
        dest.writeString( this.emision );
        dest.writeInt( this.estado );
        dest.writeInt( this.monto );
        dest.writeString( this.numero );
        //cliente
    }

    public static final Parcelable.Creator<Factura> CREATOR = new Parcelable.Creator<Factura>()
    {
        public Factura createFromParcel(Parcel in)
        {
            return new Factura(in);
        }
        public Factura[] newArray(int size)
        {
            return new Factura[size];
        }
    };

    @JsonProperty("idFactura")
    public Integer getIdFactura() {
    return idFactura;
    }

    @JsonProperty("idFactura")
    public void setIdFactura(Integer idFactura) {
    this.idFactura = idFactura;
    }

    @JsonProperty("createdAt")
    public String getCreatedAt() {
    return createdAt;
    }

    @JsonProperty("createdAt")
    public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
    }

    @JsonProperty("emision")
    public String getEmision() {
    return emision;
    }

    @JsonProperty("emision")
    public void setEmision(String emision) {
    this.emision = emision;
    }

    @JsonProperty("estado")
    public Integer getEstado() {
    return estado;
    }

    @JsonProperty("estado")
    public void setEstado(Integer estado) {
    this.estado = estado;
    }

    @JsonProperty("monto")
    public Integer getMonto() {
    return monto;
    }

    @JsonProperty("monto")
    public void setMonto(Integer monto) {
    this.monto = monto;
    }

    @JsonProperty("numero")
    public String getNumero() {
    return numero;
    }

    @JsonProperty("numero")
    public void setNumero(String numero) {
    this.numero = numero;
    }

    @JsonProperty("cliente")
    public Cliente getCliente() {
    return cliente;
    }

    @JsonProperty("cliente")
    public void setCliente(Cliente cliente) {
    this.cliente = cliente;
    }

}