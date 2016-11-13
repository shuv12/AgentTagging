package app.com.example.android.agenttagging.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import app.com.example.android.agenttagging.R;
import app.com.example.android.agenttagging.model.NotificationsModel;

import static app.com.example.android.agenttagging.Login.CONNECTION_TIMEOUT;
import static app.com.example.android.agenttagging.Login.READ_TIMEOUT;

/**
 * Created by shuvam on 08-09-2016.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    private Context context;
    private List<NotificationsModel> notificationsModelList;
    public static final String TAGREQUESTURL = "http://realthree60.com/dev/apis/TagRequest/";
    public static final String TAGREQUESTAPPROVEDURL = "http://realthree60.com/dev/apis/TagRequest/approve/";
    public static final String TAGREQUESTDENIEDURL = "http://realthree60.com/dev/apis/TagRequest/deny/";
    private String accessToken;
    private String notificationID;
    public static final String ACCESSTOKENPARA = "access_token";

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        TextView notipostStr, notiStatus, notifor;
        Button btnTag;
        ImageButton tick,cross;
        LinearLayout requestLayout;
        ImageView notiPic;

        public ViewHolder(View itemView) {
            super(itemView);
            this.notipostStr = (TextView) itemView.findViewById(R.id.notipoststr);
            this.notifor = (TextView) itemView.findViewById(R.id.fortype);
            this.notiStatus = (TextView) itemView.findViewById(R.id.status);
            this.btnTag = (Button) itemView.findViewById(R.id.btntag);
            this.requestLayout = (LinearLayout) itemView.findViewById(R.id.requested);
            this.tick = (ImageButton) itemView.findViewById(R.id.checkaccept);
            this.cross = (ImageButton) itemView.findViewById(R.id.crossdenied);

            this.notiPic = (ImageView) itemView.findViewById(R.id.notipropic);
        }
    }

    public NotificationsAdapter(Context context, List<NotificationsModel> notificationsModelList) {
        this.context = context;
        this.notificationsModelList = notificationsModelList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_display_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationsModel notificationsModel = (NotificationsModel) this.notificationsModelList.get(position);
        holder.notipostStr.setText(notificationsModel.getNotiText());
        holder.notiStatus.setText(notificationsModel.getNotiStatus());
        holder.notifor.setText(notificationsModel.getNotiforType());
        Picasso.with(context).load(notificationsModel.getNotiPic()).fit().into(holder.notiPic);
        accessToken = notificationsModel.getAccessToken();
        notificationID = notificationsModel.getNotiID();


        final String curstatus = holder.notiStatus.getText().toString();
        switch (curstatus) {
            case "Pending":
                holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.pending));
                break;
            case "Approved":
                holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.approved));
                break;
            case "Denied":
                holder.notiStatus.setTextColor(ContextCompat.getColor(context,R.color.denied));
                break;
            case "Tag":
                holder.notiStatus.setVisibility(View.GONE);
                holder.btnTag.setVisibility(View.VISIBLE);
                holder.btnTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wrapInScrollView = true;
                        final MaterialDialog dialog = new MaterialDialog.Builder(context)
                                .title(R.string.requestsentitle)
                                .customView(R.layout.senttagboxlayout,wrapInScrollView)
                                .titleGravity(GravityEnum.CENTER)
                                .show();

                        View view = dialog.getCustomView();
                        Button sentTag = (Button) view.findViewById(R.id.senttagdone);
                        sentTag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new TAGREQUEST().execute();
                                dialog.dismiss();
                            }
                        });
                    }
                });
                break;
            case "Boolean":
                holder.notiStatus.setVisibility(View.GONE);
                holder.requestLayout.setVisibility(View.VISIBLE);
                holder.tick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean wrapInScrollView = true;
                        final MaterialDialog dialogappr = new MaterialDialog.Builder(context)
                                .title(R.string.notirequest)
                                .customView(R.layout.approvedtagboxlayout,wrapInScrollView)
                                .titleGravity(GravityEnum.CENTER)
                                .show();

                        View view = dialogappr.getCustomView();
                        Button sendMsg = (Button) view.findViewById(R.id.sendtextmsg);
                        sendMsg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new TAGREQUESTAPPROVED().execute();
                                dialogappr.dismiss();
                            }
                        });
                    }
                });
                holder.cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new TAGREQUESTDENIED().execute();
                    }
                });
                break;
            default:
                holder.notiStatus.setVisibility(View.GONE);
                holder.btnTag.setVisibility(View.VISIBLE);
        }
    }

    public int getItemCount() {
        return notificationsModelList.size();
    }


    private class TAGREQUEST extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(context);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    Toast.makeText(context, "Tag Request sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cannot send request, tagging limit exceeded", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("TAG NOTI REQUEST", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tagUrl = TAGREQUESTURL + notificationID;
                url = new URL(tagUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");


                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(ACCESSTOKENPARA, accessToken);
                String query = builder.build().getEncodedQuery();

                Log.v("query", query);
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.v("Noti Data",result.toString());
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }
    }




    private class TAGREQUESTAPPROVED extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(context);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    Toast.makeText(context, "Tag Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Not approved!! Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("TAG NOTI REQUEST", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tagUrl = TAGREQUESTAPPROVEDURL + notificationID;
                url = new URL(tagUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");


                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(ACCESSTOKENPARA, accessToken);
                String query = builder.build().getEncodedQuery();

                Log.v("query", query);
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.v("Noti Data",result.toString());
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }
    }





    private class TAGREQUESTDENIED extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(context);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pdLoading.dismiss();
            try {
                JSONObject mObject = new JSONObject(s);
                Boolean Success = mObject.optBoolean("Success");
                if (Success) {
                    Toast.makeText(context, "Tag denied Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Not Denied!!Something went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("TAG NOTI REQUEST", "JSON exception", e);
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String tagUrl = TAGREQUESTDENIEDURL + notificationID;
                url = new URL(tagUrl);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");


                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter(ACCESSTOKENPARA, accessToken);
                String query = builder.build().getEncodedQuery();

                Log.v("query", query);
                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Log.v("Noti Data",result.toString());
                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }
    }
}



