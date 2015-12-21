package jdqp.navigationdrawertytabs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //    todo el contenido
    private DrawerLayout drawerLayout;

    /**
     * variables para las notificaicones de barra de estado
     */
    private NotificationManager nm;
    private static final int ID_NOTIFICACION_CREAR = 1;

    /**
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * variables para las notificaicones de barra de estado
         */
//        int icon = R.drawable.busqueda;
//        CharSequence tickerText = "Esto es una notificaion de prueba";
//        long when = System.currentTimeMillis();
//        Notification notification = new Notification(icon, tickerText, when);
//
//
//        Context context = getApplicationContext();
//        CharSequence contentTitle = "My notification";
//        CharSequence contentText = "Hello World!";
//        Intent notificationIntent = new Intent(this, this.getClass());
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        /**
         *
         */
        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            prepararDrawer(navigationView);
            // Seleccionar item por defecto
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

    }

    private void triggerNotification(Class  n) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.busqueda, "¡Nuevo mensaje de notificaion!", System.currentTimeMillis());

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);
        contentView.setImageViewResource(R.id.img_notificacion, R.drawable.coctel);
        contentView.setTextViewText(R.id.txt_notificaion, "Ey mundo! Esta es mi notificación personalizada.");

        notification.contentView = contentView;

        Intent notificationIntent = new Intent(this,n);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.contentIntent = contentIntent;

        notificationManager.notify(ID_NOTIFICACION_CREAR, notification);
    }

    private void prepararDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (itemDrawer.getItemId()) {
            case R.id.item_inicio:
                fragmentoGenerico = new FragmentoInicio();
                break;
            case R.id.item_cuenta:
                fragmentoGenerico = new FragmentoCuenta();
                break;
            case R.id.item_categorias:
//                fragmentoGenerico = new FragmentoCategorias();
                break;
            case R.id.item_configuracion:
//                startActivity(new Intent(this, ActividadConfiguracion.class));
                break;
        }
        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

        // Setear título actual
        setTitle(itemDrawer.getTitle());
    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_alerta:
                Toast.makeText(this, "notificaion de prueba", Toast.LENGTH_LONG).show();
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        triggerNotification(MainActivity.class);
                    }
                };
                timer.schedule(timerTask, 3000);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
