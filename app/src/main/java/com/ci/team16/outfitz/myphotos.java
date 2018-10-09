package com.ci.team16.outfitz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;




/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 //*
 * to handle interaction events.
 * Use the {@link myphotos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myphotos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View view;
    public static GridView gridView;
    public ArrayList<String> feed_list;
    private ArrayList<String> bitmapList;
    public static String src;
    //private OnFragmentInteractionListener mListener;

    public myphotos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment myphotos.
     */
    // TODO: Rename and change types and number of parameters
    public static myphotos newInstance(String param1, String param2) {
        myphotos fragment = new myphotos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_myphotos, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview2);

        bitmapList = new ArrayList<String>();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Feed");


        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //checkCreds((Map<String, Object>) dataSnapshot.getValue());
                Map<String, Object> users = (Map<String, Object>) dataSnapshot.getValue();
                feed_list = new ArrayList<String>();


                for (Map.Entry<String, Object> entry : users.entrySet()) {

                    //Get user map
                    Map singleUser = (Map) entry.getValue();
                    feed_list.add((String) singleUser.get("file"));

                }
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference(mParam1);
                for (int i = 0; i < feed_list.size(); i++) {
                    StorageReference fileRef = storageRef.child(feed_list.get(i));
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'

                            src = uri.toString();
                            bitmapList.add(src);
                            //bitmapList.add(src);
                            //bitmapList.add(src);
                            gridView.setAdapter(new ImageAdapter(view.getContext(), bitmapList));


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        public void onItemClick(AdapterView parent,
                                                View v, int position, long id)
                        {

                            //Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), picture_layout.class);
                            String pic = bitmapList.get(position);
                            Bundle bundle = new Bundle();
                            bundle.putString("username", mParam2);
                            bundle.putString("picture", pic);
                            bundle.putString("email", mParam1);



                            //Add the bundle to the intent
                            i.putExtras(bundle);
                            startActivity(i);


                            //startActivity(new Intent(Resultatsoversigt_akt.this, Teoriproeveaktivitet2.class));
                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        return view;


    }

   /* // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
