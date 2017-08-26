/*Inserting data into table and retrieve in JSON with PHP backend using GET method*/
class GetDoctorWroker extends AsyncTask<String, Void,ArrayList<DoctorListModel>> implements IpInterface{

    private ProgressDialog dialog;
    Context context;
    private String category;
    View view;
    private ArrayList<DoctorListModel> listModels;

    GetDoctorWroker(Context ctx, View view) {
        this.context = ctx;
        listModels = new ArrayList<>();
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getString(R.string.loadingPleaseWait));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Handler handler =  new Handler(context.getMainLooper());
        handler.post( new Runnable(){
            public void run(){
                dialog.show();
            }
        });
    }

    @Override
    protected ArrayList<DoctorListModel> doInBackground(String... params) {
        String TAG = getClass().getSimpleName();
        String line;
        category = params[0];
        String city = params[1];
        String specialization = params[2];
        String list_url = ip + "getdata.php?medicinal_category=";
        try {

            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {return true;
                }
            });



            URL url = new URL(list_url + category + "&&city=" + city + "&&qualification=" + URLEncoder.encode(specialization,"UTF-8"));
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            Log.i(TAG, String.valueOf(url));
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            String data = String.valueOf(url.openConnection().getInputStream());

            Log.i(TAG, data);

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();

            while((line = reader.readLine()) != null){
                buffer.append(line);
                Log.i(TAG, "Line: " + line);
            }

            String output = buffer.toString();
            Log.i(TAG, output);

            JSONArray jsonArray = new JSONArray(output);
            JSONObject jsonObject;
            for(int i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                DoctorListModel doctorListModel = new DoctorListModel();
                doctorListModel.setDocname(jsonObject.getString("name"));
                doctorListModel.setDocemail(jsonObject.getString("email"));
                doctorListModel.setDoccontact(jsonObject.getString("contact"));
                doctorListModel.setDocqual(jsonObject.getString("qualification"));
                doctorListModel.setDoccat(jsonObject.getString("medicinal_category"));
                doctorListModel.setDoclat(jsonObject.getString("lat"));
                doctorListModel.setDoclong(jsonObject.getString("longi"));
                doctorListModel.setDoctorProfileUrl(jsonObject.getString("profile"));
                doctorListModel.setCity(jsonObject.getString("city"));

                listModels.add(doctorListModel);
            }

            reader.close();
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, context.getString(R.string.invalidUrl),Toast.LENGTH_LONG).show();
                }
            });
        } catch (ProtocolException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, context.getString(R.string.protocolException),Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, context.getString(R.string.conFailed),Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            /*Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                        Toast.makeText(context, context.getString(R.string.failureTryAgain),Toast.LENGTH_LONG).show();
                }
            });*/
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, context.getString(R.string.algorithmException),Toast.LENGTH_LONG).show();
                }
            });
        } catch (KeyManagementException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, context.getString(R.string.keyException),Toast.LENGTH_LONG).show();
                }
            });
        }

        return listModels;
    }

    @Override
    protected void onPostExecute(ArrayList<DoctorListModel> doctorListModels) {
        int SNACKBAR_DURATION = 3;
        super.onPostExecute(doctorListModels);
        if (!listModels.isEmpty()){
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }
            context.startActivity(new Intent(context,DoctorListActivity.class).putExtra("doctor_object",doctorListModels));
        }
        if(listModels.isEmpty()){
            if(dialog.isShowing()){
                dialog.dismiss();
            }
            Snackbar snackbar = Snackbar
                    .make(view, context.getString(R.string.noDoctorsIn) + " " + category + " " + context.getString(R.string.category), Snackbar.LENGTH_LONG);
            snackbar.show();
            //Toast.makeText(context,context.getString(R.string.noDoctorsIn) + " " + category + " " + context.getString(R.string.category),Toast.LENGTH_LONG).show();
        }
    }
}