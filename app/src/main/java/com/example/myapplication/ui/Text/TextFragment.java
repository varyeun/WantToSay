package com.example.myapplication.ui.Text;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TextFragment extends Fragment {
    private ListView newsListView;
    private List<News> newsList;
    private Button search;
    private Button self;
    private LinearLayout company1;
    private LinearLayout company2;
    private LinearLayout company3;
    private LinearLayout company4;
    private LinearLayout company5;

    private DashboardViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_text, container, false);
        newsListView = (ListView)root.findViewById(R.id.NewsListView);
        newsList = new ArrayList<News>();
        newsList.add(new News("news 1","nytimes","2020-05-03","BOSTON — A sailboat race from Cape Cod to the island of Nantucket has marked the unofficial beginning of summer for the last 49 years. But the Figawi regatta, which raises money for veterans over Memorial Day weekend, will not involve any actual boats this year. Instead, organizers will host a virtual cocktail party from a boathouse, among other online events."));
        newsList.add(new News("news 2","nytimes","2020-05-03","At first, regulars vowed to sail from Hyannis to Nantucket anyway, said Shelley Hill, executive director of Figawi Charities. \"But as time went on and everybody learned more,\" she said, \"that idea has gone away.\""));
        newsList.add(new News("news 3","economist","2020-05-03","Crowded parades. Mobbed beaches. Congested public ceremonies. Jam-packed backyard barbecues. Memorial Day, which has come to mark the beginning of hot weather across much of the United States, typically brings millions of Americans shoulder to shoulder, towel to towel.\n" +
                "\n"));
        newsList.add(new News("news 4","economist","2020-05-03","But this year these first rites of summer are taking place as the country grapples with the coronavirus pandemic and cautiously emerges from two months of quarantine. Cooped-up Americans are eager for social interaction and fun. Yet public health officials warn that those impulses could result in an uptick in coronavirus cases."));
        newsList.add(new News("news 5","economist","2020-05-03","Many traditional Memorial Day events have been canceled or replaced with socially distant formats. Elected officials and event organizers are struggling to bring back as much normalcy as possible without jeopardizing public health. The results have been hopeful, maddening and bewildering. But many Americans are pressing on, and trying to preserve what is important while letting go of what is not."));
        newsList.add(new News("news 6","bbc","2020-05-03","A Memorial Day parade from Vidalia, La., to the Natchez National Cemetery in Mississippi has roots going back to 1867. But instead of marching this time, people will motorcade in masks and gloves to let veterans know \"that they have not been forgotten,\" said Laura Ann Jackson, co-chair of the parade."));
        NewsListAdapter adapter = new NewsListAdapter(getActivity(),newsList);
        newsListView.setAdapter(adapter);
        newsListView.setVerticalScrollBarEnabled(false);
        setListViewHeightBasedOnChildren(newsListView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),NewsClicked.class);

                intent.putExtra("title",newsList.get(position).getTitle());
                intent.putExtra("context",newsList.get(position).getContext());
                startActivity(intent);
            }
        });

        //신문사버튼 활성
        company1 = root.findViewById(R.id.company1);
        company2 = root.findViewById(R.id.company2);
        company3 = root.findViewById(R.id.company3);
        company4 = root.findViewById(R.id.company4);
        company5 = root.findViewById(R.id.company5);
        company1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KoreaTimesClicked.class);
                startActivity(intent);
            }
        });
        company2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ABCClicked.class);
                startActivity(intent);
            }
        });
        company3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KoreaHeraldClicked.class);
                startActivity(intent);
            }
        });
        company4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BBCClicked.class);
                startActivity(intent);
            }
        });
        company5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReutersClicked.class);
                startActivity(intent);
            }
        });

        //검색버튼 활성
        search = (Button)root.findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                search();
            }

        });

        //직접입력버튼 활성
        self = (Button)root.findViewById(R.id.btnSelf);
        self.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                self();
            }

        });

        return root;
    }
    private void search() {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(intent);
    }
    private void self() {
        Intent intent = new Intent(getContext(), SelfInput.class);
        startActivity(intent);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0, len = listAdapter.getCount(); i<len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
            //if(i==6) break;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
