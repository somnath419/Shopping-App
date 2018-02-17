package com.example.somnath.kart;

/**
 * Created by SOMNATH on 04-11-2017.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

public class WebRequestService  extends IntentService{

    public static final String REQUEST_STRING = "myRequest";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";
    private String URL = null;

    public WebRequestService() {
        super("WebRequestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String requestString = intent.getStringExtra(REQUEST_STRING);
        String responseMessage = "";
        // Do some really cool here
        // I am making web request here as an example...
        try {
            URL = requestString;
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(URL);
            HttpResponse response = httpclient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseMessage = out.toString();
            }
              else
                {
                //Closes the connection.
                Log.w("HTTP1:",statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        } catch (ClientProtocolException e) {
            Log.w("HTTP2:",e );
            responseMessage = e.getMessage();
        } catch (IOException e) {
            Log.w("HTTP3:",e );
            responseMessage = e.getMessage();
        }catch (Exception e) {
            Log.w("HTTP4:",e );
            responseMessage = e.getMessage();
        }


        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(CategoryFragment.WebRequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        sendBroadcast(broadcastIntent);

        Intent broadcastIntent2=new Intent();
        broadcastIntent2.setAction(HomeFragment.WebRequestReceiver2.PROCESS_RESPONSE2);
        broadcastIntent2.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent2.putExtra(RESPONSE_MESSAGE,responseMessage);
        sendBroadcast(broadcastIntent2);

    }

}