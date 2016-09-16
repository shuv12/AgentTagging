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

import app.com.example.android.agenttagging.adapter.PropertyAdapter;
import app.com.example.android.agenttagging.model.PropertyModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PropertyFragAll extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<PropertyModel> propertyModelList;

    String[] propertyAddress = new String[]{"22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street", "22nd Jump Street", "23nd Jump Street", "24nd Jump Street"};
    String[] propertyHeadline = new String[]{"5th Avenue", "6th Avenue", "7th Avenue", "8th Avenue", "9th Avenue", "57th Avenue", "59th Avenue", "52th Avenue", "54th Avenue"};
    String[] propertyType = new String[]{"Apartment", "Bunglo", "Flat", "Open space", "Shutter", "Apartment", "HBD", "Flat", "Bunglo"};
    String[] propertyOwner = new String[]{"Shuvam", "Suman", "Abin", "Pujan", "Ashish", "Vishal", "Shuvam", "Abin", "Suman"};
    String[] propertyPrice = new String[]{"145000", "123666", "110000", "1444999", "345678", "99999", "876787", "564535", "123987"};

    public static PropertyFragAll newInstance(int page) {
        // Required empty public constructor
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PropertyFragAll fragment = new PropertyFragAll();
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
        View view = inflater.inflate(R.layout.fragment_property_frag_all, container, false);

        this.propertyModelList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            PropertyModel propertyModel = new PropertyModel();
            propertyModel.setPropertyAddress(this.propertyAddress[i]);
            propertyModel.setPropertyHeadline(this.propertyHeadline[i]);
            propertyModel.setPropertyOwner(this.propertyOwner[i]);
            propertyModel.setPropertyPrice(this.propertyPrice[i]);
            propertyModel.setPropertyType(this.propertyType[i]);
            this.propertyModelList.add(propertyModel);
        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclepropertyall);
        final PropertyAdapter propertyAdapter = new PropertyAdapter(getContext(), this.propertyModelList);
        recyclerView.setAdapter(propertyAdapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

}
