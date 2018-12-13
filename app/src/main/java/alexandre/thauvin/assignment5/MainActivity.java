package alexandre.thauvin.assignment5;

import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity implements  TrainersFragment.OnListFragmentInteractionListener,  CoursesFragment.OnListFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.dashboard, R.drawable.baseline_dashboard_black_24dp, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.courses, R.drawable.baseline_account_balance_black_24dp, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.trainers, R.drawable.baseline_accessibility_black_24dp, R.color.colorAccent);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.travelplan, R.drawable.baseline_cloud_upload_black_18dp, R.color.colorAccent);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        bottomNavigation.setForceTint(true);
        bottomNavigation.setCurrentItem(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new HomeFragment()).commit();


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position){
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new HomeFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new CoursesFragment()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new TrainersFragment()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new UploadFragment()).commit();
                        break;
                    default :
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, new HomeFragment()).commit();

                }
                return true;
            }
        });
    }

    public void onListFragmentInteraction(Trainer item) {

    }

    public void onListFragmentInteraction(Course item) {

    }

    public void onFragmentInteraction(Uri uri) {
    }
}
