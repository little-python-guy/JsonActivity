package com.example.jsonactivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class JsonConsumer extends AsyncTask<String, Void, List<Post>> {

    private ProgressDialog dialog;
    private Context context;
    private ListView listView;

    public JsonConsumer(Context context, ListView listView){
        this.context = context;
        this.listView = listView;
    }

    @Override
    public void onPreExecute(){
        super.onPreExecute();

        dialog = ProgressDialog.show(
                context,
                "Aguarde",
                "Baixando JSON. Por favor aguarde..."
        );

    }

    @Override
    protected void onPostExecute(List<Post> posts){
        super.onPostExecute(posts);

        dialog.dismiss();

        if (posts.size() > 0) {

            ArrayAdapter<Post> adapter = new ArrayAdapter<>(
                    context, R.layout.simple_list_light, posts
            );
            listView.setAdapter(adapter);

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Atenção")
                    .setMessage("Não foi possível acessar as informações...")
                    .setPositiveButton("OK", null);

            builder.create().show();

        }

    }

    private List<Post> getPosts(String jsonString){

        List<Post> posts = new ArrayList<>();

        try {

            JSONArray postsList = new JSONArray(jsonString);
            JSONObject postJson;

            for (int i = 0; i < postsList.length(); i++){
                postJson = new JSONObject(postsList.getString(i));

                Post post = new Post();

                post.setId(postJson.getInt("id"));
                post.setUserId(postJson.getInt("userId"));
                post.setTitle(postJson.getString("title"));
                post.setBody(postJson.getString("body"));

                posts.add(post);

            }

        } catch (JSONException ignored) {}

        return posts;

    }

    @Override
    protected List<Post> doInBackground(String... params){

        String urlString = params[0];

        try {

            URL url = new URL(urlString);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            BufferedInputStream input = new BufferedInputStream(http.getInputStream());
            StringBuilder builder = new StringBuilder();

            int size;

            byte[] bytes = new byte[1024];

            while ((size = input.read(bytes)) > 0){

                builder.append(new String(bytes, 0, size));

            }

            String dados = builder.toString();

            http.disconnect();

            return getPosts(dados);

        } catch (IOException ignored) {}

        return null;

    }
}
