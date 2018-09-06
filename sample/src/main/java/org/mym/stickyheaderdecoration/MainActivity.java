package org.mym.stickyheaderdecoration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.mym.ui.decoration.library.Section;
import org.mym.ui.decoration.library.StickyHeaderDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btChange;
    private MyAdapter mAdapter;
    private List<Section> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = ((RecyclerView) findViewById(R.id.main_recyclerView));
        btChange = (Button) findViewById(R.id.bt_change);

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(0);
                mAdapter.updateSectionList(dataList);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> stringListA = new ArrayList<>();
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        stringListA.add("a");
        Section<String, String> section1 = new Section<>("A", stringListA);

        List<String> stringListB = new ArrayList<>();
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        stringListB.add("b");
        Section<String, String> section2 = new Section<>("B", stringListB);

        List<String> stringListC = new ArrayList<>();
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");
        stringListC.add("c");

        Section<String, String> section3 = new Section<>("C", stringListC);

        List<String> stringListD = new ArrayList<>();
        stringListD.add("d");
        stringListD.add("d");
        stringListD.add("d");
        stringListD.add("d");
        stringListD.add("d");
        stringListD.add("d");
        Section<String, String> section4 = new Section<>("D", stringListD);

        dataList = new ArrayList<>();
        dataList.add(section1);
        dataList.add(section2);
        dataList.add(section3);
        dataList.add(section4);

        mAdapter = new MyAdapter(dataList);
        recyclerView.setAdapter(mAdapter);

        StickyHeaderDecoration mDecoration = new StickyHeaderDecoration(mAdapter);
        recyclerView.addItemDecoration(mDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
