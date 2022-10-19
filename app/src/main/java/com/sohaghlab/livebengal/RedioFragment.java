package com.sohaghlab.livebengal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sohaghlab.livebengal.adapter.RedioAdapter;
import com.sohaghlab.livebengal.adapter.VideoAdapter;
import com.sohaghlab.livebengal.model.RedioModel;
import com.sohaghlab.livebengal.model.VideoModel;

import java.util.ArrayList;

public class RedioFragment extends Fragment {

    public RedioFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<RedioModel> list;
    private RedioAdapter adapter;
    private DatabaseReference reference;
    private StaggeredGridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_redio, container, false);


        recyclerView=v.findViewById(R.id.redioRecy);
        progressBar=v.findViewById(R.id.progressBarRedio);
        reference = FirebaseDatabase.getInstance().getReference().child("Redio");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        manager= new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        getDataFromFirebase();

        return v;
    }

    private void getDataFromFirebase() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1:snapshot.getChildren()){

                    RedioModel data =snapshot1.getValue(RedioModel.class);
                    list.add(data);

                }
                adapter = new RedioAdapter(getContext(),list);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error data set", Toast.LENGTH_SHORT).show();


            }
        });
    }
}