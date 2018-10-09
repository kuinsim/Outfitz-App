package com.ci.team16.outfitz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link //feed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link feed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class feed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> bitmapList;



    public static String src;
    public static String src2;
    public static String src3;
    private Bitmap bitmapx;


   // private OnFragmentInteractionListener mListener;

    public feed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feed.
     */
    // TODO: Rename and change types and number of parameters
    public static feed newInstance(String param1, String param2) {
        feed fragment = new feed();
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
    public static View view;
    public static GridView gridView;
    public ArrayList<String> feed_list;




    /*private void getFeed(Map<String, Object> users) {





        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            feed_list.add((String) singleUser.get("file"));

        }


    }*/
    /*private void checkCreds(Map<String, Object> users) {
        feed_list = new ArrayList<String>();


        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            feed_list.add((String) singleUser.get("file"));

        }
        Log.d("AccountProfile",feed_list.get(0));
    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.feed, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview);



       // src = "https://sslg.ulximg.com/image/405x405/artist/1392767353_217f16228baaa5dc18c82925ac76edf6.jpg/9e89e114db44f266e044addd06e88d69/1392767353_kanye_west_wall_40.jpg";
        //new GetImageFromURL(img_temp).execute(src);
        //BitmapDrawable drawable = (BitmapDrawable) img_temp.getDrawable();


        /*Bitmap bitmap2 = drawable.getBitmap();*/


        bitmapList = new ArrayList<String>();
        //Bitmap myLogo = ((BitmapDrawable) drawable).getBitmap();
       // bitmapList.add(myLogo);



       /* Drawable myDrawable = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.kanye, null);
        Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();

        String pic1 = BitMapToString(myLogo);

        Bitmap newLogo = StringToBitMap(pic1);
        bitmapList.add(myLogo);
        bitmapList.add(myLogo);
        bitmapList.add(myLogo);
        bitmapList.add(myLogo);*/


        /*Log.d("list", feed_list.get(1));
        for (int i = 0; i < feed_list.size(); i++) {

            StorageReference fileRef = storageRef.child(feed_list.get(i) + ".jpg");
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'

                    src = uri.toString();
                    bitmapList.add(src);
                    bitmapList.add(src);
                    bitmapList.add(src);
                    gridView.setAdapter(new ImageAdapter(view.getContext(), bitmapList));


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });


        }*/
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
                StorageReference storageRef = storage.getReference("feed");
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


        /*StorageReference fileRef = storageRef.child("kanye.jpg");
        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                src = uri.toString();
                bitmapList.add(src);
                bitmapList.add(src);
                bitmapList.add(src);
                gridView.setAdapter(new ImageAdapter(view.getContext(), bitmapList));


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        /*StorageReference storageRef2 = storage.getReference("kanye2.jpg");
        storageRef2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                src2 = uri.toString();
                bitmapList.add(src2);
                bitmapList.add(src2);
                bitmapList.add(src2);
                gridView.setAdapter(new ImageAdapter(view.getContext(), bitmapList));



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        StorageReference storageRef3 = storage.getReference("ian.jpg");
        storageRef3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'

                src3 = uri.toString();
                bitmapList.add(src3);
                bitmapList.add(src3);
                bitmapList.add(src3);
                gridView.setAdapter(new ImageAdapter(view.getContext(), bitmapList));

                //ok

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


            //Picasso.get().load("https://sslg.ulximg.com/image/405x405/artist/1392767353_217f16228baaa5dc18c82925ac76edf6.jpg/9e89e114db44f266e044addd06e88d69/1392767353_kanye_west_wall_40.jpg").into(img_temp);
        bitmapList.add("https://sslg.ulximg.com/image/405x405/artist/1392767353_217f16228baaa5dc18c82925ac76edf6.jpg/9e89e114db44f266e044addd06e88d69/1392767353_kanye_west_wall_40.jpg");
        bitmapList.add("https://sslg.ulximg.com/image/405x405/artist/1392767353_217f16228baaa5dc18c82925ac76edf6.jpg/9e89e114db44f266e044addd06e88d69/1392767353_kanye_west_wall_40.jpg");
        bitmapList.add("https://sslg.ulximg.com/image/405x405/artist/1392767353_217f16228baaa5dc18c82925ac76edf6.jpg/9e89e114db44f266e044addd06e88d69/1392767353_kanye_west_wall_40.jpg");
            //bitmapList.add("https://firebasestorage.googleapis.com/v0/b/outfitz-b68bc.appspot.com/o/kanye.jpg?alt=media&token=cad907fe-8e28-4efa-b4f9-bb7103797aa6");
        /*bitmapList.add(src);
        bitmapList.add(src);
        bitmapList.add(src);
        bitmapList.add(src2);
        bitmapList.add(src2);
        bitmapList.add(src2);
        bitmapList.add(src3);
        bitmapList.add(src3);
        bitmapList.add(src3);
        gridView.setAdapter(new ImageAdapter(view.getContext(), bitmapList));*/


        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap>{
        ImageView imgV;

        public GetImageFromURL(ImageView imgV){
            this.imgV = imgV;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldisplay = url[0];
            bitmapx = null;
            try {
                InputStream srt = new java.net.URL(urldisplay).openStream();
                bitmapx = BitmapFactory.decodeStream(srt);
            } catch (Exception e){
                e.printStackTrace();
            }
            return bitmapx;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgV.setImageBitmap(bitmap);



        }
    }



    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }



    /*// TODO: Rename method, update argument and hook method into UI event
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
  /*  public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
