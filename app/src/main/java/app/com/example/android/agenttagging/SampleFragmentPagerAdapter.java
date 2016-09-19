package app.com.example.android.agenttagging;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by shuvam on 09-09-2016.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"All", "Tagged"};
    private FragmentManager fragmentManager;
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
    }

    public SampleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS_FILE, 0);
        //long patientId = prefs.getLong(Constants.SELECTED_PATIENT_ID, 1);
        //Fragment fragment = null;
        switch (position) {
            case 0:
                return NotifyFragAll.newInstance(position);
            case 1:
                return NotifyFragTagged.newInstance(position + 1);
            default:
                return NotifyFragAll.newInstance(position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
