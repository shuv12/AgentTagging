package app.com.example.android.agenttagging;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.com.example.android.agenttagging.adapter.NotificationsAdapter;
import app.com.example.android.agenttagging.model.NotificationsModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragAll extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<NotificationsModel> notificationsModelList;
    String forrent = "for rent";
    String forsale = "for sale";
    String pending = "Pending";
    String approved = "Approved";
    String denied = "Denied";


    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;


    String[] notiUser = new String[]{"Shuvam Agrawal","Suman Jung","Abin Rimal","Ashish","Vishal","Pujan","Shuvam Agrawal","Suman Jung","Abin Rimal","Ashish","Vishal","Pujan"};
    String[] notiproAdd = new String[]{"22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street"};
    String[] notiproType = new String[]{"Apartment", "Bunglo", "Flat", "Open space", "Shutter", "Apartment", "HBD", "Flat", "Bunglo"};
    String[] notiforType = new String[]{forrent,forsale,forrent,forsale,forsale,forsale,forsale,forrent,forsale};
    String[] notiStatus = new String[]{approved,"tag",pending,approved,denied,"requested",approved,pending,pending,approved,approved};

    public static NotifyFragAll newInstance(int page) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        NotifyFragAll fragment = new NotifyFragAll();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notify_frag_all, container, false);

        this.notificationsModelList = new ArrayList<>();
        for (int i=0; i<8; i++){
            NotificationsModel notificationsModel = new NotificationsModel();
            notificationsModel.setNotiforType(this.notiforType[i]);
            notificationsModel.setNotiproadd(this.notiproAdd[i]);
            notificationsModel.setNotiproType(this.notiproType[i]);
            notificationsModel.setNotiStatus(this.notiStatus[i]);
            notificationsModel.setNotiUser(this.notiUser[i]);
            this.notificationsModelList.add(notificationsModel);
        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclenotipost);
        final NotificationsAdapter notificationsAdapter = new NotificationsAdapter(getContext(),notificationsModelList);
        recyclerView.setAdapter(notificationsAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

}
