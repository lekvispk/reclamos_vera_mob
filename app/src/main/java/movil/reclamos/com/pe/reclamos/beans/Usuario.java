package movil.reclamos.com.pe.reclamos.beans;

import android.content.ContentValues;

/**
 * Created by ElvisRuben on 4/11/2016.
 */
public class Usuario {

    private Integer id;
    private String username;
    private String nombres;
    private String apellidos;
    private String correo;
    private Integer estado;
    private Integer idPersona;
    private Integer idCliente;
    private String ruc;
    private String representante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("username", getUsername());
        values.put("nombres", nombres);
        values.put("apellidos", apellidos);
        values.put("correo", correo);
        values.put("estado", estado);
        values.put("id_persona", getIdPersona());
        values.put("id_cliente", getIdCliente());
        values.put("ruc", ruc);
        values.put("representante", representante);
        return values;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }


    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", estado=" + estado +
                ", idPersona=" + idPersona +
                ", idCliente=" + idCliente +
                ", ruc='" + ruc + '\'' +
                ", representante='" + representante + '\'' +
                '}';
    }
}
