package com.example.paola.appnumerosprimos;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AsyncTaskActivity";
    TextView num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num = (TextView) findViewById(R.id.numero);

    }

    public void accionbtnPrin(View view) {
        for(int i = 1; i < 100000000; i++){
            int numero=i;
            boolean flat = false;
            for(int j = 2; j < numero; j++){
                if(numero % j == 0){
                    flat = true;
                }
            }
            if(flat){
                num.setText("#: " + numero);
            }
        }
    }
    public void accionbtnSecon(View view) {
        new PrimeTask().execute(5000);
    }
    private class PrimeTask extends AsyncTask<Integer, Integer, Long> {

        private int primeCount;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "UI thread onPreExecute");
        }

        @Override
        protected Long doInBackground(Integer... params) {

            primeCount = params[0];

            long startTime = System.currentTimeMillis();

            int primesFound = 0;

            for (int i = 2; primesFound < primeCount; i++) {
                if (isPrime(i)) {
                    primesFound++;
                    // Publish progress, calls onProgressUpdate behind the scenes
                    publishProgress(i, (int) (((float) primesFound / (float) primeCount) * 100f));
                }
            }

            long endTime = System.currentTimeMillis();

            return Long.valueOf(primesFound);
        }

        /**
         * Checks if the given parameter is a prime number. Return true or false
         *
         * @param number Number to test
         * @return True if the parameter is prime number, false otherwise
         */
        private boolean isPrime(int number) {
            for (int i = number - 1; i > 1; i--) {
                if (number % i == 0) {
                    return false;
                }
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Print progress to the log
            Log.d(TAG, values[0] + " is prime " + values[1] + " % ready");
        }

        @Override
        protected void onPostExecute(Long result) {
            super.onPostExecute(result);

            Log.d(TAG, "onPostExecute");

            // Show AlertDialog to user
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(primeCount + " Proceso Terminado " + result
                    / 1000 + " second(s).");
            builder.create().show();
        }

    }
}
