package ibanez.brian.esoquieroapp.Controllers;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import java.io.File;

import ibanez.brian.esoquieroapp.Core.Dialog;
import ibanez.brian.esoquieroapp.Core.Http.HttpManager;
import ibanez.brian.esoquieroapp.Models.CategoryModel;
import ibanez.brian.esoquieroapp.Activities.CategoryActivity;
import ibanez.brian.esoquieroapp.R;
import ibanez.brian.esoquieroapp.Views.CategoryView;

/**
 * Created by brian.ibanez on 28/09/2016.
 */
public class CategoryController implements View.OnClickListener {

    private CategoryModel categoryModel;
    private static CategoryView categoryView;
    private CategoryActivity categoryActivity;

    private static Uri uri;
    public static final int CAMARA = 0;
    public static final int CAMARA_PERSMISO = 0;

    public CategoryController(CategoryModel categoryModel, CategoryActivity categoryActivity)
    {
        this.categoryModel = categoryModel;
        this.categoryActivity = categoryActivity;
    }

    public void setCategoryView(CategoryView view)
    {
        categoryView = view;
    }

    @Override
    public void onClick(View view)
    {

        if (view.getId() == R.id.ivCategoryImg)
        {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File imagesFOlder = new File(Environment.getExternalStorageDirectory(), "Categories");
            imagesFOlder.mkdirs();


            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";


            //File file = new File(imagesFOlder, "foto.jpg");
            File file = new File(imagesFOlder, imageName);
            uri = Uri.fromFile(file);

            this.categoryModel.setUri(uri);

            i.putExtra(MediaStore.EXTRA_OUTPUT, uri);

            if(ContextCompat.checkSelfPermission(this.categoryActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this.categoryActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this.categoryActivity, new String[]{Manifest.permission.CAMERA ,Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMARA_PERSMISO);
            }
            else
            {
                this.categoryActivity.startActivityForResult(i, CAMARA);
            }
        }

        if (view.getId() == R.id.btNewCategoryCreate)
        {
            this.categoryView.updateModel();

            String dialogMsg = "";
            if (this.categoryModel.getCategoryName().isEmpty())
            {
                dialogMsg = this.categoryActivity.getString(R.string.stringCategoryValidationCategoryName) + "\n";
            }

            if (this.categoryModel.getDescription().isEmpty())
            {
                dialogMsg = dialogMsg + this.categoryActivity.getString(R.string.stringCategoryValidationDescription);
            }

            // Si la variable no tiene valor es por que no hay errores.
            if (dialogMsg.isEmpty())
            {
                SharedPreferences prefs = this.categoryActivity.getSharedPreferences("EsoQuiero", Context.MODE_PRIVATE);
                String apiKey = prefs.getString("apiKey", null);

                // Llamo a la api.
                Handler.Callback callback = this.categoryActivity;
                Handler handler = new Handler(this.categoryActivity);

                Uri.Builder parameters = new Uri.Builder()
                        .appendQueryParameter("categoria_id", String.valueOf(this.categoryModel.getId()))
                        .appendQueryParameter("titulo", this.categoryModel.getCategoryName())
                        .appendQueryParameter("descripcion", this.categoryModel.getDescription());

                HttpManager threadHttpManager = null;

                // Si no tiene id es una categoria nueva.
                if (this.categoryModel.getId() == 0)
                {
                    threadHttpManager = HttpManager.postCategory(handler, apiKey ,parameters);
                }
                else
                {
                    threadHttpManager = HttpManager.putCategory(handler, apiKey ,parameters);
                }

                threadHttpManager.start();
            }
            else
            {
                // Lanzo un dialog para mostrar las validaciones.
                String dialogTitle = this.categoryActivity.getString(R.string.DialogTitleError);
                String dialogBtnAccept = this.categoryActivity.getString(R.string.DialogBtnAccept);

                Dialog md = new Dialog(dialogTitle, dialogMsg, dialogBtnAccept, null, null);
                md.show(this.categoryActivity.getSupportFragmentManager(), null);
            }
        }
    }

    public static void setImage()
    {
        categoryView.getIvCategoryImg().setImageURI(uri);
    }

}
