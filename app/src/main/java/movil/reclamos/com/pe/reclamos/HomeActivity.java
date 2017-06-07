package movil.reclamos.com.pe.reclamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import movil.reclamos.com.pe.reclamos.beans.Usuario;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String _TAG_ =  "HOME";
    private ReclamosDBHelper dbHelper;
    private Usuario usuarioActivo;
    private TextView lblEmail, lblUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = null;
                intent = new Intent(getApplicationContext(), ReclamosActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View del menu desplegale
        View hView =  navigationView.getHeaderView(0);

        lblUsername = (TextView) hView.findViewById(R.id.txtUsername);
        lblEmail = (TextView) hView.findViewById(R.id.txtEmail);

        dbHelper = new ReclamosDBHelper( this );
        Usuario user = dbHelper.getUsuario();

        Log.d(_TAG_,"validando el usuario");
        if( user != null){
            usuarioActivo = user ;
            Log.d(_TAG_," pintando datos del usuario");

            Log.d(_TAG_," email " +lblEmail );
            Log.d(_TAG_," lblUsername " + lblUsername );

            lblEmail.setText( user.getCorreo());
            lblUsername.setText( user.getUsername() );

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = null;
            intent = new Intent(getApplicationContext(), ConfigActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.nav_listareclamos) {
            intent = new Intent(getApplicationContext(), ListaReclamoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_registroReclamo) {
            intent = new Intent(getApplicationContext(), ReclamosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_notificacion) {
            intent = new Intent(getApplicationContext(), NotificacionActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_salir) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Al hacer logout, debe eliminar los registros de la base de datos
     */
    private void logout() {
        Log.v(_TAG_, "logout... eliminar usuario id =  "  + usuarioActivo.getId() );
        dbHelper.deleteUsuario( usuarioActivo.getId() );
        dbHelper.verUsarios(  );
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }
}
