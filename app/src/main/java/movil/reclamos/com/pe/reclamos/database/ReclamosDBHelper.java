package movil.reclamos.com.pe.reclamos.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.beans.Usuario;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;

/**
 * Created by elvis on 2/9/2017.
 */

public class ReclamosDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Reclamos.db";
    public static final String PARAMETRO_IP = "PARAMETRO_IP";
    private static final String _TAG_ =  "REC";

    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE usuario (" +
            "  id INTEGER NOT NULL ," +
            "  id_persona INTEGER NOT NULL ," +
            "  id_cliente INTEGER NOT NULL ," +
            "  username VARCHAR NULL," +
            "  nombres VARCHAR NULL," +
            "  apellidos VARCHAR NULL," +
            "  correo VARCHAR NULL," +
            "  estado INTEGER NULL," +
            "  PRIMARY KEY(id)" +
            ")" ;

    private static final String CREATE_TABLE_PARAMETRO = "CREATE TABLE parametros (" +
            "  id INTEGER NOT NULL ," +
            "  codigo VARCHAR NULL," +
            "  valor VARCHAR NULL," +
            "  estado INTEGER NULL," +
            "  PRIMARY KEY(id)" +
            ")" ;

    public ReclamosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ReclamosDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(_TAG_," CREAR TABLAS ");
        db.execSQL( CREATE_TABLE_USUARIO );
        db.execSQL( CREATE_TABLE_PARAMETRO );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + ReclamoContract.UsuarioEntry.TABLE_NAME );
        db.execSQL("DROP TABLE IF EXISTS " + ReclamoContract.ParametrosEntry.TABLE_NAME );

        // create new tables
        onCreate(db);

    }

    public long saveReclamo(Parametro parametro) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Log.d( _TAG_ , " Insertar parametro  = " + parametro.toContentValues() );
        return sqLiteDatabase.insert(
                ReclamoContract.ParametrosEntry.TABLE_NAME,
                null,
                parametro.toContentValues());

    }

    public long updateReclamo(Parametro parametro) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Log.d( _TAG_ , " Insertar parametro  = " + parametro.toContentValues() );

        String[] params = new String[1];
        params[0] = parametro.getId().toString();
        String selection = ReclamoContract.ParametrosEntry.ID + " = ?";
        return sqLiteDatabase.update(
                ReclamoContract.ParametrosEntry.TABLE_NAME,
                parametro.toContentValues(),
                selection,
                params);

    }

    public Parametro getParametro( String codigo ){
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("");
        query.append("select * from ");
        query.append(ReclamoContract.ParametrosEntry.TABLE_NAME);
        query.append(" where ").append(ReclamoContract.ParametrosEntry.CODIGO).append("='"+ codigo + "'");
        query.append(" and ").append(ReclamoContract.ParametrosEntry.ESTADO).append("=1");
        Cursor c =  db.rawQuery(query.toString() , null );

        Parametro parametro = null;

        if(c.moveToNext()){
            parametro = new Parametro();
            parametro.setId( c.getInt(c.getColumnIndex(ReclamoContract.ParametrosEntry.ID)) );
            parametro.setCodigo( c.getString(c.getColumnIndex(ReclamoContract.ParametrosEntry.CODIGO)) );
            parametro.setValor( c.getString(c.getColumnIndex(ReclamoContract.ParametrosEntry.VALOR)) );
            parametro.setEstado( c.getInt(c.getColumnIndex(ReclamoContract.ParametrosEntry.ESTADO)) );
            Log.d(_TAG_," parametro obtenido " );
        }

        return parametro;
    }

    /** USUARIOS  INI */

    /**
     * Retorna el usuario activo en el sistema
     * @return usuario activo en el sistema
     */
    public Usuario getUsuario( ){
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("");
        query.append("select * from ");
        query.append(ReclamoContract.UsuarioEntry.TABLE_NAME);
        query.append(" where ").append(ReclamoContract.UsuarioEntry.ESTADO).append("=1");
        Cursor c =  db.rawQuery(query.toString() , null );

        Usuario usuario = null;

        if(c.moveToNext()){
            usuario = new Usuario();
            usuario.setId( c.getInt(c.getColumnIndex(ReclamoContract.UsuarioEntry.ID)) );
            usuario.setNombres( c.getString(c.getColumnIndex(ReclamoContract.UsuarioEntry.NOMBRES)) );
            usuario.setApellidos( c.getString(c.getColumnIndex(ReclamoContract.UsuarioEntry.APELLIDOS)) );
            usuario.setUsername( c.getString(c.getColumnIndex(ReclamoContract.UsuarioEntry.USERNAME)) );
            usuario.setCorreo( c.getString(c.getColumnIndex(ReclamoContract.UsuarioEntry.CORREO)) );
            usuario.setEstado( c.getInt(c.getColumnIndex(ReclamoContract.UsuarioEntry.ESTADO)) );
            usuario.setIdPersona( c.getInt(c.getColumnIndex(ReclamoContract.UsuarioEntry.IDPERSONA)) );
            usuario.setIdCliente( c.getInt(c.getColumnIndex(ReclamoContract.UsuarioEntry.IDCLIENTE)) );
            Log.d(_TAG_," usuario obtenido " );
        }

        return usuario;
    }

    /**
     * Solo debe existir un usuario activo en la base de datos
     * estado = 1
     * @return
     */
    public Boolean existeUsuarioActivo( ){
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("");
        query.append("select * from ").append(ReclamoContract.UsuarioEntry.TABLE_NAME);
        query.append(" where estado=1 ");

        Log.d( _TAG_ , " query " + query.toString() );

        Cursor c =  db.rawQuery(query.toString() , null );
        Boolean usuario = false;

        if ( c.getCount() > 0 ){

            if(c.moveToNext()){
                /*
                Log.d( _TAG_ , " id " + c.getString(c.getColumnIndex(LicenciaContract.UsuarioEntry.ID  ))  +
                        " nombres " + c.getString(c.getColumnIndex(LicenciaContract.UsuarioEntry.NOMBRES  )) +
                        " estado " + c.getString(c.getColumnIndex(LicenciaContract.UsuarioEntry.ESTADO  )) );
                */
                usuario = true;
            }

        }

        return usuario;
    }

    /**
     * devuelve el id que debe usuar el nuevo usuario
     * @return
     */
    public Integer getSiguienteIdUsuario( ){
        SQLiteDatabase db = this.getReadableDatabase();

        StringBuilder query = new StringBuilder("select max(id) as id from ");
        query.append(ReclamoContract.UsuarioEntry.TABLE_NAME);
        Cursor c =  db.rawQuery(query.toString() , null );

        Integer usuario = null;

        if(c.moveToNext()){
            usuario = c.getInt(c.getColumnIndex(ReclamoContract.UsuarioEntry.ID));
            usuario++;
            Log.d(_TAG_," nuevo id usuario = " + usuario );
        }

        return usuario;
    }

    public long saveUsuario(Usuario usuario) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(ReclamoContract.UsuarioEntry.TABLE_NAME, null, usuario.toContentValues());
    }

    public boolean updateUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.update(ReclamoContract.UsuarioEntry.TABLE_NAME, contentValues, "id = ? ", new String[] { usuario.getId().toString() } );
        return true;
    }

    public Integer deleteUsuario(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
       /* return db.delete(LicenciaContract.UsuarioEntry.TABLE_NAME, LicenciaContract.UsuarioEntry.ID + " = ? ", new String[] { Integer.toString(id) } );*/
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReclamoContract.UsuarioEntry.ESTADO,0);
        return db.update(ReclamoContract.UsuarioEntry.TABLE_NAME, contentValues, "id = ? ", new String[] { id.toString() } );
    }

    /*** USUARIOS FIN */
}
