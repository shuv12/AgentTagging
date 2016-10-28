package app.com.example.android.agenttagging;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by shuvam on 12-09-2016.
 */
public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String[] tabTitles = new String[]{"All", "For rent", "For sale"};
    String[] noOfPost = new String[3];
    private FragmentManager fragmentManager;
    private Context context;

   /* public static final String TOTALRENT = "totalrent";
    public static final String TOTALSALE = "totalsale";
    public static final String TOTALPROPERTY = "totalproperty";
    public static final String UserPREFERENCES = "UserPrefs" ;*/

    public ProfileFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
    }

    public ProfileFragmentPagerAdapter(FragmentManager fm, Context context,String[] noOfPost) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
        this.noOfPost = noOfPost;
        Log.v("count",noOfPost.toString());
    }

    public ProfileFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        //return PropertyFragAll.newInstance(position + 1);
        switch (position) {
            case 0:
                return PropertyFragAll.newInstance(position);
            case 1:
                return PropertyFragRent.newInstance(position + 1);
            case 2:
                return PropertyFragSale.newInstance(position + 2);
            default:
                return PropertyFragAll.newInstance(position);
        }
    }

    public View getTabView(int positon) {
        View v = LayoutInflater.from(context).inflate(R.layout.tablayout, null);

        /*SharedPreferences sharedPreferences = context.getSharedPreferences(UserPREFERENCES,MODE_PRIVATE);
        noOfPost[0] = sharedPreferences.getString(TOTALPROPERTY,null);
        noOfPost[1] = sharedPreferences.getString(TOTALRENT,null);
        noOfPost[2] = sharedPreferences.getString(TOTALSALE,null);*/
     //   Log.v("Show total property : ",noOfPost[0]);
       // Log.v("Show rent property : ",noOfPost[1]);
       // Log.v("Show sale property : ",noOfPost[2]);

        TextView title = (TextView) v.findViewById(R.id.titletext);
        title.setText(tabTitles[positon]);
        TextView noPost = (TextView) v.findViewById(R.id.noofposttext);
        noPost.setText(noOfPost[positon]);
        return v;
    }
}

