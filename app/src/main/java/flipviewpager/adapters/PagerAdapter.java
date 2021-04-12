package flipviewpager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import Models.Restourant;
import flipviewpager.view.RestViewPager;

/**
 * Created by 208-it-01 on 30.03.2018.
 */

public class PagerAdapter extends FragmentPagerAdapter
{
    private ArrayList<Restourant> list=new ArrayList<Restourant>();
    private int pos;
    public PagerAdapter(FragmentManager fm, ArrayList<Restourant> l) {
        super(fm);
        list=l;
    }

    @Override
    public Fragment getItem(int position) {
        int c=list.get(position).getCount();
        String t=list.get(position).getTitle();

        return (RestViewPager.newInstance(list.get(position).getCount(),
                                            list.get(position).getTitle(),
                                            list.get(position).getComment(),
                                            list.get(position).getRest_id(),
                                            list.get(position).getLat(),
                                            list.get(position).getLng(),
                                            list.get(position).getBusness(),
                                            list.get(position).getTableOnline(),
                                            list.get(position).getVitrina(),
                                            list.get(position).getMenuOnline(),
                                            list.get(position).getBancket(),
                                            list.get(position).getDayAnshlag(),
                                            list.get(position).getMoreClients(),
                                            list.get(position).getTelephone(),
                                            list.get(position).getTime_start(),
                                            list.get(position).getTime_stop(),
                                            list.get(position).getInfo(),
                                            list.get(position).getImage_url_main()));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
