package movil.reclamos.com.pe.reclamos.database;

import android.provider.BaseColumns;

/**
 * Created by ElvisRubén on 1/11/2016.
 */
public class ReclamoContract {

    public static abstract class ParametrosEntry implements BaseColumns {
        public static final String TABLE_NAME ="parametros";
        public static final String ID = "id";
        public static final String CODIGO = "codigo";
        public static final String VALOR = "valor";
        public static final String ESTADO = "estado";
    }

    public static abstract class UsuarioEntry implements BaseColumns{
        public static final String TABLE_NAME ="usuario";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String NOMBRES = "nombres";
        public static final String APELLIDOS = "apellidos";
        public static final String CORREO = "correo";
        public static final String IDPERSONA = "id_persona";
        public static final String IDCLIENTE = "id_cliente";
        public static final String RUC = "ruc";
        public static final String REPRESENTANTE = "representante";
        public static final String ESTADO = "estado";
    }

}
