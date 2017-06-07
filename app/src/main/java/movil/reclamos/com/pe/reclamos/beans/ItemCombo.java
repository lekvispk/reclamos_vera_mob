package movil.reclamos.com.pe.reclamos.beans;

/**
 * Created by elvis on 4/6/2017.
 */

public class ItemCombo {

    private Integer codigo;
    private String nombre;
    private String extra;

    public ItemCombo(){}

    public ItemCombo(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public ItemCombo(Integer codigo, String nombre,String extra) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.setExtra(extra);
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
