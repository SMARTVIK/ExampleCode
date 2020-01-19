package com.spf.panditji.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.spf.panditji.R;

import static com.google.android.gms.common.util.CollectionUtils.setOf;

public class HomeAct extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
    }

    private void setUpDrawerLayout() {
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));


        appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();



        NavigationUI.setupActionBarWithNavController(this,navController);
        NavigationUI.navigateUp(navController,drawerLayout);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

}
