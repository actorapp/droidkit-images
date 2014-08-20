package com.droidkit.images.sample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.droidkit.images.loading.ImageLoader;
import com.droidkit.images.loading.view.ImageKitView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class UsersListActivity extends ActionBarActivity {

    private ProgressDialog dialog;
    private ImageLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        loader = new ImageLoader(this);

        dialog = new ProgressDialog(UsersListActivity.this);
        dialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.vk.com/method/friends.get?user_id=1&fields=name,photo_50,photo_100,photo_200", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                final ArrayList<Record> records = new ArrayList<Record>();
                try {
                    JSONArray array = response.getJSONArray("response");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String name = obj.getString("first_name") + " " + obj.getString("last_name");
                        String av50 = obj.getString("photo_50");
                        String av100 = obj.optString("photo_100");
                        String av200 = obj.optString("photo_200");
                        records.add(new Record(name, av50, av100, av200));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        ListView listView = (ListView) findViewById(R.id.list);
                        listView.setAdapter(new BaseAdapter() {
                            @Override
                            public int getCount() {
                                return records.size();
                            }

                            @Override
                            public Record getItem(int position) {
                                return records.get(position);
                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View res = convertView;
                                if (res == null) {
                                    res = UsersListActivity.this.getLayoutInflater().inflate(R.layout.user_item, parent, false);
                                    ((ImageKitView) res.findViewById(R.id.avatar)).initLoader(loader);
                                }
                                Record record = getItem(position);
                                ((ImageKitView) res.findViewById(R.id.avatar)).requestUrl(record.avatar50);
                                ((TextView) res.findViewById(R.id.name)).setText(record.name);
                                return res;
                            }
                        });
                    }
                });
            }
        });
    }

    class Record {
        private String name;
        private String avatar50;
        private String avatar100;
        private String avatar200;

        Record(String name, String avatar50, String avatar100, String avatar200) {
            this.name = name;
            this.avatar50 = avatar50;
            this.avatar100 = avatar100;
            this.avatar200 = avatar200;
        }

        public String getName() {
            return name;
        }

        public String getAvatar50() {
            return avatar50;
        }

        public String getAvatar100() {
            return avatar100;
        }

        public String getAvatar200() {
            return avatar200;
        }
    }
}
