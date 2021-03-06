package ibanez.brian.esoquieroapp.Core.Http;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import ibanez.brian.esoquieroapp.Activities.CategoryActivity;
import ibanez.brian.esoquieroapp.Activities.CategoryListActivity;
import ibanez.brian.esoquieroapp.Core.Http.ModelsJSON.ItemCategoryJSON;
import ibanez.brian.esoquieroapp.Core.Http.ModelsJSON.POSTCategory;
import ibanez.brian.esoquieroapp.Core.Http.ModelsJSON.GETCategoryList;
import ibanez.brian.esoquieroapp.Core.Http.ModelsJSON.POSTLogin;
import ibanez.brian.esoquieroapp.Core.Http.ModelsJSON.ResponseJSON;

/**
 * Created by brian.ibanez on 29/10/2016.
 */
public class HttpManager extends Thread
{
    private HttpURLConnection httpURLConnection;
    private Handler handler;
    private ApiServices method;
    private Uri.Builder parameters;

    public static final int ErrorHttp = 1;

    //private static String host = "http://lkdml.myq-see.com";
    private static String host = "http://localhost:52944";

    private static int idCount = 3;

    private HttpManager()
    {
    }


    public static HttpManager postRegister(Handler handler, Uri.Builder parameters)
    {
        HttpManager httpManager = new HttpManager();

        try
        {
            URL url = new URL("http://lkdml.myq-see.com/register");

            httpManager.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpManager.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //httpManager.httpURLConnection.setRequestProperty("AUTHORIZATION", apiKey);
            httpManager.httpURLConnection.setRequestMethod("POST");
            httpManager.httpURLConnection.setDoOutput(true);
            httpManager.httpURLConnection.setConnectTimeout(5000); // 5 segundos.

            httpManager.handler = handler;
            httpManager.method = ApiServices.PostRegister;
            httpManager.parameters = parameters;

            return httpManager;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static HttpManager deleteCategory(Handler handler, String apiKey, String categoryId)
    {
        HttpManager httpManager = new HttpManager();

        try
        {
            URL url = new URL("http://lkdml.myq-see.com/categorias/" + categoryId);

            httpManager.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpManager.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpManager.httpURLConnection.setRequestProperty("AUTHORIZATION", apiKey);
            httpManager.httpURLConnection.setRequestMethod("DELETE");

            //httpManager.httpURLConnection.setDoOutput(true);
            httpManager.httpURLConnection.setConnectTimeout(5000); // 5 segundos.

            httpManager.handler = handler;
            httpManager.method = ApiServices.DeleteCategory;
            //httpManager.parameters = new Uri.Builder();

            return httpManager;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static HttpManager putCategory(Handler handler, String apiKey, Uri.Builder parameters)
    {
        HttpManager httpManager = new HttpManager();

        try
        {
            URL url = new URL("http://lkdml.myq-see.com/categorias");

            httpManager.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpManager.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpManager.httpURLConnection.setRequestProperty("AUTHORIZATION", apiKey);
            httpManager.httpURLConnection.setRequestMethod("PUT");
            httpManager.httpURLConnection.setDoOutput(true);
            httpManager.httpURLConnection.setConnectTimeout(5000); // 5 segundos.

            httpManager.handler = handler;
            httpManager.method = ApiServices.PutCategory;
            httpManager.parameters = parameters;

            return httpManager;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static HttpManager postCategory(Handler handler, String apiKey, Uri.Builder parameters)
    {
        HttpManager httpManager = new HttpManager();

        try
        {
            URL url = new URL("http://lkdml.myq-see.com/categorias");

            httpManager.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpManager.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpManager.httpURLConnection.setRequestProperty("AUTHORIZATION", apiKey);
            httpManager.httpURLConnection.setRequestMethod("POST");
            httpManager.httpURLConnection.setDoOutput(true);
            httpManager.httpURLConnection.setConnectTimeout(10000); // 5 segundos.

            httpManager.handler = handler;
            httpManager.method = ApiServices.PostCategory;
            httpManager.parameters = parameters;

            return httpManager;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static HttpManager postLogin(Handler handler, Uri.Builder parameters)
    {
        HttpManager httpManager = new HttpManager();

        try
        {
            URL url = new URL("http://lkdml.myq-see.com/login");

            httpManager.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpManager.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpManager.httpURLConnection.setRequestMethod("POST");
            httpManager.httpURLConnection.setDoOutput(true);
            httpManager.httpURLConnection.setConnectTimeout(5000); // 5 segundos.

            httpManager.handler = handler;
            httpManager.method = ApiServices.PostLogin;
            httpManager.parameters = parameters;

            return httpManager;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    public static HttpManager getCategories(Handler handler, String apiKey)
    {
        HttpManager httpManager = new HttpManager();

        try
        {
            URL url = new URL("http://lkdml.myq-see.com/categorias");

            httpManager.httpURLConnection = (HttpURLConnection) url.openConnection();
            httpManager.httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpManager.httpURLConnection.setRequestProperty("AUTHORIZATION", apiKey);
            //httpManager.httpURLConnection.setRequestProperty("AUTHORIZATION", "c607392e8abdddd075a90f48af8434ab");
            httpManager.httpURLConnection.setRequestMethod("GET");
            //httpManager.httpURLConnection.setDoOutput(true);
            httpManager.httpURLConnection.setConnectTimeout(7000); // 7 segundos.

            httpManager.handler = handler;
            httpManager.method = ApiServices.GetCategories;

            return httpManager;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void run()
    {
        byte[] result = null;
        Message message = new Message();

        try
        {
            switch (this.method)
            {
                case PostLogin:
                    result = this.post();
                    message.obj = POSTLogin.getModelFromJSON(new String(result, "UTF-8"));

                    /*POSTLogin r = new POSTLogin();
                    r.apiKey = "c607392e8abdddd075a90f48af8434ab";
                    r.name = "pepe";
                    r.email = "pepe@gmail.com";
                    r.message = "ok";
                    r.error = false;
                    message.obj = r;*/
                    break;

                case GetCategories:
                    result = this.get();
                    message.arg1 = CategoryListActivity.GETcategories;
                    message.obj = GETCategoryList.getModelFromJSON(new String(result, "UTF-8"));

                    /*GETCategoryList r1 = new GETCategoryList();
                    r1.error = false;
                    r1.message = "ok";
                    r1.categorias.add(new ItemCategoryJSON(1, "Medicos", "curan gente", null, ""));
                    r1.categorias.add(new ItemCategoryJSON(2, "Abogados", "defienden gente", null, ""));
                    message.obj = r1;*/
                    break;

                case PostCategory:
                    result = this.post();
                    message.arg1 = CategoryActivity.POSTcategory;
                    message.obj = POSTCategory.getModelFromJSON(new String(result, "UTF-8"));

                    /*POSTCategory r2 = new POSTCategory();
                    r2.error = false;
                    r2.message = "OK";
                    r2.categoria_id = idCount++;
                    message.obj = r2;*/
                    break;

                case PutCategory:
                    result = this.post();
                    message.arg1 = CategoryActivity.PUTcategory;
                    message.obj = ResponseJSON.getModelFromJSON(new String(result, "UTF-8"));

                    /*ResponseJSON r4 = new ResponseJSON();
                    r4.error = false;
                    r4.message = "OK";
                    message.obj = r4; */
                    break;

                case DeleteCategory:
                    result = this.get();
                    message.arg1 = CategoryListActivity.DELETEcategory;
                    message.obj = ResponseJSON.getModelFromJSON(new String(result, "UTF-8"));

                    /*ResponseJSON r6 = new ResponseJSON();
                    r6.error = false;
                    r6.message = "OK";
                    message.obj = r6;*/
                    break;

                case PostRegister:
                    result = this.post();
                    message.obj = ResponseJSON.getModelFromJSON(new String(result, "UTF-8"));

                    //ResponseJSON r5 = new ResponseJSON();
                    //r5.error = false;
                    //r5.message = "OK";
                    //message.obj = r5;
                    break;

                default:
                    throw new Exception("");

            }

            // Emito el mensaje
            this.handler.sendMessage(message);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            // Si ocurrio una excepcion la envio en el handler.
            message.arg2 = ErrorHttp;
            this.handler.sendMessage(message);
        }
    }

    public byte[] post()
    {
        byte[] result = null;
        try
        {
            String query = this.parameters.build().getEncodedQuery();
            OutputStream os = this.httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            // Si es de la familia del codigo 200.
            int response = this.httpURLConnection.getResponseCode();
            if(response > 199 && response < 300) {

                InputStream is = this.httpURLConnection.getInputStream();
                result = getByteArray(is);

            }
            else
            {
                throw new IOException();
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    private byte[] get () throws IOException
    {
        try
        {
            this.httpURLConnection.connect();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }

        byte[] result = null;
        int response = this.httpURLConnection.getResponseCode();

        if(response == 200)
        {
            InputStream is = this.httpURLConnection.getInputStream();
            result = getByteArray(is);
        }
        else
        {
            throw new IOException();
        }

        return result;
    }

    private byte[] getByteArray(InputStream is) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int lenght = 0;

        while ((lenght = is.read(buffer)) != -1)
        {
            baos.write(buffer, 0, lenght);
        }

        is.close();

        return baos.toByteArray();
    }

    public enum ApiServices
    {
        PostLogin,
        GetCategories,
        PostCategory,
        PutCategory,
        DeleteCategory,
        PostRegister
    }
}