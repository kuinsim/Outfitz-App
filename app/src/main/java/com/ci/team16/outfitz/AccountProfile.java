package com.ci.team16.outfitz;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static com.ci.team16.outfitz.user_acc.newInstance;
import static java.sql.DriverManager.println;

public class AccountProfile extends FragmentActivity {

    private static final int NUM_PAGES = 2;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    public ArrayList<String> feed_list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new AccountProfileAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);






       /* //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String user = bundle.getString("username");
        Toast.makeText(AccountProfile.this, user, Toast.LENGTH_SHORT).show();*/


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class AccountProfileAdapter extends FragmentPagerAdapter {
        public AccountProfileAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = getIntent().getExtras();
            String user = bundle.getString("username");
            String uid = bundle.getString("uid");
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment

                    feed fragmentFirst = feed.newInstance(user, uid);
                    return fragmentFirst;
                case 1: // Fragment # 0 - This will show FirstFragment different title


                    //Extract the data…

                    Log.d("name","some");
                    Log.d("uid", uid);
                    user_acc fragmentSecond = user_acc.newInstance(user, uid);
                    return fragmentSecond;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Feed";
            }
            else {
                return "Account";
            }
        }
    }
}
