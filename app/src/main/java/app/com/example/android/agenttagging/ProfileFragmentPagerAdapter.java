package app.com.example.android.agenttagging;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shuvam on 12-09-2016.
 */
public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "All", "For rent", "For sale" };
    private String noOfPost[] = new String[]{"55","22","33"};
    private FragmentManager fragmentManager;
    private Context context;

    public ProfileFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
    }

    public ProfileFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return PropertyFragAll.newInstance(position + 1);
        //SharedPreferences prefs = context.getSharedPreferences(Constants.SHARED_PREFS_FILE, 0);
        //long patientId = prefs.getLong(Constants.SELECTED_PATIENT_ID, 1);
        //Fragment fragment = null;
        //switch (position){
        //case 0:
        //return PropertyFragAll.newInstance(position);
        // case 1:
        //    return NotifyFragTagged.newInstance(position + 1);
        //  default:
        //    return NotifyFragAll.newInstance(position);
    }

    //@Override
    //public CharSequence getPageTitle(int position) {
        // Generate title based on item position
      //  return tabTitles[position];
    //}

    public View getTabView(int positon){
        View v = LayoutInflater.from(context).inflate(R.layout.tablayout,null);
        TextView title = (TextView) v.findViewById(R.id.titletext);
        title.setText(tabTitles[positon]);
        TextView noPost = (TextView) v.findViewById(R.id.noofposttext);
        noPost.setText(noOfPost[positon]);
        return v;
    }
}

