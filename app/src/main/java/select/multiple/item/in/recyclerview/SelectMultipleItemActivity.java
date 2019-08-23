package select.multiple.item.in.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;
public class SelectMultipleItemActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView selectAllTextView, deselectAllTextView;

    private RecyclerView recyclerView;
    private ArrayList<Model> itemArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;

    private Button deleteSelected,getSelected,reverseSelected,sendSelectedToNextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_multiple_item);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();
        recyclerViewSetup();
        initData();
        initEvents();
    }

    private void initView()
    {
        selectAllTextView = findViewById(R.id.select_all);
        deselectAllTextView = findViewById(R.id.de_select_all);

        recyclerView = findViewById(R.id.recycler_view);

        deleteSelected=findViewById(R.id.delete_selected_item);
        getSelected=findViewById(R.id.get_selected_item);
        reverseSelected=findViewById(R.id.reverse_selected_item);
        sendSelectedToNextActivity=findViewById(R.id.send_selected_item_to_next_activity);
    }

    private void recyclerViewSetup()
    {
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider_10dp)));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerViewItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Model currentRecyclerViewItem = itemArrayList.get(position);
                Toast.makeText(getApplicationContext(), "onItemClick "+currentRecyclerViewItem.getItemTitle(), Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.checkCheckBox(position, !(recyclerViewAdapter.isSelected(position)));
            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                Model currentRecyclerViewItem = itemArrayList.get(position);
                Toast.makeText(getApplicationContext(), "onItemLongClick "+currentRecyclerViewItem.getItemTitle(), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void initData()
    {
        itemArrayList = new ArrayList<>();
        itemArrayList.add(new Model(1, "Salmon Teriyaki", "Roasted salon dumped in soa sauce and mint", 140, R.drawable.one));
        itemArrayList.add(new Model(2, "Grilled Mushroom and Vegetables", "Spcie grills mushrooms, cucumber, apples and lot more", 150, R.drawable.two));
        itemArrayList.add(new Model(3, "Chicken Overload Meal", "Grilled chicken & tandoori chicken in masala curry", 185, R.drawable.three));
        itemArrayList.add(new Model(4, "Chinese Egg Fry", "Exotic eggs Fried served steaming hot", 200, R.drawable.four));
        itemArrayList.add(new Model(5, "Chicken Wraps", "Grilled chicken tikka rool wrapped", 125, R.drawable.five));
        itemArrayList.add(new Model(6, "Veggie Delight", "Loads of veggies with olives", 250, R.drawable.six));
        itemArrayList.add(new Model(7, "Seafood Combo", "combo of prawns, scallop, sliced fish, calanmari, potato fries", 170, R.drawable.seven));
        itemArrayList.add(new Model(8, "Full Tandoori", "Chicken roated with lip smacking mayo dressing", 300, R.drawable.eight));

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initEvents()
    {
        selectAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked select all!", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < itemArrayList.size(); i++)
                    recyclerViewAdapter.checkCheckBox(i, true);

            }
        });

        deselectAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked deselect all", Toast.LENGTH_SHORT).show();
                recyclerViewAdapter.deselectAll();
            }
        });

        deleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray selectedRows = recyclerViewAdapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0)
                {
                    //Loop to all the selected rows array
                    for (int i = (selectedRows.size() - 1); i >= 0; i--) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //remove the checked item
                            recyclerViewAdapter.removeItem(selectedRows.keyAt(i));
                        }
                    }
                    //notify the adapter and remove all checked selection
                    recyclerViewAdapter.deselectAll();
                }
            }
        });

        getSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SparseBooleanArray selectedRows = recyclerViewAdapter.getSelectedIds();//Get the selected ids from adapter

                //Check if item is selected or not via size
                if (selectedRows.size() > 0)
                {
                    StringBuilder stringBuilder = new StringBuilder();

                    for(int i=0; i<selectedRows.size(); i++)
                    {
                        int key = selectedRows.keyAt(i);
                        String selectedRowLabel ="Selected Position "+key;
                        stringBuilder.append(selectedRowLabel + "\n");
                        Log.d("Element at "+key, " is "+selectedRows.get(key));
                    }
                    Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        reverseSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sendSelectedToNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SparseBooleanArray selectedRows = recyclerViewAdapter.getSelectedIds();//Get the selected ids from adapter
                //Check if item is selected or not via size
                if (selectedRows.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    //Loop to all the selected rows array
                    for (int i = 0; i < selectedRows.size(); i++) {

                        //Check if selected rows have value i.e. checked item
                        if (selectedRows.valueAt(i)) {

                            //Get the checked item text from array list by getting keyAt method of selectedRowsarray
                            String selectedRowLabel = itemArrayList.get(selectedRows.keyAt(i)).getItemTitle();

                            //append the row label text
                            stringBuilder.append(selectedRowLabel + "\n");
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Selected Rows\n" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
