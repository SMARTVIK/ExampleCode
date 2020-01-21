package com.spf.panditji.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.spf.panditji.ApplicationDataController;
import com.spf.panditji.R;
import com.spf.panditji.listener.OnItemClick;
import com.spf.panditji.model.CategoryModel;
import com.spf.panditji.model.PagerModel;
import com.spf.panditji.model.PopularPanditModel;
import com.spf.panditji.model.PopularPoojaModel;
import com.spf.panditji.util.ApiUtil;
import com.spf.panditji.view.CategoryListActivity;
import com.spf.panditji.view.DetailScreen;
import com.spf.panditji.view.PanditProfile;
import com.spf.panditji.view.PopularPoojaAdapter;
import com.spf.panditji.view.RoundImageAdapter;
import com.spf.panditji.view.RoundRectCornerImageView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RoundImageAdapter categoriesAdapter;
    private PopularPoojaAdapter popularPoojaAdapter;
    private PopularPanditAdapter popularPanditAdapter;
    private List<CategoryModel> categoriesList;
    private List<PagerModel> pageModels = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setUpBookByCategory(view);
        setUpPopularPoojaList(view);
        setUpPopularPanditList(view);

        getHomeCat(view);
        getBookByCategory();
        getPopularPoojaList();
        getPopularPanditList();
    }

    private void getHomeCat(final View view) {

        ApiUtil.getInstance().getHomeCat(new Callback<List<PagerModel>>() {

            @Override
            public void onResponse(Call<List<PagerModel>> call, Response<List<PagerModel>> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    eventImagesUrl = response.body();
                    setUpViewPager(view);
                    homeViewPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PagerModel>> call, Throwable t) {



            }
        });

    }

    private void initViews(View view) {
        TextView viewAllCategory = view.findViewById(R.id.view_all_bbc);
        TextView viewAllPooja = view.findViewById(R.id.view_all_popular_pooja);
        TextView viewAllPurohit = view.findViewById(R.id.view_all_purohit);

        viewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              startActivity(new Intent(getContext(),ViewAllCategories.class).putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) categoriesList));

            }
        });

        viewAllPooja.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



            }
        });

        viewAllPurohit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });
    }

    private void getPopularPanditList() {

        ApiUtil.getInstance().getPopularPanditList(new Callback<List<PopularPanditModel>>() {

            @Override
            public void onResponse(Call<List<PopularPanditModel>> call, Response<List<PopularPanditModel>> response) {

                if(response.isSuccessful() && response.code() == 200){
                    popularPanditAdapter.setData(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<PopularPanditModel>> call, Throwable t) {

            }
        });

    }

    private LinearLayout llPagerDots;
    private ViewPager viewPager;
    private List<PagerModel> eventImagesUrl = new ArrayList<>();
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private ImageView[] ivArrayDotsPager = new ImageView[3];

    public void setUpViewPager(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        llPagerDots = (LinearLayout) view.findViewById(R.id.pager_dots);
        homeViewPagerAdapter = new HomeViewPagerAdapter(getContext(), eventImagesUrl);
        viewPager.setAdapter(homeViewPagerAdapter);
        setupPagerIndidcatorDots();
        if (ivArrayDotsPager.length > 0) {
            ivArrayDotsPager[0].setImageResource(R.drawable.selected);
        }

        viewPager.setClipToPadding(false);
        viewPager.setPadding(0,0,0,0);
        viewPager.setPageMargin(50);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < ivArrayDotsPager.length; i++) {
                    ivArrayDotsPager[i].setImageResource(R.drawable.unselected);
                }
                ivArrayDotsPager[position].setImageResource(R.drawable.selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getPopularPoojaList() {

        ApiUtil.getInstance().getPopularPoojaList(new Callback<List<PopularPoojaModel>>() {

            @Override
            public void onResponse(Call<List<PopularPoojaModel>> call, Response<List<PopularPoojaModel>> response) {

                if(response.isSuccessful() && response.code() == 200){
                    popularPoojaAdapter.setData(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<PopularPoojaModel>> call, Throwable t) {

            }
        });

    }

    private void getBookByCategory() {
        ApiUtil.getInstance().getCategories(new Callback<List<CategoryModel>>() {

            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    categoriesList = response.body();
                    categoriesAdapter.setData(response.body());
                    ApplicationDataController.getInstance().setCategoriesList(categoriesList);
                }

            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    private void setUpBookByCategory(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.book_by_cat_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        categoriesAdapter = new RoundImageAdapter(new OnItemClick<CategoryModel>() {
            @Override
            public void onClick(CategoryModel categoryModel) {

                startActivity(new Intent(getContext(), CategoryListActivity.class).putExtra("cat",categoryModel.getCat()));

            }
        });
        recyclerView.setAdapter(categoriesAdapter);
    }

    private void setUpPopularPoojaList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.trending_pooja_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        popularPoojaAdapter = new PopularPoojaAdapter(new OnItemClick<PopularPoojaModel>() {
            @Override
            public void onClick(PopularPoojaModel popularPoojaModel) {

                startActivity(new Intent(getContext(), DetailScreen.class).putExtra("id",popularPoojaModel.getId()));

            }
        });
        recyclerView.setAdapter(popularPoojaAdapter);
    }

    private void setUpPopularPanditList(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.popular_pandit_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        popularPanditAdapter = new PopularPanditAdapter(new OnItemClick<PopularPanditModel>() {
            @Override
            public void onClick(PopularPanditModel popularPanditModel) {
                startActivity(new Intent(getContext(), PanditProfile.class).putExtra("pandit_model",popularPanditModel));
            }
        });
        recyclerView.setAdapter(popularPanditAdapter);
    }

    private void setupPagerIndidcatorDots() {
        ivArrayDotsPager = new ImageView[eventImagesUrl.size()];
        for (int i = 0; i < ivArrayDotsPager.length; i++) {
            ivArrayDotsPager[i] = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            ivArrayDotsPager[i].setLayoutParams(params);
            ivArrayDotsPager[i].setImageResource(R.drawable.unselected);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            ivArrayDotsPager[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            llPagerDots.addView(ivArrayDotsPager[i]);
            llPagerDots.bringToFront();
        }

    }

    private int getUrlToLoad(int resourceId) {
        String resourceScheme = "res";
        Uri uri = new Uri.Builder()
                .scheme(resourceScheme)
                .path(String.valueOf(resourceId))
                .build();
        return resourceId;
    }

    private class HomeViewPagerAdapter extends PagerAdapter {

        private List<PagerModel> eventImagesUrl;

        public HomeViewPagerAdapter(Context context, List<PagerModel> eventImagesUrl) {
            this.eventImagesUrl = eventImagesUrl;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.pager_item,null);
            final PagerModel pagerModel =  eventImagesUrl.get(position);
            RoundRectCornerImageView imageView = view.findViewById(R.id.image);

            String baseUrl = "https://vaidiksewa.in/img_big/";
            Glide.with(getContext())
                    .load(baseUrl+pagerModel.getImg())
                    .into(imageView);
            TextView textView = view.findViewById(R.id.pooja_name);
            TextView bookNow = view.findViewById(R.id.book_now);
            textView.setText(pagerModel.getName());
            bookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookPooja(pagerModel);
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return eventImagesUrl.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    private void bookPooja(PagerModel pagerModel) {
        startActivity(new Intent(getContext(), DetailScreen.class).putExtra("id",pagerModel.getId()));
    }
}
