package alexandre.thauvin.gym3000x;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInRequest {

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public interface sendDataResponse {
        void sendData(String str);
    }

    public static class MakeRequestTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();
        private sendDataResponse dataInterface;
        private WeakReference<Context> contextRef;
        private ProgressDialog dialog;

        public MakeRequestTask(sendDataResponse inter, Context context) {
            dataInterface = inter;
            contextRef = new WeakReference<>(context);
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            RequestBody body = RequestBody.create(JSON, bowlingJson(params[0], params[1]));
            Request request = new Request.Builder()
                    .url("http://163.5.84.111:8081/api/account/login")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (java.io.IOException e) {
                Log.d("requestfailure", "post: " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String response) {
            // TODO: check this.exception
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dataInterface.sendData(response);
        }

        private String bowlingJson(String email, String password) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email);
                jsonObject.put("password", password);
                return jsonObject.toString();

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}

