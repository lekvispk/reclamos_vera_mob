package movil.reclamos.com.pe.reclamos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;
import movil.reclamos.com.pe.reclamos.rest.beans.Usuario;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final String _TAG_ = "LOGIN";
    private ReclamosDBHelper dbHelper;
    private String ip;
    private HttpRequestLoginInternTask loginTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
       // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.login_title);

        dbHelper = new ReclamosDBHelper( this );
        obtenerIp();
        //Validar: si existe usuario, mostrar home
        if( dbHelper.existeUsuarioActivo() ){
            // no mostrar registro, ir directo al home
            Log.v(_TAG_, "ya existe un usuario en la bd, mostrar home");
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            //Con esto se elimina esta pantalla para evitar el mostrarla al dar clic en el boton Volver
            finish();
        }

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              //  populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        if (loginTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password,
        if ( TextUtils.isEmpty(password) ) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        obtenerIp();

        if( ip == null ){
            Toast.makeText(getApplicationContext(), "Ingrese a configuracion y coloque la ipdel servidor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            loginTask = new HttpRequestLoginInternTask();
            //mAuthTask.execute((Void) null);
            loginTask.execute( mEmailView.getText().toString() );

        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void obtenerIp(){

        Parametro tmpip = dbHelper.getParametro( ReclamosDBHelper.PARAMETRO_IP  );
        if( tmpip != null){
            this.ip = tmpip.getValor() ;
        }

    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    public void mostrarConfiguracion(View v){
        Log.d(_TAG_, "cargar pagina de configuracion" );
        Intent intent = null;
        intent = new Intent(getApplicationContext(), ConfigActivity.class);
        startActivity(intent);
    }

    public class HttpRequestLoginInternTask extends AsyncTask<String, Void, Usuario> {

        private static final String _TAG_ = "HttpLic";
        private String url = "http://"+ ip +":8080/reclamos/rest/auth/basic/{USUARIO}";
        private String errMsg = "";

        public Usuario obtenerDatosPorUsernameOEmail(String username){
            Log.v(_TAG_, "obtenerDatosPorUsernameOEmail ");
            Log.v(_TAG_, "url="+url);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Hashtable<String, String> variables = new Hashtable<String, String>();
            variables.put("USUARIO", username);
            return restTemplate.getForObject(url, Usuario.class, variables);
        }

        @Override
        protected Usuario doInBackground(String... params) {
            Usuario user = null;
            try {
                user = obtenerDatosPorUsernameOEmail(params[0]);
            }catch(HttpClientErrorException e){
                errMsg = "Error de conexion: " + e.getMessage();
                Log.e(_TAG_, "doInBackground-" + e.getMessage(), e);
            }catch (Exception e) {
                errMsg = "Error generico: " + e.getMessage();
                Log.e(_TAG_, "doInBackground-" + e.getMessage(), e);
            }
            return user;
        }

        protected void onProgressUpdate(Usuario usuario){
            Log.d(_TAG_ , "onProgressUpdate INI" );
            showProgress(false);
            if( !errMsg.equals("")){
                loginTask = null;
                Toast.makeText(getApplicationContext(), "Error: " +errMsg, Toast.LENGTH_SHORT).show();
            }
            Log.d(_TAG_ , "onProgressUpdate FIN" );
        }

        protected void onPostExecute(Usuario user) {
            Log.d(_TAG_ , "onPostExecute INI" );
            showProgress(false);
            loginTask = null;
            if( user != null){

                movil.reclamos.com.pe.reclamos.beans.Usuario u = new movil.reclamos.com.pe.reclamos.beans.Usuario();
                u.setId( dbHelper.getSiguienteIdUsuario() );
                u.setUsername( user.getUsername() );
                u.setCorreo( user.getEmail() );
                u.setNombres( user.getPersona().getNombres() );
                u.setApellidos( user.getPersona().getApePaterno() );
                u.setIdPersona( user.getPersona().getIdPersona() );
                u.setIdCliente( user.getPersona().getCliente().getIdCliente() );
                u.setEstado( 1 );
                dbHelper.saveUsuario( u );

                Log.v("auth", "autenticacion satisfactoria, mostrar home");
                Toast.makeText(getApplicationContext(), "Autenticacion satisfactoria", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivityForResult(intent, REQUEST_READ_CONTACTS);

            }else{
                String mensaje = "No se pudo obtenerel usuario" ;
                if(!errMsg.equals("")) mensaje = errMsg;
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
            Log.d(_TAG_ , "onPostExecute FIN" );
        }

        @Override
        protected void onCancelled() {
            Log.d(_TAG_ , "onCancelled INI" );
            loginTask = null;
            showProgress(false);
            Log.d(_TAG_ , "onCancelled FIN" );
        }

    }
}

