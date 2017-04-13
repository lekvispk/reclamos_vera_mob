package movil.reclamos.com.pe.reclamos.beans;

/**
 * Created by elvis on 2/7/2017.
 */

public class Reclamo {

    private String codigo;
    private String fecha;
    private String factura;
    private String estado;

    public Reclamo(){}

    public Reclamo(String codigo,String fecha,String factura,String estado){
        this.codigo=codigo;
        this.factura=factura;
        this.fecha=fecha;
        this.estado=estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
