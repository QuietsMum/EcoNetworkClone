package com.econetwork.ui.shop;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.econetwork.PartnersViewTypeAdapter;
import com.econetwork.R;
import com.econetwork.ui.dashboard.DashboardViewModel;
import com.econetwork.ui.dashboard.EventsModelMainItem;
import com.econetwork.ui.dashboard.Eventsadapter;
import com.econetwork.ui.dashboard.NewsAdapter;
import com.econetwork.ui.dashboard.NewsModelMainItem;
import com.econetwork.ui.home.Partners_model;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    FragmentContainerView fragmentContainerView;
    RecyclerView mRecyclerViewPromo;
    RecyclerView mRecyclerViewSales;
    ConstraintLayout.LayoutParams layoutParams;
    ConstraintLayout.LayoutParams layoutParams_old;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                ViewModelProviders.of(this).get(ShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        fragmentContainerView = getActivity().findViewById(R.id.nav_host_fragment);
        layoutParams_old = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                55,
                getActivity().getResources().getDisplayMetrics()
        );

        layoutParams = (ConstraintLayout.LayoutParams) fragmentContainerView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, px);
        fragmentContainerView.setLayoutParams(layoutParams);

        NestedScrollView scrollView = (NestedScrollView) root.findViewById (R.id.scroll_nest_shop);
        scrollView.setFillViewport (true);

        ArrayList<PromoModelMain> list2= new ArrayList();
        list2.add(new PromoModelMain("Посадка деревьев в Дендрарии", R.drawable.promo1));
        list2.add(new PromoModelMain("Посадка деревьев в Дендрарии", R.drawable.promo2));
        list2.add(new PromoModelMain("Посадка деревьев в Дендрарии", R.drawable.promo3));
        list2.add(new PromoModelMain("Посадка деревьев в Дендрарии", R.drawable.promo4));

        PromoAdapter adapter2 = new PromoAdapter(list2,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.HORIZONTAL, false);
        mRecyclerViewPromo = root.findViewById(R.id.promocontainer);
        mRecyclerViewPromo.setLayoutManager(linearLayoutManager2);
        mRecyclerViewPromo.setAdapter(adapter2);

        ArrayList<SalesModelMain> list_partners= new ArrayList();
        list_partners.add(new SalesModelMain("Наш новый партнер -компания Nike!",R.drawable.nike));
        list_partners.add(new SalesModelMain("Теперь вы можете приобрести эко-товары со скидкой 15%",R.drawable.sber));
        list_partners.add(new SalesModelMain("Starbucks запустили новые товары",R.drawable.starbucks));

        SalesAdapterMain adapter3= new SalesAdapterMain(list_partners,getActivity().getApplicationContext());
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity().getApplicationContext(), RecyclerView.VERTICAL, false);
        mRecyclerViewSales = root.findViewById(R.id.sales_container);
        mRecyclerViewSales.setLayoutManager(linearLayoutManager3);
        mRecyclerViewSales.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewSales.setAdapter(adapter3);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentContainerView.setLayoutParams(layoutParams);
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentContainerView.setLayoutParams(layoutParams_old);
    }

}
