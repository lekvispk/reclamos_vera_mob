package movil.reclamos.com.pe.reclamos.beans;

import android.content.ContentValues;

import movil.reclamos.com.pe.reclamos.database.ReclamoContract;

/**
 * Created by elvis on 2/9/2017.
 */

public class Parametro {

    private Integer id;
    private String codigo;
    private String valor;
    private Integer estado;

    public Parametro(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(ReclamoContract.ParametrosEntry.ID, this.id);
        values.put(ReclamoContract.ParametrosEntry.CODIGO, this.codigo);
        values.put(ReclamoContract.ParametrosEntry.VALOR, this.valor);
        values.put(ReclamoContract.ParametrosEntry.ESTADO, this.estado);
        return values;
    }
}
