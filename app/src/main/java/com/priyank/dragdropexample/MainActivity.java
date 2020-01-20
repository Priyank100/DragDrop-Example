package com.priyank.dragdropexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnDragListener {
    LinearLayout layout1, layout2;
    RecyclerView recyclerView1, recyclerView2;
    ArrayList<String> list1;
    ArrayList<String> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        implementEvents();
    }

    private void findViews() {

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        recyclerView1 = findViewById(R.id.recycler_view_1);
        recyclerView2 = findViewById(R.id.recycler_view_2);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();

        list1.add("Label 1");
        list1.add("Label 2");
        list1.add("Label 3");
        list1.add("Label 4");

        recyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        MyAdapter adapter = new MyAdapter(MainActivity.this, list1);
        recyclerView1.setAdapter(adapter);
    }

    private void implementEvents() {
        layout1.setOnDragListener(this);
        layout2.setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    return true;
                }
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:
//                view.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN);
                view.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                return true;

            case DragEvent.ACTION_DRAG_EXITED:
                view.getBackground().clearColorFilter();
                view.invalidate();
                return true;

            case DragEvent.ACTION_DROP:
                System.out.println("ACTION_DROP");
//                ClipData.Item item = event.getClipData().getItemAt(0);
//                String dragData = item.getText().toString();
//                Toast.makeText(this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                view.getBackground().clearColorFilter();
                view.invalidate();

                View v = (View) event.getLocalState();
                ViewGroup owner = (ViewGroup) v.getParent();
                owner.removeView(v);
                LinearLayout container = (LinearLayout) view;
                container.addView(v);
                v.setVisibility(View.VISIBLE);
                System.out.println(event.getClipData().getItemAt(0).getText().toString());
                System.out.println("List1Size>> " + list1.size());
                return true;

            case DragEvent.ACTION_DRAG_ENDED:
                view.getBackground().clearColorFilter();
                view.invalidate();

                if (!event.getResult()) {
                    Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    return false;
                }

                return true;

            default:
                Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
                break;
        }
        return false;
    }
}
