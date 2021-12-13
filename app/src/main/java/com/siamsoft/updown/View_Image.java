package com.siamsoft.updown;

import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_Image extends Fragment {
    View rootView;
    EditText name;
    Button ViewImage;
    String url =null;
    NetworkImageView previewImage;
    ImageLoader imageLoader;
    Activity mActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);

        rootView = inflater.inflate(R.layout.fragment_view__image, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        previewImage = (NetworkImageView)rootView.findViewById(R.id.previewImage);

        name = (EditText)rootView.findViewById(R.id.name);
        ViewImage = (Button)rootView.findViewById(R.id.viewButton);

        imageLoader = CustomVolleyRequest.getInstance(getActivity().getApplicationContext())
                .getImageLoader();

        ViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchImage();
            }
        });
        return rootView;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


    public void FetchImage() {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://10.0.2.2//LMEM_01/view.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject res = new JSONObject(response);
                                JSONArray thread = res.getJSONArray("image"); // jsonarray name

                                for (int i = 0; i < thread.length(); i++) {
                                    JSONObject obj = thread.getJSONObject(i);
                                    url  = obj.getString("image");  // database field name


                                }

                                imageLoader.get(url, ImageLoader.getImageListener(previewImage
                                        ,0,android.R.drawable
                                                .ic_dialog_alert));
                                previewImage.setImageUrl(url, imageLoader);



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name",name.getText().toString());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mActivity);

            //Adding request to the queue
            requestQueue.add(stringRequest);
        }

    }
}