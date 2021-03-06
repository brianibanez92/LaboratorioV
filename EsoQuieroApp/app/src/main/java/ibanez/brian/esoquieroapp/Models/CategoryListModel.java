package ibanez.brian.esoquieroapp.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brian.ibanez on 27/09/2016.
 */
public class CategoryListModel {

    List<CategoryModel> categories;

    public CategoryListModel()
    {
        this.categories = new ArrayList<CategoryModel>();
    }

    public CategoryListModel(List<CategoryModel> categories)
    {
        this.categories = categories;
    }

    public List<CategoryModel> getCategories()
    {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories)
    {
        this.categories = categories;
    }

}
